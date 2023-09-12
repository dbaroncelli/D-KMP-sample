//
//  CountryDetailScreen.swift
//
//  Created by Daniele Baroncelli on 13/03/2021.
//

import SwiftUI
import shared

struct CountryDetailScreen: View {
    @ObservedObject var observableScreenState: ObservableScreenState
    
    init(
        observableScreenState: ObservableScreenState
    ) {
        self.observableScreenState = observableScreenState
    }
    
    var body: some View {
        VStack {
            let countryDetailState = self.observableScreenState.state as! CountryDetailState
            if countryDetailState.isLoading {
                LoadingScreen()
            } else {
                let data = countryDetailState.countryInfo
                ScrollView(.vertical) {
                    VStack(alignment: .leading, spacing: 5) {
                        DataElement(label: "total population", value: data.population)
                        DataElement(label: "    with first dose", value: data.firstDoses, percentage: data.firstDosesPerc)
                        DataElement(label: "    fully vaccinated", value: data.fullyVaccinated, percentage: data.fullyVaccinatedPerc)
                        Spacer().frame(height: 20)
                        if data.vaccinesList != nil  {
                            Text("Vaccines:").font(Font.callout.bold())
                            ForEach(data.vaccinesList!, id: \.self) { vaccine in
                                Text("   ‣ \(vaccine)").font(Font.callout)
                            }
                        }
                    }.padding(.all, 15).frame(maxWidth: .infinity, alignment: .leading)
                }
            }
        }
    }
}


struct DataElement: View {
    var label: String
    var value: String = ""
    var percentage: String = ""
    var body: some View {
        HStack {
            Text("\(label):").font(Font.callout.bold())
            Text(value).font(Font.callout)
            if percentage != "" {
                Text(" (\(percentage))").font(Font.callout)
            }
        }
    }
}
