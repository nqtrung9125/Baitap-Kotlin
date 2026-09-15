data class Student(val StudentID: String, val FullName: String, val Age: Int, val Major: String, val GPA: Double);
class StudentManagement() {
    val listStudent = mutableListOf<Student>()

    init {
        listStudent.add(Student("2415053122246", "Nguyễn Quốc Trung", 20, "CNTT", 6.7))
        listStudent.add(Student("2415053122245", "Phan Quốc A", 20, "TMDT", 8.0))
        listStudent.add(Student("2415053122244", "Lê Huỳnh B", 20, "KT", 3.0))
        listStudent.add(Student("2415053122243", "Mai Quang C", 20, "TTNT", 9.0))
        listStudent.add(Student("2415053122242", "Trịnh Thị D", 20, "DT", 5.0))
    }
    fun add() {
        println("Nhập MSV: ")
        val StudentID: String = readln().toString();
        println("Nhập họ và tên: ")
        val FullName: String = readln().toString();
        println("Nhập tuổi: ")
        val Age: Int = readln().toInt();
        println("Nhập ngành học: ")
        val Major: String = readln().toString();
        println("Nhập GPA: ")
        val GPA: Double = readln().toDouble();
        listStudent.add(Student(StudentID, FullName, Age, Major, GPA))
        println("=> Đã thêm sinh viên thành công!")
    }
    fun display() {
        println("Cách thức hiển thị: ")
        println("1. Hiển thị toàn bộ sinh viên")
        println("2. Hiển thị 3 sinh viên có GPA cao nhất")
        print("Choose: ")
        when (readln().toInt()) {
            1 -> {
                if (listStudent.isEmpty()) println("Danh sách trống!")
                for (sv in listStudent) println(sv)
            }
            2 -> {
                val top3 = listStudent.sortedByDescending { it.GPA }.take(3)
                for (sv in top3) println(sv)
            }
            else -> println("Lựa chọn không hợp lệ!")
        }
    }
    fun count() {
        println("Cách thức đếm: ")
        println("1. Đếm số sinh viên có GPA >= 8.0")
        println("2. Đếm số sinh viên có GPA < 5.0")
        print("Choose: ")
        when (readln().toInt()) {
            1 -> {
                val count8 = listStudent.count { it.GPA >= 8.0 }
                println("Số sinh viên có GPA >= 8.0: $count8")
            }
            2 -> {
                val count5 = listStudent.count { it.GPA < 5.0 }
                println("Số sinh viên có GPA < 5.0: $count5")
            }
            else -> println("Lựa chọn không hợp lệ!")
        }
    }
    fun search() {
        println("Cách thức tìm: ")
        println("1. Tìm sinh viên có GPA cao nhất")
        println("2. Tìm sinh viên lớn tuổi nhất")
        println("3. Tìm sinh viên có GPA nằm trong khoảng 7.0 -> 8.5")
        println("4. Tìm tất cả sinh viên thuộc một ngành")
        println("5. Tìm sinh viên theo một phần tên")
        print("Choose: ")
        when (readln().toInt()) {
            1 -> {
                val maxGpa = listStudent.maxBy { it.GPA }
                println("Sinh viên có GPA cao nhất: $maxGpa")
            }
            2 -> {
                val oldest = listStudent.maxBy { it.Age }
                println("Sinh viên lớn tuổi nhất: $oldest")
            }
            3 -> {
                val result = listStudent.filter { it.GPA in 7.0..8.5 }
                if (result.isEmpty()) println("Không có sinh viên trong khoảng GPA 7.0 - 8.5")
                for (sv in result) println(sv)
            }
            4 -> {
                print("Nhập tên ngành cần tìm: ")
                val major = readln()
                val result = listStudent.filter { it.Major.equals(major, ignoreCase = true) }
                if (result.isEmpty()) println("Không tìm thấy sinh viên ngành $major")
                for (sv in result) println(sv)
            }
            5 -> {
                print("Nhập từ khóa tên: ")
                val keyword = readln()
                val result = listStudent.filter { it.FullName.contains(keyword, ignoreCase = true) }
                if (result.isEmpty()) println("Không tìm thấy sinh viên chứa từ khóa '$keyword'")
                for (sv in result) println(sv)
            }
            else -> println("Lựa chọn không hợp lệ!")
        }
    }
    fun sum() {
        println("Cách thức tính ")
        println("1. Tính trung bình GPA toàn bộ sinh viên")
        println("2. Tính GPA trung bình của sinh viên ngành được giao")
        print("Choose: ")
        when (readln().toInt()) {
            1 -> {
                if (listStudent.isEmpty()) {
                    println("Danh sách trống!")
                } else {
                    val avg = listStudent.map { it.GPA }.average()
                    println("GPA trung bình toàn trường: $avg")
                }
            }
            2 -> {
                print("Nhập tên ngành: ")
                val major = readln()
                val studentsInMajor = listStudent.filter { it.Major.equals(major, ignoreCase = true) }
                if (studentsInMajor.isEmpty()) {
                    println("Không có sinh viên nào thuộc ngành $major để tính điểm!")
                } else {
                    val avg = studentsInMajor.map { it.GPA }.average()
                    println("GPA trung bình ngành $major: $avg")
                }
            }
            else -> println("Lựa chọn không hợp lệ!")
        }
    }
    fun arrange() {
        println("Cách thức sắp xếp: ")
        println("1. Sắp xếp sinh viên theo GPA giảm dần")
        println("2. Sắp xếp sinh viên theo tuổi")
        println("3. Sắp xếp sinh viên theo tên")
        print("Choose: ")
        when (readln().toInt()) {
            1 -> {
                val sorted = listStudent.sortedByDescending { it.GPA }
                for (sv in sorted) println(sv)
            }
            2 -> {
                val sorted = listStudent.sortedBy { it.Age }
                for (sv in sorted) println(sv)
            }
            3 -> {
                val sorted = listStudent.sortedBy { it.FullName.trim().split(" ").last() }
                for (sv in sorted) println(sv)
            }
            else -> println("Lựa chọn không hợp lệ!")
        }
    }
    fun remove() {
        print("Nhập MSV cần xóa: ")
        val id = readln()
        val isRemoved = listStudent.removeIf { it.StudentID.equals(id, ignoreCase = true) }
        if (isRemoved) {
            println("=> Đã xóa sinh viên có MSV: $id")
        } else {
            println("=> Không tìm thấy sinh viên với MSV: $id")
        }
    }
}
fun main()
{
    var option: Int = 0
    val students = StudentManagement()
    println("\n========== STUDENT MANAGEMENT ==========")
    println("1. Add student")
    println("2. Display students")
    println("3. Count student")
    println("4. Search student")
    println("5. Calculate average GPA")
    println("6. Arrange student")
    println("7. Remove student")
    println("0. Exit")
    do {
        println("========================================")
        println("Choose: ")
        option = readln().toInt()
        when (option) {
            1 -> students.add()
            2 -> students.display()
            3 -> students.count()
            4 -> students.search()
            5 -> students.sum()
            6 -> students.arrange()
            7 -> students.remove()
            0 -> println("Kết thúc chương trình!")
            else -> println("Lựa chọn không hợp lệ, vui lòng chọn lại!")
        }
    } while (option != 0)
}