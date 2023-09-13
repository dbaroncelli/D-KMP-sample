import SwiftUI
import shared

class ObservableScreenState: ObservableObject {
    @Published var state: (any ScreenState)

    var requestedSId: ScreenIdentifier
    private var stateProvider: StateProvider
    
    init(requestedSId: ScreenIdentifier, stateProvider: StateProvider, state: (any ScreenState)? = nil) {
        self.requestedSId = requestedSId
        self.stateProvider = stateProvider
        self.state = state ?? stateProvider.getToCast(screenIdentifier: requestedSId).value
    }
    
    
    @MainActor
    func collectScreenStateFlow() async {
        for await state in stateProvider.getToCast(screenIdentifier: requestedSId) {
            self.state = state
        }
    }
}