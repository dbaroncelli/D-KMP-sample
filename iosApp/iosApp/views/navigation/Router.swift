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

extension Navigation {

    func isTwoPane() -> Bool {
        let width = UIScreen.main.bounds.width
        let height = UIScreen.main.bounds.height
        if width < height || width < twopaneWidthThreshold {
            return false
        }
        return true
    }
    
    
    @ViewBuilder func router() -> some View {

        ZStack {
            ForEach(self.level1ScreenIdentifiers, id: \.self.URI) { screenIdentifier in
                if !self.isTwoPane() {
                    self.onePane(screenIdentifier)
                        .opacity(screenIdentifier.URI == self.currentLevel1ScreenIdentifier.URI ? 1 : 0)
                } else {
                    self.twoPane(screenIdentifier)
                        .opacity(screenIdentifier.URI == self.currentLevel1ScreenIdentifier.URI ? 1 : 0)
                }
            }
        }
        .navigationBarColor(backgroundUIColor: UIColor(customBgColor), tintUIColor: .white)
        .toolbarColor(backgroundUIColor: UIColor(customBgColor), tintUIColor: .white)

    }
    

    
    func navigate(_ screen: Screen, _ params: ScreenParams?) -> ScreenIdentifier {
        return ScreenIdentifier.Factory().get(screen: screen, params: params)
    }

    
}



struct NavLink<Content: View>: View {
    var linkFunction: () -> ScreenIdentifier
    let content: () -> Content
    
    @EnvironmentObject var appObj: AppObservableObject
    @State private var selected : Bool = false
    var body: some View {
        if appObj.dkmpNav.isTwoPane() {
            Button(action: {
                let screenIdentifier = linkFunction()
                appObj.dkmpNav.navigateByScreenIdentifier(screenIdentifier: screenIdentifier)
                self.selected = true
            }) {
                VStack(spacing: 0) {
                    HStack(spacing: 0) {
                        content()
                        Image(systemName: "chevron.right").resizable().frame(width: 6, height: 12).foregroundColor(lightGreyColor)
                    }
                }
            }
        } else {
            let isActive = Binding<Bool> (
                get: {
                    selected && appObj.dkmpNav.isInCurrentVerticalBackstack(screenIdentifier: linkFunction())
                },
                set: { isActive in
                    if isActive {
                        let screenIdentifier = linkFunction()
                        appObj.dkmpNav.navigateByScreenIdentifier(screenIdentifier: screenIdentifier)
                        self.selected = true
                    }
                }
            )
            NavigationLink(
                destination: LazyDestinationView(
                    appObj.dkmpNav.screenPicker(linkFunction())
                        .navigationBarTitle(appObj.dkmpNav.getTitle(screenIdentifier: linkFunction()), displayMode: .inline)
                        .onDisappear {
                            let screenIdentifier = linkFunction()
                            //print("onDisappear: "+screenIdentifier.URI)
                            if appObj.dkmpNav.isInCurrentVerticalBackstack(screenIdentifier: screenIdentifier) {
                                //print("confimed disappear")
                                self.selected = false
                                isActive.wrappedValue = false
                                appObj.dkmpNav.exitScreen(screenIdentifier: screenIdentifier, triggerRecomposition: false)
                            }
                        }
                ),
                isActive: isActive
            ) {
                content()
            }
        }
    }
}


struct LazyDestinationView<Content: View>: View {
    let build: () -> Content
    init(_ build: @autoclosure @escaping () -> Content) {
        self.build = build
    }
    var body: Content {
        build()
    }
}
