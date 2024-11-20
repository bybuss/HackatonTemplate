package bob.colbaskin.hackatontemplate.profile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import javax.inject.Inject

/**
 * @author bybuss
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel() {

    init {
        getTasks()
        getInventory()
        loadAchievements()
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