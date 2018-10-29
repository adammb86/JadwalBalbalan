package com.example.adammb.jadwalbalbalan.search

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.widget.AutoCompleteTextView
import com.example.adammb.jadwalbalbalan.R
import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.event.EventAdapter
import com.example.adammb.jadwalbalbalan.eventdetail.EventDetailActivity
import com.example.adammb.jadwalbalbalan.model.event.Event
import com.example.adammb.jadwalbalbalan.model.team.Team
import com.example.adammb.jadwalbalbalan.team.TeamAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh


class SearchActivity : AppCompatActivity(),
        SearchContract.SearchView {

    private lateinit var type: String
    private var query: String = ""

    private var events: MutableList<Event> = mutableListOf()
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var eventAdapter: EventAdapter
    private lateinit var teamAdapter: TeamAdapter

    private lateinit var presenter: SearchPresenter

    companion object {
        const val TYPE = "type"
        const val TYPE_EVENT = "type-event"
        const val TYPE_TEAM = "type-team"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        type = intent.extras.getString(TYPE)

        setupToolbar()
        setupSwipeRefreshLayout()
        setupRecyclerView()
        setupSearchView()

        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchPresenter(this, request, gson)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun getContext(): Context? {
        return this@SearchActivity
    }

    override fun showLoading() {
        swipeRefreshLayoutSearch.post {
            swipeRefreshLayoutSearch.isRefreshing = true
        }
    }

    override fun hideLoading() {
        swipeRefreshLayoutSearch.post {
            swipeRefreshLayoutSearch.isRefreshing = false
        }
    }

    override fun showEventList(events: List<Event>) {
        hideLoading()
        this@SearchActivity.events.clear()
        for (event: Event in events) {
            if (event.sportType.equals("soccer", true)) {
                this@SearchActivity.events.add(event)
            }
        }
        eventAdapter.notifyDataSetChanged()
    }

    override fun showTeamList(teams: List<Team>) {
        hideLoading()
        this@SearchActivity.teams.clear()
        this@SearchActivity.teams.addAll(teams)
        teamAdapter.notifyDataSetChanged()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbarSearch)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupSearchView() {
        searchViewSearch.queryHint = when (type) {
            TYPE_EVENT -> getString(R.string.hint_search_match)
            TYPE_TEAM -> getString(R.string.hint_search_team)
            else -> getString(R.string.hint_search)
        }

        val autoCompleteTextView = searchViewSearch.findViewById(android.support.v7.appcompat.R.id.search_src_text) as AutoCompleteTextView
        autoCompleteTextView.threshold = 3

        searchViewSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                query = newText
                when (type) {
                    TYPE_EVENT -> presenter.searchEvents(query)
                    TYPE_TEAM -> presenter.searchTeams(query)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                this@SearchActivity.query = query
                when (type) {
                    TYPE_EVENT -> presenter.searchEvents(query)
                    TYPE_TEAM -> presenter.searchTeams(query)
                }
                return false
            }
        })
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayoutSearch.onRefresh {
            when (type) {
                TYPE_EVENT -> presenter.searchEvents(query)
                TYPE_TEAM -> presenter.searchTeams(query)
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerViewSearch.layoutManager = LinearLayoutManager(getContext())
        when (type) {
            TYPE_EVENT -> {
                eventAdapter = EventAdapter(getContext(), events, false, { event ->
                    startActivity<EventDetailActivity>(EventDetailActivity.EVENT_EXTRA to event)
                }, {})
                recyclerViewSearch.adapter = eventAdapter
            }
            TYPE_TEAM -> {
                teamAdapter = TeamAdapter(getContext(), teams) { team ->
                    startActivity<TeamDetailActivity>(TeamDetailActivity.TEAM_EXTRA to team)
                }
                recyclerViewSearch.adapter = teamAdapter
            }
        }
    }
}
