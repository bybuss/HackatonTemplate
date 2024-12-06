package bob.colbaskin.hackatontemplate.onBoarding.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.hackatontemplate.auth.domain.network.AuthRepository
import bob.colbaskin.hackatontemplate.navigation.Screen
import bob.colbaskin.hackatontemplate.navigation.graph.Graph
import bob.colbaskin.hackatontemplate.onBoarding.domain.OnBoardingDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel  @Inject constructor(
    private val dataStoreRepository: OnBoardingDataStoreRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val isLoading = mutableStateOf(true)
    val startDestination = mutableStateOf(Screen.Welcome.route)

    init {
        viewModelScope.launch {
            val onboardingCompleted = dataStoreRepository.readOnBoardingState().first()
            if (onboardingCompleted) {
                startDestination.value = if (authRepository.isLoggedIn()) Graph.MAIN else Graph.AUTH
            } else {
                startDestination.value = Screen.Welcome.route
            }
            isLoading.value = false
        }
    }
}