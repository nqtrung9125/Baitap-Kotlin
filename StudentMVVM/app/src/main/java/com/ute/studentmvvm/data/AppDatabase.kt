package com.ute.studentmvvm.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ute.studentmvvm.model.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.room.migration.Migration
@Database(
    entities = [Student::class],
    version = 2,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ute_student_room.db"
                )
                    .addCallback(DatabaseCallback())
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
        private class DatabaseCallback : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                val students = listOf(
                    Student(
                        fullName = "Nguyễn Văn An",
                        studentCode = "20110001",
                        email = "an.nv@ute.udn.vn", gpa = 3.85
                    ),

                    Student(
                        fullName = "Trần Thị Mai",
                        studentCode = "20110002",
                        email = "mai.tt@ute.udn.vn", gpa = 3.92
                    ),

                    Student(
                        fullName = "Lê Hoàng Phúc",
                        studentCode = "20110003",
                        email = "phuc.lh@ute.udn.vn", gpa = 3.45
                    ),

                    Student(
                        fullName = "Phạm Quốc Dũng",
                        studentCode = "20110004",
                        email = "dung.pq@ute.udn.vn", gpa = 3.60
                    )
                )

                students.forEach { student ->
                    db.execSQL(
                        """
                INSERT OR IGNORE INTO students_table
                (full_name, student_code, email, gpa, major)
                VALUES (?, ?, ?, ?, ?)
                """.trimIndent(),
                        arrayOf(
                            student.fullName,
                            student.studentCode,
                            student.email,
                            student.gpa,
                            student.major
                        )
                    )
                }
            }
        }
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
            DELETE FROM students_table
            WHERE id NOT IN (
                SELECT MAX(id)
                FROM students_table
                GROUP BY student_code
            )
            """.trimIndent()
                )
                db.execSQL(
                    "CREATE UNIQUE INDEX IF NOT EXISTS index_students_table_student_code " +
                            "ON students_table(student_code)"
                )
            }
        }
    }

}