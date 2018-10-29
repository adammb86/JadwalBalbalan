package com.example.adammb.jadwalbalbalan.event


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.example.adammb.jadwalbalbalan.R
import com.example.adammb.jadwalbalbalan.search.SearchActivity
import com.example.adammb.jadwalbalbalan.shared.TabAdapter
import kotlinx.android.synthetic.main.fragment_event_tab.*
import org.jetbrains.anko.support.v4.startActivity

class EventTabFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViewPager()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                startActivity<SearchActivity>(SearchActivity.TYPE to SearchActivity.TYPE_EVENT)

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun newInstance(): EventTabFragment = EventTabFragment()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbarEventTab)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_match)
    }

    private fun setupViewPager() {
        val tabAdapter = TabAdapter(childFragmentManager)
        tabAdapter.addFragment(EventFragment.newInstance(EventFragment.EVENT_TYPE_NEXT), getString(R.string.title_next_match))
        tabAdapter.addFragment(EventFragment.newInstance(EventFragment.EVENT_TYPE_PREV), getString(R.string.title_prev_match))

        viewPagerEventTab.adapter = tabAdapter
        tabEventTab.setupWithViewPager(viewPagerEventTab)
    }
}
