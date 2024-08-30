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
import com.google.android.material.bottomsheet.BottomSheetBehavior


class BottomSheetFragment : BottomSheetDialogFragment() {


    private lateinit var chipGroupRegions: ChipGroup
    private lateinit var chipGroupGenre: ChipGroup
    private lateinit var chipGroupTime: ChipGroup
    private lateinit var chipGroupSort: ChipGroup
    private lateinit var chipGroupCategory: ChipGroup
    private lateinit var resetBtn: Button
    private lateinit var applyBtn: Button

    interface FilterCallback {
        fun onFilterApplied(filterObj: FilterObj)
    }

    private var filterCallback: FilterCallback? = null

    fun setFilterCallback(callback: FilterCallback) {
        filterCallback = callback
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)

        bottomSheetBehavior.peekHeight = 600
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        chipGroupRegions.setOnCheckedChangeListener{ chipGroupRegions, checkedId ->
            handleChipSelection(chipGroupRegions, checkedId, "Region")

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
            filterCallback?.onFilterApplied(filterObj)
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
            //Toast.makeText(context, "$chipType: $chipText", Toast.LENGTH_SHORT).show()
        } else {
            //Toast.makeText(context, "No $chipType selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getChipText(chipGroup: ChipGroup): String {
        return chipGroup.checkedChipId.takeIf { it != View.NO_ID }
            ?.let { chipGroup.findViewById<Chip>(it).tag.toString() }
            ?: ""
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}