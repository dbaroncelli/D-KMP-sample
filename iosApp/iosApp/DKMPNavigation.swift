//
//  DKMPNavigation.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 04/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


class DKMPNavigation {
    
    var model : DKMPViewModel
    var backstack = [String]()

    init(model: DKMPViewModel) {
        self.model = model
    }
    
    func getStartScreen() -> Screen {
        return model.getStartScreen()
    }
    
    func enterScreen(screen: Screen, instanceId: String?) {
        let routeId = self.makeRouteId(screen: screen, instanceId: instanceId)
        if self.backstack.last != routeId {
            self.backstack.append(routeId)
        }
    }
    
    func popScreen() {
        let oldRouteId = self.backstack.popLast()
        if let lastEntry = self.backstack.last {
            self.model.exitScreen(oldRouteId: oldRouteId!, newRouteId: lastEntry)
        }
    }

    func makeRouteId(screen: Screen, instanceId: String?) -> String {
        var routeId = screen.route
        if (instanceId != nil) {
            routeId += "/"+instanceId!
        }
        return routeId
    }


    
}
