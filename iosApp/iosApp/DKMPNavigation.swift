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
    
    
    func enterRoute(_ screen: Screen, _ instanceId: String?) {
        var routeId = screen.route
        if (instanceId != nil) {
            routeId += "/"+instanceId!
        }
        if self.backstack.last != routeId {
            self.backstack.append(routeId)
        }
    }
    
    func popRoute() {
        let oldRouteId = self.backstack.popLast()
        if let lastEntry = self.backstack.last {
            self.model.exitScreen(oldRouteId: oldRouteId!, newRouteId: lastEntry)
        }
    }


    
}
