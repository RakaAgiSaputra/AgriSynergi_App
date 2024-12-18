package com.example.agrisynergi_mobile

import android.app.Application
import android.content.Context
import com.example.agrisynergi_mobile.retrofit.network.ApiService
import com.example.agrisynergi_mobile.retrofit.network.RetrofitInstance
import com.example.agrisynergi_mobile.database.ModelKomunitas.CommunityRepository
import com.example.agrisynergi_mobile.retrofit.model.view.viewmodel.SharedPreferenceManager
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
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

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

    @Provides
    fun provideSharedPreferenceManager(context: Context): SharedPreferenceManager {
        return SharedPreferenceManager(context)
    }
}
