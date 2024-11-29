package bob.colbaskin.hackatontemplate.onBoarding.presentation

import androidx.annotation.DrawableRes
import bob.colbaskin.hackatontemplate.R

sealed class OnBoardingPage(
    @DrawableRes val image: Int
) {
    object First : OnBoardingPage(
        image = R.drawable.first
    )

    object Second : OnBoardingPage(
        image = R.drawable.second,
    )

    object Third : OnBoardingPage(
        image = R.drawable.third,
    )
}