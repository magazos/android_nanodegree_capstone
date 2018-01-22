Udacity Android Nanodegree Capstone
====================================

Ultimate Scrobbler is the app that I've created as my capstone project for the Android Nanodegree of Udacity.

This app will scrobble played songs in the device through Spotify to Last.fm. In the future other services like Libre.fm and other players could be supported.

## How to work with the source

In order to unlock the keystore, two variables must be set in `gradle.properties`
```gradle
CAPSTONE_KEYSTORE_PASSWORD
CAPSTONE_KEY_PASSWORD
```

This app uses an API provided by [Last.fm](http://www.last.fm) and it requires an API key and an API secret stored in the following variables in `gradle.properties`
```gradle
LAST_FM_API_KEY
LAST_FM_API_SECRET
```

## Libraries
This app wouldn't be possible without the following libraries

* [Dagger 2](http://google.github.io/dagger/)
* [ButterKnife](http://jakewharton.github.io/butterknife/)
* [Timber](https://github.com/JakeWharton/timber)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [RxBinding](https://github.com/JakeWharton/RxBinding)
* [RxRelay](https://github.com/JakeWharton/RxRelay)
* [Retrofit](http://square.github.io/retrofit/)
* [Retrofit RxJava2 Adapter](https://github.com/JakeWharton/retrofit2-rxjava2-adapter)
* [OkHttp](http://square.github.io/okhttp/)
* [Moshi](https://github.com/square/moshi)
* [Moshi Lazy Adapters](https://github.com/serj-lotutovici/moshi-lazy-adapters)
* [Picasso](http://square.github.io/picasso/)
* [AutoValue](https://github.com/google/auto/tree/master/value)
* [AutoValue Moshi](https://github.com/rharter/auto-value-moshi)
* [RxPreferences](https://github.com/f2prateek/rx-preferences)
* [Three Ten BP](https://github.com/ThreeTen/threetenbp)
* [Three Ten ABP](https://github.com/JakeWharton/ThreeTenABP)
* [Material Design Dimens](https://github.com/DmitryMalkovich/material-design-dimens)
* [Indicator SeekBar](https://github.com/warkiz/IndicatorSeekBar)
* [Schematic](https://github.com/SimonVT/schematic)
* [ChipCloud](https://github.com/fiskurgit/ChipCloud)
* [Flexbox](https://github.com/google/flexbox-layout)
* [Firebase Job Dispatcher](https://github.com/firebase/firebase-jobdispatcher-android)
* [Gradle Git](https://github.com/ajoberstar/gradle-git)
* [Gradle Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin)
* [Dexcount Gradle Plugin](https://github.com/KeepSafe/dexcount-gradle-plugin)
* [Apt](https://github.com/tbroyer/gradle-apt-plugin)
* [OkLog](https://github.com/simonpercic/OkLog)
* [Chuck](https://github.com/jgilfelt/chuck)
* [Stetho](http://facebook.github.io/stetho/)
* [LeakCanary](https://github.com/square/leakcanary)
* [RxSealedUnions2](https://github.com/pakoito/RxSealedUnions2)

License
=======
```
  Copyright 2018 Borja Quevedo
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
    http://www.apache.org/licenses/LICENSE-2.0
    
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```
