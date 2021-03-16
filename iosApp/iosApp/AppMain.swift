//
//  AppMain.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//
//

import SwiftUI
import shared

@main
struct iosApp: App {
    @StateObject var vm = AppViewModel()
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(vm)
        }
    }
}
