package com.example.adammb.jadwalbalbalan.event

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import com.example.adammb.jadwalbalbalan.R
import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.eventdetail.EventDetailActivity
import com.example.adammb.jadwalbalbalan.model.event.Event
import com.example.adammb.jadwalbalbalan.model.league.League
import com.example.adammb.jadwalbalbalan.shared.LeagueAdapter
import com.example.adammb.jadwalbalbalan.util.DateUtil
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class EventFragment : Fragment(),
        EventContract.EventView {

    private var eventType: String? = null
    private var leagueId: String? = "4328"

    private var events: MutableList<Event> = mutableListOf()
    private var leagues: MutableList<League> = mutableListOf()
    private lateinit var presenter: EventPresenter
    private lateinit var adapter: EventAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinner: Spinner
    private lateinit var spinnerAdapter: LeagueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            eventType = arguments?.getString(EVENT_TYPE)
        }
    }

    override fun onResume() {
        super.onResume()
        when (arguments?.get(EVENT_TYPE)) {
            EVENT_TYPE_FAVORITE -> presenter.getFavoriteEventList()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return EventFragmentUI()
                .createView(AnkoContext.create(container!!.context, container))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isRemindable = when (arguments?.get(EVENT_TYPE)) {
            EVENT_TYPE_NEXT -> true
            else -> false
        }

        setupLeagueSpinner(view)
        setupSwipeRefreshLayout(view)
        setupRecyclerView(view, isRemindable)

        val request = ApiRepository()
        val gson = Gson()
        presenter = EventPresenter(this, request, gson)
        when (arguments?.get(EVENT_TYPE)) {
            EVENT_TYPE_FAVORITE -> {
                spinner.visibility = View.GONE
                presenter.getFavoriteEventList()
            }
            else -> {
                presenter.getLeagueList()
            }
        }
    }

    companion object {
        const val EVENT_TYPE = "event-type"
        const val EVENT_TYPE_PREV = "event-type-prev"
        const val EVENT_TYPE_NEXT = "event-type-next"
        const val EVENT_TYPE_FAVORITE = "event-type-favorite"

        fun newInstance(eventType: String): EventFragment {
            val fragment = EventFragment()
            val args = Bundle()
            args.putString(EVENT_TYPE, eventType)
            fragment.arguments = args
            return fragment
        }
    }

    class EventFragmentUI() : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            verticalLayout {
                lparams {
                    width = matchParent
                    height = matchParent
                }

                padding = dip(8)
                backgroundColor = ContextCompat.getColor(context, R.color.grey_200)

                spinner {
                    id = R.id.event_spinner_league
                }.lparams {
                    leftMargin = dip(8)
                }

                swipeRefreshLayout {
                    id = R.id.event_swiperefreshlayout
                    recyclerView {
                        id = R.id.event_recyclerview
                        lparams(width = matchParent, height = matchParent)
                        layoutManager = LinearLayoutManager(context)
                    }
                }
            }
        }
    }

    override fun getContextFromFragment(): Context? {
        return this@EventFragment.context
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
        this@EventFragment.leagues.clear()
        for (league: League in leagues) {
            if (league.sportType.equals("soccer", true)) {
                this@EventFragment.leagues.add(league)
            }
        }
        spinnerAdapter.notifyDataSetChanged()
    }

    override fun showEventList(events: List<Event>) {
        hideLoading()
        this@EventFragment.events.clear()
        this@EventFragment.events.addAll(events)
        adapter.notifyDataSetChanged()
    }

    private fun setupLeagueSpinner(view: View) {
        spinner = view.find(R.id.event_spinner_league)
        spinnerAdapter = LeagueAdapter(context, leagues)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueId = leagues[position].leagueId
                when (arguments?.get(EVENT_TYPE)) {
                    EVENT_TYPE_PREV -> presenter.getPrevEventList(leagueId)
                    EVENT_TYPE_NEXT -> presenter.getNextEventList(leagueId)
                }
            }
        }
    }

    private fun setupSwipeRefreshLayout(view: View) {
        swipeRefreshLayout = view.find(R.id.event_swiperefreshlayout)
        swipeRefreshLayout.onRefresh {
            when (arguments?.get(EVENT_TYPE)) {
                EVENT_TYPE_PREV -> presenter.getPrevEventList(leagueId)
                EVENT_TYPE_NEXT -> presenter.getNextEventList(leagueId)
                EVENT_TYPE_FAVORITE -> presenter.getFavoriteEventList()
            }
        }
    }

    private fun setupRecyclerView(view: View, isRemindable: Boolean) {
        recyclerView = view.find(R.id.event_recyclerview)
        adapter = EventAdapter(context, events, isRemindable, { event ->
            startActivity<EventDetailActivity>(EventDetailActivity.EVENT_EXTRA to event)
        }, { event ->
            DateUtil.calendarIntent(context, event)
        })
        recyclerView.adapter = adapter
    }
}