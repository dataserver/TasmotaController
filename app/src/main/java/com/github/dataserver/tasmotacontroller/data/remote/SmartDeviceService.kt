package com.github.dataserver.tasmotacontroller.data.remote

import com.github.dataserver.tasmotacontroller.data.model.DeviceStateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface SmartDeviceService {
    @GET
    fun toggleDeviceState(@Url url: String?): Call<DeviceStateResponse?>?
}