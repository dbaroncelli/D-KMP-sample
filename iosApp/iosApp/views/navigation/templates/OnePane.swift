//
//  OnePane.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 24/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


extension Navigation {
    
    @ViewBuilder func onePane(_ navState: LocalNavigationState) -> some View {
        
        NavigationStack() {
            let _ = NSLog("onePane latest path URI "+navState.level1ScreenIdentifier.URI)
            self.screenPicker(requestedSId: navState.level1ScreenIdentifier, navState: navState)
                .navigationDestination(for: ScreenIdentifier.self) { sId in
                    let _ = NSLog("destination screen: "+sId.URI)
                    self.screenPicker(requestedSId: sId, navState: navState)
                }
        }
        .toolbar {
            ToolbarItemGroup(placement: .bottomBar) {
                Level1ButtonBar()
            }
        }
        
    }
    
}


struct OnePane: View {
    @EnvironmentObject var appObj: AppObservableObject
    
    var body: some View {
        NavigationStack(path: $appObj.localNavigationState.path) {
            appObj.dkmpNav.screenPicker(requestedSId: appObj.localNavigationState.level1ScreenIdentifier, navState: appObj.localNavigationState)
                .navigationDestination(for: ScreenIdentifier.self) { sId in
                    let _ = appObj.dkmpNav.navigateToScreen(screenIdentifier: sId)
                    appObj.dkmpNav.screenPicker(requestedSId: sId, navState: appObj.localNavigationState)
                }
        }
        .toolbar {
            ToolbarItemGroup(placement: .bottomBar) {
                Level1ButtonBar()
            }
        }
    }
}
