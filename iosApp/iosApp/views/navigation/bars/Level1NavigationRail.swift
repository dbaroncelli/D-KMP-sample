//
//  Level1NavigationRail.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 24/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


// this is the left vertical navigation bar for 2-Pane visualization
// (used by bigger devices in landscape mode)
    
struct Level1NavigationRail: View {
    @EnvironmentObject var appObj: AppObservableObject
        
    var body: some View {
        let level1ScreenIdentifier = appObj.localNavigationState.currentLevel1ScreenIdentifier
        VStack {
            Spacer()
            NavigationRailButton(
                itemLabel: "All Countries",
                iconName: "list.bullet",
                selected: level1ScreenIdentifier.URI==Level1Navigation.allCountries.screenIdentifier.URI,
                onClick: { appObj.dkmpNav.navigateByLevel1Menu(appObj, level1Navigation: Level1Navigation.allCountries) }
            )
            NavigationRailButton(
                itemLabel: "Favorites",
                iconName: "star.fill",
                selected: level1ScreenIdentifier.URI==Level1Navigation.favoriteCountries.screenIdentifier.URI,
                onClick: { appObj.dkmpNav.navigateByLevel1Menu(appObj, level1Navigation: Level1Navigation.favoriteCountries) }
            )
            Spacer()
        }
    }
    
}




struct NavigationRailButton: View {
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
            .padding(.top, 25)
            .padding(.bottom, 25)
            .foregroundColor(selected ? .white : linkColor)
        }
    }
}
