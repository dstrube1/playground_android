package com.dstrube.savedinstancestatetest

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    var roleText: TextView? = null
    var editText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roleText = findViewById(R.id.roleText)
        editText = findViewById(R.id.editText1)
    }

    fun Click(v: View) {
        val text = editText?.text.toString()
        val role = "Role: $text"
        roleText?.text = role
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("role", roleText?.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val role = savedInstanceState.getString("role")
        //Comment out this line and the magic breaks:
        roleText?.text = role
        Toast.makeText(applicationContext, "Role restored", Toast.LENGTH_SHORT).show()

    }
}
