//
//  NavigationBarColor.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 14/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI




// navigation bar color

struct NavigationBarColor: ViewModifier {

  init(backgroundColor: UIColor, tintColor: UIColor) {
    let coloredAppearance = UINavigationBarAppearance()
    coloredAppearance.configureWithOpaqueBackground()
    coloredAppearance.backgroundColor = backgroundColor
    coloredAppearance.titleTextAttributes = [.foregroundColor: tintColor]
    coloredAppearance.largeTitleTextAttributes = [.foregroundColor: tintColor]
    UINavigationBar.appearance().standardAppearance = coloredAppearance
    UINavigationBar.appearance().compactAppearance = coloredAppearance
    UINavigationBar.appearance().tintColor = tintColor
  }

  func body(content: Content) -> some View {
    content
  }
}

extension View {
  func navigationBarColor(backgroundUIColor: UIColor, tintUIColor: UIColor) -> some View {
    self.modifier(NavigationBarColor(backgroundColor: backgroundUIColor, tintColor: tintUIColor))
  }
}
