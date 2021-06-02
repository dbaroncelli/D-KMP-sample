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

    @ViewBuilder func twoPane(_ level1ScreenIdentifier: ScreenIdentifier) -> some View {
        
        let navigationLevelsMap = getNavigationLevelsMap(level1ScreenIdentifier: level1ScreenIdentifier)!
        NavigationView {
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
                .frame(width: 400)
                
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
        }
        .navigationViewStyle(StackNavigationViewStyle())
        
    }
    
}
