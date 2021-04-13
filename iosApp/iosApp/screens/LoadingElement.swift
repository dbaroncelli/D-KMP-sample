//
//  LoadingElement.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI

struct LoadingElement: View {
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
