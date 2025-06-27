package com.vkdev.numbersinfo.di

import android.content.Context
import androidx.room.Room
import com.vkdev.numbersinfo.data.AppDatabase
import com.vkdev.numbersinfo.data.api.NumbersApi
import com.vkdev.numbersinfo.data.dao.NumbersDao
import com.vkdev.numbersinfo.data.repository.NumbersRepositoryImpl
import com.vkdev.numbersinfo.domain.repository.NumbersRepository
import com.vkdev.numbersinfo.domain.useCase.GetNumberByIdUseCase
import com.vkdev.numbersinfo.domain.useCase.GetNumberInfoUseCase
import com.vkdev.numbersinfo.domain.useCase.GetPagedNumbersUseCase
import com.vkdev.numbersinfo.domain.useCase.GetRandomInfoUseCase
import com.vkdev.numbersinfo.domain.useCase.SaveNumberUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNumbersApi(): NumbersApi {
        val link = "http://numbersapi.com/"

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(link)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NumbersApi::class.java)
    }

    @Provides
    fun provideNumberRepository(numbersApi: NumbersApi, numbersDao: NumbersDao): NumbersRepository {
        return NumbersRepositoryImpl(numbersApi, numbersDao)
    }

    @Provides
    fun provideGetNumberUseCase(numbersRepository: NumbersRepository): GetNumberInfoUseCase {
        return GetNumberInfoUseCase(numbersRepository)
    }

    @Provides
    fun provideSaveNumberUseCase(numbersRepository: NumbersRepository): SaveNumberUseCase {
        return SaveNumberUseCase(numbersRepository)
    }

    @Provides
    fun provideGetRandomInfoUseCase(numbersRepository: NumbersRepository): GetRandomInfoUseCase {
        return GetRandomInfoUseCase(numbersRepository)
    }

    @Provides
    fun provideGetNumberByIdUseCase(numbersRepository: NumbersRepository): GetNumberByIdUseCase {
        return GetNumberByIdUseCase(numbersRepository)
    }

    @Provides
    fun provideGetPagedNumbersUseCase(numbersRepository: NumbersRepository): GetPagedNumbersUseCase {
        return GetPagedNumbersUseCase(numbersRepository)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "numbers_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDetailsDao(db: AppDatabase): NumbersDao {
        return db.numbersDao()
    }
}