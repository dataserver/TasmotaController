package com.github.dataserver.tasmotacontroller.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.dataserver.tasmotacontroller.data.model.SmartDevice
import com.github.dataserver.tasmotacontroller.data.SmartDeviceRepository
import kotlinx.coroutines.launch


/**
 * View Model to keep a reference to the Smart Device repository and
 * an up-to-date list of all devices.
 */
class SmartDeviceViewModel(private val repository: SmartDeviceRepository) : ViewModel() {

    val allDevices: LiveData<List<SmartDevice>> = repository.allDevices.asLiveData()

    fun findDeviceById(id: Int): LiveData<SmartDevice> {
        return repository.findDeviceById(id)
    }

    fun insert(device: SmartDevice) = viewModelScope.launch {
        repository.insert(device)
    }

    fun update(device: SmartDevice) = viewModelScope.launch {
        repository.update(device)
    }

    fun delete(device: SmartDevice) = viewModelScope.launch {
        repository.delete(device)
    }


}

class SmartDeviceViewModelFactory(private val repository: SmartDeviceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SmartDeviceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SmartDeviceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
