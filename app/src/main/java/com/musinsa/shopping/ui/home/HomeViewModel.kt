package com.musinsa.shopping.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musinsa.shopping.domain.Resource
import com.musinsa.shopping.domain.model.remote.HomeContents
import com.musinsa.shopping.domain.usecase.FetchHomeContentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchHomeContentsUseCase: FetchHomeContentsUseCase
) : ViewModel() {

    private val _homeContentsState = MutableStateFlow<Resource<HomeContents>>(Resource.Loading)
    val homeContentsState: StateFlow<Resource<HomeContents>> = _homeContentsState

    var gridGoodsIndex = 6
    var gridStyleIndex = 4

    fun fetchHomeContents() {
        viewModelScope.launch {
            _homeContentsState.value = fetchHomeContentsUseCase.invoke()
        }
    }
}