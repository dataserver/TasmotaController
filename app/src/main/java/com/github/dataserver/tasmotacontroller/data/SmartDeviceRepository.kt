package com.github.dataserver.tasmotacontroller.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.github.dataserver.tasmotacontroller.data.local.SmartDeviceDao
import com.github.dataserver.tasmotacontroller.data.model.SmartDevice
import kotlinx.coroutines.flow.Flow


class SmartDeviceRepository(private val smartDeviceDao: SmartDeviceDao) {

    val allDevices: Flow<List<SmartDevice>> = smartDeviceDao.getAllDevices()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(device: SmartDevice) {
        smartDeviceDao.insert(device)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(device: SmartDevice) {
        smartDeviceDao.update(device)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(device: SmartDevice) {
        smartDeviceDao.delete(device)
    }

    fun findDeviceById(id: Int): LiveData<SmartDevice> {
        return smartDeviceDao.findDeviceById(id)
    }
}