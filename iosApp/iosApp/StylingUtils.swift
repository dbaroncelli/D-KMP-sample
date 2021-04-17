//
//  StylingUtils.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//
//

import SwiftUI

extension String {
    func toColor() -> Color {
        let hex = self.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let a, r, g, b: UInt64
        switch hex.count {
        case 3: // RGB (12-bit)
            (a, r, g, b) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
        case 6: // RGB (24-bit)
            (a, r, g, b) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
        case 8: // ARGB (32-bit)
            (a, r, g, b) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
        default:
            (a, r, g, b) = (1, 1, 1, 0)
        }

        return Color(
            .sRGB,
            red: Double(r) / 255,
            green: Double(g) / 255,
            blue:  Double(b) / 255,
            opacity: Double(a) / 255
        )
    }
    func toUIColor() -> UIColor {
        return UIColor(self.toColor())
    }
}


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
  func navigationBarColor(backgroundColor: UIColor, tintColor: UIColor) -> some View {
    self.modifier(NavigationBarColor(backgroundColor: backgroundColor, tintColor: tintColor))
  }
}



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
  func toolbarColor(backgroundColor: UIColor, tintColor: UIColor) -> some View {
    self.modifier(ToolbarColor(backgroundColor: backgroundColor, tintColor: tintColor))
  }
}

