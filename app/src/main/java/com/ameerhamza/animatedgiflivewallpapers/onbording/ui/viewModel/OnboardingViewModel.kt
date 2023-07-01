package com.ameerhamza.animatedgiflivewallpapers.onbording.ui.viewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.model.OnboardingItem
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.dataSource.FakeCongfigDataSource
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.dataSource.OnboardingDataSource
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.repository.OnboardingRepository
import com.ameerhamza.animatedgiflivewallpapers.onbording.data.model.Settings
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val repository: OnboardingRepository) : ViewModel() {

    private val _onboardingItems = MutableLiveData<List<OnboardingItem>>()
    val onboardingItems: LiveData<List<OnboardingItem>> = _onboardingItems
     val onboardingDataSource: OnboardingDataSource = FakeCongfigDataSource()

    private val _isOnboardingCompleted = MutableLiveData<Boolean>()
    val isOnboardingCompleted: LiveData<Boolean> = _isOnboardingCompleted


}
