package com.github.dataserver.tasmotacontroller.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.github.dataserver.tasmotacontroller.Constants.TASMOTA_QUERY_STRING_POWER_TOGGLE
import com.github.dataserver.tasmotacontroller.R
import com.github.dataserver.tasmotacontroller.UpdateActivity
import com.github.dataserver.tasmotacontroller.data.model.DeviceStateResponse
import com.github.dataserver.tasmotacontroller.data.model.SmartDevice
import com.github.dataserver.tasmotacontroller.data.remote.RetrofitClient.client
import com.github.dataserver.tasmotacontroller.data.remote.SmartDeviceService
import com.github.dataserver.tasmotacontroller.data.SmartDeviceDiff
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SmartDeviceListAdapter(
    private val context: Context,
) : ListAdapter<SmartDevice, SmartDeviceViewHolder>(SmartDeviceDiff()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmartDeviceViewHolder {
        val row_item_view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_row_item, parent, false)
        val smartDeviceViewHolder = SmartDeviceViewHolder(row_item_view)
        val context = row_item_view.context

        smartDeviceViewHolder.itemView.setOnClickListener { view: View ->
            val intent = Intent(view.context, UpdateActivity::class.java)
            intent.putExtra("id", "" + smartDeviceViewHolder.id)
            view.context.startActivity(intent)
        }

        smartDeviceViewHolder.btn_toggle.setOnClickListener { view: View? ->
            val position = smartDeviceViewHolder.adapterPosition // Get position
            if (position != RecyclerView.NO_POSITION) {
                val currentDevice = getItem(position) // Get device at position
                toggleDeviceState(currentDevice, context, smartDeviceViewHolder)
            }
        }
        smartDeviceViewHolder.btn_openlink.setOnClickListener { view: View? ->
            val position = smartDeviceViewHolder.adapterPosition // Get position
            if (position != RecyclerView.NO_POSITION) {
                openLink(smartDeviceViewHolder.url)
            }
        }

        return smartDeviceViewHolder
    }

    override fun onBindViewHolder(holder: SmartDeviceViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    private fun toggleDeviceState(
        device: SmartDevice?,
        context: Context,
        holder: SmartDeviceViewHolder
    ) {
        val urlToggle = device!!.url + TASMOTA_QUERY_STRING_POWER_TOGGLE

        val service = client!!.create(
            SmartDeviceService::class.java
        )
        val call = service.toggleDeviceState(urlToggle)

        call?.enqueue(object : Callback<DeviceStateResponse?> {
            override fun onResponse(
                call: Call<DeviceStateResponse?>,
                response: Response<DeviceStateResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val mySwitch = holder.itemView.findViewById<SwitchMaterial>(R.id.row_btn_toggle)
                    val powerState = response.body()!!.power

                    // Set the switch state based on power state
                    mySwitch.isChecked = "ON" == powerState

                    Toast.makeText(context, "Device is $powerState", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        context,
                        "Something went wrong (Response not successful)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<DeviceStateResponse?>, t: Throwable) {
                Log.e("toggleDeviceState Error", "Error : " + t.message)
                Toast.makeText(context, "Something went wrong (Exception)", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}

