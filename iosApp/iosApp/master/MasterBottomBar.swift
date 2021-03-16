//
//  MasterBottomBar.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 16/03/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


struct MasterBottomBar: View {
    var selectedItem : MenuItem
    var onItemClick : (MenuItem) -> Void
    
    var body: some View {
        Spacer()
        ButtonBarButton(menuItem: MenuItem.all, iconName: "list.bullet", selected: selectedItem==MenuItem.all, onItemClick: onItemClick)
        Spacer()
        ButtonBarButton(menuItem: MenuItem.favorites, iconName: "star.fill", selected: selectedItem==MenuItem.favorites, onItemClick: onItemClick)
        Spacer()
    }
}




struct ButtonBarButton: View {
    var menuItem : MenuItem
    var iconName : String
    var selected : Bool
    var onItemClick : (MenuItem) -> Void

    var body: some View {
        Button(action: { onItemClick(menuItem) }) {
            HStack(spacing: 10) {
                Image(systemName: iconName)
                Text(menuItem.name)
            }
            .frame(height: 50)
            .foregroundColor(selected ? .white : "AAAAAA".toColor())
        }
    }
}
