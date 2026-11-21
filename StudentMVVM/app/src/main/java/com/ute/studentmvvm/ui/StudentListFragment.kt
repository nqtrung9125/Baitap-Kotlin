package com.ute.studentmvvm.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ute.studentmvvm.R
import com.ute.studentmvvm.data.AppDatabase
import com.ute.studentmvvm.databinding.FragmentStudentListBinding
import com.ute.studentmvvm.model.Student
import com.ute.studentmvvm.repository.StudentRepository
import com.ute.studentmvvm.ui.adapter.StudentAdapter
import com.ute.studentmvvm.viewmodel.StudentViewModel
import com.ute.studentmvvm.viewmodel.StudentViewModelFactory
import androidx.appcompat.app.AlertDialog
class StudentListFragment : Fragment(R.layout.fragment_student_list) {

    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentViewModel by activityViewModels {
        StudentViewModelFactory(
            StudentRepository(
                AppDatabase.getDatabase(requireContext()).studentDao()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentStudentListBinding.bind(view)

        val studentAdapter = StudentAdapter(
            onStudentClick = { showDetail(it) },
            onDeleteClick = { confirmDelete(it) }
        )

        binding.rvStudents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStudents.adapter = studentAdapter

        viewModel.allStudents.observe(viewLifecycleOwner) { students ->
            studentAdapter.submitList(students)
            binding.tvStudentCount.text = "${students.size} sinh viên"
        }

        viewModel.statusMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAdd.setOnClickListener {
            AddStudentDialogFragment().show(childFragmentManager, "AddStudent")
        }
    }

    private fun showDetail(student: Student) {
        AlertDialog.Builder(requireContext())
            .setTitle("Thông tin sinh viên")
            .setMessage(
                "Họ tên: ${student.fullName}\n" +
                        "MSSV: ${student.studentCode}\n" +
                        "Email: ${student.email}\n" +
                        "GPA: ${student.gpa}\n" +
                        "Ngành: ${student.major}"
            )
            .setPositiveButton("Đóng", null)
            .show()
    }

    private fun confirmDelete(student: Student) {
        AlertDialog.Builder(requireContext())
            .setTitle("Xóa sinh viên")
            .setMessage("Bạn muốn xóa ${student.fullName}?")
            .setPositiveButton("Xóa") { _, _ ->
                viewModel.delete(student)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onDestroyView() {
        binding.rvStudents.adapter = null
        _binding = null
        super.onDestroyView()
    }
}