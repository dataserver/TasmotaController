package com.github.dataserver.tasmotacontroller.data

import androidx.recyclerview.widget.DiffUtil
import com.github.dataserver.tasmotacontroller.data.model.SmartDevice

class SmartDeviceDiff : DiffUtil.ItemCallback<SmartDevice>() {
    override fun areItemsTheSame(oldItem: SmartDevice, newItem: SmartDevice): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SmartDevice, newItem: SmartDevice): Boolean {
        return (
                (oldItem.name == newItem.name && oldItem.url == newItem.url)
                        || (oldItem.id == newItem.id)
                )
    }
}
