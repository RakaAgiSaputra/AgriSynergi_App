package com.example.agrisynergi_mobile.database.testDatabase

import com.example.agrisynergi_mobile.database.DatabaseKalender.Kalender
import com.example.agrisynergi_mobile.database.DatabaseKalender.KalenderResponse
import com.google.android.gms.common.api.Response
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

data class Produk(
    val id_produk: Int,
    val id_user: Int,
    val id_kategori: Int,
    val nama: String,
    val harga: String,
    val kuantitas: Int,
    val deskripsi: String,
    val tanggal_diposting: String,
    val foto_produk: String,
    val nama_kategori: String,
    val rata_rating: String?,
    val user_komen: String?,
    val komentar: String?,
    val tanggal_ulasan: String?
)

data class Keranjang(
    val id_keranjang: Int,
    val id_produk: Int,
    val id_user: Int,
    val total_produk: Int,
    val total_harga: String,
    val nama_produk: String,
    val foto_produk: String
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

data class ProdukResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Produk>,
    @SerializedName("pagination") val pagination: Pagination,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("errors") val errors: Any?
)

data class KeranjangResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Keranjang>,
    @SerializedName("pagination") val pagination: Pagination,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("errors") val errors: Any?
)

data class CartRequest(
    val id_produk: Int,
    val total_produk: Int
)

data class AddToCartResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Keranjang>,
    @SerializedName("pagination") val pagination: Pagination,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("errors") val errors: Any?
)

//Api
interface Api {
    @GET("api/users")
    fun getUsers(): Call<UserResponse>

    @GET("api/produk")
    fun getProduk(): Call<ProdukResponse>

    @FormUrlEncoded
    @POST("api/keranjang")
    suspend fun addToCart(
        @Field("id_produk") productId: Int,
        @Field("id_user") userId: Int,
        @Field("total_produk") quantity: Int
    ): retrofit2.Response<AddToCartResponse>

    @GET("api/keranjang")
    suspend fun getKeranjang(): retrofit2.Response<KeranjangResponse>

    @GET("api/kalender")
    fun getKalender(): Call<KalenderResponse>

    @POST("api/kalender")
    suspend fun addKalender(@Body kalender: Kalender): Call<KalenderResponse>
}

//RetrofilClient
class RetrofitClient1 {
    private val BASE_URL = "http://36.74.38.214:8080/"

    val instance: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}



