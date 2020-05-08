package com.example.greenwaste2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.example.greenwaste2.R
import com.example.greenwaste2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var navController:NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController=findNavController(R.id.nav_host_fragment)
        setUpNavigation()
    }

    private fun setUpNavigation(){
        appBarConfiguration= AppBarConfiguration(navController.graph)
        binding.bottomnav.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() = when {
        navController.graph.startDestination == navController.currentDestination?.id ->  closeDialoge()
        else ->goUp()
    }

    private fun goUp(){
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
        navController.navigateUp()
    }

    private fun closeDialoge(){
        MaterialDialog(this).show {
            cornerRadius(5F)
            title(text = "End?")
            message(text = "Are you sure you want to exit the app?")

            positiveButton(text = "yes") { dialog ->
                finishAffinity()
            }

            negativeButton(text = "no") {
                hide()
            }

        }
    }

}
