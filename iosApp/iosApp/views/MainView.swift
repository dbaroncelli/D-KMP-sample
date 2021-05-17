//
//  MainView.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 15/05/21.
//  Copyright © 2021 orgName. All rights reserved.


import SwiftUI
import shared


struct MainView: View {
    @ObservedObject var appObj: AppObservableObject
    var body: some View {
        let dkmpNav = appObj.appState.getNavigation(model: appObj.model)
        dkmpNav.router(appObj.stateProvider, appObj.events)
    }
}



/*
struct RouterView<T : Hashable & AnyObject, Content : View> : View {
    @ObservedObject
    private var state: ObservableValue<RouterState<AnyObject, T>>
    private let render: (T, _ isHidden: Bool) -> Content
​
    init(_ routerState: Value<RouterState<AnyObject, T>>, @ViewBuilder render: @escaping (T, _ isHidden: Bool) -> Content) {
        self.state = ObservableValue(routerState)
        self.render = render
    }
​
    var body: some View {
        let routerState = self.state.value
​
        let backstack =
            routerState
                .backStack
                .compactMap { $0 as? RouterStateEntryCreated }
                .map { $0.component }
​
        return ZStack {
            ForEach(backstack, id: \.hashValue) {
                self.render($0, true)
            }
​
            self.render(routerState.activeChild.component, false)
        }
    }
}
*/
