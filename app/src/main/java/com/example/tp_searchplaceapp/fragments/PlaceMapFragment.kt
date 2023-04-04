package com.example.tp_searchplaceapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tp_searchplaceapp.databinding.FragmentPlaceListBinding
import com.example.tp_searchplaceapp.databinding.FragmentPlaceMapBinding

class PlaceMapFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var binding: FragmentPlaceMapBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceMapBinding.inflate(inflater,container,false)
        return binding.root
    }

}