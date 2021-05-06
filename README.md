# D-KMP architecture - official sample

This is the official sample of the **D-KMP architecture**, presenting a simple master/detail app, for both **Android** and **iOS**.

For more info on the D-KMP Architecture, please read the relevant [Medium article](https://danielebaroncelli.medium.com/the-future-of-apps-declarative-uis-with-kotlin-multiplatform-d-kmp-part-1-3-c0e1530a5343).

<img width="400" src="https://user-images.githubusercontent.com/5320104/112217256-b518a500-8c22-11eb-93d5-52298f7b765f.png"></img>

## Key features of the D-KMP architecture:

- it uses the latest **declarative UI** toolkits: **JetpackCompose** for *Android* and **SwiftUI** for *iOS*
- it **fully shares the ViewModel** (and the *DataLayer*) via **Kotlin MultiPlatform**
- **coroutine scopes** are **cancelled/reinitialized automatically**, based on the current active screens and the app lifecycle (using LifecycleObserver on **Android** and the SwiftUI lifecycle on **iOS**)
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
If you want to create your own app using the D-KMP Architecture, here are the instructions you need:
### shared code:
#### View Model
<img width="361" src="https://user-images.githubusercontent.com/5320104/117335160-204ccc80-ae9b-11eb-9df0-e90168e1a0eb.png">
  - :hammer_and_wrench: in the **viewmodel/screens** folder: create a folder for each screen of the app, containing these **4 files** (as shown in the sample app structure above):
    - _screen_**Events.kt**, where the event functions for that screen are defined
    - _screen_**State.kt**, where the data class of the state for that screen is defined
    - _screen_**StateProvider.kt**, where the state provider function for that screen is defined
    - _screen_**StateReducers.kt**, where the state reducers functions (called by the events) for that screen are defined
  - :hammer_and_wrench: in the **ScreenEnum.kt** file in the **viewmodel** folder, you should define the enum with all screens in your app
  - :white_check_mark: the other **5 files** in the **viewmodel** folder (_DKMPViewModel.kt_, _Events.kt_, _StateManager.kt_, _StateProviders.kt_, _StateReducers.kt_) don't need to be modified
  - :white_check_mark: also **DKMPViewModelForAndroid.kt** in _androidMain_ and **DKMPViewModelForIos.kt** in _iosMain_ don't need to be modified
#### Data Layer
<img width="322" src="https://user-images.githubusercontent.com/5320104/114903196-d7af6f80-9e16-11eb-823c-8ef9e2039ab6.png"></img>
  - :hammer_and_wrench: in the **datalayer/functions** folder: create a file for each repository function to be called by the ViewModel's StateReducers
  - :hammer_and_wrench: in the **datalayer/objects** folder: create a file for each data class used by the repository functions
  - :hammer_and_wrench: in the **datalayer/sources** folder: create a folder for each datasource, where the datasource-specific functions (called by the repository functions) are defined
  - :white_check_mark: the **datalayer/Repository.kt** file should be modified only in case you want to add an extra datasource

### platform-specific code:
#### androidApp

<img width="262" src="https://user-images.githubusercontent.com/5320104/117334809-b92f1800-ae9a-11eb-8f51-9a86a05e1ec5.png"></img>
  - :hammer_and_wrench: in the **screens** folder: create a folder for each screen of the app, containing all JetpackCompose composables for that screen
  - :hammer_and_wrench: the **ComposablesDefinition.kt** file should be modified to define the screen composables in the app
  - :white_check_mark: the **DKMPApp.kt** file doesn't need to be modified
  - :white_check_mark: the **DKMPNavigation.kt** file doesn't need to be modified
  - :white_check_mark: the **MainActivity.kt** file doesn't need to be modified
#### iosApp

<img width="285" src="https://user-images.githubusercontent.com/5320104/117334463-50e03680-ae9a-11eb-9707-09408661f068.png"></img>
  - :hammer_and_wrench: in the **screens** folder: create a folder for each screen of the app, containing all SwiftUI views for that screen
  - :white_check_mark: the **AppObservableObject.swift** file doesn't need to be modified
  - :white_check_mark: the **DKMPApp.swift** file doesn't need to be modified
  - :white_check_mark: the **DKMPNavigation.swift** file doesn't need to be modified
  - :hammer_and_wrench: the **ViewsDefinition.swift** file should be modified to define the screen views the app
#### webApp (coming soon!)
  - waiting for **Compose for Web** to become available (it recently hit a [great milestone](https://twitter.com/shikasd_/status/1379757917893722114))
