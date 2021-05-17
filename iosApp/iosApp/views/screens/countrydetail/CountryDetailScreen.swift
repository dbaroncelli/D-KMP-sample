//
//  CountryDetailScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct CountryDetailScreen: View {
    var countryDetailState: CountryDetailState
    
    var body: some View {
        VStack {
            if countryDetailState.isLoading {
                LoadingScreen()
            } else {
                CountryDetailContent(data: countryDetailState.countryInfo)
            }
        }
        .onAppear()
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .principal) {
                Text(countryDetailState.params.countryName).font(.headline).foregroundColor(.white)
            }
        }
    }
}
