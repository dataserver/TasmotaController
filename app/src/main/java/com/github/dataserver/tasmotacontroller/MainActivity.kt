package com.github.dataserver.tasmotacontroller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.github.dataserver.tasmotacontroller.ui.SmartDeviceListAdapter
import com.github.dataserver.tasmotacontroller.ui.SmartDeviceViewModel
import com.github.dataserver.tasmotacontroller.ui.SmartDeviceViewModelFactory
import com.github.dataserver.tasmotacontroller.util.StatusBarUtils


class MainActivity : AppCompatActivity() {
    private val smartDeviceViewModel: SmartDeviceViewModel by viewModels {
        SmartDeviceViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        StatusBarUtils.setStatusBarColor(this)

        val adapter = SmartDeviceListAdapter(this) // Initialize the adapter for the RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        smartDeviceViewModel.allDevices.observe(this, Observer { devices ->
            // Update the cached copy of the words in the adapter.
            devices.let { adapter.submitList(it) }
        })


        // Set an onClickListener for the FloatingActionButton to launch AddNewActivity
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity, AddActivity::class.java
                )
            )
        }
        val reloadButton = findViewById<Button>(R.id.reload_button)
        reloadButton.setOnClickListener {
            finish();
            startActivity(getIntent());
        }

    }
}