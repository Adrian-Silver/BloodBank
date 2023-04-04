package com.example.bloodbank2_0_main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.bloodbank2_0_main.databinding.ActivityDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

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

        // Change this functionality to button
        // Button should lead to DashboardActivity

//        binding.textView19.setOnClickListener {
//            val internt = Intent(this, DashboardActivity::class.java)
//            startActivity(intent)
//        }

        binding.registerButton.setOnClickListener {
            saveUserData()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val spinner = binding.spinnerBloodType
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object :
            android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                Timber.d("Selected item is $selectedItem")
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {
                Toast.makeText(this@DetailsActivity, "Please Select Blood Group", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun saveUserData() {
        val name = binding.nameEt.text.toString()
        val age = binding.ageEt.text.toString()
        val gender = binding.genderEt.text.toString()
        val phoneNO = binding.phoneNoEt.text.toString()
        val location = binding.locationEt.text.toString()

        val bloodGroup = binding.spinnerBloodType.selectedItem.toString()
//        val bloodGroup = binding.spinnerBloodType.selectedItem.toString()
//        binding.spinnerBloodType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val bloodGroup = parent?.getItemAtPosition(position).toString()
//                // do something with selected blood group
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // do something when nothing is selected
//            }
//        }


        val user: HashMap<kotlin.String, kotlin.Any> = kotlin.collections.hashMapOf(
            "name" to name,
            "age" to age,
            "phoneNumber" to phoneNO,
            "gender" to gender,
            "location" to location,
             "bloodGroup" to bloodGroup
        )



        val email = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.email
        val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()

        if (email != null) {
            db.collection("users")
                .document(email)
                .set(user)
                .addOnSuccessListener {
                    android.widget.Toast.makeText(this, "User data saved successfully", android.widget.Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    android.widget.Toast.makeText(this, "Failed to save user data", android.widget.Toast.LENGTH_SHORT).show()
                }
        }
    }




}