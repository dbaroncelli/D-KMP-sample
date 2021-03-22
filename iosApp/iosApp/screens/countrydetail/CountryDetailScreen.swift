//
//  DetailScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct CountryDetailScreen: View {
    var countryName : String
    @EnvironmentObject var viewModel: AppViewModel
    
    var body: some View {
        let countryDetailState = viewModel.stateProvider.getCountryDetailState(country: countryName)
        VStack {
            if countryDetailState.isLoading {
                LoadingElement()
            } else {
                CountryDetailContent(data: countryDetailState.countryInfo)
            }
        }
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .principal) {
                Text(countryName).font(.headline).foregroundColor(.white)
            }
        }
    }
}
