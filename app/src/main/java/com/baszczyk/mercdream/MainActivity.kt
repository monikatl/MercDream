package com.baszczyk.mercdream

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.baszczyk.mercdream.database.PiggyDatabase
import com.baszczyk.mercdream.database.enities.PiggyBank
import com.baszczyk.mercdream.databinding.ActivityMainBinding
import com.baszczyk.mercdream.form.FormFragmentDirections
import com.baszczyk.mercdream.home.ListFragmentDirections
import com.baszczyk.mercdream.logging.LoggingViewModel
import com.baszczyk.mercdream.piggy.PiggyBankFragment
import com.baszczyk.mercdream.piggy.PiggyBankFragmentDirections
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_logging.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.myNavHostFragment
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_history.view.*
import kotlinx.android.synthetic.main.nav_header.view.*
import java.util.*
import kotlin.jvm.java as java1

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)


        val prompt = Prompt(this.lifecycle)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawerLayout = binding.drawerMenu

        navController = this.findNavController(R.id.myNavHostFragment)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        NavigationUI.setupWithNavController(binding.navView, navController)

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
            return NavigationUI.navigateUp(navController, drawerLayout)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                intent = Intent(this@MainActivity, LoggingActivity::class.java1)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if(navController.graph.id == R.id.listFragment){
            showLogoutDialog()

        }
        else {
            super.onBackPressed()
        }
    }

    fun showLogoutDialog(){
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Czy chcesz sie wylogowac?")
        dialog.setPositiveButton("TAK"){ dialogInterface: DialogInterface, i: Int ->
            intent = Intent(this@MainActivity, LoggingActivity::class.java1)
            startActivity(intent)
        }
        dialog.show()
    }

}




