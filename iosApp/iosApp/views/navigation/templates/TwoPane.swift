//
//  TwoPane.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 24/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


extension Navigation {
    
    @ViewBuilder func twoPane(_ navState: LocalNavigationState) -> some View {
        
        NavigationView {
            /*
            HStack {
                VStack {
                    level1NavigationRail(selectedTab: navigationLevelsMap[1]!)
                }
                .padding(.leading,30)
                .frame(maxWidth: 120, maxHeight: .infinity)
                .background(customBgColor)
                .ignoresSafeArea()
                
                VStack {
                    self.screenPicker(navigationLevelsMap[1]!)
                }
                .frame(width: 420)
                
                VStack {
                    if navigationLevelsMap[2] == nil {
                        twoPaneDefaultDetail(navigationLevelsMap[1]!)
                    } else {
                        self.screenPicker(navigationLevelsMap[2]!)
                            .padding(20)
                    }
                }
                .frame(maxWidth: .infinity)
            }
            .frame(maxWidth: .infinity)
            .navigationBarTitle(getTitle(screenIdentifier: navigationLevelsMap[2] ?? navigationLevelsMap[1]!), displayMode: .inline)
             */
        }
        .navigationViewStyle(StackNavigationViewStyle())
        
    }
    
    
}

struct TwoPane: View {
    @EnvironmentObject var appObj: AppObservableObject
    
    var body: some View {
        NavigationStack(path: $appObj.localNavigationState.path) {
            appObj.dkmpNav.screenPicker(requestedSId: appObj.localNavigationState.level1ScreenIdentifier, navState: appObj.localNavigationState)
                .navigationDestination(for: ScreenIdentifier.self) { sId in
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


func twoPaneMasterScreen(_ navState: LocalNavigationState) -> ScreenIdentifier {
    if (navState.path.count > 1) {
        return navState.path[navState.path.count-2]
    } else {
        return navState.level1ScreenIdentifier
    }
}

func twoPaneDetailScreen(_ navState: LocalNavigationState) -> ScreenIdentifier? {
    if (navState.path.count > 1) {
        return navState.path.last
    } else {
        return nil
    }
}
