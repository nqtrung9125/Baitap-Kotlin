package com.ute.btevent_validation

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.ute.btevent_validation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nút chuyển trực tiếp sang trang Chi tiết
        binding.btnGoToDetailDirectly.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

        // Nút Đăng ký và kiểm tra validation
        binding.btnRegister.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun validateAndSubmit() {
        val fullName = binding.edtFullName.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val phone = binding.edtPhone.text.toString().trim()
        val studentId = binding.edtStudentId.text.toString().trim()
        val className = binding.edtClassName.text.toString().trim()

        var isValid = true

        // 1. Kiểm tra Họ và tên
        if (fullName.isEmpty()) {
            binding.tilFullName.error = "Vui lòng nhập họ và tên"
            isValid = false
        } else if (fullName.length < 3) {
            binding.tilFullName.error = "Họ tên phải có ít nhất 3 ký tự"
            isValid = false
        } else {
            binding.tilFullName.error = null
        }

        // 2. Kiểm tra Email
        if (email.isEmpty()) {
            binding.tilEmail.error = "Vui lòng nhập email"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Email không hợp lệ (VD: user@sv.ute.udn.vn)"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        // 3. Kiểm tra Số điện thoại (10 số, đầu 03, 05, 07, 08, 09)
        val phoneRegex = Regex("^0[35789][0-9]{8}$")
        if (phone.isEmpty()) {
            binding.tilPhone.error = "Vui lòng nhập số điện thoại"
            isValid = false
        } else if (!phoneRegex.matches(phone)) {
            binding.tilPhone.error = "SĐT phải gồm 10 chữ số (đầu 03, 05, 07, 08, 09)"
            isValid = false
        } else {
            binding.tilPhone.error = null
        }

        // 4. Kiểm tra Mã sinh viên (5 - 15 ký tự chữ/số)
        val studentIdRegex = Regex("^[a-zA-Z0-9]{5,15}$")
        if (studentId.isEmpty()) {
            binding.tilStudentId.error = "Vui lòng nhập mã sinh viên"
            isValid = false
        } else if (!studentIdRegex.matches(studentId)) {
            binding.tilStudentId.error = "MSV từ 5-15 ký tự chữ/số"
            isValid = false
        } else {
            binding.tilStudentId.error = null
        }

        // 5. Kiểm tra Lớp
        if (className.isEmpty()) {
            binding.tilClassName.error = "Vui lòng nhập tên lớp"
            isValid = false
        } else {
            binding.tilClassName.error = null
        }

        if (isValid) {
            val student = Student(fullName, email, phone, studentId, className)

            val snackbar = Snackbar.make(
                binding.root,
                "✓ Đăng ký thành công!",
                Snackbar.LENGTH_INDEFINITE
            )
            snackbar.setBackgroundTint(Color.parseColor("#2E7D32"))
            snackbar.setActionTextColor(Color.parseColor("#FFD54F"))
            snackbar.setAction("XEM CHI TIẾT") {
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra("EXTRA_STUDENT", student)
                }
                startActivity(intent)
            }
            snackbar.show()
        } else {
            val failSnackbar = Snackbar.make(
                binding.root,
                "Đăng ký thất bại! Vui lòng kiểm tra lại thông tin.",
                Snackbar.LENGTH_LONG
            )
            failSnackbar.setBackgroundTint(Color.parseColor("#C62828"))
            failSnackbar.show()
        }
    }
}