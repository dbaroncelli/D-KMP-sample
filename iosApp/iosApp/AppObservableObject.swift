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
    var screenStates: [ScreenIdentifier:ObservableScreenState] = [:]
    var dkmpNav : Navigation {
        return self.model.navigation
    }
    @Published var localNavigationState : NavigationState
    @Published var screenStates = [ScreenIdentifier: any ScreenState]()

    
    init() {
        self.localNavigationState = model.navigation.navigationState
    }

    @MainActor // collecting the screen's Kotlin StateFlow (seamlessly, thanks to the SKIE plugin)
    func collectScreenStateFlow(sID: ScreenIdentifier) async {
        for await state in model.navigation.stateProvider.getScreenStateFlow(screenIdentifier: sID) {
            self.screenStates[sID] = state
        }
        return screenStates[screenIdentifier]!
    }
    
    func getScreenState(sID: ScreenIdentifier) -> ScreenState {
        return screenStates[sID] ?? model.navigation.stateProvider.getScreenStateFlow(screenIdentifier: sID).value
    }

}
