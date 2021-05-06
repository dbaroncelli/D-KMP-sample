//
//  CountryDetailScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct CountryDetailScreen: View {
    var countryName : String
    @EnvironmentObject var appObj: AppObservableObject
    
    var body: some View {
        let countryDetailState = appObj.stateProviders.getCountryDetailState(country: countryName)
        VStack {
            if countryDetailState.isLoading {
                LoadingElement()
            } else {
                CountryDetailContent(data: countryDetailState.countryInfo)
            }
        }
        .onAppear()
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .principal) {
                Text(countryName).font(.headline).foregroundColor(.white)
            }
        }
    }
}
