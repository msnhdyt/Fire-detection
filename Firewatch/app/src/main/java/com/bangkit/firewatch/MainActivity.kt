package com.bangkit.firewatch

import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: AppCompatButton = findViewById(R.id.buttonclasifikasi)
        val imageView4: ImageView = findViewById(R.id.img_logo)
        button.setOnClickListener {

            val queue = Volley.newRequestQueue(this)
            val url = "https://0783c861cb8b.ngrok.io/get_status"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val person = JSONObject(response)
                    val imageByteArray: ByteArray = Base64.decode(person.getString("img"), Base64.DEFAULT)
                    Glide.with(this)
                        .asBitmap()
                        .load(imageByteArray)
                        .placeholder(R.drawable.ic_broken_image)
                        .into(imageView4);
                },
                { _ ->
                    Toast.makeText(
                        this,
                        "failed connect to the server, please try again and make sure your internet connection is stable.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                })
            queue.add(stringRequest)
        }
    }


}