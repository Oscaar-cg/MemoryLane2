package com.example.memorylane2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CapsuleListFragment : Fragment(R.layout.fragment_capsule_list) {

    private val viewModel: CapsuleViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewFragment)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.capsules.observe(viewLifecycleOwner) {
            recyclerView.adapter = CapsuleAdapter(it) { _, _ -> }
        }
    }
}