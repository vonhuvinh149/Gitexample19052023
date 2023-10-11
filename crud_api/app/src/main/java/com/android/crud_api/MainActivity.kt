package com.android.crud_api

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    private val urlGetData: String = "http://192.168.1.12:8080/customers?size=2&page=2"
    private var arrCustomer: ArrayList<String> = ArrayList()
    private var arrAdapter: ArrayAdapter<String>? = null
    private var listView: ListView? = null
    private var showMore : TextView? = null
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.list_view_customer)
        showMore = findViewById(R.id.show_more)


        GetData().execute(urlGetData)

        arrAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrCustomer)

        listView?.adapter = arrAdapter

        showMore?.setOnClickListener {
            page++
            GetData().execute("$urlGetData?size=$page")
            arrAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrCustomer)

            listView?.adapter = arrAdapter
        }

    }




    inner class GetData : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String {
            return getContentUrl(params[0])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            var jsonArray: JSONArray = JSONObject(result).getJSONArray("content")

            var name: String = ""
            var email: String = ""

            for (i in 0..jsonArray.length() - 1) {
                var customer: JSONObject = jsonArray.getJSONObject(i)
                name = customer.getString("name")
                email = customer.getString("email")
                arrCustomer.add("$name -  $email")
            }
        }
    }

    private fun getContentUrl(url: String?): String {
        var content: StringBuilder = StringBuilder()
        val url: URL = URL(url)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val inputStream: InputStreamReader = InputStreamReader(urlConnection.inputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStream)

        var line: String = ""

        try {
            do {
                line = bufferedReader.readLine()
                if (line != null) {
                    content.append(line)
                }
            } while (bufferedReader.readLine() != null)
            bufferedReader.close()
        } catch (e: Exception) {
            Log.d("BBB", e.toString())
        }
        return content.toString()
    }
}