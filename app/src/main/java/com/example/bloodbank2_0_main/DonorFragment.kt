package com.example.bloodbank2_0_main

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbank2_0_main.databinding.FragmentDonorBinding
import com.example.bloodbank2_0_main.databinding.ItemDonorBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class DonorFragment : Fragment() {

//    private lateinit var binding: FragmentDonorBinding

    private var _binding: FragmentDonorBinding? = null
    private val binding get() = _binding!!

    private lateinit var donorAdapter: DonorAdapter
    private lateinit var db: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_donor, container, false)
        _binding = FragmentDonorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        getDonorData()
    }

    private fun setUpRecyclerView(donors: MutableList<Donor>) {
        donorAdapter = DonorAdapter(donors)
        binding.rvDonationRequests.layoutManager = LinearLayoutManager(activity)
        binding.rvDonationRequests.adapter = donorAdapter
    }

    private fun getDonorData() {
        val donors = mutableListOf<Donor>()
        db.collection("Donors").get()
            .addOnSuccessListener { documents: QuerySnapshot ->
                for (document in documents) {
                    val name = document.getString("name")
                    val contact = document.getString("contact")
                    val bloodType = document.getString("bloodGroup")
                    val location = document.getString("location")
                    if (name != null && contact!= null && bloodType != null && location != null) {
                        val donor = Donor(name, contact, bloodType, location)
                        donors.add(donor)
                    }
                }
                setUpRecyclerView(donors)
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

//@Suppress("unused")
@Parcelize
data class Donor(
    val name: String? = "",
    val contact: String? = "",
    val bloodType: String? = "",
    val location: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

//    companion object CREATOR : Parcelable.Creator<Donor> {
//        override fun createFromParcel(parcel: Parcel): Donor {
//            return Donor(
//                parcel.readString() ?: "",
//                parcel.readString() ?: "",
//                parcel.readString() ?: ""
//            )
//        }

//        override fun newArray(size: Int): Array<Donor?> {
//            return arrayOfNulls(size)
//        }
    //}
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(contact)
        parcel.writeString(bloodType)
        parcel.writeString(location)
    }

    companion object CREATOR : Parcelable.Creator<Donor> {
        override fun createFromParcel(parcel: Parcel): Donor {
            return Donor(parcel)
        }

        override fun newArray(size: Int): Array<Donor?> {
            return arrayOfNulls(size)
        }
    }
}

annotation class Parcelize

class DonorAdapter(private val donors: MutableList<Donor>) :
    RecyclerView.Adapter<DonorAdapter.DonorViewHolder>() {

    fun addDonor(donor: Donor) {
        donors.add(donor)
        notifyDataSetChanged()
    }

    inner class DonorViewHolder(private val binding: ItemDonorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(donor: Donor) {
            binding.tvPersonName.text = donor.name
            binding.tvContact.text = donor.contact
            binding.tvBloodType.text = donor.bloodType
            binding.tvPersonLocation.text = donor.location
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorViewHolder {
        val binding =
            ItemDonorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DonorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DonorViewHolder, position: Int) {
        holder.bind(donors[position])
    }

    override fun getItemCount(): Int = donors.size
}