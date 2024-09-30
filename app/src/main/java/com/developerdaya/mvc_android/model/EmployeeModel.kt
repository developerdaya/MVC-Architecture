package com.developerdaya.mvc_android.model


data class EmployeeResponse(
    val message: String,
    val employees: List<EmployeeModel>
)

data class EmployeeModel(
    val name: String,
    val profile: String
)
