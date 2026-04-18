package com.example.memorylane2

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.memorylane2.databinding.ActivityCreateCapsuleBinding
import java.util.Calendar

class CreateCapsuleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCapsuleBinding
    private var selectedImageUri: Uri? = null
    private var selectedDate: Long = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateCapsuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.dateButton.setOnClickListener {
            val calendar = Calendar.getInstance()

            DatePickerDialog(this,
                { _, year, month, day ->
                    val cal = Calendar.getInstance()
                    cal.set(year, month, day)
                    selectedDate = cal.timeInMillis
                    binding.dateButton.text = "$day/${month + 1}/$year"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.photoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(intent, 2)
        }

        binding.saveButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("title", binding.titleInput.text.toString())
            intent.putExtra("message", binding.messageInput.text.toString())
            intent.putExtra("date", selectedDate)
            intent.putExtra("image", selectedImageUri?.toString())

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2 && resultCode == RESULT_OK) {
            selectedImageUri = data?.data

            selectedImageUri?.let {
                contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        }
    }
}