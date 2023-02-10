//
//  Level3Screen.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 02/07/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct Level3Screen: View {
    var level3ScreenState: Level3ScreenState
    
    var body: some View {
        VStack {
            Text(level3ScreenState.text)
        }
    }
}
