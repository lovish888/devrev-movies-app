package com.lovish888.devrev.movies.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lovish888.devrev.movies.R
import com.lovish888.devrev.movies.adapter.MoviesPagerAdapter

class MoviesListTabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list_tab, container, false)

        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)
        val tabLayout = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val adapter = MoviesPagerAdapter(this)
        viewPager.adapter = adapter

        tabLayout.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_latest -> viewPager.currentItem = 0
                R.id.nav_popular -> viewPager.currentItem = 1
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> tabLayout.selectedItemId = R.id.nav_latest
                    1 -> tabLayout.selectedItemId = R.id.nav_popular
                }
            }
        })

        return view
    }
}
