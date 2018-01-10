package com.github.niltsiar.ultimatescrobbler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import timber.log.Timber;

public class LinkingDebugTree extends Timber.DebugTree {

    private static final int CALL_STACK_INDEX = 4;
    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length <= CALL_STACK_INDEX) {
            throw new IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?");
        }
        String newMessage = message;
        for (int initialCallStackIndex = CALL_STACK_INDEX; initialCallStackIndex < stackTrace.length; initialCallStackIndex++) {
            String clazz = extractClassName(stackTrace[initialCallStackIndex]);
            if (!"Timber".equals(clazz)) {
                int lineNumber = stackTrace[initialCallStackIndex].getLineNumber();
                newMessage = clazz + ".java:" + lineNumber + " - " + message;
                break;
            }
        }
        super.log(priority, tag, newMessage, t);
    }

    /**
     * Extract the class name without any anonymous class suffixes (e.g., {@code Foo$1}
     * becomes {@code Foo}).
     */
    private String extractClassName(StackTraceElement element) {
        String tag = element.getClassName();
        Matcher m = ANONYMOUS_CLASS.matcher(tag);
        if (m.find()) {
            tag = m.replaceAll("");
        }
        return tag.substring(tag.lastIndexOf('.') + 1);
    }
}
