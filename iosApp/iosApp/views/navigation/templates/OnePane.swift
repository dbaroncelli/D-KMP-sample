//
//  OnePane.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 24/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


extension Binding where Value == NSMutableArray {
    public func cast() -> Binding<[ScreenIdentifier]> {
        return Binding<[ScreenIdentifier]>(get:{ self.wrappedValue as NSArray as! [ScreenIdentifier] },
            set: { self.wrappedValue = NSMutableArray(array: $0) })
    }
}



struct OnePane: View {
    @EnvironmentObject var appObj: AppObservableObject

    var body: some View {
        NavigationStack(path: $appObj.localNavigationState.path.cast()) {
            appObj.dkmpNav.screenPicker(requestedSId: appObj.localNavigationState.level1ScreenIdentifier)
                .navigationDestination(for: ScreenIdentifier.self) { sId in
                    let _ = appObj.dkmpNav.navigateToScreen(screenIdentifier: sId)
                    appObj.dkmpNav.screenPicker(requestedSId: sId)
                }
        }
        .toolbar {
            ToolbarItemGroup(placement: .bottomBar) {
                Level1ButtonBar()
            }
        }
    }
}
