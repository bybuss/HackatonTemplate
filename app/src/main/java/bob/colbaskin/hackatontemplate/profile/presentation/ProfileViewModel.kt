package bob.colbaskin.hackatontemplate.profile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.hackatontemplate.auth.domain.local.AuthDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

/**
 * @author bybuss
 */
enum class ProfileTab {
    Activity,
    Inventory,
    Achievements
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authDataStoreRepository: AuthDataStoreRepository
): ViewModel() {

    private val _selectedTab = MutableStateFlow(ProfileTab.Activity)
    val selectedTab: StateFlow<ProfileTab> = _selectedTab

    init {
        getTasks()
        getInventory()
        loadAchievements()
    }

    fun updateSelectedTab(tab: ProfileTab) {
        _selectedTab.value = tab
    }

    fun accountExit() {
        viewModelScope.launch{
            authDataStoreRepository.clearToken()
            Log.d("Profile", "accountExit -> token cleared")
        }
    }

    fun getTasks() {
        Log.d("ProfileViewModel", "getTasks")
    }

    fun getInventory() {
        Log.d("ProfileViewModel", "getInventory")
    }

    fun loadAchievements() {
       Log.d("ProfileViewModel", "loadAchievements")
    }
}