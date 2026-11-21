package com.ute.studentmvvm.repository
import com.ute.studentmvvm.data.StudentDao
import com.ute.studentmvvm.model.Student
import kotlinx.coroutines.flow.Flow
class StudentRepository(
    private val studentDao: StudentDao
) {
    val allStudents: Flow<List<Student>> =
        studentDao.getAllStudents()
    suspend fun insert(student: Student): Long =
        studentDao.insertStudent(student)
    suspend fun update(student: Student) =
        studentDao.updateStudent(student)
    suspend fun delete(student: Student) =
        studentDao.deleteStudent(student)
}
