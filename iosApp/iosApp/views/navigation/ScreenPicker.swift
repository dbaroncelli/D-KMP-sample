//
//  ScreenPicker.swift
//
//  Created by Daniele Baroncelli on 06/05/2021.
//
//

import SwiftUI
import shared

extension Navigation {
    
    @ViewBuilder func screenPicker(_ sId: ScreenIdentifier,_ stateProvider: StateProvider,_ events: Events) -> some View {
        
        switch sId.screen {
        case .countrieslist:
            CountriesListScreen(
                countriesListState: stateProvider.getToCast(screenIdentifier: sId) as! CountriesListState,
                onMenuItemClick: { item in self.navigateByLevel1Menu(level1NavigationItem: item) },
                onListItemClick: { name in self.navigate(.countrydetail, CountryDetailParams(countryName: name)) },
                onFavoriteIconClick: { name in events.selectFavorite(countryName: name) }
            )
        case .countrydetail:
            CountryDetailScreen(
                countryDetailState: stateProvider.getToCast(screenIdentifier: sId) as! CountryDetailState
            )
        default:
            EmptyView()
        }
    }
}

