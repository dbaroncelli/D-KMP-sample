//
//  ScreenPicker.swift
//
//  Created by Daniele Baroncelli on 06/05/2021.
//
//

import SwiftUI
import shared

extension Navigation {
    
    @ViewBuilder func screenPicker(requestedSId: ScreenIdentifier, navState: LocalNavigationState) -> some View {
        
        let currentSId = navState.topScreenIdentifier

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
                    countryDetailState: self.stateProvider.getToCast(screenIdentifier: requestedSId) as! CountryDetailState,
                    ontestScreenOpened: { name in self.navigate(.level3screen, Level3ScreenParams(name: name)) }
                )
                
            case .level3screen:
                Level3Screen(
                    level3ScreenState: self.stateProvider.getToCast(screenIdentifier: requestedSId) as! Level3ScreenState
                )
                
            default:
                EmptyView()
            }
            
        }
        .navigationTitle(getTitle(screenIdentifier: requestedSId))
        .navigationBarTitleDisplayMode(.inline)
        .onAppear { if requestedSId.URI == currentSId.URI {
            NSLog("  onAppear: "+requestedSId.URI)
        } }
        .onDisappear { if requestedSId.URI == currentSId.URI {
            self.exitScreen(screenIdentifier: requestedSId)
        } }
        
        
    }
    
    
    @ViewBuilder func twoPaneDefaultDetail(_ sId: ScreenIdentifier) -> some View {
        
        switch sId.screen {

        case .countrieslist: CountriesListTwoPaneDefaultDetail()

        default:
            EmptyView()
        }
    }
    
    
}

