//
//  CountriesListScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct CountriesListScreen: View {
    @ObservedObject var observableScreenState: ObservableScreenState
    var onListItemClick: (String) -> ScreenIdentifier
    var onFavoriteIconClick : (String) -> Void
    
    init(
        observableScreenState: ObservableScreenState,
        onListItemClick: @escaping (String) -> ScreenIdentifier,
        onFavoriteIconClick: @escaping (String) -> Void
    ) {
        self.observableScreenState = observableScreenState
        self.onListItemClick = onListItemClick
        self.onFavoriteIconClick = onFavoriteIconClick
    }
    
    
    var body: some View {
        let countriesListState = self.observableScreenState.state as! CountriesListState
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
                                NavigationLink(value: onListItemClick(item.name) ) {
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
                .listStyle(.insetGrouped)
            }
        }
    }
    
}



struct CountryListView_Previews: PreviewProvider {
    static var previews: some View {
        let model : DKMPViewModel = DKMPViewModel.Factory().getIosInstance()
        NavigationStack {
            CountriesListScreen(
                observableScreenState: ObservableScreenState(
                    requestedSId: ScreenIdentifier.companion.get(
                        screen: Screen.countrieslist,
                        params: CountriesListParams.init(listType: CountriesListType.all)
                    ),
                    stateProvider: model.navigation.stateProvider,
                    state: CountriesListState(isLoading: false, countriesListItems: [CountriesListItem(_data: CountryListData(name: "AAAAAAAA", population: 1000, firstDoses: 700, fullyVaccinated: 530)), CountriesListItem(_data: CountryListData(name: "BBBBBBBB", population: 1000, firstDoses: 654, fullyVaccinated: 432)), CountriesListItem(_data: CountryListData(name: "CCCCCCCC", population: 1000, firstDoses: 557, fullyVaccinated: 398))], favoriteCountries: [:])),
                onListItemClick: { name in ScreenIdentifier.Factory().self.getByURI(URI: "countrieslist:listType=ALL")! },
                onFavoriteIconClick: { name in print() }
            )
        }
    }
}
