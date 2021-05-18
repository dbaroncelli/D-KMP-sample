//
//  MainView.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 15/05/21.
//  Copyright © 2021 orgName. All rights reserved.


import SwiftUI
import shared


struct MainView: View {
    @ObservedObject var appObj: AppObservableObject
    var body: some View {
        let dkmpNav = appObj.dkmpNav
        NavigationView {
            dkmpNav.router(appObj.model.stateProvider, appObj.model.events)
        }
        .navigationViewStyle(StackNavigationViewStyle())
        .navigationBarColor(backgroundUIColor: UIColor(customBgColor), tintUIColor: .white)
    }
}
