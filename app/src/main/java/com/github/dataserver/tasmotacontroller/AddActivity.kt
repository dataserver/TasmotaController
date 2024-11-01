package com.github.dataserver.tasmotacontroller

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.dataserver.tasmotacontroller.data.model.SmartDevice
import com.github.dataserver.tasmotacontroller.ui.SmartDeviceViewModel
import com.github.dataserver.tasmotacontroller.util.StatusBarUtils
import android.text.TextUtils
import androidx.activity.viewModels
import com.github.dataserver.tasmotacontroller.ui.SmartDeviceViewModelFactory


class AddActivity : AppCompatActivity() {
    private val viewModel: SmartDeviceViewModel by viewModels {
        SmartDeviceViewModelFactory((application as MyApplication).repository)
    }
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        StatusBarUtils.setStatusBarColor(this)


        val edit_name = findViewById<EditText>(R.id.edit_name)
        val edit_url = findViewById<EditText>(R.id.edit_url)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener { view: View ->

            if (TextUtils.isEmpty(edit_name.text)) {
                Toast.makeText(view.context, "Please name", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(edit_url.text)) {
                Toast.makeText(view.context, "Please enter IP", Toast.LENGTH_SHORT).show()
            } else {
                val device = SmartDevice(name = edit_name.text.toString(), url = edit_url.text.toString())
                viewModel.insert(device)
                finish()
            }
        }
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}