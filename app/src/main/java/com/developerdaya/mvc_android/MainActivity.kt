package com.developerdaya.mvc_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developerdaya.mvc_android.controller.ApiController
import com.developerdaya.mvc_android.view.EmployeeAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var employeeAdapter: EmployeeAdapter
    private val apiController = ApiController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting up RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        employeeAdapter = EmployeeAdapter(emptyList())
        recyclerView.adapter = employeeAdapter

        // Fetching the data from API
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
