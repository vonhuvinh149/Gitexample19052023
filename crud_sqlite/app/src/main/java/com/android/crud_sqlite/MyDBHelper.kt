package com.android.crud_sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(private val context: Context) : SQLiteOpenHelper(context, "crud", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE USERS(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
                    "USERNAME TEXT ," +
                    "PASSWORD TEXT" +
                    ");"
        )

        db?.execSQL(
            "INSERT INTO USERS(USERNAME , PASSWORD) " +
                    "VALUES ('vonhuvinh149@gmail.com' , '123456789') ," +
                    "('vonhuvinh@gmail.com' , '123456789') , " +
                    "('vnvpubg149@gmail.com' , '123456789') "
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}