//
//  AppObservableObject.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//
//

import Foundation
import SwiftUI
import shared

class AppObservableObject: ObservableObject {
    let model : DKMPViewModel = DKMPViewModel.Factory().getIosInstance()
    var events : Events {
        return self.model.events
    }
    var stateProviders : StateProviders {
        return self.appState.getStateProviders(model: self.model)
    }
    var dkmpNav : DKMPNavigation
    @Published var appState : AppState

    
    init() {
        self.dkmpNav = DKMPNavigation(model: model)
        // "getDefaultAppState" and "onChange" are iOS-only DKMPViewModel's extension functions, defined in shared/iosMain
        self.appState = model.getDefaultAppState()
        model.onChange { newState in
            self.appState = newState
            NSLog("D-KMP-SAMPLE: recomposition Index: "+String(newState.recompositionIndex))
        }
    }
    
    @ViewBuilder func getView(screen: Screen, instanceId: String? = nil) -> some View {
        self.getViewInstance(screen: screen, instanceId: instanceId)
            .onAppear { self.dkmpNav.enterScreen(screen: screen, instanceId: instanceId) }
            .onDisappear { self.dkmpNav.popScreen() }
    }


}
