//
//  Level1BottomBar.swift
//
//  Created by Daniele Baroncelli on 16/03/21.
//

import SwiftUI
import shared


extension Navigation {

    // this is the bottom horizontal navigation bar for 1-Pane visualization
    // (used by small devices and in Portrait mode)
    
    @ViewBuilder func level1ButtonBar(selectedTab: ScreenIdentifier) -> some View {

        Spacer()
        BottomBarButton(
            itemLabel: "All Countries",
            iconName: "list.bullet",
            selected: selectedTab.URI==Level1Navigation.allcountries.screenIdentifier.URI,
            onClick: { self.navigateByLevel1Menu(level1NavigationItem: Level1Navigation.allcountries) }
        )
        Spacer()
        BottomBarButton(
            itemLabel: "Favorites",
            iconName: "star.fill",
            selected: selectedTab.URI==Level1Navigation.favoritecountries.screenIdentifier.URI,
            onClick: { self.navigateByLevel1Menu(level1NavigationItem: Level1Navigation.favoritecountries) }
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
