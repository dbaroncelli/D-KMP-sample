//
//  Router.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 04/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


let twopaneWidthThreshold : CGFloat = 1000


struct Router: View {
    
    var body: some View {
        VStack {
            if !isTwoPane() {
                OnePane()
            } else {
                TwoPane()
            }
        }
        .toolbarColor(backgroundUIColor: UIColor(customBgColor), tintUIColor: .white)
    }
    
}



func isTwoPane() -> Bool {
    let width = UIScreen.main.bounds.width
    let height = UIScreen.main.bounds.height
    if width < height || width < twopaneWidthThreshold {
        return false
    }
    return true
}




extension Navigation {
    
    func navigate(_ screen: Screen, _ params: ScreenParams?) -> ScreenIdentifier {
        return ScreenIdentifier.Factory().get(screen: screen, params: params)
    }

    func navigateByLevel1Menu(_ appObj: AppObservableObject, level1Navigation: Level1Navigation) {
        selectLevel1Navigation(level1ScreenIdentifier: level1Navigation.screenIdentifier) // change navigationState
        appObj.localNavigationState = navigationState
    }
    
}
