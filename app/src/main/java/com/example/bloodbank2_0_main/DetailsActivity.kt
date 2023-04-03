package com.example.bloodbank2_0_main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.bloodbank2_0_main.databinding.ActivityDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.registerButton.setOnClickListener {
//            val name = binding.firstNameET.editText?.text.toString()
//            val phone_number = binding.phoneET.editText?.text.toString()
//
//            val gender = binding.genderET.editText?.text.toString()
////            val location = binding.locationET.editText?.text.toString()
//            val location = binding.locationET.editText?.text.toString()
//
//            val bloodType = binding.spinnerBloodType.selectedItem.toString()
//
//            val banker = Banker(name, phone_number, gender, bloodType, location)
//
//
//            databaseReference = FirebaseDatabase.getInstance().getReference("Bankers")
//
//            databaseReference.child(uId).setValue(banker).addOnSuccessListener {
//
//                Toast.makeText(this, "User Details Saved Successfully!!", Toast.LENGTH_SHORT).show()
//
//            }.addOnFailureListener {
//                Toast.makeText(this, "Failed to record user details!!", Toast.LENGTH_SHORT).show()
//            }
//
//
//        }

        // Change this functionality to buuton
        // Button should lead to DashboardActivity
        binding.textView19.setOnClickListener {
            val internt = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
}