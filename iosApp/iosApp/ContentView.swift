//
//  ContentView.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//
//

import SwiftUI

struct ContentView: View {
    @EnvironmentObject var appObj: AppObservableObject
    var body: some View {
        appObj.getStartView()
    }
}
