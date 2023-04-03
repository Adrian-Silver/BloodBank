package com.example.bloodbank2_0_main

import android.os.Binder
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbank2_0_main.databinding.FragmentRecipientBinding
import com.example.bloodbank2_0_main.databinding.ItemRecipient2Binding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class RecipientFragment : Fragment() {

//    private lateinit var binding: FragmentRecipientBinding

    private var _binding: FragmentRecipientBinding? = null // modified
    private val binding get() = _binding!!
    private lateinit var patientAdapter: PatientAdapter // modified
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_recipient, container, false)

//        binding = FragmentRecipientBinding.inflate(inflater, container, false)
        _binding = FragmentRecipientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        getPatientData() // modified
    }

    private fun setUpRecyclerView(patients: MutableList<Patient>) { // modified
        patientAdapter = PatientAdapter(patients)
        binding.rvBloodRequests.layoutManager = LinearLayoutManager(activity)
        binding.rvBloodRequests.adapter = patientAdapter
    }

    private fun getPatientData() { // modified
        val patients = mutableListOf<Patient>()
        db.collection("Patients").get()
            .addOnSuccessListener { documents: QuerySnapshot ->
                for (document in documents) {
                    val name = document.getString("name")
                    val contact = document.getString("contact")
                    val location = document.getString("location")
                    if (name != null && contact != null && location != null) {
                        val patient = Patient(name, contact, location)
                        patients.add(patient)
                    }
                }
                setUpRecyclerView(patients)
            }
            .addOnFailureListener { exception ->
                // Handle any errors
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

@Parcelize
data class Patient( // modified
    val name: String? = "",
    val contact: String? = "",
    val location: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(contact)
        parcel.writeString(location)
    }

    companion object CREATOR : Parcelable.Creator<Patient> {
        override fun createFromParcel(parcel: Parcel): Patient {
            return Patient(parcel)
        }

        override fun newArray(size: Int): Array<Patient?> {
            return arrayOfNulls(size)
        }
    }
}

class PatientAdapter(private val patients: MutableList<Patient>) : // modified
    RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    fun addPatient(patient: Patient) { // modified
        patients.add(patient)
        notifyDataSetChanged()
    }

    inner class PatientViewHolder(private val binding: ItemRecipient2Binding) : // modified
        RecyclerView.ViewHolder(binding.root) {

        fun bind(patient: Patient) { // modified
            binding.tvPersonName.text = patient.name
            binding.tvBloodType.text = patient.contact
            binding.tvPersonLocation.text = patient.location
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder { // modified
        val binding =
            ItemRecipient2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientViewHolder(binding) // modified
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) { // modified
        holder.bind(patients[position]) // modified
    }

    override fun getItemCount(): Int = patients.size // modified
}