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

    private var requestedSId: ScreenIdentifier
    private var stateProvider: StateProvider
    
    init(requestedSId: ScreenIdentifier, stateProvider: StateProvider) {
        self.requestedSId = requestedSId
        self.stateProvider = stateProvider
        self.state = stateProvider.getToCast(screenIdentifier: requestedSId).value as! any ScreenState
    }
    
    
    @MainActor
    func collectScreenStateFlow() async {
        for await state in stateProvider.getToCast(screenIdentifier: requestedSId) {
            self.state = state
        }
    }
}

extension Navigation {
    
    @ViewBuilder func screenPicker(requestedSId: ScreenIdentifier) -> some View {
        let screenState = ObservableScreenState(requestedSId: requestedSId, stateProvider: self.stateProvider)

        VStack {
            switch requestedSId.screen {
                
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

