package com.deny.ibitur.android

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.*

class SignInActivity : AppCompatActivity() {

    private lateinit var textVouMeCadastar: TextView
    private lateinit var buttonLogin: Button
    private lateinit var loginEmail: TextInputEditText
    private lateinit var loginSenha: TextInputEditText

    companion object{
        private const val RC_SIGN_IN = 120
    }

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var googleSignInClient: GoogleSignInClient

    lateinit var buttonSignIn: SignInButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        supportActionBar?.hide()

        buttonSignIn = findViewById(R.id.button_sign_in)
        textVouMeCadastar = findViewById(R.id.textVouMeCadastrar)
        buttonLogin = findViewById(R.id.buttonLoginEmailSenha)
        loginEmail = findViewById(R.id.loginEmail)
        loginSenha = findViewById(R.id.loginSenha)


        textVouMeCadastar.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        })

        buttonLogin.setOnClickListener(View.OnClickListener {
            loginEmailSenha()
        })

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        buttonSignIn.setOnClickListener(View.OnClickListener {
            signIn()
        })

    }

    fun loginEmailSenha(){

        var email = loginEmail.text.toString()
        var senha = loginSenha.text.toString()

        if (!email.isEmpty()){
            if (!senha.isEmpty()){

                mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(
                    OnCompleteListener {
                        if (it.isSuccessful){
                            startActivity(Intent(this, ConteudoActivity::class.java))
                            finish()
                        } else if(!it.isSuccessful){
                            try {
                                throw it.exception!!
                            } catch (e: FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(this, "Email invalido", Toast.LENGTH_SHORT).show()
                            } catch (e: FirebaseAuthInvalidUserException){
                                Toast.makeText(this, "Usuario não cadastrado", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception){
                                Toast.makeText(this, "Erro: "+e.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

            } else{
                Toast.makeText(this, "Digite a sua senha", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this, "Digite o seu email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            } else{
                Log.w("SignInActivity", exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                    val intent2 = Intent(this, ConteudoActivity::class.java)
                    startActivity(intent2)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)
                }
            }
    }
}