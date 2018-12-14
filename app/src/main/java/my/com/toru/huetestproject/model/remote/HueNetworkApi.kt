package my.com.toru.huetestproject.model.remote

import my.com.toru.huetestproject.model.data.Model
import my.com.toru.huetestproject.util.Util
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Url
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException

interface HueNetworkApi {
    @PUT
    fun getData(@Url url:String, @Body model: Model): Call<String>

    companion object {
        val api: HueNetworkApi = Retrofit.Builder()
                .baseUrl(Util.BASE_URL)
                .client(OkHttp3Initializer.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HueNetworkApi::class.java)
    }

    object OkHttp3Initializer{
        val okHttpClient: OkHttpClient

        init {
            val builder = getUnsafeOkHttpClient()
            okHttpClient = builder
                    .addNetworkInterceptor(initInterceptor())
                    .build()
        }

        private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
            try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { _, _ -> true }

                return builder
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }


        private fun initInterceptor(): HttpLoggingInterceptor
                = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}