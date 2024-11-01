package com.github.dataserver.tasmotacontroller.data.model

import com.squareup.moshi.Json

class DeviceStateResponse {
    @Json(name = "POWER")
    var power: String? = null
}