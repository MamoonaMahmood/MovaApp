package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.MovieResult
import com.example.myapplication.Data.UserData
import com.example.myapplication.ViewModel.DataBaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MyListFragment : Fragment(R.layout.fragment_my_list), onMovieLongClick {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbViewModel: DataBaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewMyList)

        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.hasFixedSize()

        val adapter = DatabasePagingAdapter(this)
        recyclerView.adapter = adapter

        dbViewModel = ViewModelProvider(this)[DataBaseViewModel::class.java]

        lifecycleScope.launch {
            dbViewModel.readAllDataFlow.collectLatest {  pagingData ->
                adapter.submitData(pagingData)
            }
        }

    }

    override fun onMovieLongClicked(userData: UserData) {
        TODO("Not yet implemented")
    }
}