package com.deny.ibitur.android

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.deny.ibitur.android.databinding.ActivityCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException


class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root

        supportActionBar?.hide()

        binding.buttonMeCadastrar.setOnClickListener(View.OnClickListener {
            meCadastrar()
        })

        binding.textVoltarTelaInicial.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        })

        setContentView(view)
    }
    fun meCadastrar(){

        var email = binding.cadastrarEmail.text.toString()
        var senha = binding.cadastrarSenha.text.toString()

        if (!email.isEmpty()){
            if (!senha.isEmpty()){
                mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener {task->
                    if (!task.isSuccessful) {
                        try {
                            throw task.exception!!
                        } catch (e: FirebaseAuthWeakPasswordException) {
                            Toast.makeText(applicationContext, "Senha fraca, digite uma senha mais forte", Toast.LENGTH_LONG).show()
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(applicationContext, "Email invalido, digite um email valido", Toast.LENGTH_LONG).show()
                        } catch (e: FirebaseAuthUserCollisionException) {
                            Toast.makeText(applicationContext, "Usuario já existente", Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {

                        }
                    } else if(task.isSuccessful){
                        startActivity(Intent(this, ConteudoActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}