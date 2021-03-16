//
//  LoadingScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI

struct LoadingScreen: View {
    var body: some View {
        VStack {
            Spacer()
            Text("loading...")
            Spacer().frame(height: 30)
            ProgressView().progressViewStyle(CircularProgressViewStyle())
            Spacer()
        }
    }
}
