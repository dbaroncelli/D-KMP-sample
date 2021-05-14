//
//  StylingUtils.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//
//

import SwiftUI



// toolbar color

struct ToolbarColor: ViewModifier {

  init(backgroundColor: UIColor, tintColor: UIColor) {
    let coloredAppearance = UIToolbarAppearance()
    coloredAppearance.configureWithOpaqueBackground()
    coloredAppearance.backgroundColor = backgroundColor
    UIToolbar.appearance().standardAppearance = coloredAppearance
    UIToolbar.appearance().compactAppearance = coloredAppearance
    UIToolbar.appearance().tintColor = tintColor
  }

  func body(content: Content) -> some View {
    content
  }
}

extension View {
  func toolbarColor(backgroundUIColor: UIColor, tintUIColor: UIColor) -> some View {
    self.modifier(ToolbarColor(backgroundColor: backgroundUIColor, tintColor: tintUIColor))
  }
}

