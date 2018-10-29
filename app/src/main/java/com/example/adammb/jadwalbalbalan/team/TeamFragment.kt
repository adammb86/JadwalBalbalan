package com.example.adammb.jadwalbalbalan.team


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.AdapterView
import android.widget.Spinner
import com.example.adammb.jadwalbalbalan.R
import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.model.league.League
import com.example.adammb.jadwalbalbalan.model.team.Team
import com.example.adammb.jadwalbalbalan.search.SearchActivity
import com.example.adammb.jadwalbalbalan.shared.LeagueAdapter
import com.example.adammb.jadwalbalbalan.teamdetail.TeamDetailActivity
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamFragment : Fragment(),
        TeamContract.TeamView {

    private var type: String? = null
    private var leagueId: String? = "4328"

    private var teams: MutableList<Team> = mutableListOf()
    private var leagues: MutableList<League> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinner: Spinner
    private lateinit var spinnerAdapter: LeagueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            type = arguments?.getString(TYPE)
        }
    }

    override fun onResume() {
        super.onResume()
        when (arguments?.get(TYPE)) {
            TYPE_FAVORITE -> presenter.getFavoriteTeamList()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return TeamFragmentUI()
                .createView(AnkoContext.create(container!!.context, container))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setupToolbar(view)

        setupLeagueSpinner(view)
        setupSwipeRefreshLayout(view)
        setupRecyclerView(view)

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, request, gson)
        when (arguments?.get(TYPE)) {
            TYPE_FAVORITE -> {
                spinner.visibility = View.GONE
                presenter.getFavoriteTeamList()
                setHasOptionsMenu(false)
                toolbar.visibility = View.GONE
            }
            else -> {
                presenter.getLeagueList()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                startActivity<SearchActivity>(SearchActivity.TYPE to SearchActivity.TYPE_TEAM)

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val TYPE = "type"
        const val TYPE_LIST = "type-list"
        const val TYPE_FAVORITE = "type-favorite"

        fun newInstance(type: String): TeamFragment {
            val fragment = TeamFragment()
            val args = Bundle()
            args.putString(TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }

    class TeamFragmentUI() : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            coordinatorLayout {
                lparams {
                    width = matchParent
                    height = matchParent
                }
                backgroundColor = ContextCompat.getColor(context, R.color.grey_200)

                themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {
                    id = R.id.team_appbar

                    toolbar {
                        id = R.id.team_toolbar
                        setTitleTextColor(Color.WHITE)
                        popupTheme = R.style.AppTheme_PopupOverlay
                    }.lparams(width = matchParent) {
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                    }
                }.lparams(width = matchParent)

                verticalLayout {
                    lparams {
                        width = matchParent
                        height = matchParent
                    }

                    padding = dip(8)
                    backgroundColor = ContextCompat.getColor(context, R.color.grey_200)

                    spinner {
                        id = R.id.team_spinner_league
                    }.lparams {
                        leftMargin = dip(8)
                    }

                    swipeRefreshLayout {
                        id = R.id.team_swiperefreshlayout
                        recyclerView {
                            id = R.id.team_recyclerview
                            lparams(width = matchParent, height = matchParent)
                            layoutManager = LinearLayoutManager(context)
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }.lparams {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                    width = matchParent
                    height = matchParent
                }
            }
        }
    }

    override fun getContextFromFragment(): Context? {
        return this@TeamFragment.context
    }

    override fun showLoading() {
        swipeRefreshLayout.post {
            swipeRefreshLayout.isRefreshing = true
        }
    }

    override fun hideLoading() {
        swipeRefreshLayout.post {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun showLeagueList(leagues: List<League>) {
        this@TeamFragment.leagues.clear()
        for (league: League in leagues) {
            if (league.sportType.equals("soccer", true)) {
                this@TeamFragment.leagues.add(league)
            }
        }
        spinnerAdapter.notifyDataSetChanged()
    }

    override fun showTeamList(teams: List<Team>) {
        hideLoading()
        this@TeamFragment.teams.clear()
        this@TeamFragment.teams.addAll(teams)
        adapter.notifyDataSetChanged()
    }

    private fun setupToolbar(view: View) {
        toolbar = view.find(R.id.team_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_team)
    }

    private fun setupLeagueSpinner(view: View) {
        spinner = view.find(R.id.team_spinner_league)
        spinnerAdapter = LeagueAdapter(context, leagues)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueId = leagues[position].leagueId
                presenter.getTeamList(leagueId)
            }
        }
    }

    private fun setupSwipeRefreshLayout(view: View) {
        swipeRefreshLayout = view.find(R.id.team_swiperefreshlayout)
        swipeRefreshLayout.onRefresh {
            when (arguments?.get(TYPE)) {
                TYPE_FAVORITE -> presenter.getFavoriteTeamList()
                else -> presenter.getTeamList(leagueId)
            }
        }
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.find(R.id.team_recyclerview)
        adapter = TeamAdapter(context, teams) { team ->
            startActivity<TeamDetailActivity>(TeamDetailActivity.TEAM_EXTRA to team)
        }
        recyclerView.adapter = adapter
    }
}
