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

    @ViewBuilder func onePane(_ level1ScreenIdentifier: ScreenIdentifier) -> some View {
        NavigationView {
            self.screenPicker(level1ScreenIdentifier)
                .navigationBarTitle(getTitle(screenIdentifier: level1ScreenIdentifier), displayMode: .inline)
        }
        .toolbar {
            ToolbarItemGroup(placement: .bottomBar) {
                if level1ScreenIdentifier.URI == currentLevel1ScreenIdentifier.URI {
                    self.level1ButtonBar(selectedTab: self.currentLevel1ScreenIdentifier)
                }
            }
        }
        .navigationViewStyle(StackNavigationViewStyle())
    }
    
}
