package com.bangkit.firewatch.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bangkit.firewatch.R
import com.bangkit.firewatch.databinding.FragmentHomeBinding
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val toggle: SwitchCompat = binding.switchFirewatch

        if (sharedPref != null) {
            toggle.isChecked = sharedPref.getBoolean(getString(R.string.notification), false)
        }


        toggle.setOnCheckedChangeListener { _, isChecked ->
            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                if (it.isComplete) {
                    val min = 1
                    val max = 8000

                    val r = Random()
                    val random: Int = r.nextInt(max - min + 1) + min
                    val queue = Volley.newRequestQueue(binding.root.context)
                    val urlOn = "https://0783c861cb8b.ngrok.io/turn_on"
                    val stringRequest1 = StringRequest(Request.Method.GET, urlOn,
                        { response ->
//                            Toast.makeText(
//                                binding.root.context,
//                                "CCTV connected",
//                                Toast.LENGTH_SHORT
//                            ).show()
                        },
                        {
//                            Toast.makeText(
//                                binding.root.context,
//                                "CCTV not connected",
//                                Toast.LENGTH_SHORT
//                            ).show()
                        })
                    queue.add(stringRequest1)


                    val url: String = if (isChecked) {
                        "https://bumdesgemahripahsawotratap.com/uploadtoken.php?token=" + it.result.toString() + "&type=Tambah&random=" + random
                    } else {
                        "https://bumdesgemahripahsawotratap.com/uploadtoken.php?token=" + it.result.toString() + "&type=Hapus&random=" + random
                    }
                    val stringRequest = StringRequest(
                        Request.Method.GET, url,
                        { _ ->
                            if (isChecked) {
                                Toast.makeText(
                                    binding.root.context,
                                    "successfully connected to the server, you will receive a notification in case of fire",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    binding.root.context,
                                    "successfully disconnected to the server, you will not receive notification in case of fire",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
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
            }
            with(sharedPref?.edit()) {
                this?.putBoolean(getString(R.string.notification), isChecked)
                this?.apply()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}