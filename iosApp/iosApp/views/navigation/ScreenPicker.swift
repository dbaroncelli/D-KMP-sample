//
//  ScreenPicker.swift
//
//  Created by Daniele Baroncelli on 06/05/2021.
//
//

import SwiftUI
import shared



extension Navigation {
    
    
    @ViewBuilder func screenPicker(requestedSId: ScreenIdentifier) -> some View {
        
        VStack {
        
            switch requestedSId.screen {
            
            case .countrieslist:
                CountriesListScreen(
                    countriesListState: self.stateProvider.getToCast(screenIdentifier: requestedSId) as! CountriesListState,
                    onListItemClick: { name in self.navigate(.countrydetail, CountryDetailParams(countryName: name)) },
                    onFavoriteIconClick: { name in self.events.selectFavorite(countryName: name) }
                )
                
            case .countrydetail:
                CountryDetailScreen(
                    countryDetailState: self.stateProvider.getToCast(screenIdentifier: requestedSId) as! CountryDetailState
                )
                
            default:
                EmptyView()
            }
            
        }
        .navigationTitle(getTitle(screenIdentifier: requestedSId))
        .navigationBarTitleDisplayMode(.inline)
        .onAppear { if requestedSId.URI == self.navigationState.topScreenIdentifier.URI {
            NSLog("iOS side:  onAppear URI "+requestedSId.URI)
        } }
        .onDisappear {
            self.exitScreenForIos(screenIdentifier: requestedSId)
        }
        
        
    }
    
    
    
    @ViewBuilder func twoPaneDefaultDetail(level1ScreenIdentifier: ScreenIdentifier) -> some View {
        
        switch level1ScreenIdentifier.screen {

        case .countrieslist: CountriesListTwoPaneDefaultDetail()

        default:
            EmptyView()
        }
        
    }
    
    
}

