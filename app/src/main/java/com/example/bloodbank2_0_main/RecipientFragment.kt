package com.example.bloodbank2_0_main

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank2_0_main.databinding.FragmentRecipientBinding


class RecipientFragment : Fragment() {

    private lateinit var binding: FragmentRecipientBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_recipient, container, false)

        binding = FragmentRecipientBinding.inflate(inflater, container, false)
        return binding.root
    }


}