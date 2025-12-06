package com.example.madimplementation.viewmodel

//import androidx.lifecycle.ViewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.madimplementation.data.AppDatabase
import com.example.madimplementation.data.ScannedItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class IntentType { UX, SHOPPING, TROUBLESHOOTING }


//holds the app state
class MainViewModel (application: Application) : AndroidViewModel(application) {


    private val _intent = MutableStateFlow<IntentType?>(null) //stores path of intents
    val intent = _intent.asStateFlow()

    private val _category = MutableStateFlow<String?>(null) //stores path of categories
    val category = _category.asStateFlow()

    private val _capturedUri = MutableStateFlow<Uri?>(null) //stores path of photos
    val capturedUri = _capturedUri.asStateFlow()

    private val _brand = MutableStateFlow<String?>(null)
    val brand = _brand.asStateFlow()

    private val _model = MutableStateFlow<String?>(null)
    val model = _model.asStateFlow()

    //prevents duplicate saves
    private val _saved = MutableStateFlow(false)
    val saved = _saved.asStateFlow()

    fun setIntent(i: IntentType) { _intent.value = i }
    fun setCategory(cat: String) { _category.value = cat }
    //fun setCapturedUri(uri: String) { _capturedUri.value = uri }
    fun setCapturedUri(uri: Uri?) { _capturedUri.value = uri }
    fun setBrand(b: String?) { _brand.value = b }
    fun setModel(m: String?) { _model.value = m }
    //allows screens to do state updates



    //Auto-saves the current item to the database
    //This is called by ItemInfoScreen when the user reaches that screen

    fun saveCurrentItemIfNeeded() {
        //prevents re-saving the same item multiple times
        if (_saved.value) return

        val db = AppDatabase.getDatabase(getApplication())
        val dao = db.scannedItemDao()

        //Build summary text based on recorded intent
        val intentString = _intent.value?.name
        val summary = when (_intent.value) {
            IntentType.SHOPPING -> "Shopping: [Placeholder shopping summary text]"
            IntentType.UX -> "UX: [Placeholder UX tips and usage advice]"
            IntentType.TROUBLESHOOTING -> "Troubleshooting: [Placeholder troubleshooting steps]"
            //not really needed but good as a failsafe
            else -> "General: [Placeholder general information]"
        }

        val item = ScannedItem(
            brand = _brand.value,
            model = _model.value,
            imagePath = _capturedUri.value?.toString(),
            category = _category.value,
            intent = intentString,
            summaryText = summary
        )

        viewModelScope.launch {
            dao.insert(item)
            _saved.value = true
        }
    }

    // Reset state to start a new flow
    fun resetAll() {
        _intent.value = null
        _category.value = null
        _capturedUri.value = null
        _brand.value = null
        _model.value = null
        _saved.value = false
    }
}