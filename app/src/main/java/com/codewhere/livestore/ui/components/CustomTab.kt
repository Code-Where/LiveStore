package com.codewhere.livestore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codewhere.livestore.common.constants.HomeTabs

@Composable
fun CustomTab(
    selectedTab: HomeTabs,
    onTabSelected: (HomeTabs) -> Unit
) {
    val tabs = HomeTabs.entries

    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        indicator = { positions ->
            Box(
                Modifier
                    .tabIndicatorOffset(positions[selectedTab.ordinal])
                    .padding(horizontal = 32.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.Black)
            )
        },
        divider = {}
    ) {
        tabs.forEach { tab ->
            Tab(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                text = {
                    Text(
                        tab.title,
                        color = if (selectedTab == tab) Color.Black else Color.Gray,
                        fontWeight = if (selectedTab == tab)
                            FontWeight.Bold else FontWeight.Medium
                    )
                }
            )
        }
    }
}
