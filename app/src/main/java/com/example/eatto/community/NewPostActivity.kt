package com.example.eatto.community

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.eatto.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NewPostActivity : AppCompatActivity() {

    private lateinit var selectedImageView: ImageView
    private var selectedImageUri: Uri? = null
    private var userNickname: String = "익명 사용자"

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageView.setImageURI(it)
            selectedImageView.visibility = View.VISIBLE
            selectedImageUri = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        // 닉네임 가져오기
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    userNickname = document.getString("nickname") ?: "익명 사용자"
                }
        }

        val selectImageButton = findViewById<ImageButton>(R.id.selectImageButton)
        selectedImageView = findViewById(R.id.selectedImage)
        val contentEditText = findViewById<EditText>(R.id.postContent)
        val uploadButton = findViewById<Button>(R.id.uploadPost)

        selectImageButton.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        uploadButton.setOnClickListener {
            val content = contentEditText.text.toString()
            val imageUrl = selectedImageUri?.toString()
            val timestamp = System.currentTimeMillis()

            val post = hashMapOf(
                "nickname" to userNickname,
                "content" to content,
                "imageUrl" to imageUrl,
                "timestamp" to timestamp,
                "likes" to 0,
                "comments" to 0
            )

            FirebaseFirestore.getInstance()
                .collection("feed_posts")
                .add(post)
                .addOnSuccessListener {
                    setResult(RESULT_OK)
                    finish()
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }


    }
}
