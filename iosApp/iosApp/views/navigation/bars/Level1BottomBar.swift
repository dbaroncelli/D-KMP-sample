//
//  Level1BottomBar.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 16/03/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


// this is the bottom horizontal navigation bar for 1-Pane visualization
// (used by small devices and in Portrait mode)
    
struct Level1ButtonBar: View {
    @EnvironmentObject var appObj: AppObservableObject
        
    var body: some View {
        let level1ScreenIdentifier = appObj.localNavigationState.level1ScreenIdentifier
        Spacer()
        BottomBarButton(
            itemLabel: "All Countries",
            iconName: "list.bullet",
            selected: level1ScreenIdentifier.URI==Level1Navigation.allcountries.screenIdentifier.URI,
            onClick: { appObj.dkmpNav.navigateByLevel1Menu(appObj, level1Navigation: Level1Navigation.allcountries) }
        )
        Spacer()
        BottomBarButton(
            itemLabel: "Favorites",
            iconName: "star.fill",
            selected: level1ScreenIdentifier.URI==Level1Navigation.favoritecountries.screenIdentifier.URI,
            onClick: { appObj.dkmpNav.navigateByLevel1Menu(appObj, level1Navigation: Level1Navigation.favoritecountries) }
        )
        Spacer()
        
    }
}



struct BottomBarButton: View {
    var itemLabel : String
    var iconName : String
    var selected : Bool
    var onClick : () -> Void
    
    var body: some View {
        Button(action: { onClick() }) {
            VStack(spacing: 5) {
                Image(systemName: iconName).resizable().scaledToFit().frame(height:15)
                Text(itemLabel).font(Font.footnote)
            }
            .foregroundColor(selected ? .white : linkColor)
        }
    }
}
