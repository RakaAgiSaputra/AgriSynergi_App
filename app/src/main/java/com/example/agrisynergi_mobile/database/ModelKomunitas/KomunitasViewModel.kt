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
import com.example.agrisynergi_mobile.database.ModelKomunitas.Result

@HiltViewModel
class ForumViewModel @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val apiService: ApiService
) : ViewModel() {

    private val _communityData = MutableStateFlow<Result<CommunityResponse>>(Result.Loading)
    val communityData: StateFlow<Result<CommunityResponse>> = _communityData

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _postResult = MutableStateFlow<Result<CommunityResponse>>(Result.Loading)
    val postResult: StateFlow<Result<CommunityResponse>> = _postResult

    init {
        fetchCommunityData()
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = apiService.getUsers()  // Memanggil API untuk mendapatkan data
                // Memeriksa apakah respons sukses
                if (response.success) {
                    // Menyimpan data user yang berhasil diambil ke dalam state
                    _users.value = response.data
                } else {
                    // Jika respons tidak berhasil, kembalikan list kosong
                    _users.value = emptyList()
                    println("Failed to fetch users: ${response.message}")
                }
            } catch (e: Exception) {
                // Menangani error jika terjadi exception
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
//    fun postCommunityData(
//        idUser: RequestBody,
//        image: MultipartBody.Part,
//        description: RequestBody
//    ) {
//        viewModelScope.launch {
//            _postResult.value = Result.Loading
//            try {
//                val result = communityRepository.postCommunityData(idUser, image, description)
//                _postResult.value = result
//            } catch (e: Exception) {
//                _postResult.value = Result.Error("Error: ${e.localizedMessage}")
//            }
//        }
//    }

    // Post community data
    fun postCommunityData(idUser: RequestBody, image: MultipartBody.Part, description: RequestBody) {

        _postResult.value = Result.Success(CommunityResponse(
            success = true,
            code = 200,
            message = "Post successful",
            data = listOf()
        ))
    }


}
