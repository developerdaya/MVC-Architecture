package com.developerdaya.mvc_android.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developerdaya.mvc_android.R
import com.developerdaya.mvc_android.controller.ApiController

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var employeeAdapter: EmployeeAdapter
    private val apiController = ApiController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        employeeAdapter = EmployeeAdapter(emptyList())
        recyclerView.adapter = employeeAdapter
        fetchEmployees()
    }

    private fun fetchEmployees() {
        apiController.fetchEmployees { employees ->
            runOnUiThread {
                employees?.let {
                    employeeAdapter.updateEmployees(it)
                }
            }
        }
    }
}
