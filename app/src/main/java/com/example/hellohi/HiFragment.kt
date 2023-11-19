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

                // Log before executing getAvgDuration query
                Log.d("HiFragment", "Before executing getAvgDuration query")

                sleepDao.getAllEntries().collect { listOfEntries ->
                    adapter.sleepDataList =
                        listOfEntries.map { SleepData(it.date, it.duration, it.quality) }.toMutableList()
                    adapter.notifyDataSetChanged()
                }cd Hek

                // Log after executing getAvgDuration query
                val avgDuration = sleepDao.getAvgDuration()
                Log.d("HiFragment", "After executing getAvgDuration query. Average Duration: $avgDuration")

                // Launch a coroutine to update the average sleep duration
                withContext(Dispatchers.Main) {
                    // Log before updating the UI
                    Log.d("HiFragment", "Before updating UI with average duration")

                    updateAvgDuration(avgDuration)

                    // Log after updating the UI
                    Log.d("HiFragment", "After updating UI with average duration")
                }
            }
        }
    }

    // Function to update the average sleep duration in textView2
    private fun updateAvgDuration(avgDuration: Double) {
        Log.d("HiFragment", "Received average duration: $avgDuration")
        val textView2: TextView = requireView().findViewById(R.id.textView2)
        textView2.text = "Average Sleep Duration: $avgDuration hours"
    }
}