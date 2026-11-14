package com.example.btfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil

class StudentAdapter(
    private val onClick: (Student) -> Unit
) : ListAdapter<Student, StudentAdapter.StudentViewHolder>(Diff()) {

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvName)
        val id: TextView = view.findViewById(R.id.tvStudentId)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)

        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)

        holder.name.text = student.name
        holder.id.text = student.studentId
        holder.itemView.setOnClickListener {
            onClick(student)
        }
    }

    private class Diff : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Student, newItem: Student) =
            oldItem == newItem
    }
}