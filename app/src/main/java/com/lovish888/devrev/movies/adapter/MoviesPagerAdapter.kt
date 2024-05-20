package com.lovish888.devrev.movies.adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lovish888.devrev.movies.ui.MoviesListFragment

class MoviesPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return MoviesListFragment().apply {
            arguments = Bundle().apply {
                putString("tabType", if (position == 0) "latest" else "popular")
            }
        }
    }
}
