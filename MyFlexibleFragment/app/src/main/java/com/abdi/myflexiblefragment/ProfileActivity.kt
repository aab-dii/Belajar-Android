package com.abdi.myflexiblefragment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnHome = findViewById<Button>(R.id.btn_home)

        btnHome.setOnClickListener{
            val moveToHome = Intent(this, MainActivity::class.java)
            startActivity(moveToHome)
        }
    }
}