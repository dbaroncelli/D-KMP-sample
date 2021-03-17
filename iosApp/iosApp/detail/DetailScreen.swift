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
        VStack {
            if vm.appState.detailState.isLoading {
                LoadingScreen()
            } else {
                DetailContent(data: vm.appState.detailState.countryInfo)
            }
        }.onAppear {
            vm.coreModel.loadDetailItem(country: detailName)
        }
    }
}
