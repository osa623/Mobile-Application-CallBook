package com.example.callbook.ActivityFunctions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.callbook.contactListDatabaseConfigs.contactDatabaseHelper
import com.example.callbook.contactListDatabaseConfigs.contactsAdapter
import com.example.callbook.databinding.ActivityContactListBinding


class ContactListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactListBinding
    private lateinit var db : contactDatabaseHelper
    private lateinit var contactsAdapter : contactsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contackListBackButton.setOnClickListener{
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
        binding.addContactButton.setOnClickListener{
            val intent = Intent(this, AddContactActivity::class.java)
            startActivity(intent)
        }


        db = contactDatabaseHelper(this)
        contactsAdapter = contactsAdapter(db.getAllContacts(), this)

        binding.contactsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.contactsRecyclerView.adapter = contactsAdapter


    }

    override fun onResume() {
        super.onResume()
        contactsAdapter.refreshContacts(db.getAllContacts())
    }
}