package com.example.hellohi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project5.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HelloFragment : Fragment(R.layout.activity_sleep_tracking) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = SleepDatabase.getDatabase(requireContext())
        val sleepDao = database.sleepDao()

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val adapter = SleepDataAdapter(emptyList())

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            sleepDao.getAllEntries().collect { listOfEntries ->
                adapter.sleepDataList =
                    listOfEntries.map { SleepData(it.date, it.duration, it.quality) }.toMutableList()
                adapter.notifyDataSetChanged()
            }
        }

        val button: Button = view.findViewById(R.id.button)
        val editTextDay: EditText = view.findViewById(R.id.editTextText)
        val editTextDuration: EditText = view.findViewById(R.id.editTextText2)
        val editTextQuality: EditText = view.findViewById(R.id.editTextText3)

        button.setOnClickListener {
            val day = editTextDay.text.toString()
            val duration = editTextDuration.text.toString()
            val quality = editTextQuality.text.toString()

            if (day.isNotEmpty() && duration.isNotEmpty() && quality.isNotEmpty()) {
                val newEntry = SleepEntry(duration = duration, date = day, quality = quality)

                lifecycleScope.launch(Dispatchers.IO) {
                    val id = sleepDao.insert(newEntry)
                    Log.d("HelloFragment", "New Sleep Entry Added with ID: $id")

                    launch(Dispatchers.Main) {
                        // Update UI if needed
                        // ...
                    }
                }

                editTextDay.text.clear()
                editTextDuration.text.clear()
                editTextQuality.text.clear()

            } else {
                Log.d("HelloFragment", "Invalid input")
            }
        }
    }
}
