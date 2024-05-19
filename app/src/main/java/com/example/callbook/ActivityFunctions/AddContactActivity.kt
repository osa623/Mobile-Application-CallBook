package com.example.callbook.ActivityFunctions

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.callbook.contactListDatabaseConfigs.contacts
import com.example.callbook.contactListDatabaseConfigs.contactDatabaseHelper
import com.example.callbook.databinding.ActivityAddContactBinding
import java.io.ByteArrayOutputStream

class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding
    private lateinit var db: contactDatabaseHelper
    private var selectedImage: Bitmap? = null

    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addContactBackButton.setOnClickListener {
            val intent = Intent(this, ContactListActivity::class.java)
            startActivity(intent)
        }

        db = contactDatabaseHelper(this)

        binding.addContactImagerButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }

        binding.contactSaveButton.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val contactNumber = binding.contactEditText.text.toString().toInt()
            val email = binding.emailEditText.text.toString()
            val notes = binding.addNotesEditText.text.toString()

            val imageByteArray = selectedImage?.let { bitmapToByteArray(it) }
            val contactList = contacts(0, imageByteArray, firstName, lastName, contactNumber, email, notes)

            db.insertContactDetails(contactList)
            Toast.makeText(this, "Contact has been saved successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val imageUri = data?.data
            selectedImage = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
            binding.addcontactimageview.setImageBitmap(selectedImage)
        }
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}
