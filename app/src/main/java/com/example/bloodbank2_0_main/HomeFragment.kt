package com.example.bloodbank2_0_main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bloodbank2_0_main.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("users")
    private val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Donate blood Button
        binding.donateBloodButton.setOnClickListener {
            getDonorData(currentUserEmail)
        }

        // Request Blood Fragment
        binding.requestBLoodButton.setOnClickListener {
            getPatientData(currentUserEmail)
        }

    }

    private fun getDonorData(email: String?) {
        email?.let {
            userCollection.document(email)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val data = documentSnapshot.data
                        data?.let {
                            postDonorData(requireContext(), it["name"].toString(), it["contact"].toString(), it["bloodGroup"].toString(), it["location"].toString())
                        } ?: run {
                            Toast.makeText(requireContext(), "User data is null", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), "Error getting user data: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
        } ?: run {
            Toast.makeText(requireContext(), "Current user email is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun postDonorData(
        context: Context, name: String, contact: String, bloodGroup: String, location: String) {
        val donorData = hashMapOf(
            "name" to name,
            "contact" to contact,
            "bloodGroup" to bloodGroup,
            "location" to location
        )

        db.collection("Donors")
            .add(donorData)
            .addOnSuccessListener {
                Toast.makeText(context, "Thank you for donating blood!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error posting donor data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getPatientData(email: String?) {
        email?.let {
            userCollection.document(email)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val data = documentSnapshot.data
                        data?.let {
                            postPatientData(requireContext(), it["name"].toString(),it["contact"].toString(), it["bloodGroup"].toString(), it["location"].toString())
                        } ?: run {
                            Toast.makeText(requireContext(), "User data is null", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), "Error getting user data: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
        } ?: run {
            Toast.makeText(requireContext(), "Current user email is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun postPatientData(
        context: Context, name: String,contact: String, bloodGroup  : String, location: String) {
        val donorData = hashMapOf(
            "name" to name,
            "contact" to contact,
            "bloodGroup" to bloodGroup,
            "location" to location
        )

        db.collection("Patients")
            .add(donorData)
            .addOnSuccessListener {
                Toast.makeText(context, "Thank you for posting, help is on the way!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error posting patient's data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


}