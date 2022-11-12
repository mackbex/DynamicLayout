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

    private var _homeContentsState = MutableStateFlow<Resource<HomeContents>>(Resource.Loading)
    val homeContentsState: StateFlow<Resource<HomeContents>> = _homeContentsState

    var gridGoodsIndex = 6
        private set
//    val gridGoodsIndex = gridGoodsIndex

    var gridStyleIndex = 4
        private set
//    val gridStyleIndex = _gridStyleIndex

    fun fetchHomeContents() {
        viewModelScope.launch {
            _homeContentsState.value = fetchHomeContentsUseCase.invoke()
        }
    }

    fun increaseGoodsIndex(index: Int) {
        gridGoodsIndex += index
    }

    fun increaseStyleIndex(index: Int) {
        gridStyleIndex += index
    }
}