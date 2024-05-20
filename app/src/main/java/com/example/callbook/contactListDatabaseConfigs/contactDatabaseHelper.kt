package com.example.callbook.contactListDatabaseConfigs

import android.content.ClipData.Item
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.net.IDN

// This is the Database helper for this database
class contactDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Companion object containing constants for the database configuration
    companion object {
        private const val DATABASE_NAME = "contact_database.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "contactList"
        private const val COLUMN_ID = "id"
        private const val COLUMN_IMAGE = "image"
        private const val COLUMN_FIRSTNAME = "first_name"
        private const val COLUMN_LASTNAME = "last_name"
        private const val COLUMN_CONTACTNUMBER = "contact_number"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_NOTES = "notes"
    }

    // Creating the database
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY,
                $COLUMN_FIRSTNAME TEXT,
                $COLUMN_LASTNAME TEXT,
                $COLUMN_CONTACTNUMBER INTEGER,
                $COLUMN_EMAIL TEXT,
                $COLUMN_NOTES TEXT,
                $COLUMN_IMAGE BLOB
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    // Upgrading the database
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    //Implementation of data inserting function to the database
    fun insertContactDetails(contact: contacts) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IMAGE, contact.image)
            put(COLUMN_FIRSTNAME, contact.firstName)
            put(COLUMN_LASTNAME, contact.lastName)
            put(COLUMN_CONTACTNUMBER, contact.contactNumber)
            put(COLUMN_EMAIL, contact.email)
            put(COLUMN_NOTES, contact.notes)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Fetch all contacts from the database
    fun getAllContacts(): List<contacts> {
        val contactsList = mutableListOf<contacts>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
            val firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRSTNAME))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LASTNAME))
            val contactNumber = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CONTACTNUMBER))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES))

            val contact = contacts(id, image, firstName, lastName, contactNumber, email, notes)
            contactsList.add(contact)
        }
        cursor.close()
        db.close()
        return contactsList
    }

    // Fetch a contact by ID
    fun getContactById(id: Int): contacts? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        var contact: contacts? = null
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
            val firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRSTNAME))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LASTNAME))
            val contactNumber = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CONTACTNUMBER))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES))

            contact = contacts(id, image, firstName, lastName, contactNumber, email, notes)
        }
        cursor.close()
        db.close()
        return contact
    }

}
