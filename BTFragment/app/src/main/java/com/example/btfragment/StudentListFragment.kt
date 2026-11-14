package com.example.btfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StudentListFragment : Fragment(R.layout.fragment_student_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val students = listOf(
            Student(1, "Nguyễn Quốc Trung", "2415053122246"),
            Student(2, "Nguyễn Văn An", "2415053122001"),
            Student(3, "Trần Minh Huy", "2415053122002"),
            Student(4, "Lê Hoàng Nam", "2415053122003")
        )

        val adapter = StudentAdapter { student ->
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    StudentDetailFragment.newInstance(student)
                )
                .addToBackStack(null)
                .commit()
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvStudents)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        adapter.submitList(students)
    }
}