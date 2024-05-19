package com.lovish888.devrev.movies.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lovish888.devrev.movies.ui.MoviesListTabFragment

class MoviesPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return MoviesListTabFragment.newInstance(
            if (position == 0) "latest" else "popular"
        )
    }
}
