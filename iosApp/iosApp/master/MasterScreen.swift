//
//  MasterScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct MasterScreen: View {
    @EnvironmentObject var viewModel: AppViewModel
    
    var body: some View {
        let state = viewModel.stateProvider.getMaster()
        let events = viewModel.events
        if state.isLoading {
            LoadingScreen()
        } else {
            NavigationView {
                List {
                    if state.countriesList.count == 0 {
                        HStack(spacing: 0) {
                            Spacer()
                            Text("empty list")
                            Spacer()
                        }
                    } else {
                        Section(header: MasterListHeader()) {
                            ForEach (state.countriesList, id: \.name) { item in
                                NavigationLink(destination: DetailScreen(detailName: item.name)) {
                                    MasterListItem(
                                        item: item,
                                        favorite: state.favoriteCountries[item.name] != nil,
                                        onFavoriteIconClick: { events.selectFavorite(country: item.name) }
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
                            selectedItem : state.selectedMenuItem,
                            onItemClick: { menuItem in events.selectMenuItem(menuItem: menuItem) }
                        )
                    }
                }
            }
            .navigationBarColor(backgroundColor: "6200EE".toUIColor(), tintColor: .white)
            .toolbarColor(backgroundColor: "6200EE".toUIColor(), tintColor: .white)
        }
    }
    
}
