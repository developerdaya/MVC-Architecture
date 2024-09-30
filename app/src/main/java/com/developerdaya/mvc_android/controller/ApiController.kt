package com.developerdaya.mvc_android.controller

import android.util.Log
import com.developerdaya.mvc_android.model.EmployeeModel
import com.developerdaya.mvc_android.model.EmployeeResponse
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class ApiController {

    private val client = OkHttpClient()

    fun fetchEmployees(callback: (List<EmployeeModel>?) -> Unit) {
        val request = Request.Builder()
            .url("https://mocki.io/v1/1a44a28a-7c86-4738-8a03-1eafeffe38c8")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("API", "Failed to fetch data", e)
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val responseString = responseBody.string()
                    val employeeResponse = parseEmployeeResponse(responseString)
                    callback(employeeResponse?.employees)
                }
            }
        })
    }

    private fun parseEmployeeResponse(jsonString: String): EmployeeResponse? {
        return try {
            val gson = Gson()
            gson.fromJson(jsonString, EmployeeResponse::class.java)
        } catch (e: Exception) {
            Log.e("API", "Failed to parse JSON", e)
            null
        }
    }
}
