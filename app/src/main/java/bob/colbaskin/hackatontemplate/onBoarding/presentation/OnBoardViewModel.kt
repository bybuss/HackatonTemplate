package bob.colbaskin.hackatontemplate.onBoarding.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.hackatontemplate.auth.domain.AuthRepository
import bob.colbaskin.hackatontemplate.navigation.AuthScreen
import bob.colbaskin.hackatontemplate.navigation.DetailsScreen
import bob.colbaskin.hackatontemplate.navigation.Screen
import bob.colbaskin.hackatontemplate.onBoarding.domain.OnBoardingDataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnBoardViewModel  @Inject constructor(
    private val dataStoreRepository: OnBoardingDataStoreRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val isLoading = mutableStateOf(true)
    val startDestination = mutableStateOf(DetailsScreen.Welcome.route)

    init {
        viewModelScope.launch {
            val onboardingCompleted = dataStoreRepository.readOnBoardingState().first()
            if (onboardingCompleted) {
                if (authRepository.isLoggedIn()) {
                    startDestination.value = Screen.Home.route
                } else {
                    startDestination.value = AuthScreen.Login.route
                }
            } else {
                startDestination.value = DetailsScreen.Welcome.route
            }
            isLoading.value = false
        }
    }
}