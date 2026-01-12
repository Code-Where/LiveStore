package com.codewhere.livestore.presentation.detailed_product

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.codewhere.livestore.common.constants.ErrorTypes
import com.codewhere.livestore.common.events.UiEvents
import com.codewhere.livestore.common.extentions.shimmerEffect
import com.codewhere.livestore.common.extentions.toSentenceCase
import com.codewhere.livestore.common.states.NetworkState
import com.codewhere.livestore.presentation.detailed_product.viewmodel.ProductScreenViewModel
import com.codewhere.livestore.ui.components.NetworkUnavailableOverlay
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    productId: Int,
    onBack: () -> Unit,
    viewModel: ProductScreenViewModel = koinViewModel()
) {
    val product by viewModel.product.collectAsState()
    val networkState by viewModel.networkState.collectAsState()
    val context = LocalContext.current

    // Initial load
    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }


    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvents.ShowToast ->
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                else -> Unit
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // Image Section
            Box {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.title,
                    contentScale = ContentScale.Inside,
                    placeholder = painterResource(com.codewhere.livestore.R.drawable.logo),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .background(Color.LightGray)
                        .shimmerEffect(networkState is NetworkState.Loading)
                )

                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                Text(
                    text = product?.title ?: "",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmerEffect(networkState is NetworkState.Loading)
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = if (networkState is NetworkState.Loading) ""
                    else "${product?.category?.uppercase()} · ⭐ ${product?.rating?.rate} (${product?.rating?.count})",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmerEffect(networkState is NetworkState.Loading)
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = if (networkState is NetworkState.Loading) ""
                    else "₹ ${product?.price}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmerEffect(networkState is NetworkState.Loading)
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Description",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = product?.description?.toSentenceCase() ?: "",
                    color = Color.DarkGray,
                    lineHeight = 20.sp,
                    minLines = if (networkState is NetworkState.Loading) 5 else 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmerEffect(networkState is NetworkState.Loading)
                )
            }
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
}
