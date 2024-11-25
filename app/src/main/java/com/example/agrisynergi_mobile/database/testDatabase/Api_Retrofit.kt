package com.example.agrisynergi_mobile.database.testDatabase

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//Coba coba database untuk user. Penerapan di halaman notifikasi content

//dataclass sesuai dengan data yang ada di database tabel user
data class User(
    @SerializedName("id_user") val idUser: Int,
    @SerializedName("nama") val name: String,
    @SerializedName("no_hp") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("katasandi") val password: String,
    @SerializedName("role") val role: String,
    @SerializedName("foto") val photo: String?
)

data class Pagination(
    @SerializedName("total") val total: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("total_pages") val totalPages: Int
)

data class UserResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<User>,
    @SerializedName("pagination") val pagination: Pagination,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("errors") val errors: Any?
)

//Api
interface Api {
    @GET("api/users")
    fun getUsers(): Call<UserResponse>
}

//RetrofilClient
class RetrofitClient1 {
    private val BASE_URL = "https://pretty-tips-sort.loca.lt/"

    val instance: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}



