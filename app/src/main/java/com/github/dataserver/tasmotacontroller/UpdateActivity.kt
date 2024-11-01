package com.github.dataserver.tasmotacontroller

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.github.dataserver.tasmotacontroller.data.model.SmartDevice
import com.github.dataserver.tasmotacontroller.ui.SmartDeviceViewModel
import com.github.dataserver.tasmotacontroller.ui.SmartDeviceViewModelFactory
import com.github.dataserver.tasmotacontroller.util.StatusBarUtils

class UpdateActivity : AppCompatActivity() {
    private val viewModel: SmartDeviceViewModel by viewModels {
        SmartDeviceViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        StatusBarUtils.setStatusBarColor(this)

        val id = intent.getStringExtra("id")?.toIntOrNull() ?: throw IllegalArgumentException("ID must be provided")

        val editName = findViewById<EditText>(R.id.edit_name)
        val editUrl = findViewById<EditText>(R.id.edit_url)

        viewModel.findDeviceById(id).observe(this) { device: SmartDevice? ->
            if (device != null) {
                editName.setText(device.name)
                editUrl.setText(device.url)
            } else {
                finish()
            }
        }

        val btn_save = findViewById<Button>(R.id.save_button)
        btn_save.setOnClickListener { view: View ->
            if (editName.text.toString().isEmpty()) {
                Toast.makeText(view.context, "Please enter device name", Toast.LENGTH_SHORT).show()
            } else if (editUrl.text.toString().isEmpty()) {
                Toast.makeText(
                    view.context,
                    "Please enter URL to toggle device",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val device = SmartDevice(id, editName.text.toString(), editUrl.text.toString())
                viewModel.update(device)
                finish()
            }
        }

        val btn_delete = findViewById<Button>(R.id.delete_button)
        btn_delete.setOnClickListener { view: View ->
            val builder: AlertDialog.Builder = MaterialAlertDialogBuilder(view.context)
            builder.setTitle("Delete Device")
            builder.setMessage("Are you sure you want to delete this device?")
            builder.setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                val device = SmartDevice(id, editName.text.toString(), editUrl.text.toString())
                viewModel.delete(device)
                finish()
            }
            builder.setNegativeButton("No", null)
            builder.show()
        }
    }
}