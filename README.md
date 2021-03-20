# D-KMP architecture - official sample

This is the official sample of the D-KMP architecture, which is described in this [Medium article](https://danielebaroncelli.medium.com/the-future-of-apps-declarative-uis-with-kotlin-multiplatform-d-kmp-part-1-3-c0e1530a5343)

The sample presents a simple master/detail app, for both Android and iOS, following the D-KMP architecture.

These are the main characterics of the D-KMP architecture:

- it uses the latest UI declarative toolkits: JetpackCompose for Android and SwiftUI for iOS
- it provides just two types of functions to the UI layer: Event functions and StateProvider functions
- it fully shares the ViewModel and the DataLayer via Kotlin MultiPlatform
- it implements the MVI pattern and the unidirectional data flow
- it uses Kotlin's StateFlow for the observable mechanism

The sample only use 2 types of sources in the DataLayer:
- webservices (using [Ktor Http Client](https://ktor.io/docs/client.html))
- local settings (using [MultiplaformSettings](https://github.com/russhwolf/multiplatform-settings))
you could use more source:
- local db (using [SqlDelight](https://github.com/cashapp/sqldelight))
- realtime db (using [Firestore](https://github.com/GitLiveApp/firebase-kotlin-sdk))
- graphQL (using [SqlDelight](https://github.com/apollographql/apollo-android))

![app_structure](https://user-images.githubusercontent.com/5320104/111846880-04e82b00-8908-11eb-983b-133387d0fb6a.png)

