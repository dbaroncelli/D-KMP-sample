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
            itemLabel: "All",
            iconName: "list.bullet",
            selected: selectedTab==Level1Navigation.allcountries.screenIdentifier,
            onItemClick: { onItemClick(Level1Navigation.allcountries) }
        )
        Spacer()
        ButtonBarButton(
            itemLabel: "Favorites",
            iconName: "star.fill",
            selected: selectedTab==Level1Navigation.favoritecountries.screenIdentifier,
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
            HStack(spacing: 10) {
                Image(systemName: iconName)
                Text(itemLabel)
            }
            .frame(height: 50)
            .foregroundColor(selected ? .white : greyColor)
        }
    }
}
