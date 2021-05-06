//
//  ViewsDefinition.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//
//

import SwiftUI
import shared

extension AppObservableObject {
    @ViewBuilder func getViewInstance(screen: Screen, instanceId: String? = nil) -> some View {
        switch screen {
        case Screen.countrieslist:
            CountriesListScreen()
        case Screen.countrydetail:
            CountryDetailScreen(countryName: instanceId!)
        default:
            EmptyView()
        }
    }
}
