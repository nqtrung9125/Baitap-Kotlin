package com.ute.studentprofile
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ute.studentprofile.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentStudent: Student? = Student(
        id = "2415053122246",
        name = "Nguyễn Quốc Trung",
        className = "CNTT",
        email = "trungnq@gmail.com",
        phone = "0905123456",
        gpa = 3.6
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentStudent?.let { bindStudentData(it) }
        binding.btnUpdateGpa.setOnClickListener {
            val inputStr = binding.edtNewGpa.text.toString().trim()
            val newGpa = inputStr.toDoubleOrNull()
            if (newGpa == null || newGpa !in 0.0..4.0) {
                binding.edtNewGpa.error = "Vui lòng nhập GPA hợp lệ (0.0 - 4.0)"
                toast("Điểm GPA không hợp lệ!")
                return@setOnClickListener
            }
            currentStudent = currentStudent?.copy(gpa = newGpa)
            currentStudent?.let { bindStudentData(it) }
            toast("Cập nhật điểm thành công!")
        }
        binding.btnCall.setOnClickListener {
            val phone = currentStudent?.phone
            if (!phone.isNullOrEmpty()) {
                val callIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phone")
                }
                startActivity(callIntent)
            } else {
                toast("Không tìm thấy số điện thoại!")
            }
        }
        binding.btnDelete.setOnClickListener {
            showConfirmDialog(
                title = "Xác nhận xóa",
                message = "Bạn có chắc chắn muốn xóa hồ sơ sinh viên này không?"
            ) {
                currentStudent = null
                binding.cardProfile.visibility = View.GONE
                toast("Đã xóa hồ sơ thành công!")
            }
        }
    }
    private fun bindStudentData(student: Student) {
        with(binding) {
            tvName.text = student.name
            tvStudentId.text = "MSSV: ${student.id} | Lớp: ${student.className}"
            tvPhone.text = "SĐT: ${student.phone}"
            tvGpaBadge.text = "${student.gpa} GPA (${student.gpa.toAcademicRanking()})"
            edtNewGpa.setText(student.gpa.toString())
        }
    }
}