//
//  AppObservableObject.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//
//

import SwiftUI
import shared

class AppObservableObject: ObservableObject {
    let model : DKMPViewModel = DKMPViewModel.Factory().getIosInstance()
    var dkmpNav : Navigation {
        return self.appState.getNavigation(model: self.model)
    }
    @Published var appState : AppState
    @Published var localNavigationState : LocalNavigationState


    init() {
        // "getDefaultAppState" and "onChange" are iOS-only DKMPViewModel's extension functions, defined in shared/iosMain
        self.appState = model.getDefaultAppState()
        self.localNavigationState = model.navigation.getStartNavigationState()
        model.onChange { newState in
            self.appState = newState
            NSLog("APP STATE RECOMPOSITION: index #"+String(newState.recompositionIndex))
        }
    }


}
