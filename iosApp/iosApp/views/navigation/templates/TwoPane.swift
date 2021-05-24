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

    @ViewBuilder func twoPane(_ width: CGFloat) -> some View {
        
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
                .frame(width: 330)
                
                VStack {
                    if navigationLevelsMap[2] == nil {
                        CountriesListTwoPaneDefaultDetail()
                    } else {
                        self.screenPicker(navigationLevelsMap[2]!)
                            .padding(20)
                    }
                }
                .frame(maxWidth: .infinity)
            }
            .frame(maxWidth: .infinity)
            .navigationBarTitle(title, displayMode: .inline)
        }
        
    }
}
