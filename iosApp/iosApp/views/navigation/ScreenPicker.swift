//
//  ScreenPicker.swift
//
//  Created by Daniele Baroncelli on 06/05/2021.
//
//

import SwiftUI
import shared

class ObservableScreenState: ObservableObject {
    @Published var state: (any ScreenState)

    var requestedSId: ScreenIdentifier
    private var stateProvider: StateProvider
    
    init(requestedSId: ScreenIdentifier, stateProvider: StateProvider) {
        self.requestedSId = requestedSId
        self.stateProvider = stateProvider
        self.state = stateProvider.getToCast(screenIdentifier: requestedSId).value
    }
    
    
    @MainActor
    func collectScreenStateFlow() async {
        for await state in stateProvider.getToCast(screenIdentifier: requestedSId) {
            self.state = state
        }
    }
}

extension Navigation {
        
    @ViewBuilder func screenPicker(screenState: ObservableScreenState) -> some View {
        
        VStack {
            switch screenState.requestedSId.screen {
                
            case .countrieslist:
                CountriesListScreen(
                    observableScreenState: screenState,
                    onListItemClick: { name in self.navigate(.countrydetail, CountryDetailParams(countryName: name)) },
                    onFavoriteIconClick: { name in self.events.selectFavorite(countryName: name) }
                )
                
            case .countrydetail:
                CountryDetailScreen(
                    observableScreenState: screenState
                )
                
            default:
                EmptyView()
            }
            
        }
        .navigationTitle(getTitle(screenIdentifier: screenState.requestedSId))
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            if screenState.requestedSId.URI == self.navigationState.topScreenIdentifier.URI {
                NSLog("iOS side:  onAppear URI "+screenState.requestedSId.URI)
            }
        }
        .onDisappear {
            self.exitScreenForIos(screenIdentifier: screenState.requestedSId)
        }
        .task {
            await screenState.collectScreenStateFlow()
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

