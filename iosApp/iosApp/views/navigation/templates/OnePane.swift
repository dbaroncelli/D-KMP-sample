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

    @ViewBuilder func onePane(_ screenIdentifier: ScreenIdentifier) -> some View {
        
            NavigationView {
                self.screenPicker(screenIdentifier)
                    .opacity(screenIdentifier.URI == self.currentScreenIdentifier.URI ? 1 : 0)
                    .navigationBarItems(leading: self.backButton() )
                    .navigationBarTitle(title, displayMode: .inline)
                    .toolbar {
                        ToolbarItemGroup(placement: .bottomBar) {
                            if (currentScreenIdentifier.screen.navigationLevel == 1) {
                                self.level1ButtonBar(selectedTab: currentScreenIdentifier)
                            } else {
                                EmptyView()
                            }
                        }
                    }
            }
        
    }
    
    
}
