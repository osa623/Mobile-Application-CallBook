package com.example.callbook.contactListDatabaseConfigs

import com.example.callbook.ActivityFunctions.ViewContactActivity
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.callbook.R

class contactsAdapter(private var contacts: List<contacts>, private val context: Context) :
    RecyclerView.Adapter<contactsAdapter.contactViewHolder>() {

    class contactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.contactImage)
        val firstNameTextView: TextView = itemView.findViewById(R.id.firstName)
        val lastNameTextView: TextView = itemView.findViewById(R.id.LastName)
        val contactTextView: TextView = itemView.findViewById(R.id.contactNumber)
        val profileView : Button = itemView.findViewById(R.id.ViewProfile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): contactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_view, parent, false)
        return contactViewHolder(view)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: contactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.firstNameTextView.text = contact.firstName
        holder.lastNameTextView.text = contact.lastName
        holder.contactTextView.text = contact.contactNumber.toString()

        holder.profileView.setOnClickListener{
            val intent = Intent(holder.itemView.context, ViewContactActivity::class.java).apply{
                putExtra("contact_id", contacts[position].id) // Corrected accessing the ID of the contact
            }
            holder.itemView.context.startActivity(intent)
        }


        // Convert ByteArray to Bitmap and set it to ImageView
        contact.image?.let {
            val bitmap = byteArrayToBitmap(it)
            holder.imageView.setImageBitmap(bitmap)
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshContacts(newContacts: List<contacts>) {
        contacts = newContacts
        notifyDataSetChanged()
    }

    private fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }


}
