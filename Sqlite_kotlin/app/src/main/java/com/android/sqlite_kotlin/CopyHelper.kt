package com.android.sqlite_kotlin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class CopyHelper(private val context: Context) {

    companion object {
        private val DB_NAME = "dbnew.db"
    }

    fun openDataBase(): SQLiteDatabase {
        val dbFile = context.getDatabasePath(DB_NAME)
        val file = File(dbFile.toString())
        if (file.exists()) {
            Log.e("BBB", "file da ton tai")
        } else {
            copyDataBase(dbFile)
        }
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    private fun copyDataBase(dbFile: File?) {
        val openDB =context.assets.open(DB_NAME)
        val outputStream = FileOutputStream(dbFile)
        val buffer = ByteArray(1024)
        while (openDB.read(buffer) > 0){
            outputStream.write(buffer)
            Log.wtf("BBB" , "Writing")
        }
        outputStream.flush()
        outputStream.close()
        openDB.close()
        Log.wtf("BBB" , "copy db complete")
    }
}