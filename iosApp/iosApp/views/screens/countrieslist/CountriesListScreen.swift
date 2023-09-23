//
//  CountriesListScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct CountriesListScreen: View {
    let state: CountriesListState
    let onListItemClick: (String) -> ScreenIdentifier
    let onFavoriteIconClick : (String) -> Void
    
    
    var body: some View {
        VStack {
            if state.isLoading {
                LoadingScreen()
            } else {
                List {
                    if state.countriesListItems.count == 0 {
                        HStack(spacing: 0) {
                            Text("empty list")
                        }
                    } else {
                        Section(header: CountriesListHeader()) {
                            ForEach (state.countriesListItems, id: \.name) { item in
                                NavigationLink(value: onListItemClick(item.name) ) {
                                    CountriesListRow(
                                        item: item,
                                        favorite: state.favoriteCountries[item.name] != nil,
                                        onFavoriteIconClick: { onFavoriteIconClick(item.name) }
                                    )
                                }
                            }
                        }
                    }
                }
                .listStyle(.insetGrouped)
            }
        }
    }
    
}



struct CountryListView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationStack {
            CountriesListScreen(
                state: CountriesListState(isLoading: false, countriesListItems: [CountriesListItem(_data: CountryListData(name: "AAAAAAAA", population: 1000, firstDoses: 700, fullyVaccinated: 530)), CountriesListItem(_data: CountryListData(name: "BBBBBBBB", population: 1000, firstDoses: 654, fullyVaccinated: 432)), CountriesListItem(_data: CountryListData(name: "CCCCCCCC", population: 1000, firstDoses: 557, fullyVaccinated: 398))], favoriteCountries: [:]),
                onListItemClick: { name in ScreenIdentifier.Factory().self.getByURI(URI: "countrieslist:listType=ALL")! },
                onFavoriteIconClick: { name in print() }
            )
        }
    }
}
