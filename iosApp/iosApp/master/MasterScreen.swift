//
//  MasterScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct MasterScreen: View {
    @EnvironmentObject var vm: AppViewModel
    
    var body: some View {
        if vm.appState.masterState.isLoading {
            LoadingScreen()
        } else {
            NavigationView {
                List {
                    if vm.appState.masterState.countriesList.count == 0 {
                        HStack(spacing: 0) {
                            Spacer()
                            Text("empty list")
                            Spacer()
                        }
                    } else {
                        Section(header: MasterListHeader()) {
                            ForEach (vm.appState.masterState.countriesList, id: \.name) { item in
                                NavigationLink(destination: DetailScreen(detailName: item.name)) {
                                    MasterListItem(
                                        item: item,
                                        favorite: vm.appState.masterState.favoriteCountries[item.name] != nil,
                                        onFavoriteIconClick: { vm.coreModel.selectFavorite(country: item.name) }
                                    )
                                }
                            }
                        }
                    }
                }
                .navigationBarTitleDisplayMode(.inline)
                .toolbar {
                    ToolbarItem(placement: .principal) {
                        Text("D-KMP sample").font(.headline).foregroundColor(.white)
                    }
                    ToolbarItemGroup(placement: .bottomBar) {
                        MasterBottomBar(
                            selectedItem : vm.appState.masterState.selectedMenuItem,
                            onItemClick: { menuItem in vm.coreModel.selectMenuItem(menuItem: menuItem) }
                        )
                    }
                }
            }
            .navigationBarColor(backgroundColor: "6200EE".toUIColor(), tintColor: .white)
            .toolbarColor(backgroundColor: "6200EE".toUIColor(), tintColor: .white)
        }
    }
    
}
