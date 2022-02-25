package com.deny.ibitur.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class DashBoardActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var idText: TextView
    lateinit var nameText: TextView
    lateinit var emailText: TextView
    lateinit var imageViewEmail: ImageView
    lateinit var buttonDeslogar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        idText = findViewById(R.id.idText)
        nameText = findViewById(R.id.nameText)
        emailText = findViewById(R.id.emailText)
        imageViewEmail = findViewById(R.id.imageViewEmail)
        buttonDeslogar = findViewById(R.id.buttonDeslogar)

        val currentUser = mAuth.currentUser

        idText.text = currentUser?.uid
        nameText.text = currentUser?.displayName
        emailText.text = currentUser?.email

        Glide.with(this).load(currentUser?.photoUrl).into(imageViewEmail)

        buttonDeslogar.setOnClickListener(View.OnClickListener {
            mAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        })

    }
}