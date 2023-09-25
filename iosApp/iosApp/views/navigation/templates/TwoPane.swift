//
//  TwoPane.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 24/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


struct TwoPane: View {
    @EnvironmentObject var appObj: AppObservableObject
    var level1ScreenIdentifier : ScreenIdentifier
    
    var body: some View {
        NavigationSplitView(columnVisibility: .constant(.all)) {
            Level1NavigationRail()
                .padding(.leading, 28)
                .padding(.trailing, 12)
                .background(customBgColor)
                .navigationSplitViewColumnWidth(ideal: 100)
        } content: {
            appObj.dkmpNav.screenPicker(requestedSId: level1ScreenIdentifier, appObj: appObj)
                .navigationDestination(for: ScreenIdentifier.self) { sId in
                    let _ = appObj.dkmpNav.navigateToScreenForIos(screenIdentifier: sId, level1ScreenIdentifier: level1ScreenIdentifier)
                    appObj.dkmpNav.screenPicker(requestedSId: sId, appObj: appObj)
                }
                .navigationSplitViewColumnWidth(ideal: 420)
        } detail: {
            appObj.dkmpNav.twoPaneDefaultDetail(level1ScreenIdentifier: level1ScreenIdentifier)
        }
        .navigationSplitViewStyle(.balanced)
    }
    
}
