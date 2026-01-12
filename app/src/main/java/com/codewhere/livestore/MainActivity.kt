package com.codewhere.livestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph
import com.codewhere.livestore.di.networkModule
import com.codewhere.livestore.di.repositoryModule
import com.codewhere.livestore.di.viewModelModule
import com.codewhere.livestore.presentation.navigation.BuildNavGraph
import com.codewhere.livestore.ui.theme.LiveStoreTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiveStoreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BuildNavGraph(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
