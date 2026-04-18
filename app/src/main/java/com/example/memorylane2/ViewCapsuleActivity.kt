package com.example.memorylane2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.memorylane2.databinding.ActivityViewCapsuleBinding

class ViewCapsuleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewCapsuleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewCapsuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("position", -1)

        binding.title.text = intent.getStringExtra("title")
        binding.message.text = intent.getStringExtra("message")

        val imageUri = intent.getStringExtra("image")
        if (imageUri != null) {
            binding.image.setImageURI(Uri.parse(imageUri))
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.closeButton.setOnClickListener {
            finish()
        }

        binding.editButton.setOnClickListener {
            val intent = Intent(this, CreateCapsuleActivity::class.java)
            intent.putExtra("title", binding.title.text.toString())
            intent.putExtra("message", binding.message.text.toString())
            intent.putExtra("image", imageUri)
            intent.putExtra("position", position)
            startActivityForResult(intent, 3)
        }

        binding.deleteButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("delete_index", position)
            setResult(Activity.RESULT_OK, resultIntent)
            Toast.makeText(this, "Capsule deleted", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}