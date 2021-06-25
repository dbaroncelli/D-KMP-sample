//
//  CountriesListScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct CountriesListScreen: View {
    var countriesListState: CountriesListState
    var onListItemClick: (String) -> ScreenIdentifier
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
                        // let _ = print("count: "+String(countriesListState.countriesListItems.count))
                        Section(header: CountriesListHeader()) {
                            ForEach (countriesListState.countriesListItems, id: \.name) { item in
                                NavLink( linkFunction: { onListItemClick(item.name) } ) {
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
                .listStyle(InsetGroupedListStyle())
            }
        }
    }
    
}
