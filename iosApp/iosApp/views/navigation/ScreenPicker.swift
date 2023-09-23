//
//  ScreenPicker.swift
//
//  Created by Daniele Baroncelli on 06/05/2021.
//
//

import SwiftUI
import shared

struct ScreenPicker: View {
    
    let navigation: Navigation
    @ObservedObject var screenState: ObservableScreenState
    
    var body: some View {
        
        VStack {
            switch screenState.requestedSId.screen {
                
            case .countrieslist:
                CountriesListScreen(
                    state: screenState.state as! CountriesListState,
                    onListItemClick: { name in navigation.navigate(.countrydetail, CountryDetailParams(countryName: name)) },
                    onFavoriteIconClick: { name in navigation.events.selectFavorite(countryName: name) }
                )
                
            case .countrydetail:
                CountryDetailScreen(
                    state: screenState.state as! CountryDetailState
                )
                
            default:
                EmptyView()
            }
            
        }
        .navigationTitle(navigation.getTitle(screenIdentifier: screenState.requestedSId))
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            if screenState.requestedSId.URI == navigation.navigationState.topScreenIdentifier.URI {
                NSLog("iOS side:  onAppear URI "+screenState.requestedSId.URI)
            }
        }
        .onDisappear {
            navigation.exitScreenForIos(screenIdentifier: screenState.requestedSId)
        }
        .task {
            await screenState.collectScreenStateFlow()
        }
        
    }
}

extension Navigation {
    
    @ViewBuilder func screenPicker(screenState: ObservableScreenState) -> some View {
        ScreenPicker(navigation: self, screenState: screenState)
    }
    
    @ViewBuilder func twoPaneDefaultDetail(level1ScreenIdentifier: ScreenIdentifier) -> some View {
        
        switch level1ScreenIdentifier.screen {
            
        case .countrieslist: CountriesListTwoPaneDefaultDetail()
            
        default:
            EmptyView()
        }
        
    }
    
    
}

