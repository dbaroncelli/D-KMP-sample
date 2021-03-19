package eu.baroncelli.dkmpsample.shared.viewmodel.statereducers

import com.russhwolf.settings.MockSettings
import eu.baroncelli.dkmpsample.shared.datalayer.Repository
import eu.baroncelli.dkmpsample.shared.utils.runBlockingTest
import eu.baroncelli.dkmpsample.shared.viewmodel.StateManager
import eu.baroncelli.dkmpsample.shared.viewmodel.detail.updateDetailState
import eu.baroncelli.dkmpsample.shared.viewmodel.master.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MasterReducersTests {

    @Test
    fun testDefaultTab() {
        val sm = StateManager(Repository(MockSettings()))
        sm.restoreSelectedMenuItem()
        assertEquals(sm.state.masterState.selectedMenuItem, MenuItem.ALL)
    }

    @Test
    fun testFavoritesTab() = runBlockingTest {
        val sm = StateManager(Repository(MockSettings()))
        sm.getDataByMenuItem(MenuItem.FAVORITES)
        assertEquals(sm.state.masterState.selectedMenuItem, MenuItem.FAVORITES)
    }

    @Test
    fun testFavoriteCountry() {
        val sm = StateManager(Repository(MockSettings()))
        sm.selectFavorite("Italy")
        assertTrue(sm.state.masterState.favoriteCountries.containsKey("Italy"))
    }

    @Test
    fun testMasterSubstate() {
        val sm = StateManager(Repository(MockSettings()))
        sm.updateMasterState {
            it.copy(isLoading = false)
        }
        assertEquals(sm.state.masterState.isLoading, false)
    }

    @Test
    fun testDetailsSubstate() {
        val sm = StateManager(Repository(MockSettings()))
        sm.updateDetailState {
            it.copy(isLoading = true)
        }
        assertEquals(sm.state.detailState.isLoading, true)
    }


}