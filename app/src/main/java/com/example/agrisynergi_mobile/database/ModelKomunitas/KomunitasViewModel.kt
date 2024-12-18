package com.example.agrisynergi_mobile.database.ModelKomunitas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrisynergi_mobile.retrofit.model.User
import com.example.agrisynergi_mobile.retrofit.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.SharedPreferenceManager
import retrofit2.Response

@HiltViewModel
class ForumViewModel @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val apiService: ApiService,
    private val komentarRepository: KomentarRepository,
    private val sharedPreferenceManager: SharedPreferenceManager // Inject SharedPreferenceManager
) : ViewModel() {

    private val _communityData = MutableStateFlow<Result<CommunityResponse>>(Result.Loading)
    val communityData: StateFlow<Result<CommunityResponse>> = _communityData

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _postResult = MutableStateFlow<Result<CommunityResponse>>(Result.Loading)
    val postResult: StateFlow<Result<CommunityResponse>> = _postResult

    private val _komentarList = MutableStateFlow<List<Komentator>>(emptyList())
    val komentarList: StateFlow<List<Komentator>> = _komentarList

    private val _selectedKomentar = MutableStateFlow<List<Komentator>>(emptyList())
    val selectedKomentar: StateFlow<List<Komentator>> = _selectedKomentar

    // LiveData for response (optional)
    private val _komentarResponse = MutableLiveData<Response<KomentatorResponse>>()
    val komentarResponse: LiveData<Response<KomentatorResponse>> = _komentarResponse

    init {
        fetchCommunityData()
        fetchUsers()
    }

    // Fetch users data
    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = apiService.getUsers()
                if (response.success) {
                    _users.value = response.data
                } else {
                    _users.value = emptyList()
                    println("Failed to fetch users: ${response.message}")
                }
            } catch (e: Exception) {
                _users.value = emptyList()
                println("Error fetching users: ${e.message}")
            }
        }
    }

    // Fetch community data using the repository
    fun fetchCommunityData() {
        viewModelScope.launch {
            _communityData.value = Result.Loading
            try {
                val data = communityRepository.fetchCommunityData()
                _communityData.value = Result.Success(data)
            } catch (e: Exception) {
                _communityData.value = Result.Error("Error: ${e.localizedMessage}")
                println("Error fetching community data: ${e.localizedMessage}")
            }
        }
    }

    // Post community data
    fun postCommunityData(idUser: RequestBody, image: MultipartBody.Part, description: RequestBody) {
        _postResult.value = Result.Success(CommunityResponse(
            success = true,
            code = 200,
            message = "Post successful",
            data = listOf()
        ))
    }

    // Mengambil data komentar berdasarkan id_komunitas
    fun fetchKomentarByKomunitasId(idKomunitas: Int) {
        viewModelScope.launch {
            try {
                val commentsData = komentarRepository.getKomentarByKomunitasId(idKomunitas)
                _komentarList.value = commentsData
            } catch (e: Exception) {
                _komentarList.value = emptyList()
                println("Error fetching comments: ${e.localizedMessage}")
            }
        }
    }

    // Post a comment
    fun postKomentar(
        idKomunitas: Int,
        deskripsi: String,
        type: String = "", // Default type is empty string
        onSuccess: (Komentator) -> Unit,
        onError: (String) -> Unit
    ) {
        // Ambil userId dari SharedPreferences melalui injected SharedPreferenceManager
        val userId = sharedPreferenceManager.getUserId()
        if (userId == -1) {
            onError("User ID not found")
            return
        }

        viewModelScope.launch {
            try {
                val komentarRequest = KomentarRequest(
                    id_user = userId,
                    id_komunitas = idKomunitas,
                    deskripsi = deskripsi,
                    type = type // Pass the type value ("" or "like" or "dislike")
                )

                // Make the API call
                val response = komentarRepository.postKomentar(komentarRequest)

                if (response.isSuccessful) {
                    val komentarResponse = response.body()
                    if (komentarResponse != null && komentarResponse.success) {
                        val komentar = komentarResponse.data.firstOrNull()
                        if (komentar != null) {
                            onSuccess(komentar)
                            fetchKomentarByKomunitasId(idKomunitas) // Refresh comments
                        } else {
                            onError("No komentar data found")
                        }
                    } else {
                        onError(komentarResponse?.message ?: "Failed to post comment")
                    }
                } else {
                    onError("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred while posting the comment")
            }
        }
    }



}


    // Post comment to the community
//    fun postKomentar(
//        idKomunitas: Int,
//        deskripsi: String,
//        onSuccess: (Komentator?) -> Unit,
//        onError: (String) -> Unit
//    ) {
//        // Ambil userId dari SharedPreferences melalui injected SharedPreferenceManager
//        val userId = sharedPreferenceManager.getUserId()
//        if (userId == -1) {
//            onError("User ID not found")
//            return
//        }
//
//        viewModelScope.launch {
//            try {
//                val komentar = komentarRepository.postKomentar(userId, idKomunitas, deskripsi)
//                onSuccess(komentar)
//            } catch (e: Exception) {
//                onError(e.localizedMessage ?: "Error posting comment")
//            }
//        }
//    }

