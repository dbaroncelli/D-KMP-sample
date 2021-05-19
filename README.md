# D-KMP architecture - official sample

This is the official sample of the **D-KMP architecture**, presenting a simple master/detail app, for both **Android** and **iOS**. Planning to publish the **Web** and **Desktop** version soon too.

For more info on the D-KMP Architecture, please read the relevant [Medium article](https://danielebaroncelli.medium.com/the-future-of-apps-declarative-uis-with-kotlin-multiplatform-d-kmp-part-1-3-c0e1530a5343).


<img width="500" src="https://user-images.githubusercontent.com/5320104/118643793-4c424900-b7dd-11eb-85c7-1f55b06da6aa.png"></img>

## Key features of the D-KMP architecture:

- it uses the latest **declarative UI** toolkits: **Compose** for *Android* and **SwiftUI** for *iOS*
- it **fully shares the ViewModel** (including **navigation logic** and **data layer**) via **Kotlin MultiPlatform**
- **coroutine scopes** are **cancelled/reinitialized automatically**, based on the current active screens and the app lifecycle (using LifecycleObserver on **Android** and the SwiftUI lifecycle on **iOS**)
- it implements the **MVI pattern** and the *unidirectional data flow*
- it implements the **CQRS pattern**, by providing **Command** functions (via _Events_ and _Navigation_) and **Query** functions (via _StateProviders_)
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

<img width="272" src="https://user-images.githubusercontent.com/5320104/118641163-194a8600-b7da-11eb-9bdd-b59e34392d36.png"></img>
  - :hammer_and_wrench: in the **viewmodel/screens** folder: create a folder for each screen of the app, containing these **3 files** (as shown in the sample app structure above):
    - _screen_**Events.kt**, where the event functions for that screen are defined
    - _screen_**Init.kt**, where the initialization settings for that screen are defined
    - _screen_**State.kt**, where the data class of the state for that screen is defined
  - :hammer_and_wrench: in the **NavigationSettings.kt** file in the **screens** folder, you should define your level 1 navigation and other settings
  - :hammer_and_wrench: in the **ScreenEnum.kt** file in the **screens** folder, you should define the enum with all screens in your app
  - :white_check_mark: the **ScreenInitSettings.kt** file in the **screens** folder doesn't need to be modified
  - :white_check_mark: the **6 files** in the **viewmodel** folder (_DKMPViewModel.kt_, _Events.kt_, _Navigation.kt_, _ScreenIdentifier.kt_, _StateManager.kt_, _StateProviders.kt_) don't need to be modified
  - :white_check_mark: also **DKMPViewModelForAndroid.kt** in _androidMain_ and **DKMPViewModelForIos.kt** in _iosMain_ don't need to be modified


#### Data Layer
<img width="322" src="https://user-images.githubusercontent.com/5320104/114903196-d7af6f80-9e16-11eb-823c-8ef9e2039ab6.png"></img>
  - :hammer_and_wrench: in the **datalayer/functions** folder: create a file for each repository function to be called by the ViewModel's StateReducers
  - :hammer_and_wrench: in the **datalayer/objects** folder: create a file for each data class used by the repository functions
  - :hammer_and_wrench: in the **datalayer/sources** folder: create a folder for each datasource, where the datasource-specific functions (called by the repository functions) are defined
  - :white_check_mark: the **datalayer/Repository.kt** file should be modified only in case you want to add an extra datasource


### platform-specific code:

#### androidApp

<img width="227" src="https://user-images.githubusercontent.com/5320104/118641954-100de900-b7db-11eb-9ac3-218b54613549.png"></img>
  - :white_check_mark: the **Router.kt** file in the **composables/navigation** folder doesn't need to be modified
  - :hammer_and_wrench: in the **ScreenPicker.kt** file in the **composables/navigation** folder, you should define the screen composables in your app
  - :hammer_and_wrench: in the **composables/screens** folder: create a folder for each screen of the app, containing all composables for that screen
  - :white_check_mark: the **MainComposable.kt** file in the **composables** folder doesn't need to be modified
  - :white_check_mark: the **DKMPApp.kt** file doesn't need to be modified
  - :white_check_mark: the **MainActivity.kt** file doesn't need to be modified

#### iosApp

<img width="260" src="https://user-images.githubusercontent.com/5320104/118642505-bce86600-b7db-11eb-9af0-75d310ba24b4.png"></img>
  - :white_check_mark: the **Router.swift** file in the **views/navigation** folder doesn't need to be modified
  - :hammer_and_wrench: in the **ScreenPicker.swift** file in the **views/navigation** folder, you should define the screen composables in your app
  - :hammer_and_wrench: in the **views/screens** folder: create a folder for each screen of the app, containing all SwiftUI views for that screen
  - :white_check_mark: the **MainView.swift** file doesn't need to be modified
  - :white_check_mark: the **AppObservableObject.swift** file doesn't need to be modified
  - :white_check_mark: the **DKMPApp.swift** file doesn't need to be modified

#### webApp (coming soon!)
  - we'll be adding also the web version, using [Jetpack Compose for Web](https://blog.jetbrains.com/kotlin/2021/05/technology-preview-jetpack-compose-for-web/)
