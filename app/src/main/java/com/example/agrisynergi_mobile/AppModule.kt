package com.example.agrisynergi_mobile

import com.example.agrisynergi_mobile.retrofit.network.ApiService
import com.example.agrisynergi_mobile.retrofit.network.RetrofitInstance
import com.example.agrisynergi_mobile.database.ModelKomunitas.CommunityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitInstance.retrofit  // Ensure RetrofitInstance is properly configured
    }

    @Provides
    @Singleton
    fun provideForumRepository(apiService: ApiService): CommunityRepository {
        return CommunityRepository(apiService)  // Make sure ForumRepository exists in the correct package
    }
}
