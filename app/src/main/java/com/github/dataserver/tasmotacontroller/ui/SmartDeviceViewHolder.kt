package com.github.dataserver.tasmotacontroller.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.github.dataserver.tasmotacontroller.Constants.TASMOTA_QUERY_STRING_POWER_STATE
import com.github.dataserver.tasmotacontroller.R
import com.github.dataserver.tasmotacontroller.data.model.DeviceStateResponse
import com.github.dataserver.tasmotacontroller.data.model.SmartDevice
import com.github.dataserver.tasmotacontroller.data.remote.RetrofitClient.client
import com.github.dataserver.tasmotacontroller.data.remote.SmartDeviceService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class SmartDeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var id: Int = 0
    var url: String = ""
    val rowItemView: TextView = itemView.findViewById(R.id.row_device_name)
    var btn_toggle: SwitchMaterial = itemView.findViewById(R.id.row_btn_toggle)
    var btn_openlink: Button = itemView.findViewById(R.id.row_btn_openlink)

    fun bind(device: SmartDevice) {
        rowItemView.text = device.name
        id = device.id!!
        url = device.url
        updateButtonState(device.url)
    }

    private fun updateButtonState(urlState: String) {
        val url = urlState + TASMOTA_QUERY_STRING_POWER_STATE
        val service = client!!.create(
            SmartDeviceService::class.java
        )
        val call = service.toggleDeviceState(url)

        call?.enqueue(object : Callback<DeviceStateResponse?> {
            override fun onResponse(
                call: Call<DeviceStateResponse?>,
                response: Response<DeviceStateResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val powerState = response.body()!!.power
                    val mySwitch = itemView.findViewById<SwitchMaterial>(R.id.row_btn_toggle)
                    // Set the switch state based on power state
                    mySwitch.isChecked = "ON" == powerState
                } else {
                }
            }

            override fun onFailure(call: Call<DeviceStateResponse?>, t: Throwable) {
                Log.e("toggleDeviceState Error", "Error : " + t.message)
            }
        })
    }

    companion object {
        fun create(parent: ViewGroup): SmartDeviceViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_row_item, parent, false)
            return SmartDeviceViewHolder(view)
        }
    }
}

