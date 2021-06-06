package com.bangkit.firewatch.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import android.util.Base64
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bangkit.firewatch.R
import com.bangkit.firewatch.databinding.FragmentNotificationBinding
import com.bumptech.glide.Glide
import org.json.JSONObject




class NotificationFragment : Fragment() {

    private lateinit var notificationViewModel: NotificationViewModel
    private var _binding: FragmentNotificationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notificationViewModel =
            ViewModelProvider(this).get(NotificationViewModel::class.java)

        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val button: AppCompatButton = binding.buttonclasifikasi
        val imageView4: ImageView = binding.imgLogo
        button.setOnClickListener {
            val queue = Volley.newRequestQueue(binding.root.context)
            val url = "https://1caccf0db0e5.ngrok.io/get_status"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val person = JSONObject(response)
                    val imageByteArray: ByteArray = Base64.decode(person.getString("img"), Base64.DEFAULT)
                    Glide.with(this)
                        .asBitmap()
                        .load(imageByteArray)
                        .placeholder(R.drawable.ic_broken_image)
                        .into(imageView4)
                },
                { _ ->
                    Toast.makeText(
                        binding.root.context,
                        "failed connect to the server, please try again and make sure your internet connection is stable.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                })
            queue.add(stringRequest)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}