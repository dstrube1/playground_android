package com.dstrube.samplesqllite

import android.content.Intent
import android.widget.EditText
import android.os.Bundle
import android.app.Activity
import android.view.View


class NewAnimal : Activity() {
    private var animalName: EditText? = null
    private var animalTallness: EditText? = null

    private var controller = DBController(this)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_animal)
        animalName = findViewById(R.id.animalName)
        animalTallness = findViewById(R.id.animalTallness)

    }

    fun addNewAnimal(view: View) {
        val queryValues = HashMap<String, String>()
        queryValues["animalName"] = animalName?.text.toString()
        queryValues["animalTallness"] = animalTallness?.text.toString()

        controller.insertAnimal(queryValues)
        this.callHomeActivity(view)
    }

    private fun callHomeActivity(view: View) {
        val objIntent = Intent(applicationContext, MainActivity::class.java)
        startActivity(objIntent)
    }
}
