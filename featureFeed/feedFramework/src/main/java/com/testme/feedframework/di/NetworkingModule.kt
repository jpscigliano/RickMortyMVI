package com.testme.feedframework.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.testme.feedframework.remote.rest.api.CharactersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

  @Provides
  @Singleton
  fun provideHttpClient(): OkHttpClient = OkHttpClient().newBuilder().build()

  @Provides
  @Singleton
  fun getRetrofit(client: OkHttpClient): Retrofit =
    Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
      .addConverterFactory(Json {
        ignoreUnknownKeys = true
      }.asConverterFactory(MediaType.get("application/json")))
      .client(client)
      .build()

  @Provides
  @Singleton
  fun getCharactersApi(retrofit: Retrofit): CharactersApi =
    retrofit.create(CharactersApi::class.java)


}