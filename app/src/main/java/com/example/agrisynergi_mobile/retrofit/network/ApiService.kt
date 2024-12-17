package com.example.agrisynergi_mobile.retrofit.network

import com.example.agrisynergi_mobile.database.DatabaseMaps.SawahResponse
import com.example.agrisynergi_mobile.database.ModelKomunitas.CommunityResponse
import com.example.agrisynergi_mobile.database.ModelKomunitas.Komentator
import com.example.agrisynergi_mobile.database.ModelKomunitas.KomentatorResponse
import com.example.agrisynergi_mobile.retrofit.model.ApiResponse
import com.example.agrisynergi_mobile.retrofit.model.LoginRequest
import com.example.agrisynergi_mobile.retrofit.model.LoginResponse
import com.example.agrisynergi_mobile.retrofit.model.User
import com.example.agrisynergi_mobile.retrofit.model.UserRequest
import com.example.agrisynergi_mobile.retrofit.model.UserResponse
import retrofit2.Call
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(): ApiResponse

//    @POST("register")
    @POST("auth/register")
    suspend fun registerUser(@Body userRequest: UserRequest): Response<UserResponse>

//    @POST("login")
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    // ambil data berdasarkan lokasi
    @GET("sawah")
    suspend fun getSawahByLokasi(@Query("lokasi") lokasi: String): Response<SawahResponse>

    // ambil semua list data sawah
    @GET("sawah")
    suspend fun getSawahList(): Response<SawahResponse>

    //ambil semua data komunitas
    @GET("komunitas")  // Replace with actual API endpoint
    suspend fun getCommunityData(): CommunityResponse

    // Tambahkan data komunitas (POST)
    @Multipart
    @POST("komunitas")
    suspend fun addCommunityPost(
        @Part("id_user") idUser: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("deskripsi") description: RequestBody
    ): Response<CommunityResponse>
//    @GET("user/profile")
//    suspend fun getUserProfile(): User
//
//    @POST("user/update")
//    suspend fun updateUserProfile(@Body user: User): ApiResponse

    @PUT("api/users/{id_user}")
    fun updateUserProfile(
        @Path("id_user") userId: Int,
        @Body request: UserRequest
    ): Call<UserResponse>




//data class ApiResponse(
//    val success: Boolean,
//    val message: String
//)

    // Fungsi untuk mengambil komentar berdasarkan id_komunitas
    @GET("komentator")
    suspend fun getKomentarByKomunitas(
        @Query("id_komunitas") idKomunitas: Int // Menggunakan query parameter untuk id_komunitas
    ): Response<List<Komentator>>

    @GET("komentator")
    suspend fun getKomentator(): Response<KomentatorResponse>





}

