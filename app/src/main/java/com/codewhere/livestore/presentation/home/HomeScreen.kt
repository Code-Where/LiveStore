package com.codewhere.livestore.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codewhere.livestore.common.constants.ErrorTypes
import com.codewhere.livestore.common.constants.HomeTabs
import com.codewhere.livestore.common.events.UiEvents
import com.codewhere.livestore.common.states.NetworkState
import com.codewhere.livestore.domain.modal.Product
import com.codewhere.livestore.domain.modal.Rating
import com.codewhere.livestore.presentation.home.viewmodel.HomeViewModel
import com.codewhere.livestore.ui.components.CustomTab
import com.codewhere.livestore.ui.components.NetworkUnavailableOverlay
import com.codewhere.livestore.ui.components.ProductCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()
    val networkState by viewModel.networkState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvents.Navigate -> navController.navigate(event.route)
                is UiEvents.ShowToast ->
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(networkState) {
        if (networkState is NetworkState.Error) {
            val error = networkState as NetworkState.Error
            if (error.errorType != ErrorTypes.NETWORK) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                viewModel.resetNetworkState()
            }
        }

        if (networkState is NetworkState.Success) {
            viewModel.resetNetworkState()
        }
    }

    if (
        networkState is NetworkState.Error &&
        (networkState as NetworkState.Error).errorType == ErrorTypes.NETWORK
    ) {
        NetworkUnavailableOverlay {
            viewModel.retry()
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CustomTab(
            selectedTab = state.selectedTab,
            onTabSelected = viewModel::onTabChanged
        )

        val products = when (state.selectedTab) {
            HomeTabs.ELECTRONICS -> state.electronicProducts
            HomeTabs.CLOTHING -> state.clothingProducts
        }

        ProductList(
            isLoading = networkState is NetworkState.Loading,
            products = products,
            onProductClick = { viewModel.onProductClicked(it.id) }
        )
    }
}


@Composable
fun ProductList(
    isLoading: Boolean,
    products: List<Product>,
    onProductClick: (Product) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isLoading) {
            items(10) {
                ProductCard(
                    isShimmering = true,
                    product = Product.empty(),
                    onClick = {}
                )
            }
        } else {
            items(
                items = products,
                key = { it.id }
            ) { product ->
                ProductCard(
                    product = product,
                    onClick = { onProductClick(product) }
                )
            }
        }
    }
}

