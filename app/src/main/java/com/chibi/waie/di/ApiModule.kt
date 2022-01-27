package com.chibi.waie.di

import android.content.Context
import androidx.room.Room
import com.chibi.waie.common.EndPoints
import com.chibi.waie.data.local.dao.IAstronomyDAO
import com.chibi.waie.data.local.database.WaieDataBase
import com.chibi.waie.data.local.repository.AstronomyLocalRepository
import com.chibi.waie.data.remote.APIServices
import com.chibi.waie.data.remote.repository.BaseApiRepositoryImpl
import com.chibi.waie.data.remote.repository_interface.IBaseAPIRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Dependency Injected only complete application, we can able to access single instance
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideBaseUrl() = EndPoints.COMMON_BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient
    {
        var basic = ""

        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            // Set Log Interceptor
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            return OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier(HostnameVerifier { hostname, session -> true })
                .addInterceptor(logging)
                .readTimeout(50, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(APIServices::class.java)

    @Singleton
    @Provides
    fun providesBaseAPIRepositoryImpl(apiServices: APIServices) =
        BaseApiRepositoryImpl(apiServices) as IBaseAPIRepository

    @Provides
    fun providesAstronomyDao(dataBase: WaieDataBase) : IAstronomyDAO = dataBase.astronomyDAO()

    @Provides
    @Singleton
    fun providesUserDatabase(@ApplicationContext context: Context) : WaieDataBase
            = Room.databaseBuilder(context,WaieDataBase::class.java,"WaieDataBase").build()

    @Provides
    fun providesUserRepository(astronomyDao : IAstronomyDAO) : AstronomyLocalRepository
            = AstronomyLocalRepository(astronomyDao)
}