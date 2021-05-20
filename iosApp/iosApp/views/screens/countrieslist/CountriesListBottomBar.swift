//
//  CountriesListBottomBar.swift
//
//  Created by Daniele Baroncelli on 16/03/21.
//

import SwiftUI
import shared


struct CountriesListBottomBar: View {
    var selectedTab : ScreenIdentifier
    var onItemClick : (Level1Navigation) -> Void
    
    var body: some View {
        Spacer()
        ButtonBarButton(
            itemLabel: "All Countries",
            iconName: "list.bullet",
            selected: selectedTab.URI==Level1Navigation.allcountries.screenIdentifier.URI,
            onItemClick: { onItemClick(Level1Navigation.allcountries) }
        )
        Spacer()
        ButtonBarButton(
            itemLabel: "Favorites",
            iconName: "star.fill",
            selected: selectedTab.URI==Level1Navigation.favoritecountries.screenIdentifier.URI,
            onItemClick: { onItemClick(Level1Navigation.favoritecountries) }
        )
        Spacer()
    }
}




struct ButtonBarButton: View {
    var itemLabel : String
    var iconName : String
    var selected : Bool
    var onItemClick : () -> Void
    
    var body: some View {
        Button(action: { onItemClick() }) {
            VStack(spacing: 5) {
                Image(systemName: iconName).resizable().scaledToFit().frame(height:15)
                Text(itemLabel).font(Font.footnote)
            }
            .foregroundColor(selected ? .white : linkColor)
        }
    }
}
