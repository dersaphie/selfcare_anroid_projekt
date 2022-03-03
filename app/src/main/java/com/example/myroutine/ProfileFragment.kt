package com.example.myroutine

import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //anim for side change
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        /*
        *FEHLER!! Das neue Fragment wird zum Home Fragment -> eigentlich sollte Action aufruf es lösen
        * aber absturz bis jetzt
         */
        //click for next fragment //change writing navigate_sport_button
        view.findViewById<Button>(R.id.btn_save_user_data)?.setOnClickListener {
            val cv = ContentValues()
            //var userNameTV = view.findViewById<Button>(R.id.et_your_name)
            //var userAgeTV = view.findViewById<Button>(R.id.et_your_age).text
            cv.put("NAME", R.id.et_your_name.toString())
            //cv.put("NAME", R.id.et_your_age.toString())
            cv.put("AGE", R.id.et_your_age.toString())
            //userNameTV.text = ""
            //userAgeTV.text = ""
            //binding.editTextNumberPassword.setText("")
            (activity as MainActivity?)!!.saveUserDataInDB(cv)
        }

        // navigate with action for safe arguments
        view.findViewById<Button>(R.id.btn_show_user_stats)?.setOnClickListener {
            //val flowStepNumberArg = 1
            val action = ProfileFragmentDirections.actionProfileFragmentToResultsFragment()
            findNavController().navigate(action, null)
        }

        /*
        // Navigate via destination
        view.findViewById<Button>(R.id.btn_show_user_stats)?.setOnClickListener(
        Navigation.createNavigateOnClickListener(R.id.resultsFragment, null)
        )
        */

    }

companion object {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profilFragment.
     */
    // TODO: Rename and change types and number of parameters
    @JvmStatic
    fun newInstance(param1: String, param2: String) =
        ProfileFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
}
}