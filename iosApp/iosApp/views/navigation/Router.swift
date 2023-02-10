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

struct LocalNavigationState {
    var level1ScreenIdentifier: ScreenIdentifier
    var path: [ScreenIdentifier] { // path is the backstack without the level1ScreenIdentifier
        didSet {
            NSLog("UI NAVIGATION RECOMPOSITION: URI changed "+level1ScreenIdentifier.URI+" / "+self.path.map{$0.URI}.joined(separator: " / "))
        }
    }
    var topScreenIdentifier: ScreenIdentifier {
        return path.isEmpty ? level1ScreenIdentifier : path.last!
    }
    var orientation = UIDevice.current.orientation
    mutating func isOrientationChanged(_ newOrientation: UIDeviceOrientation) -> Bool {
        if UIDevice.current.orientation != UIDeviceOrientation.faceDown && UIDevice.current.orientation != UIDeviceOrientation.faceUp {
            if newOrientation != orientation {
                self.orientation = newOrientation
                return true
            }
        }
        return false
    }
}


struct Router: View {
    
    var body: some View {
        VStack {
            //ForEach(self.level1ScreenIdentifiers, id: \.self.URI) { screenIdentifier in
                if !isTwoPane() {
                    OnePane()
                        //.opacity(localNavigationState.level1ScreenIdentifier.URI == self.currentLevel1ScreenIdentifier.URI ? 1 : 0)
                } else {
                    //TwoPane(navState: navState)
                        //.opacity(localNavigationState.level1ScreenIdentifier.URI == self.currentLevel1ScreenIdentifier.URI ? 1 : 0)
                }
            //}
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
        //NSLog("UI NAVIGATION RECOMPOSITION: navigate level 1 -> "+level1Navigation.screenIdentifier.URI)
        appObj.localNavigationState = LocalNavigationState(
            level1ScreenIdentifier: level1Navigation.screenIdentifier,
            path: appObj.dkmpNav.getPath(level1ScreenIdentifier: level1Navigation.screenIdentifier) as! [ScreenIdentifier]
        )
    }
    
}
