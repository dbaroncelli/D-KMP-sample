//
//  CountriesListScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct CountriesListScreen: View {
    var destinationScreen : Screen
    @EnvironmentObject var appObj: AppObservableObject
    let bgUIColor = UIColor(Color(.sRGB, red: 98/255, green: 0, blue: 238/255, opacity: 1)) // purple
    
    var body: some View {
        let countriesListState = appObj.stateProviders.getCountriesListState()
        let events = appObj.events
        if countriesListState.isLoading {
            LoadingElement()
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
                                NavigationLink(destination: appObj.getView(destinationScreen, item.name)) {
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
            .navigationBarColor(backgroundUIColor: bgUIColor, tintUIColor: .white)
            .toolbarColor(backgroundUIColor: bgUIColor, tintUIColor: .white)
        }
    }
    
}
