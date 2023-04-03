package com.example.bloodbank2_0_main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bloodbank2_0_main.databinding.FragmentDonorBinding


class DonorFragment : Fragment() {

    private lateinit var binding: FragmentDonorBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_donor, container, false)
        binding = FragmentDonorBinding.inflate(inflater, container, false)
        return binding.root
    }




}