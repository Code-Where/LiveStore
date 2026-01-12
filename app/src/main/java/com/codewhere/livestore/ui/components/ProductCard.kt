package com.codewhere.livestore.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.codewhere.livestore.common.extentions.shimmerEffect
import com.codewhere.livestore.domain.modal.Product

@Composable
fun ProductCard(
    isShimmering: Boolean = false,
    product: Product,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.Black),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .shimmerEffect(isShimmering),
                contentScale = ContentScale.Inside
            )

            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {

                Text(
                    text = product.title,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.shimmerEffect(isShimmering)
                        .fillMaxWidth(if (isShimmering) 0.8f else 1f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isShimmering) "" else "Rs. ${product.price}",
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.shimmerEffect(isShimmering)
                        .fillMaxWidth(if (isShimmering) 0.4f else 1f)
                )
            }
        }
    }
}
