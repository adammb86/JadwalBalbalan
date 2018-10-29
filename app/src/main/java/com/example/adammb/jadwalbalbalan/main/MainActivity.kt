package com.example.adammb.jadwalbalbalan.main

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import com.example.adammb.jadwalbalbalan.R
import com.example.adammb.jadwalbalbalan.event.EventFragment
import com.example.adammb.jadwalbalbalan.event.EventTabFragment
import com.example.adammb.jadwalbalbalan.favourite.FavoriteTabFragment
import com.example.adammb.jadwalbalbalan.team.TeamFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout


class MainActivity : AppCompatActivity() {
    private lateinit var navigation: BottomNavigationView

    private val mOnNavigationItemSelectedListener = OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_match -> {
                replaceFragment(EventTabFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_team -> {
                replaceFragment(TeamFragment.newInstance(TeamFragment.TYPE_LIST))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                replaceFragment(FavoriteTabFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)

        navigation = find(R.id.main_navigation)

        if (savedInstanceState == null) {
            replaceFragment(EventTabFragment.newInstance())
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
                .replace(R.id.main_framelayout_container, fragment)
                .commit()
    }

    class MainActivityUI() : AnkoComponent<MainActivity> {
        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            coordinatorLayout {
                lparams {
                    width = matchParent
                    height = matchParent
                }
                backgroundColor = ContextCompat.getColor(context, R.color.grey_200)

                frameLayout {
                    id = R.id.main_framelayout_container
                    bottomPadding = dip(48)
                }.lparams(width = matchParent, height = matchParent) {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }

                bottomNavigationView {
                    id = R.id.main_navigation
                    backgroundColor = ContextCompat.getColor(context, R.color.white)
                    inflateMenu(R.menu.navigation)
                }.lparams(width = matchParent) {
                    gravity = Gravity.BOTTOM
                }
            }
        }
    }
}