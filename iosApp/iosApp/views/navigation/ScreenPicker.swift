//
//  ScreenPicker.swift
//
//  Created by Daniele Baroncelli on 06/05/2021.
//
//

import SwiftUI
import shared

extension Navigation {

    @ViewBuilder func screenPicker(requestedSId: ScreenIdentifier, appObj: AppObservableObject) -> some View {
            
        VStack {
            
            let state = appObj.getScreenState(sID: requestedSId)
            
            switch requestedSId.screen {
                
            case .countrieslist:
                CountriesListScreen(
                    countriesListState: state as! CountriesListState,
                    onListItemClick: { name in self.navigate(.countrydetail, CountryDetailParams(countryName: name)) },
                    onFavoriteIconClick: { name in self.events.selectFavorite(countryName: name) }
                )
                
            case .countrydetail:
                CountryDetailScreen(
                    countryDetailState: state as! CountryDetailState
                )
                
            }
            
        }
        .navigationTitle(getTitle(screenIdentifier: requestedSId))
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            if requestedSId.URI == self.navigationState.topScreenIdentifier.URI {
                NSLog("iOS side:  onAppear URI "+requestedSId.URI)
            }
        }
        .onDisappear {
            self.exitScreenForIos(screenIdentifier: requestedSId)
        }
        .task {
            await appObj.collectScreenStateFlow(sID: requestedSId)
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

