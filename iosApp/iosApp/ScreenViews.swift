//
//  ScreenViews.swift
//
//  Created by Daniele Baroncelli on 06/05/2021.
//
//

import SwiftUI
import shared

extension DKMPNavigation {
    @ViewBuilder func getViewInstance(_ screen: Screen, _ instanceId: String?) -> some View {
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
