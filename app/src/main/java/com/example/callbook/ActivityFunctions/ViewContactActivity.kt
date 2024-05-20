package com.example.callbook.ActivityFunctions

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.callbook.contactListDatabaseConfigs.contactDatabaseHelper
import com.example.callbook.databinding.ActivityViewContactBinding

class ViewContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewContactBinding
    private lateinit var db: contactDatabaseHelper
    private var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = contactDatabaseHelper(this)

        contactId = intent.getIntExtra("contact_id", -1)
        if (contactId == -1) {
            finish()
            return
        }

        // Fetch the contact details by ID
        val contact = db.getContactById(contactId)
        contact?.let {
            val bitmap = it.image?.let { byteArray ->
                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            }
            binding.addcontactimageview.setImageBitmap(bitmap)
            binding.firstNameViewText.text = it.firstName
            binding.lastNameViewText.text = it.lastName
            binding.contactViewText.text = it.contactNumber.toString()
            binding.emailViewText.text = it.email
            binding.notesViewText.text = it.notes
        } ?: run {
            // Handle the case where the contact is not found
            finish()
            return
        }

        // Set up the back button
        binding.addContactBackButton.setOnClickListener {
            val intent = Intent(this, ContactListActivity::class.java)
            startActivity(intent)
        }
    }
}
