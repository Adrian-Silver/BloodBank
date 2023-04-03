package com.example.bloodbank2_0_main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bloodbank2_0_main.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserProfileViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("users")
    private val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

    val userData = MutableLiveData<HashMap<String, Any>>()
    val userEmail = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    init {
        isLoading.value = true
        currentUserEmail?.let {
            getUserData(it)
        }
    }

    private fun getUserData(email: String) {
        userCollection.document(email)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val data = documentSnapshot.data
                    data?.let {
                        userData.postValue(it as HashMap<String, Any>)
                        userEmail.postValue(email)
                        isLoading.postValue(false)
                    } ?: run {
                        isLoading.postValue(false)
                        error.postValue("User data is null")
                    }
                } else {
                    isLoading.postValue(false)
                    error.postValue("User not found")
                }
            }
            .addOnFailureListener { exception ->
                isLoading.postValue(false)
                error.postValue(exception.localizedMessage)
                Log.e(TAG, "Error getting user data", exception)
            }

    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }

}

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var viewModel: UserProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            binding.apply {
                userName.text = userData["name"] as String
                userAge.text = userData["age"] as String
                userGender.text = userData["gender"] as String
                userPhoneNo.text = userData["phoneNumber"] as String
                userLocation.text = userData["location"] as String
                // userBloodG.text = userData["bloodType"] as String
            }
        }

        viewModel.userEmail.observe(viewLifecycleOwner) { userEmail ->
            binding.userEmail.text = userEmail
        }

    }


}