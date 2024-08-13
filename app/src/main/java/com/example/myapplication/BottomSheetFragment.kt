package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Data.FilterObj
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.example.myapplication.ViewModel.NewMovieViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BottomSheetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var chipGroupRegions: ChipGroup
    private lateinit var chipGroupGenre: ChipGroup
    private lateinit var chipGroupTime: ChipGroup
    private lateinit var chipGroupSort: ChipGroup
    private lateinit var chipGroupCategory: ChipGroup
    private lateinit var regionChipText: String //? = null
    private lateinit var genreChipText: String //? = null
    private lateinit var timeChipText: String //? = null
    private lateinit var sortChipText: String //? = null
    private lateinit var resetBtn: Button
    private lateinit var applyBtn: Button

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
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chipGroupRegions = view.findViewById(R.id.chipGroupRegions)
        chipGroupGenre = view.findViewById(R.id.chipGroupGenre)
        chipGroupTime = view.findViewById(R.id.chipGroupTime)
        chipGroupSort = view.findViewById(R.id.chipGroupSort)
        chipGroupCategory = view.findViewById(R.id.categoriesGroup)


        resetBtn = view.findViewById(R.id.buttonReset)
        applyBtn = view.findViewById(R.id.buttonApply)


        chipGroupRegions.setOnCheckedChangeListener{ chipGroupRegions, checkedId ->
            handleChipSelection(chipGroupRegions, checkedId, "Region")

        }

        chipGroupCategory.setOnCheckedChangeListener{ chipGroupCategory, checkedId ->
            Toast.makeText(context, "leave me alone", Toast.LENGTH_SHORT).show()
        }

        chipGroupGenre.setOnCheckedChangeListener{ chipGroupGenre, checkedId ->
            handleChipSelection(chipGroupGenre, checkedId, "Genre")

        }

        chipGroupSort.setOnCheckedChangeListener{ chipGroupSort, checkedId ->
            handleChipSelection(chipGroupSort, checkedId, "Sort")

        }

        chipGroupTime.setOnCheckedChangeListener{ chipGroupTime, checkedId ->
            handleChipSelection(chipGroupTime, checkedId, "Time")

        }



        applyBtn.setOnClickListener{
            val filterObj = FilterObj(
                 getChipText(chipGroupRegions),
                getChipText(chipGroupSort),
                getChipText(chipGroupGenre),
                getChipText(chipGroupTime)
            )
            val movieViewModel = ViewModelProvider(requireActivity())[NewMovieViewModel::class.java]
            movieViewModel.setFilterData(filterObj)
            Log.d("BottomSheet", "onViewCreated: Filter Object created")
            dismiss()
        }

        resetBtn.setOnClickListener{
            chipGroupCategory.clearCheck()
            chipGroupRegions.clearCheck()
            chipGroupTime.clearCheck()
            chipGroupSort.clearCheck()
            chipGroupGenre.clearCheck()
        }


    }

    private fun handleChipSelection(chipGroup: ChipGroup, checkedId: Int, chipType: String) {
        if (checkedId != View.NO_ID) {
            val selectedChip = chipGroup.findViewById<Chip>(checkedId)
            val chipText = selectedChip.tag.toString()
            Toast.makeText(context, "$chipType: $chipText", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "No $chipType selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getChipText(chipGroup: ChipGroup): String {
        return chipGroup.checkedChipId.takeIf { it != View.NO_ID }
            ?.let { chipGroup.findViewById<Chip>(it).tag.toString() }
            ?: ""
    }

    companion object {
        const val TAG = "ModalBottomSheet"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BottomSheetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}