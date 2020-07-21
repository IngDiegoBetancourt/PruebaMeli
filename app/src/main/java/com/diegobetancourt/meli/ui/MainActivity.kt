package com.diegobetancourt.meli.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.diegobetancourt.meli.R

/* contains the toolbar & navigation */
class MainActivity : AppCompatActivity() {

    private lateinit var currentNavController: NavController
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentNavController =  findNavController(R.id.nav_host_fragment)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toolbar.setNavigationOnClickListener {
            onSupportNavigateUp()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }



}