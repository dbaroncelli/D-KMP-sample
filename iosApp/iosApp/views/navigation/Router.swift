//
//  Router.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 04/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


struct Router: View {
    @EnvironmentObject var appObj: AppObservableObject
    
    var body: some View {
        
        let isIPad = UIDevice.current.userInterfaceIdiom == .pad
        
        let level1ScreenIdentifiers = getAllLevel1ScreenIdentifiers()
        let level1ScreenIdentifiersWithState = appObj.dkmpNav.stateManager.verticalNavigationLevels.map{ ($0.value as! Dictionary<Int,ScreenIdentifier>)[1]! }
        
        
        if !isIPad {
            ZStack {
                ForEach(level1ScreenIdentifiers, id: \.self.URI) { screenIdentifier in
                    if ( level1ScreenIdentifiersWithState.contains{ $0.URI == screenIdentifier.URI } ) {
                        OnePane(level1ScreenIdentifier: screenIdentifier)
                            .opacity(screenIdentifier.URI == appObj.dkmpNav.stateManager.currentLevel1ScreenIdentifier?.URI ? 1 : 0)
                    } else {
                        EmptyView().opacity(0)
                    }
                }
            }
            .toolbarColor(backgroundUIColor: UIColor(customBgColor), tintUIColor: .white)
            .toolbar {
                ToolbarItemGroup(placement: .bottomBar) {
                    Level1ButtonBar()
                }
            }
        } else {
            ZStack {
                ForEach(level1ScreenIdentifiers, id: \.self.URI) { screenIdentifier in
                    if ( level1ScreenIdentifiersWithState.contains{ $0.URI == screenIdentifier.URI } ) {
                        TwoPane(level1ScreenIdentifier: screenIdentifier)
                            .opacity(screenIdentifier.URI == appObj.dkmpNav.stateManager.currentLevel1ScreenIdentifier?.URI ? 1 : 0)
                    } else {
                        EmptyView().opacity(0)
                    }
                }
            }
        }
        
    }
    
}



func getAllLevel1ScreenIdentifiers() -> [ScreenIdentifier] {
    return Level1Navigation.allCases.map { l1Navigation in
        l1Navigation.screenIdentifier
    }
}





extension Navigation {
    
    func navigate(_ screen: Screen, _ params: ScreenParams?) -> ScreenIdentifier {
        return ScreenIdentifier.Factory().get(screen: screen, params: params)
    }

    func navigateByLevel1Menu(_ appObj: AppObservableObject, level1Navigation: Level1Navigation) {
        selectLevel1Navigation(level1ScreenIdentifier: level1Navigation.screenIdentifier) // shared navigationState is updated
        appObj.localNavigationState = navigationState // update localNavigationState
    }
    
}

