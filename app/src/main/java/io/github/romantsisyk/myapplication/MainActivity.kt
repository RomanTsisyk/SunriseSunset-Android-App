package io.github.romantsisyk.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.romantsisyk.myapplication.presentation.ui.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import io.github.romantsisyk.common.presentation.viewmodel.SunriseSunsetViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: SunriseSunsetViewModel = hiltViewModel()
            MainScreen(viewModel = viewModel)
        }
    }
}
