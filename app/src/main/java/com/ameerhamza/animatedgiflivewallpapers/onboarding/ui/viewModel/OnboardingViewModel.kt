package com.ameerhamza.animatedgiflivewallpapers.onboarding.ui.viewModel
import androidx.lifecycle.ViewModel
import com.ameerhamza.animatedgiflivewallpapers.onboarding.data.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class OnboardingViewModelDeprecate @Inject constructor(private val repository: OnboardingRepository) : ViewModel() {

//    private val _onboardingItems = MutableLiveData<List<OnboardingItem>>()
//    val onboardingItems: LiveData<List<OnboardingItem>> = _onboardingItems
//     val onboardingDataSource: OnboardingDataSource = FakeCongfigDataSource()
//
//    private val _isOnboardingCompleted = MutableLiveData<Boolean>()
//
//    fun markOnboardingCompleted() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                repository.insertSettings(Settings(onboardingCompleted = true))
//            }
//            _isOnboardingCompleted.value = true
//        }
//    }
//
//     fun fetchOnboardingData() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val items = repository.fetchOnboardingItems()
//            _onboardingItems.postValue(items )
//        }
//    }
}
