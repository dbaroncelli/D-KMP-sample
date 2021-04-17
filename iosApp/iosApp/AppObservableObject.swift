//
//  AppObservableObject.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import Foundation
import shared

class AppObservableObject: ObservableObject {
    let model : DKMPViewModel = DKMPViewModel.Factory().getIosInstance()
    var events : Events {
        return self.model.events
    }
    var stateProviders : StateProviders {
        return self.appState.getStateProviders(model: self.model)
    }
    @Published var appState : AppState
    
    init() {
        // "getDefaultAppState" and "onChange" are iOS-only KMPViewModel's extension functions, defined in shared/iosMain
        self.appState = model.getDefaultAppState()
        model.onChange { newState in
            self.appState = newState
            NSLog("D-KMP-SAMPLE: recomposition Index: "+String(newState.recompositionIndex))
        }
    }

}
