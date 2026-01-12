package com.codewhere.livestore.presentation.detailed_product.viewmodel

import androidx.lifecycle.ViewModel
import com.codewhere.livestore.common.constants.ErrorTypes
import com.codewhere.livestore.common.constants.NetworkChecker
import com.codewhere.livestore.common.events.UiEvents
import com.codewhere.livestore.common.extentions.toErrorTypes
import com.codewhere.livestore.common.states.NetworkState
import com.codewhere.livestore.domain.modal.Product
import com.codewhere.livestore.domain.usecases.GetProductUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class ProductScreenViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _product = MutableStateFlow(Product.empty())
    val product = _product.asStateFlow()

    private val _networkState = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val networkState = _networkState.asStateFlow()

    private val _uiEvents = Channel<UiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun saveProductId(productId: Int){
        if(_product.value.id == productId) return
        _product.update {
            it.copy(id = productId)
        }
    }

    fun loadProduct(productId: Int) {
        saveProductId(productId)
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

        getProductUseCase(productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { product ->
                    _product.value = product
                    _networkState.value = NetworkState.Success(Unit)
                },
                { throwable ->
                    _networkState.value = NetworkState.Error(
                        throwable.toErrorTypes(),
                        throwable.message ?: "Something went wrong"
                    )
                }
            )
            .addTo(disposable)
    }

    fun retry() {
        if (_product.value.id == -25) return
        loadProduct(_product.value.id)
    }

    fun resetNetworkState() {
        _networkState.value = NetworkState.Idle
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}
