
package com.ute.studentmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ute.studentmvvm.model.Student
import com.ute.studentmvvm.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(
    private val repository: StudentRepository
) : ViewModel() {

    val allStudents: LiveData<List<Student>> =
        repository.allStudents.asLiveData()

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    fun insert(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.insert(student)
            _statusMessage.postValue("Đã thêm sinh viên: ${student.fullName}")
        } catch (e: Exception) {
            _statusMessage.postValue("Lỗi khi thêm: ${e.message}")
        }
    }

    fun update(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(student)
    }

    fun delete(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.delete(student)
            _statusMessage.postValue("Đã xóa sinh viên: ${student.fullName}")
        } catch (e: Exception) {
            _statusMessage.postValue("Lỗi khi xóa: ${e.message}")
        }
    }
}
