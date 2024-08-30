package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CallbackInterfaces.onMovieLongClick
import com.example.myapplication.Data.UserData
import com.example.myapplication.ViewModel.NewMovieViewModel
import com.example.myapplication.adapter.DatabasePagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MyListFragment : Fragment(R.layout.fragment_my_list), onMovieLongClick {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbViewModel: NewMovieViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewMyList)

        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.hasFixedSize()

        val adapter = DatabasePagingAdapter(this)
        recyclerView.adapter = adapter

        dbViewModel = ViewModelProvider(this)[NewMovieViewModel::class.java]

        lifecycleScope.launch {
            dbViewModel.readAllDataFlow.collectLatest {  pagingData ->
                adapter.submitData(pagingData)

            }
        }

    }


    override fun onMovieLongClicked(userData: UserData) {
        showDialogueBox(
            onPositiveClick = {
                dbViewModel.deleteUserLike(userData)
                Toast.makeText(context, "Successfully Removed", Toast.LENGTH_SHORT).show()
            },
            onNegativeClick = {
                Toast.makeText(context, "Not Removed", Toast.LENGTH_SHORT).show()
            })
    }

    private fun showDialogueBox(onPositiveClick: () -> Unit, onNegativeClick: () -> Unit)
    {
        val builder =  AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
        builder.setTitle("Confirmation")
        builder.setMessage("Do you want to delete this title?")

        builder.setPositiveButton("Yes"){ _, _ ->
            onPositiveClick()
        }

        builder.setNegativeButton("No"){ _,_ ->
            onNegativeClick()
        }
        builder.show()
    }
}