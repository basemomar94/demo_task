package com.bassem.demo_task

import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.materialswitch.MaterialSwitch
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? NavHostFragment
        navController = navHostFragment?.navController
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        val itemSwitch = menu?.findItem(R.id.favorite_switch)
        itemSwitch?.setActionView(R.layout.switch_item)

        val sw: MaterialSwitch? =
            menu?.findItem(R.id.favorite_switch)?.actionView?.findViewById(R.id.switch2)

        sw?.setOnCheckedChangeListener { _, isChecked ->
            handleSwitch(isChecked)
        }
        return true
    }

    private fun handleSwitch(isChecked: Boolean) {
        if(isChecked){
            navController?.navigate(R.id.action_global_favoriteFragment)

        } else {
            navController?.navigate(R.id.action_global_matchesFragment)

        }
    }


}