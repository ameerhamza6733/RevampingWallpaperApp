package com.ameerhamza.animatedgiflivewallpapers.onbording.ui.viewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.model.OnboardingItem
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.dataSource.FakeCongfigDataSource
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.dataSource.OnboardDataSource
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val repository: OnboardingRepository) : ViewModel() {

    private val _onboardingItems = MutableLiveData<List<OnboardingItem>>()
    val onboardingItems: LiveData<List<OnboardingItem>> = _onboardingItems
     val onboardingDataSource: OnboardDataSource = FakeCongfigDataSource()

    private val _isOnboardingCompleted = MutableLiveData<Boolean>()
    val isOnboardingCompleted: LiveData<Boolean> = _isOnboardingCompleted


}
