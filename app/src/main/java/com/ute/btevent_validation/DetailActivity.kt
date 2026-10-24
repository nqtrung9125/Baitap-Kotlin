package com.ute.btevent_validation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ute.btevent_validation.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nhận dữ liệu Student từ Intent
        val student = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("EXTRA_STUDENT", Student::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("EXTRA_STUDENT") as? Student
        }

        // Đổ dữ liệu lên thẻ hiển thị
        if (student != null) {
            binding.tvDetailFullName.text = student.fullName
            binding.tvDetailStudentIdHeader.text = "MSV: ${student.studentId}"
            binding.tvDetailEmail.text = "Email: ${student.email}"
            binding.tvDetailPhone.text = "Số điện thoại: ${student.phone}"
            binding.tvDetailStudentId.text = "Mã sinh viên: ${student.studentId}"
            binding.tvDetailClassName.text = "Lớp sinh hoạt: ${student.className}"

            // Tạo chữ viết tắt hiển thị trong avatar
            val words = student.fullName.trim().split("\\s+".toRegex())
            val initials = if (words.size >= 2) {
                "${words.first().take(1)}${words.last().take(1)}".uppercase()
            } else {
                student.fullName.take(2).uppercase()
            }
            binding.tvAvatarInitials.text = initials
        }

        // Nút quay lại trang chính (MainActivity)
        binding.btnBackToRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}