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
        
        VStack {
        
            switch sId.screen {
            
            case .countrieslist:
                CountriesListScreen(
                    countriesListState: stateProvider.getToCast(screenIdentifier: sId) as! CountriesListState,
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
    
    
    @ViewBuilder func twoPaneDefaultDetail(_ sId: ScreenIdentifier,_ stateProvider: StateProvider,_ events: Events) -> some View {
        
        switch sId.screen {

        case .countrieslist: CountriesListTwoPaneDefaultDetail()

        default:
            EmptyView()
        }
    }
    
    
}

