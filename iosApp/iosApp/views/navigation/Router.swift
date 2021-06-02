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
            ForEach(self.level1Backstack, id: \.self.screenIdentifier.URI) { level1Entry in
                if !isTwoPane() {
                    self.onePane(level1Entry.screenIdentifier)
                        .opacity(level1Entry.screenIdentifier.URI == self.currentLevel1ScreenIdentifier.URI ? 1 : 0)
                } else {
                    self.twoPane(level1Entry.screenIdentifier)
                        .opacity(level1Entry.screenIdentifier.URI == self.currentLevel1ScreenIdentifier.URI ? 1 : 0)
                }
            }
        }
        .navigationBarColor(backgroundUIColor: UIColor(customBgColor), tintUIColor: .white)
        .toolbarColor(backgroundUIColor: UIColor(customBgColor), tintUIColor: .white)

    }
    

    
    func navigate(_ screen: Screen, _ params: ScreenParams?) -> (Navigation, ScreenIdentifier) {
        return (self, ScreenIdentifier.Factory().get(screen: screen, params: params))
    }
    
    
}



struct NavLink<Content: View>: View {
    var linkFunction: () -> (Navigation, ScreenIdentifier)
    var itemKey: String
    let content: () -> Content

    @EnvironmentObject var appObj: AppObservableObject
    @State private var selectedItemKey : String?
    var body: some View {
        if isTwoPane() {
            Button(action: {
                let resultsObjs = linkFunction()
                let navigation = resultsObjs.0
                let screenIdentifier = resultsObjs.1
                navigation.navigateByScreenIdentifier(screenIdentifier: screenIdentifier, triggerRecomposition: true)
                self.selectedItemKey = itemKey
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
                    self.selectedItemKey == itemKey
                },
                set: { isActive in
                    if isActive {
                        let resultsObjs = linkFunction()
                        let navigation = resultsObjs.0
                        let screenIdentifier = resultsObjs.1
                        navigation.navigateByScreenIdentifier(screenIdentifier: screenIdentifier, triggerRecomposition: false)
                        self.selectedItemKey = itemKey
                    }
                }
            )
            NavigationLink(
                destination: LazyDestinationView(
                    linkFunction().0.screenPicker(linkFunction().1)
                        .navigationBarTitle(linkFunction().0.getTitle(screenIdentifier: linkFunction().1), displayMode: .inline)
                        .onDisappear {
                            self.selectedItemKey = nil
                            isActive.wrappedValue = false
                            let navigation = linkFunction().0
                            navigation.exitScreen(triggerRecomposition: false)
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
