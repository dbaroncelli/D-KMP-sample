# D-KMP architecture - official sample

This is the official sample of the **D-KMP architecture**, presenting a simple master/detail app, for **Android**, **iOS** and **Desktop**.<br>
Only the business logic is written (and shared) in Kotlin. The UI of each platform is kept native (_SwiftUI_ in case of **iOS**).<br>
<br>
**UPDATE MAY 2025**<br>
_Please notice that the D-KMP architecture was conceived in 2020, when Compose Multiplatform still didn't exist.<br>
Since the May 2025, [Compose Multiplatform is stable and production-ready for **Android**, **iOS** and **Desktop**](https://blog.jetbrains.com/kotlin/2025/05/compose-multiplatform-1-8-0-released-compose-multiplatform-for-ios-is-stable-and-production-ready/), so writing in Kotlin both the UI and the business logic has become the preferred KMP solution.<br>
The full documentation on how to build KMP apps using Compose Multiplaform, can be found [here](https://www.jetbrains.com/compose-multiplatform/).<br>
If you are still interested in D-KMP, you can carry on reading._<br>

<img width="500" src="https://user-images.githubusercontent.com/5320104/219511497-0c494b86-2716-420d-bbc7-fe50c552667a.png"></img>

## Key features of the D-KMP architecture:

- it uses the latest **declarative UI** toolkits: **Compose** for *Android* and **SwiftUI** for *iOS*
- it **fully shares the ViewModel** (including **navigation logic** and **data layer**) via **Kotlin MultiPlatform**
- **coroutine scopes** are **cancelled/reinitialized automatically**, based on the current active screens and the app lifecycle (using LifecycleObserver on **Android** and the SwiftUI lifecycle on **iOS**)
- it implements the **MVI pattern** and the *unidirectional data flow*
- it implements the **CQRS pattern**, by providing **Command** functions (via _Events_ and _Navigation_) and **Query** functions (via _StateProviders_)
- it uses Kotlin's **StateFlow** to trigger UI layer recompositions
- the **navigation state** is processed in the **shared code**, and then exposed to the **UI layer**:
  - on **SwiftUI** it seamlessly integrates with the new iOS 16 navigation patterns (_NavigationStack_ and/or _NavigationSplitView_)
  - on **Compose** it's a "remembered" data class which works on any platform (unlike _Jetpack Navigation_, which only works on Android)

_you can find more info on these articles:_
- _[D-KMP sample now leverages iOS 16 navigation](https://danielebaroncelli.medium.com/d-kmp-sample-now-leverages-ios-16-navigation-cebbb81ba2e7) (february 2023)_
- _[The future of apps:
Declarative UIs with Kotlin MultiPlatform (D-KMP)](https://danielebaroncelli.medium.com/the-future-of-apps-declarative-uis-with-kotlin-multiplatform-d-kmp-part-1-3-c0e1530a5343) (november 2020)_

## Data sources used by this sample:
- **webservices** (using [Ktor Http Client](https://ktor.io/docs/client.html))
- **local db** (using [SqlDelight](https://github.com/cashapp/sqldelight))
- **local settings** (using [MultiplaformSettings](https://github.com/russhwolf/multiplatform-settings))

#### these are other data sources, not used by this sample, for which popular KMP libraries exist:
- **realtime db** (using [Firestore](https://github.com/GitLiveApp/firebase-kotlin-sdk))
- **graphQL** (using [Apollo GraphQL](https://github.com/apollographql/apollo-android))
- **device bluetooth** (using [Kable]( https://github.com/JuulLabs/kable))
- etc...

## Instructions to write your own D-KMP app:
If you want to create your own app using the D-KMP Architecture, here are the instructions you need:
<br>

### SHARED CODE:

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

<br><br>

### PLATFORM-SPECIFIC CODE:

### Android
<img width="352" alt="Schermata 2021-06-26 alle 16 54 32" src="https://user-images.githubusercontent.com/5320104/123515260-f0e65f00-d696-11eb-9ba5-9d44faa58563.png"></img>

<img width="390" alt="Schermata 2021-06-26 alle 17 03 13" src="https://user-images.githubusercontent.com/5320104/123515523-0d36cb80-d698-11eb-9be9-257e1603174d.png"></img>

  - :white_check_mark: the **App.kt** file doesn't need to be modified
  - :white_check_mark: the **MainActivity.kt** file doesn't need to be modified
  - **The composables are used by both Android and Desktop apps:**
    - :hammer_and_wrench: the **Level1BottomBar.kt**  and **Level1NavigationRail.kt** files in the **navigation/bars** folder should be modified to custom the Navigation bars items
    - :white_check_mark: the **TopBar.kt** file in the **navigation/bars** folder doesn't need to be modified
    - :white_check_mark: the **OnePane.kt**  and **TwoPane.kt** files in the **navigation/templates** folder don't need to be modified
    - :white_check_mark: the **HandleBackButton.kt** file in the **navigation** folder doesn't need to be modified
    - :white_check_mark: the **Router.kt** file in the **navigation** folder doesn't need to be modified
    - :hammer_and_wrench: in the **ScreenPicker.kt** file in the **navigation** folder, you should define the screen composables in your app
    - :hammer_and_wrench: in the **screens** folder: create a folder for each screen of the app, containing all composables for that screen
    - :white_check_mark: the **MainComposable.kt** file doesn't need to be modified
<br>

### iOS

<img width="307" alt="ios-files" src="https://user-images.githubusercontent.com/5320104/219498843-a2db7d84-6bd8-40f9-a730-79732d320d8a.png"></img>
  - :hammer_and_wrench: the **Level1BottomBar.swift**  and **Level1NavigationRail.swift** files in the **composables/navigation/bars** folder should be modified to custom the Navigation bars items
  - :white_check_mark: the **TopBar.swift** file in the **composables/navigation/bars** folder doesn't need to be modified
  - :white_check_mark: the **OnePane.swift**  and **TwoPane.swift** files in the **composables/navigation/templates** folder don't need to be modified
  - :white_check_mark: the **Router.swift** file in the **composables/navigation** folder doesn't need to be modified
  - :hammer_and_wrench: in the **ScreenPicker.swift** file in the **views/navigation** folder, you should define the screen composables in your app
  - :hammer_and_wrench: in the **views/screens** folder: create a folder for each screen of the app, containing all SwiftUI views for that screen
  - :white_check_mark: the **App.swift** file doesn't need to be modified
  - :white_check_mark: the **AppObservableObject.swift** file doesn't need to be modified
<br>

### Desktop
<img width="298" alt="Schermata 2021-06-26 alle 16 54 15" src="https://user-images.githubusercontent.com/5320104/123515803-3efc6200-d699-11eb-9703-4ca4850c89d9.png"></img>

<img width="390" alt="Schermata 2021-06-26 alle 17 03 13" src="https://user-images.githubusercontent.com/5320104/123515523-0d36cb80-d698-11eb-9be9-257e1603174d.png"></img>

  - :white_check_mark: the **main.kt** file doesn't need to be modified
  - **The composables are used by both Android and Desktop apps:**
    - look at the description on the [Android section](#android) above
<br>
