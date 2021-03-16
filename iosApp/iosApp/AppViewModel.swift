//
//  AppViewModel.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import Foundation
import shared

class AppViewModel: ObservableObject {
    let coreModel : KMPViewModel = KMPViewModel()
    @Published var appState : AppState
    
    init() {
        // "getDefaultAppState" and "onChange" are iOS-only KMPViewModel's extension functions, defined in shared/iosMain
        self.appState = coreModel.getDefaultAppState()
        coreModel.onChange { newState in
            self.appState = newState
        }
    }
}
