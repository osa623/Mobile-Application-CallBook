package com.example.callbook.ActivityFunctions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.callbook.databinding.ActivityViewContactBinding

class ViewContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewContactBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addContactBackButton.setOnClickListener{

            val intent = Intent(this, ViewContactActivity::class.java)
            startActivity(intent)
        }
    }
}