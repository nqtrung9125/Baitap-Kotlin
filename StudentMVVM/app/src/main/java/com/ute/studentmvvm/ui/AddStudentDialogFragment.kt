
package com.ute.studentmvvm.ui
import android.util.Patterns
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ute.studentmvvm.data.AppDatabase
import com.ute.studentmvvm.databinding.DialogAddStudentBinding
import com.ute.studentmvvm.model.Student
import com.ute.studentmvvm.repository.StudentRepository
import com.ute.studentmvvm.viewmodel.StudentViewModel
import com.ute.studentmvvm.viewmodel.StudentViewModelFactory

class AddStudentDialogFragment : DialogFragment() {

    private val viewModel: StudentViewModel by activityViewModels {
        StudentViewModelFactory(
            StudentRepository(
                AppDatabase.getDatabase(requireContext()).studentDao()
            )
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogAddStudentBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Thêm sinh viên")
            .setView(binding.root)
            .setPositiveButton("Lưu", null)
            .setNegativeButton("Hủy", null)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val name = binding.etFullName.text.toString().trim()
                val code = binding.etStudentCode.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val gpa = binding.etGpa.text.toString().toDoubleOrNull()
                var isValid = true
                if (name.isEmpty()) {
                    binding.etFullName.error = "Vui lòng nhập họ tên"
                    isValid = false
                }
                if (!code.matches(Regex("\\d{13}"))) {
                    binding.etStudentCode.error = "MSSV phải gồm 13 chữ số"
                    isValid = false
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.etEmail.error = "Email không hợp lệ"
                    isValid = false
                }

                if (gpa == null || gpa !in 0.0..4.0) {
                    binding.etGpa.error = "GPA phải từ 0 đến 4"
                    isValid = false
                }
                if (!isValid) return@setOnClickListener
                viewModel.insert(
                    Student(
                        fullName = name,
                        studentCode = code,
                        email = email,
                        gpa = gpa ?: return@setOnClickListener
                    )
                )
                dialog.dismiss()
            }
        }
        return dialog
    }
}
