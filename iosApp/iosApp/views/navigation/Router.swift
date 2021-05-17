//
//  Router.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 04/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

extension Navigation {
    
    @ViewBuilder func router(_ stateProvider: StateProvider,_ events: Events) -> some View {
        self.screenPicker(self.currentScreenIdentifier, stateProvider, events)
    }
}

extension Navigation {
    
    func navigate(_ screen: Screen, _ params: ScreenParams?) {
        let sId = getScreenIdentifier(screen, params)
        navigateByScreenIdentifier(screenIdentifier: sId)
    }
    
}

func getScreenIdentifier(_ screen : Screen, _ params: ScreenParams? = nil) -> ScreenIdentifier {
    return ScreenIdentifier(screen: screen, params: params, paramsAsString: nil)
}



/*
 
add new NavigationLink here
 
 */
