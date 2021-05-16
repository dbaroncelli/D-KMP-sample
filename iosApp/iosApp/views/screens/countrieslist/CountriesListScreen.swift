//
//  CountriesListScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct CountriesListScreen: View {
    @EnvironmentObject var appObj: AppObservableObject
    
    var body: some View {
        let countriesListState = appObj.stateProviders.getCountriesListState()
        let events = appObj.events
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
                                NavigationLink(destination: appObj.getView(Screen.countrydetail, item.name)) {
                                    CountriesListRow(
                                        item: item,
                                        favorite: countriesListState.favoriteCountries[item.name] != nil,
                                        onFavoriteIconClick: { events.selectFavorite(country: item.name) }
                                    )
                                }
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
                            selectedItem : countriesListState.selectedMenuItem,
                            onItemClick: { menuItem in events.selectMenuItem(menuItem: menuItem) }
                        )
                    }
                }
            }
            .navigationBarColor(backgroundUIColor: purpleUIColor, tintUIColor: .white)
            .toolbarColor(backgroundUIColor: purpleUIColor, tintUIColor: .white)
        }
    }
    
}
