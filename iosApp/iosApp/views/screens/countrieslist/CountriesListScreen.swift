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
        VStack {
            if countriesListState.isLoading {
                LoadingScreen()
            } else {
                List {
                    if countriesListState.countriesListItems.count == 0 {
                        HStack(spacing: 0) {
                            Text("empty list")
                        }
                    } else {
                        Section(header: CountriesListHeader()) {
                            ForEach (countriesListState.countriesListItems, id: \.name) { item in
                                NavigationLink( linkFunction: { onListItemClick(item.name) } ) {
                                    CountriesListRow(
                                        item: item,
                                        favorite: countriesListState.favoriteCountries[item.name] != nil,
                                        onFavoriteIconClick: { onFavoriteIconClick(item.name) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        .navigationBarTitle("D-KMP sample", displayMode: .inline)
        .toolbar {
            ToolbarItemGroup(placement: .bottomBar) {
                CountriesListBottomBar(
                    selectedTab : getScreenIdentifier(.countrieslist, countriesListState.params),
                    onItemClick: { menuItem in onMenuItemClick(menuItem) }
                )
            }
        }
    }
    
}
