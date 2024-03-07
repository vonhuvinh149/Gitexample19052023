package com.android.my_app_music.data.repository

import com.android.my_app_music.data.model.Advertisement
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdvertisingRepository {

    private var dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("music")

    fun fetchAdvertisingData(callBack: (MutableList<Advertisement>) -> Unit) {
        dbRef.child("advertisement").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var lists: MutableList<Advertisement> = mutableListOf()
                if (snapshot.exists()) {
                    for (e in snapshot.children) {
                        val item = e.getValue(Advertisement::class.java)
                        item?.let { lists.add(it) }
                    }
                    callBack.invoke(lists)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callBack.invoke(mutableListOf())
            }

        })
    }
}