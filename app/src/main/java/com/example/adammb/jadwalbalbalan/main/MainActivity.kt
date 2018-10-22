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
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout


class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var navigation: BottomNavigationView

    private val mOnNavigationItemSelectedListener = OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_previous_match -> {
                supportActionBar?.title = getString(R.string.title_previous_match)
                replaceFragment(EventFragment.newInstance(EventFragment.EVENT_TYPE_PREV))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_next_match -> {
                supportActionBar?.title = getString(R.string.title_next_match)
                replaceFragment(EventFragment.newInstance(EventFragment.EVENT_TYPE_NEXT))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                supportActionBar?.title = getString(R.string.title_favorite)
                replaceFragment(EventFragment.newInstance(EventFragment.EVENT_TYPE_FAVORITE))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)

        toolbar = find(R.id.main_toolbar)
        navigation = find(R.id.main_navigation)

        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            replaceFragment(EventFragment.newInstance(EventFragment.EVENT_TYPE_PREV))
            supportActionBar?.title = getString(R.string.title_previous_match)
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

                themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {
                    id = R.id.main_appbar

                    toolbar {
                        id = R.id.main_toolbar
                        setTitleTextColor(Color.WHITE)
                        popupTheme = R.style.AppTheme_PopupOverlay
                    }.lparams(width = matchParent) {
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                    }
                }.lparams(width = matchParent)

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

