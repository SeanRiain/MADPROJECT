package com.example.madimplementation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class IntentType { UX, SHOPPING, TROUBLESHOOTING }


//holds the app state
class MainViewModel : ViewModel() {
    private val _intent = MutableStateFlow<IntentType?>(null) //stores path of intents
    val intent = _intent.asStateFlow()

    private val _category = MutableStateFlow<String?>(null) //stores path of categories
    val category = _category.asStateFlow()

    private val _capturedUri = MutableStateFlow<String?>(null) //stores path of photos
    val capturedUri = _capturedUri.asStateFlow()

    fun setIntent(i: IntentType) { _intent.value = i }
    fun setCategory(cat: String) { _category.value = cat }
    fun setCapturedUri(uri: String) { _capturedUri.value = uri }
    //allows screens to do state updates
}
