package com.android.content_provider

import android.content.ContentResolver
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    private var recyclerView: RecyclerView? = null
    private lateinit var phoneAdapter: PhoneAdapter
    private var list = mutableListOf<PhoneBook>()
    private lateinit var contentResolver: ContentResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        event()

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf<String>(
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts._ID,
            ),
            null,
            null,
            null
        ) as Cursor

        try {
            if (cursor.moveToFirst()) {
                val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                val displayName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

                do {
                    val contactId = cursor.getString(idIndex)
                    val contactName = cursor.getString(displayName)
                    list.add(PhoneBook(contactId, contactName))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {

        } finally {
            cursor.close()
        }

        phoneAdapter = PhoneAdapter(mutableListOf())
        recyclerView?.adapter = phoneAdapter
        phoneAdapter.updateData(list)

    }

    private fun event() {
        recyclerView = findViewById(R.id.recycler_view)
        contentResolver = getContentResolver()
    }
}