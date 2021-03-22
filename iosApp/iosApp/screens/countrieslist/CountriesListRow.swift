//
//  MasterListItem.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct CountriesListRow: View {
    var item : CountriesListItem
    var favorite : Bool
    var onFavoriteIconClick: () -> Void
    
    var body: some View {
        HStack {
            Text(item.name).font(Font.subheadline).bold().frame(alignment: .leading)
            Spacer()
            Text(item.firstDosesPerc).font(Font.subheadline).multilineTextAlignment(.trailing)
                .frame(width: 60)
            Text(item.fullyVaccinatedPerc).font(Font.subheadline).multilineTextAlignment(.trailing)
                .frame(width: 60)
            Image(systemName: favorite ? "star.fill" : "star").foregroundColor(favorite ? .purple : .gray)
                .highPriorityGesture(TapGesture().onEnded(onFavoriteIconClick))
                .frame(width: 70)
        }
    }
}
