//
//  DetailScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct DetailScreen: View {
    var detailName : String
    @EnvironmentObject var vm: AppViewModel
    
    var body: some View {
        let detailState = vm.stateProvider.getDetail(country: detailName)
        VStack {
            if detailState.isLoading {
                LoadingScreen()
            } else {
                DetailContent(data: detailState.countryInfo)
            }
        }
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .principal) {
                Text(detailName).font(.headline).foregroundColor(.white)
            }
        }
    }
}
