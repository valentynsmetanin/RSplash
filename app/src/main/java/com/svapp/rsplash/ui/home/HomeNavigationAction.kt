package com.svapp.rsplash.ui.home

sealed interface HomeNavigationAction {

    class NavigateToPhotoDetails(val photoId: String) : HomeNavigationAction

    object NavigateToRandomPhoto : HomeNavigationAction
}
