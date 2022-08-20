package com.svapp.rsplash.ui.home

sealed class HomeNavigationAction {

    class NavigateToPhotoDetails(val photoId: String) : HomeNavigationAction()
}
