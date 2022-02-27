package com.deny.ibitur.android

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.convertTo
import com.bumptech.glide.Glide
import com.deny.ibitur.android.databinding.ActivityConteudoBinding
import com.google.firebase.auth.FirebaseAuth

class ConteudoActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityConteudoBinding

    lateinit var imageUsuario: ImageView
    lateinit var nomeUsuario: TextView
    lateinit var emailUsuario: TextView
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityConteudoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarConteudo.toolbar)

        binding.appBarConteudo.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_conteudo)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )

        var header: View = navView.getHeaderView(0)
        imageUsuario = header.findViewById(R.id.imageUsuario)
        nomeUsuario = header.findViewById(R.id.nomeUsuario)
        emailUsuario = header.findViewById(R.id.emailUsuario)

        val currentUser = mAuth.currentUser

        nomeUsuario.text = currentUser?.displayName
        emailUsuario.text = currentUser?.email
        Glide.with(this).load(currentUser?.photoUrl).into(imageUsuario)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.conteudo, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_conteudo)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}