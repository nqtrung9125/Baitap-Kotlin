package com.example.btfragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class StudentDetailFragment : Fragment(R.layout.fragment_student_detail) {

    companion object {
        fun newInstance(student: Student): StudentDetailFragment {
            return StudentDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("name", student.name)
                    putString("studentId", student.studentId)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("name").orEmpty()
        val id = arguments?.getString("studentId").orEmpty()

        view.findViewById<TextView>(R.id.tvDetailName).text =
            "Họ tên: $name"

        view.findViewById<TextView>(R.id.tvDetailId).text =
            "Mã sinh viên: $id"

        view.findViewById<Button>(R.id.btnBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}