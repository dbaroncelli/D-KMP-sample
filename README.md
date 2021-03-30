# D-KMP architecture - official sample

This is the official sample of the **D-KMP architecture**, presenting a simple master/detail app, for both **Android** and **iOS**.

For more info on the D-KMP Architecture, please read the relevant [Medium article](https://danielebaroncelli.medium.com/the-future-of-apps-declarative-uis-with-kotlin-multiplatform-d-kmp-part-1-3-c0e1530a5343).

## Key features of the D-KMP architecture:

- it uses the latest **declarative UI** toolkits: **JetpackCompose** for *Android* and **SwiftUI** for *iOS*
- it **fully shares the ViewModel** (and the *DataLayer*) via **Kotlin MultiPlatform**
- it implements the **MVI pattern** and the *unidirectional data flow*
- it uses Kotlin's **StateFlow** for the multi-platform observable mechanism
- it implements the **CQRS pattern**, by providing 2 types of functions to the UI layer: **Events** and **StateProviders**

## Data sources used by this sample:
- **webservices** (using [Ktor Http Client](https://ktor.io/docs/client.html))
- **local settings** (using [MultiplaformSettings](https://github.com/russhwolf/multiplatform-settings))

#### other popular KMP libraries for connecting to different data sources:
- **local db** (using [SqlDelight](https://github.com/cashapp/sqldelight))
- **realtime db** (using [Firestore](https://github.com/GitLiveApp/firebase-kotlin-sdk))
- **graphQL** (using [Apollo GraphQL](https://github.com/apollographql/apollo-android))
- **device bluetooth** (using [Kable]( https://github.com/JuulLabs/kable))
- etc...

![app_structure](https://user-images.githubusercontent.com/5320104/112217256-b518a500-8c22-11eb-93d5-52298f7b765f.png)
