package com.example.themeswitcher20dieeine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.themeswitcher20dieeine.databinding.FragmentHomeBinding


class HomeFragment: Fragment() {
    private val sportFragment = SportFragment()
    //special Binding: creating null exception for later use
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    //Layout needs to be inflated and destroyed, binding just lives in that amount of time
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val view = binding.root


        //replace current fragment with new one
        binding.animSport.setOnClickListener({
            childFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.randomfragmentContainer,sportFragment)
                .commit()
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}