package my.com.toru.huetestproject

import my.com.toru.huetestproject.model.data.Model
import my.com.toru.huetestproject.model.remote.HueNetworkApi
import my.com.toru.huetestproject.util.Util
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testRetrofit(){
        val model = Model(false, null, null)
        HueNetworkApi.api.getData(Util.URL, model).enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                assertEquals(true, response.isSuccessful)
            }
        })
    }

}
