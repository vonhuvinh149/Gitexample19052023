package com.android.firebase_1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.Calendar

class SaveImageActivity : AppCompatActivity() {

    private val storage = FirebaseStorage.getInstance()
    private var img: ImageView? = null
    private var btnSave: Button? = null
    private val REQUEST_CODE = 1
    private lateinit var storageRef: StorageReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_image)
        storageRef = storage.reference
        initView()
        event()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val bitmap: Bitmap = data.extras?.get("data") as Bitmap
            img?.setImageBitmap(bitmap)
        }
    }

    private fun event() {
        img?.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(intent, REQUEST_CODE)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }

        btnSave?.setOnClickListener {
            img?.isDrawingCacheEnabled = true
            img?.buildDrawingCache()

            val calender = Calendar.getInstance()

            val mountainsRef = storageRef.child("mountains" + calender.timeInMillis + ".png")

            val bitmap = (img?.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val data = baos.toByteArray()

            var uploadTask = mountainsRef.putBytes(data)
            uploadTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Upload thành công, lấy đường dẫn (Uri) của ảnh
                    mountainsRef.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()

                        Log.d("BBB" ,imageUrl )
                    }.addOnFailureListener {
                    }
                } else {
                    // Upload thất bại, xử lý lỗi nếu cần

                }
            }

        }
    }

    private fun initView() {
        img = findViewById(R.id.img_snapshot)
        btnSave = findViewById(R.id.btn_save)
    }
}