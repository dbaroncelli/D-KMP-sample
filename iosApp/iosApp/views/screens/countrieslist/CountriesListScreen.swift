//
//  CountriesListScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct CountriesListScreen: View {
    var countriesListState: CountriesListState
    var onMenuItemClick: (Level1Navigation) -> Void
    var onListItemClick: (String) -> Void
    var onFavoriteIconClick : (String) -> Void
    
    var body: some View {
        if countriesListState.isLoading {
            LoadingScreen()
        } else {
            NavigationView {
                List {
                    if countriesListState.countriesListItems.count == 0 {
                        HStack(spacing: 0) {
                            Text("empty list")
                        }
                    } else {
                        Section(header: CountriesListHeader()) {
                            ForEach (countriesListState.countriesListItems, id: \.name) { item in
                                CountriesListRow(
                                    item: item,
                                    favorite: countriesListState.favoriteCountries[item.name] != nil,
                                    onFavoriteIconClick: { onFavoriteIconClick(item.name) }
                                )
                            }
                        }
                    }
                }
                .navigationBarTitleDisplayMode(.inline)
                .toolbar {
                    ToolbarItem(placement: .principal) {
                        Text("D-KMP sample").font(.headline).foregroundColor(.white)
                    }
                    ToolbarItemGroup(placement: .bottomBar) {
                        CountriesListBottomBar(
                            selectedTab : getScreenIdentifier(.countrieslist, countriesListState.params),
                            onItemClick: { menuItem in onMenuItemClick(menuItem) }
                        )
                    }
                }
            }
            .navigationBarColor(backgroundUIColor: purpleUIColor, tintUIColor: .white)
            .toolbarColor(backgroundUIColor: purpleUIColor, tintUIColor: .white)
        }
    }
    
}
