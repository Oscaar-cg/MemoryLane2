package com.example.memorylane2

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memorylane2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CapsuleViewModel by viewModels()
    private lateinit var adapter: CapsuleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CapsuleAdapter(mutableListOf()) { capsule, position ->
            val intent = Intent(this, ViewCapsuleActivity::class.java)
            intent.putExtra("title", capsule.title)
            intent.putExtra("message", capsule.message)
            intent.putExtra("image", capsule.imageUri)
            intent.putExtra("position", position)
            startActivityForResult(intent, 2)
        }

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }

        binding.recyclerView.adapter = adapter

        viewModel.capsules.observe(this) {
            adapter = CapsuleAdapter(it) { capsule, position ->
                val intent = Intent(this, ViewCapsuleActivity::class.java)
                intent.putExtra("title", capsule.title)
                intent.putExtra("message", capsule.message)
                intent.putExtra("image", capsule.imageUri)
                intent.putExtra("position", position)
                startActivityForResult(intent, 2)
            }
            binding.recyclerView.adapter = adapter
        }

        binding.addButton.setOnClickListener {
            startActivityForResult(Intent(this, CreateCapsuleActivity::class.java), 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            val deleteIndex = data?.getIntExtra("delete_index", -1) ?: -1
            if (deleteIndex != -1) {
                viewModel.deleteCapsule(deleteIndex)
                return
            }

            if (requestCode == 3) {
                val index = data?.getIntExtra("position", -1) ?: -1
                if (index != -1) {
                    val updated = Capsule(
                        data?.getStringExtra("title") ?: "",
                        data?.getStringExtra("message") ?: "",
                        data?.getLongExtra("date", System.currentTimeMillis()) ?: System.currentTimeMillis(),
                        data?.getStringExtra("image")
                    )
                    viewModel.updateCapsule(index, updated)
                }
            }

            if (requestCode == 1) {
                val capsule = Capsule(
                    data?.getStringExtra("title") ?: "",
                    data?.getStringExtra("message") ?: "",
                    data?.getLongExtra("date", System.currentTimeMillis()) ?: System.currentTimeMillis(),
                    data?.getStringExtra("image")
                )
                viewModel.addCapsule(capsule)
            }
        }
    }
}