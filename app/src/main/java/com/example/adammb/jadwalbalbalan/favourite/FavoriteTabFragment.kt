package com.example.adammb.jadwalbalbalan.favourite


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adammb.jadwalbalbalan.R
import com.example.adammb.jadwalbalbalan.event.EventFragment
import com.example.adammb.jadwalbalbalan.shared.TabAdapter
import com.example.adammb.jadwalbalbalan.team.TeamFragment
import kotlinx.android.synthetic.main.fragment_favorite_tab.*

class FavoriteTabFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_tab, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViewPager()
    }

    companion object {
        fun newInstance(): FavoriteTabFragment = FavoriteTabFragment()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbarFavoriteTab)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_favorite)
    }

    private fun setupViewPager() {
        val tabAdapter = TabAdapter(childFragmentManager)
        tabAdapter.addFragment(EventFragment.newInstance(EventFragment.EVENT_TYPE_FAVORITE), getString(R.string.title_match))
        tabAdapter.addFragment(TeamFragment.newInstance(TeamFragment.TYPE_FAVORITE), getString(R.string.title_team))

        viewPagerFavoriteTab.adapter = tabAdapter
        tabFavoriteTab.setupWithViewPager(viewPagerFavoriteTab)
    }

}
