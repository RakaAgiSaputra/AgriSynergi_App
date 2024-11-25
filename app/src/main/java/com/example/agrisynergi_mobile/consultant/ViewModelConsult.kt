package com.example.agrisynergi_mobile.consultant

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.osmdroid.library.BuildConfig

class ChatViewModel : ViewModel() {
    private val _userMessage = mutableStateOf<List<UserMessage>>(emptyList())
    private val _textGenerationResult = MutableStateFlow<String?>(null)
    val userMessage: State<List<UserMessage>> = _userMessage
    val textGenerationResult = _textGenerationResult.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "AIzaSyDrU3CHomozODCEBBrlxdgNiR47_JVdb7o"
//        apiKey = BuildConfig.GEMINI_API_KEY
    )

    var gabungan: String = """ini adalah bagian penting prompt dasar "nama adalah
        |Budi, konsultan pertanian dan jangan sebutkan nama lalu langsung jawab saja pertanyaan :"
    """.trimMargin()
    init {
        gabungan
    }


    fun generateStoryFromApi(prompt: String) {
        viewModelScope.launch(Dispatchers.IO) {
            gabungan += prompt
            try {
                val result = generativeModel.generateContent(gabungan)
                _textGenerationResult.value = result.text
                val textReply = _textGenerationResult.value
                addMessage(UserMessage("Consultant", "consult", textReply.toString(), true))
            } catch (e: Exception) {
                _textGenerationResult.value = "Error: ${e.message}"
            }
        }
    }

    // Fungsi untuk menambah pesan
    fun addMessage(message: UserMessage) {
        _userMessage.value = _userMessage.value.toMutableList().apply { add(message) }
    }

//    fun addMessage(message: UserMessage) {
//        _userMessage.value += message // Membuat daftar baru dengan elemen yang ditambahkan
//    }
}
