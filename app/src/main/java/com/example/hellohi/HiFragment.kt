package com.example.hellohi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View

import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project5.R
import kotlinx.coroutines.launch
import androidx.lifecycle.observe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



// HiFragment class with some cleanup and improvements
class HiFragment : Fragment(R.layout.hifragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val adapter = SleepDataAdapter(emptyList())

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Use lifecycleScope.launch(Dispatchers.IO) to perform database operations on a background thread
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val database = SleepDatabase.getDatabase(requireContext())
                val sleepDao = database.sleepDao()



                sleepDao.getAllEntries().collect { listOfEntries ->
                    adapter.sleepDataList =
                        listOfEntries.map { SleepData(it.date, it.duration, it.quality) }
                            .toMutableList()
                    adapter.notifyDataSetChanged()
                }

            }
        }
    }
}