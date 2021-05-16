//
//  Router.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 04/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared


struct Router: View {
    @EnvironmentObject var appObj: AppObservableObject
    var body: some View {
        appObj.getStartView()
    }
}


/*
 
add new NavigationLink here
 
 */
