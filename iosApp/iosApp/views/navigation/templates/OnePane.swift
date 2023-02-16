//
//  OnePane.swift
//  iosApp
//
//  Created by Daniele Baroncelli on 24/05/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

 

struct OnePane: View {
    @EnvironmentObject var appObj: AppObservableObject
    var level1ScreenIdentifier : ScreenIdentifier

    var body: some View {
        NavigationStack(path: ($appObj.localNavigationState.paths.getPath(level1URI: level1ScreenIdentifier.URI))) {
            appObj.dkmpNav.screenPicker(requestedSId: level1ScreenIdentifier)
                .navigationDestination(for: ScreenIdentifier.self) { sId in
                    let _ = appObj.dkmpNav.navigateToScreenForIos(screenIdentifier: sId, level1ScreenIdentifier: level1ScreenIdentifier)
                    appObj.dkmpNav.screenPicker(requestedSId: sId)
                }
        }
    }
    
}


// this is used to bind a path defined in Kotlin's shared code, to the NavigationStack path
// N.B. in our Kotlin code, paths are stored as MutableMap<String,MutableList<ScreenIdentifier>>,
//     where "String" is the Level1ScreenIdentifier and "MutableList<ScreenIdentifier>" is the path
extension Binding where Value == KotlinMutableDictionary<NSString,NSMutableArray> {
    public func getPath(level1URI: String) -> Binding<[ScreenIdentifier]> {
        return Binding<[ScreenIdentifier]>(
            get:{
                let dict = self.wrappedValue as! [String:[ScreenIdentifier]]
                //NSLog("NavigationStack read the path "+level1URI+": count "+String(dict[level1URI]!.count))
                return dict[level1URI]!
            },
            set: {
                var writableDict = self.wrappedValue as! [NSString:NSMutableArray]
                writableDict[level1URI as NSString] = NSMutableArray(array: $0)
                //NSLog("NavigationStack modified the path "+level1URI+": new count "+String($0.count))
                self.wrappedValue = KotlinMutableDictionary<NSString, NSMutableArray>.init(dictionary: writableDict)
            }
        )
    }
}
