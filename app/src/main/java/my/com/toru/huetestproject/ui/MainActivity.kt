package my.com.toru.huetestproject.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import my.com.toru.huetestproject.R
import my.com.toru.huetestproject.model.data.Model
import my.com.toru.huetestproject.model.remote.HueNetworkApi
import my.com.toru.huetestproject.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seek_bar_hue.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.w(TAG, "progress:: $progress")
                callNetwork(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun callNetwork(bri:Int){
        val model = Model(true, null, bri)
        HueNetworkApi.api.getData(Util.URL, model).enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
            }
        })
    }
}
