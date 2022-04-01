package com.deny.ibitur.app.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.deny.ibitur.app.R
import com.deny.ibitur.app.databinding.ActivityConteudoBinding
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
        Glide.with(this).load(currentUser?.photoUrl).transform(CircleCrop()).into(imageUsuario)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.conteudo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_sair){
            mAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_conteudo)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}