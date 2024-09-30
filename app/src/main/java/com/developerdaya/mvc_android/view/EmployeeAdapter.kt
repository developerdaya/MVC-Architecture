package com.developerdaya.mvc_android.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developerdaya.mvc_android.R
import com.developerdaya.mvc_android.model.EmployeeModel

class EmployeeAdapter(private var employees: List<EmployeeModel>) :
    RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val profileTextView: TextView = itemView.findViewById(R.id.profileTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.employee_item, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employees[position]
        holder.nameTextView.text = employee.name
        holder.profileTextView.text = employee.profile
    }

    override fun getItemCount(): Int {
        return employees.size
    }

    fun updateEmployees(newEmployees: List<EmployeeModel>) {
        this.employees = newEmployees
        notifyDataSetChanged()
    }
}
