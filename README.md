# MVC Architecture

### Step-by-Step MVC Architecture:

#### 1. **Model**:
The `Model` is responsible for fetching and providing the data.

#### 2. **View**:
The `View` represents the `Activity` and `RecyclerView` UI where data will be displayed.

#### 3. **Controller**:
The `Controller` manages the interaction between the `Model` and the `View`.

### Let's implement it.

---

#### 1. **Model (EmployeeModel.kt)**:
This will represent the data fetched from the API.

```kotlin
data class EmployeeModel(
    val name: String,
    val profile: String
)

data class EmployeeResponse(
    val message: String,
    val employees: List<EmployeeModel>
)
```

---

#### 2. **Controller (ApiController.kt)**:
This class will fetch the data from the API and pass it to the `View`.

```kotlin
import android.util.Log
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
                    // Parsing the JSON response
                    val employeeResponse = parseEmployeeResponse(responseString)
                    callback(employeeResponse?.employees)
                }
            }
        })
    }

    // Parse JSON using Gson (or any other method)
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
```

---

#### 3. **View (MainActivity.kt)**:
The `MainActivity` will represent the UI, where the data will be shown in a `RecyclerView`.

```kotlin
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
```

---

#### 4. **RecyclerView Adapter (EmployeeAdapter.kt)**:
The `RecyclerView` adapter to bind the employee data to the list.

```kotlin
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
```

---

#### 5. **Layout Files**:

- **`activity_main.xml`** (RecyclerView Layout):

```xml
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/recyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

- **`employee_item.xml`** (Employee item for the RecyclerView):

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/nameTextView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="16sp"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/profileTextView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="14sp" />

</LinearLayout>
```

---

#### 6. **Dependencies**:
Add the following dependencies to your `build.gradle` for networking and JSON parsing.

```groovy
implementation 'com.squareup.okhttp3:okhttp:4.9.1'
implementation 'com.google.code.gson:gson:2.8.6'
```

---

### Summary:
1. **Model**: `EmployeeModel` represents the employee data.
2. **View**: `MainActivity` displays the data in a `RecyclerView`.
3. **Controller**: `ApiController` handles the API calls and data fetching.

Here's the folder structure for implementing MVC architecture in Android Kotlin for your project:

```
/YourProjectName
│
├── /app
│   ├── /src
│   │   ├── /main
│   │   │   ├── /java/com/yourpackage/
│   │   │   │   ├── /controller
│   │   │   │   │   └── ApiController.kt  # The controller class that fetches data from the API
│   │   │   │   ├── /model
│   │   │   │   │   └── EmployeeModel.kt  # Model class representing employee data
│   │   │   │   ├── /view
│   │   │   │   │   ├── MainActivity.kt   # The view class (Activity) for displaying data
│   │   │   │   │   └── EmployeeAdapter.kt # Adapter for the RecyclerView
│   │   │   ├── /res
│   │   │   │   ├── /layout
│   │   │   │   │   ├── activity_main.xml   # Layout for the MainActivity (RecyclerView)
│   │   │   │   │   └── employee_item.xml   # Layout for individual employee item
│   │   │   │   └── /values
│   │   │   │       └── strings.xml         # Strings used in the app (optional)
│   │   ├── /gradle
│   │   └── AndroidManifest.xml              # Manifest file for declaring activities and permissions
│
├── build.gradle (Project)                    # Project-level build.gradle
└── build.gradle (App Module)                 # App-level build.gradle
```

### Explanation of Folder Structure:

1. **Controller** (`/controller`):
   - Contains `ApiController.kt` which handles API requests and serves data to the `View`.

2. **Model** (`/model`):
   - Holds `EmployeeModel.kt` representing employee data structures and `EmployeeResponse` for handling the API response format.

3. **View** (`/view`):
   - Includes `MainActivity.kt` (which contains the UI logic to display data) and `EmployeeAdapter.kt` (RecyclerView adapter to handle the display of employee data).
   
4. **Layout Files** (`/res/layout`):
   - `activity_main.xml`: Layout file containing the `RecyclerView` for displaying the list of employees.
   - `employee_item.xml`: Layout file for each item in the `RecyclerView`.

