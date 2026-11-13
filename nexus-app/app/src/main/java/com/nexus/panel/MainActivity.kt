package com.nexus.panel

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.nexus.panel.fragments.HomeFragment
import com.nexus.panel.fragments.PermissionFragment
import com.nexus.panel.fragments.VpnFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navView: NavigationView
    private lateinit var tvTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar     = findViewById(R.id.toolbar)
        drawerLayout= findViewById(R.id.drawerLayout)
        navView     = findViewById(R.id.navView)
        tvTitle     = findViewById(R.id.tvToolbarTitle)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.nav_open, R.string.nav_close
        )
        // ícone customizado via drawer toggle
        toggle.drawerArrowDrawable.color = getColor(R.color.red_neon)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        // abre HomeFragment na inicialização
        loadFragment(HomeFragment(), "HOME")
        navView.setCheckedItem(R.id.navHome)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navHome       -> loadFragment(HomeFragment(),       "HOME")
            R.id.navPermission -> loadFragment(PermissionFragment(), "PERMISSION")
            R.id.navVpn        -> loadFragment(VpnFragment(),        "VPN LOCAL")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadFragment(fragment: Fragment, title: String) {
        tvTitle.text = title
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
