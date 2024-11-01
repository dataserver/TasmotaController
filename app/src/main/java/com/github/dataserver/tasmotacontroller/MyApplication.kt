package com.github.dataserver.tasmotacontroller

import android.app.Application
import com.github.dataserver.tasmotacontroller.data.SmartDeviceRepository
import com.github.dataserver.tasmotacontroller.data.local.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { SmartDeviceRepository(database.smartDeviceDao()) }
}
