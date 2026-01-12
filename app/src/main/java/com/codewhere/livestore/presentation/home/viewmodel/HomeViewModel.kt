package com.codewhere.livestore.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewhere.livestore.common.constants.ErrorTypes
import com.codewhere.livestore.common.constants.HomeTabs
import com.codewhere.livestore.common.constants.NetworkChecker
import com.codewhere.livestore.common.events.UiEvents
import com.codewhere.livestore.common.extentions.toErrorTypes
import com.codewhere.livestore.common.states.NetworkState
import com.codewhere.livestore.domain.usecases.GetClothingProductUseCase
import com.codewhere.livestore.domain.usecases.GetElectronicsProductUseCase
import com.codewhere.livestore.presentation.home.state.StateData
import com.codewhere.livestore.presentation.navigation.Routes
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getClothingProductUseCase: GetClothingProductUseCase,
    private val getElectronicsProductUseCase: GetElectronicsProductUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _state = MutableStateFlow(StateData())
    val state = _state.asStateFlow()

    private val _networkState = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val networkState = _networkState.asStateFlow()

    private val _uiEvents = Channel<UiEvents>(Channel.BUFFERED)
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        loadProducts()
    }

    fun onTabChanged(tab: HomeTabs) {
        if (_state.value.selectedTab == tab) return
        _state.update { it.copy(selectedTab = tab) }
    }

    fun loadProducts() {
        if (_networkState.value is NetworkState.Loading) return

        if (!networkChecker.isInternetAvailable()) {
            _uiEvents.trySend(
                UiEvents.ShowToast("No internet connection")
            )
            _networkState.value = NetworkState.Error(
                ErrorTypes.NETWORK,
                "No internet connection"
            )
            return
        }

        _networkState.value = NetworkState.Loading

        Single.zip(
            getElectronicsProductUseCase(),
            getClothingProductUseCase()
        ) { electronics, clothing ->
            electronics to clothing
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { (electronics, clothing) ->
                    _state.update {
                        it.copy(
                            electronicProducts = electronics,
                            clothingProducts = clothing
                        )
                    }
                    _networkState.value = NetworkState.Success(Unit)
                },
                { error ->
                    _networkState.value = NetworkState.Error(
                        error.toErrorTypes(),
                        error.message ?: "Something went wrong"
                    )
                }
            )
            .addTo(disposables)
    }

    fun retry() {
        if (_state.value.electronicProducts.isEmpty() &&
            _state.value.clothingProducts.isEmpty()
        ) {
            loadProducts()
        }
    }

    fun onProductClicked(productId: Int) {
        viewModelScope.launch {
            _uiEvents.send(
                UiEvents.Navigate(
                    Routes.ProductScreen(productId)
                )
            )
        }
    }

    fun resetNetworkState() {
        _networkState.value = NetworkState.Idle
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
