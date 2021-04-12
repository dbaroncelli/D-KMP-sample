//
//  AppViewModel.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import Foundation
import shared

class AppViewModel: ObservableObject {
    private let coreModel : KMPViewModel = KMPViewModel.Factory().getIosInstance()
    var events : Events {
        return self.coreModel.events
    }
    var stateProviders : StateProviders {
        return self.appState.getStateProviders(model: self.coreModel)
    }
    @Published var appState : AppState
    
    init() {
        // "getDefaultAppState" and "onChange" are iOS-only KMPViewModel's extension functions, defined in shared/iosMain
        self.appState = coreModel.getDefaultAppState()
        coreModel.onChange { newState in
            self.appState = newState
            NSLog("D-KMP-SAMPLE: recomposition Index: "+String(newState.recompositionIndex))
        }
    }

}
