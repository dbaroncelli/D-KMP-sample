//
//  MasterListHeader.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared


struct MasterListHeader: View {
    
    var body: some View {
        HStack {
            Text("country").font(Font.caption).frame(alignment: .leading)
            Spacer()
            Text("first\ndose").font(Font.caption).multilineTextAlignment(.trailing)
                .frame(width: 60)
            Text("fully\nvax'd").font(Font.caption).multilineTextAlignment(.trailing)
                .frame(width: 60)
            Text("favorite?").font(Font.caption).frame(alignment: .center)
                .frame(width: 70)
                .padding(.trailing, 20)
        }.frame(height: 50)
    }
}
