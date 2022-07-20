package com.baszczyk.mercdream

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.baszczyk.mercdream.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawerLayout = binding.drawerMenu
        navController = this.findNavController(R.id.myNavHostFragment)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
        binding.navView.setNavigationItemSelectedListener(this)

    }

    override fun onSupportNavigateUp(): Boolean {
            return NavigationUI.navigateUp(navController, drawerLayout)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                intent = Intent(this@MainActivity, LoggingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                startActivity(intent)
            }
           else -> {
               drawerLayout.closeDrawer(GravityCompat.START)
               NavigationUI.onNavDestinationSelected(item, navController)
           }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        when {
            drawerLayout.isDrawerOpen(GravityCompat.START) -> {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            navController.graph.id == R.id.listFragment -> {
                showLogoutDialog()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    private fun showLogoutDialog(){
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Czy chcesz sie wylogowac?")
        dialog.setPositiveButton("TAK"){ _: DialogInterface, _: Int ->
            intent = Intent(this@MainActivity, LoggingActivity::class.java)
            startActivity(intent)
        }
        dialog.show()
    }

}




