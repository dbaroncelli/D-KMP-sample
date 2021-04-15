# D-KMP architecture - official sample

This is the official sample of the **D-KMP architecture**, presenting a simple master/detail app, for both **Android** and **iOS**.

For more info on the D-KMP Architecture, please read the relevant [Medium article](https://danielebaroncelli.medium.com/the-future-of-apps-declarative-uis-with-kotlin-multiplatform-d-kmp-part-1-3-c0e1530a5343).

## Key features of the D-KMP architecture:

- it uses the latest **declarative UI** toolkits: **JetpackCompose** for *Android* and **SwiftUI** for *iOS*
- it **fully shares the ViewModel** (and the *DataLayer*) via **Kotlin MultiPlatform**
- **coroutine scopes** are **cancelled/reinitialized automatically**, based on the current active screens and the app lifecycle (using LifecycleOwner on **Android** and the SwiftUI lifecycle on **iOS**)
- it implements the **MVI pattern** and the *unidirectional data flow*
- it implements the **CQRS pattern**, by providing 2 types of functions to the UI layer: **Events** and **StateProviders**
- it uses Kotlin's **StateFlow** to trigger UI layer recompositions

## Data sources used by this sample:
- **webservices** (using [Ktor Http Client](https://ktor.io/docs/client.html))
- **local db** (using [SqlDelight](https://github.com/cashapp/sqldelight))
- **local settings** (using [MultiplaformSettings](https://github.com/russhwolf/multiplatform-settings))

#### other popular KMP libraries for connecting to different data sources:
- **realtime db** (using [Firestore](https://github.com/GitLiveApp/firebase-kotlin-sdk))
- **graphQL** (using [Apollo GraphQL](https://github.com/apollographql/apollo-android))
- **device bluetooth** (using [Kable]( https://github.com/JuulLabs/kable))
- etc...

## Instructions to write your own D-KMP app:
If you want to create your own app using the D-KMP Architecture, here are some instructions:
### shared code:
#### View Model
  - in the **viewmodel/screens** folder: create a folder for each screen of the app, containing these **4 files** (as shown in the sample app):
    - _screen_**Events.kt**, where the event functions for that screen are defined
    - _screen_**State.kt**, where the data class of the state for that screen is defined
    - _screen_**StateProvider.kt**, where the state provider function for that screen is defined
    - _screen_**StateReducers.kt**, where the state reducers functions (called by the events) for that screen are defined
  - the **5 files** in the **viewmodel** folder (_Events.kt_, _KMPViewModel.kt_, _StateManager.kt_, _StateProviders.kt_, _StateReducers.kt_) don't need to be modified
  - also **KMPViewModelForAndroid.kt** in _androidMain_ and **KMPViewModelForIos.kt** in _iosMain_ don't need to be modified
#### Data Layer
  - in the **datalayer/functions** folder: create a file for each repository function to be called by the KMPViewModel's StateReducers
  - in the **datalayer/objects** folder: create a file for each data class used by the repository functions
  - in the **datalayer/sources** folder: create a folder for each datasource, where the datasource-specific functions (called by the repository functions) are defined
  - the **datalayer/Repository.kt** file should be modified only in case you want to add an extra datasource

### platform-specific code:
#### androidApp
  - the **MainActivity.kt** file should be modified only to change the name of the Android theme
  - the **MyApp.kt** file doesn't need to be modified
  - the **Navigation.kt** file should be modified to define the screens navigation in the app
  - in the **screens** folder: create a folder for each screen of the app, containing all JetpackCompose composables for that screen
#### iosApp
  - the **AppMain.swift** file doesn't need to be modified
  - the **AppObservableObject.swift** file doesn't need to be modified
  - the **ContentView.swift** file should be modified to define which is the startup screen of the app
  - in the **screens** folder: create a folder for each screen of the app, containing all SwiftUI views for that screen

![app_structure](https://user-images.githubusercontent.com/5320104/112217256-b518a500-8c22-11eb-93d5-52298f7b765f.png)
