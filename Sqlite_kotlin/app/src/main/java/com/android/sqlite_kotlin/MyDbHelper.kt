package com.android.sqlite_kotlin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, "FURAMA_MANAGER", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        // create table
        db?.execSQL(
            "CREATE TABLE CUSTOMER (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
                    "NAME TEXT ," +
                    "AGE INTEGER" +
                    ");"
        )

        db?.execSQL(
            "INSERT INTO CUSTOMER(NAME , AGE ) " +
                    "VALUES ('Vo Nhu Vinh' , 26) , " +
                    "('Nguyen Thi Thu Huong' , 24) , " +
                    "('Nguyen Van Nam' , 27) ;"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}