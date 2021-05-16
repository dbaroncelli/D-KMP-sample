//
//  MainView.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 15/05/21.
//  Copyright © 2021 orgName. All rights reserved.


import SwiftUI
import shared


struct MainView: View {
    @EnvironmentObject var appObj: AppObservableObject
    var body: some View {
        appObj.getStartView()
    }
}
