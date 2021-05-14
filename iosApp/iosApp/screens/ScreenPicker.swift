//
//  ScreenViews.swift
//
//  Created by Daniele Baroncelli on 06/05/2021.
//
//

import SwiftUI
import shared

extension Navigation {
    @ViewBuilder func screenPicker(_ screen: Screen, _ instanceId: String?) -> some View {
        switch screen {
        case .countrieslist:
            CountriesListScreen()
        case .countrydetail:
            CountryDetailScreen(countryName: instanceId!)
        default:
            EmptyView()
        }
    }
}
