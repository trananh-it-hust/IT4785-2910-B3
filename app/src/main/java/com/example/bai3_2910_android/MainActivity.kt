package com.example.bai3_2910_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var students: List<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        students = loadStudentsFromAssets()
        studentAdapter = StudentAdapter(students)
        recyclerView.adapter = studentAdapter

        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length > 2) {
                    filter(newText)
                } else {
                    studentAdapter.updateList(students)
                }
                return true
            }
        })
    }

    private fun loadStudentsFromAssets(): List<Student> {
        val inputStream = assets.open("data.json")
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<Student>>() {}.type
        return Gson().fromJson(reader, type)
    }

    private fun filter(text: String) {
        val normalizedText = text.lowercase(Locale("vi"))
        val filteredList = students.filter {
            it.name.lowercase(Locale("vi")).contains(normalizedText) || it.studentId.contains(normalizedText)
        }
        studentAdapter.updateList(filteredList)
    }
}