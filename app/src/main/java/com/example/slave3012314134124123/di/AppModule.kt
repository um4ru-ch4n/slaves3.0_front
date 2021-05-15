package com.example.slave3012314134124123.di


import com.example.slave3012314134124123.data.remote.UserApi
import com.example.slave3012314134124123.repository.UserRepository
import com.example.slave3012314134124123.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        api: UserApi
    ) = UserRepository(api)

    @Singleton @Provides
    fun provideUserApi(): UserApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(UserApi::class.java)
    }

}