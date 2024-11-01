package com.github.dataserver.tasmotacontroller.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.dataserver.tasmotacontroller.data.model.SmartDevice
import kotlinx.coroutines.flow.Flow

@Dao
interface SmartDeviceDao {

    @Query("SELECT * FROM smart_devices ORDER BY name ASC")
    fun getAllDevices(): Flow<List<SmartDevice>>

    @Query("SELECT * FROM smart_devices WHERE id= :id")
    fun findDeviceById(id: Int): LiveData<SmartDevice>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(device: SmartDevice)

    @Update
    suspend fun update(device: SmartDevice)

    @Delete
    suspend fun delete(device: SmartDevice)

    @Query("DELETE FROM smart_devices")
    suspend fun deleteAll()
}