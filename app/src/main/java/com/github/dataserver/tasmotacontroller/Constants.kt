package com.github.dataserver.tasmotacontroller

object Constants {
    const val PLACEHOLDER_BASE_URL: String = "http://placeholder.url/" // Can be anything. Not used.

    const val TASMOTA_QUERY_STRING_POWER_STATE: String = "cm?cmnd=Power"
    const val TASMOTA_QUERY_STRING_POWER_TOGGLE: String = "cm?cmnd=Power%20TOGGLE"
}
