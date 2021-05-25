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

func isTwoPane() -> Bool {
    let width = UIScreen.main.bounds.width
    let height = UIScreen.main.bounds.height
    if width < height || width < twopaneWidthThreshold {
        return false
    }
    return true
}


extension Navigation {

    @ViewBuilder func router() -> some View {

        ZStack {
            if !isTwoPane() {
                ForEach(self.statefulBackstack, id: \.self.index) { entry in
                    self.onePane(entry.screenIdentifier)
                }
            } else {
                self.twoPane()
            }
        }
        .navigationViewStyle(StackNavigationViewStyle())
        .gesture(
            DragGesture(minimumDistance: 20, coordinateSpace: .local).onEnded({ value in
                if value.translation.width > 0 { // RIGHT SWIPE
                    if (!self.only1ScreenInBackstack) { self.exitScreen() }
                }
            })
        )
        .navigationBarColor(backgroundUIColor: UIColor(customBgColor), tintUIColor: .white)
        .toolbarColor(backgroundUIColor: UIColor(customBgColor), tintUIColor: .white)

    }
    
    
    @ViewBuilder func backButton()  -> some View {
        if (!only1ScreenInBackstack) {
            Button(action: { withAnimation { self.exitScreen() } } ) {
                HStack {
                    Image(systemName: "chevron.left")
                    Text("Back")
                }.foregroundColor(linkColor)
            }
        }
    }

    
}


extension Navigation {
    func navigate(_ screen: Screen, _ params: ScreenParams?) { // just to remove named parameters
        navigate(screen: screen, params: params)
    }
}



struct NavigationLink<Content: View>: View {
    var linkFunction: () -> Void
    let content: () -> Content

    var body: some View {
        Button(action: { linkFunction() }) {
            VStack(spacing: 0) {
                HStack(spacing: 0) {
                    content()
                    Image(systemName: "chevron.right").resizable().frame(width: 6, height: 12).foregroundColor(lightGreyColor)
                }
            }
        }
    }
}
