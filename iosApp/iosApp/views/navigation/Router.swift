//
//  Router.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 04/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

extension Navigation {

    @ViewBuilder func router(_ stateProvider: StateProvider,_ events: Events) -> some View {
        
        ZStack {
            ForEach(navigationLevelsStack, id: \.self) { screenIdentifier in
                self.screenPicker(screenIdentifier, stateProvider, events)
                    .opacity(screenIdentifier == self.currentScreenIdentifier ? 1 : 0)
                    .navigationBarItems(leading: self.topLeftButton() )
                    .gesture(
                        DragGesture(minimumDistance: 5, coordinateSpace: .local).onEnded({ value in
                            if value.translation.width > 0 { // RIGHT SWIPE
                                if (!self.only1ScreenInBackstack) { self.exitScreen() }
                            }
                        })
                    )
            }
        }
        
    }
    
    
    @ViewBuilder func topLeftButton()  -> some View {
        if (!only1ScreenInBackstack) {
            Button(action: { withAnimation { self.exitScreen() } } ) {
                HStack {
                    Image(systemName: "chevron.left")
                    Text("Back")
                }
            }
        }
    }

    
}


extension Navigation {
    func navigate(_ screen: Screen, _ params: ScreenParams?) { // just to remove named parameters
        navigate(screen: screen, params: params)
    }
}

func getScreenIdentifier(_ screen : Screen, _ params: ScreenParams? = nil) -> ScreenIdentifier {
    return ScreenIdentifier(screen: screen, params: params, paramsAsString: nil)
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
