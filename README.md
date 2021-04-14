# D-KMP architecture - official sample

This is the official sample of the **D-KMP architecture**, presenting a simple master/detail app, for both **Android** and **iOS**.

For more info on the D-KMP Architecture, please read the relevant [Medium article](https://danielebaroncelli.medium.com/the-future-of-apps-declarative-uis-with-kotlin-multiplatform-d-kmp-part-1-3-c0e1530a5343).

## Key features of the D-KMP architecture:

- it uses the latest **declarative UI** toolkits: **JetpackCompose** for *Android* and **SwiftUI** for *iOS*
- it **fully shares the ViewModel** (and the *DataLayer*) via **Kotlin MultiPlatform**
- **coroutine scopes** are **cancelled/reinitialized automatically**, based on the current active screens and the app lifecycle (using LifecycleOwner on **Android** and the SwiftUI lifecycle on **iOS**)
- it implements the **MVI pattern** and the *unidirectional data flow*
- it uses Kotlin's **StateFlow** to trigger UI layer recompositions
- it implements the **CQRS pattern**, by providing 2 types of functions to the UI layer: **Events** and **StateProviders**

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
- the **5 files** in the ViewModel root folder don't need to be modified. They can be used as they are:
  - _Events.kt_
  - _KMPViewModel.kt_
  - _StateManager.kt_
  - _StateProviders.kt_
  - _StateReducers.kt_
- the _Repository.kt_ file in the DataLayer root folder should be modified only in case you want to add an extra data source
- these are the app-specific files in the shared code:
  - in the **viewmodel**, create a folder for each screen in _viewmodel/screens_, containing these **4 files** (as shown in the sample app):
    - _screen_**Events**, where the event functions are defined
    - _screen_**State**, where the screen state data class is defined
    - _screen_**StateProvider**, where the screen state provider function is defined
    - _screen_**StateReducers**, where the screen state reducers functions (called by the events) are defined
  - in the **datalayer**:
    - in the **functions** folder: a file for each repository function to be called by the StateReducers
    - in the **objects** folder: a file for each data class used by the repository functions
    - in the **sources** folder: create a folder for each datasource, where the datasource-specific functions called by the repository functions are defined

![app_structure](https://user-images.githubusercontent.com/5320104/112217256-b518a500-8c22-11eb-93d5-52298f7b765f.png)
