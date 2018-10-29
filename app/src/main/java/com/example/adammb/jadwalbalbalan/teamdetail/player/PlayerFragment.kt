package com.example.adammb.jadwalbalbalan.teamdetail.player


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adammb.jadwalbalbalan.R
import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.model.player.Player
import com.example.adammb.jadwalbalbalan.teamdetail.playerdetail.PlayerDetailActivity
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayerFragment : Fragment(),
        PlayerContract.PlayerView {
    private var teamId: String? = null

    private var players: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            teamId = it.getString(TEAM_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return PlayerFragmentUI()
                .createView(AnkoContext.create(container!!.context, container))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSwipeRefreshLayout(view)
        setupRecyclerView(view)

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)
        presenter.getPlayerList(teamId)
    }

    class PlayerFragmentUI() : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            swipeRefreshLayout {
                id = R.id.player_swiperefreshlayout
                nestedScrollView {
                    verticalLayout {
                        lparams {
                            width = matchParent
                            height = matchParent
                            padding = dip(16)
                        }

                        recyclerView {
                            id = R.id.player_recyclerview
                            lparams(width = matchParent, height = matchParent)
                            layoutManager = LinearLayoutManager(context)
                            isNestedScrollingEnabled = false
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }
            }
        }
    }

    companion object {
        const val TEAM_ID = "team-id"

        @JvmStatic
        fun newInstance(teamId: String?) =
                PlayerFragment().apply {
                    arguments = Bundle().apply {
                        putString(TEAM_ID, teamId)
                    }
                }
    }

    override fun getContextFromFragment(): Context? {
        return this@PlayerFragment.context
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

    override fun showPlayerList(players: List<Player>) {
        hideLoading()
        this@PlayerFragment.players.clear()
        this@PlayerFragment.players.addAll(players)
        adapter.notifyDataSetChanged()
    }

    private fun setupSwipeRefreshLayout(view: View) {
        swipeRefreshLayout = view.find(R.id.player_swiperefreshlayout)
        swipeRefreshLayout.onRefresh {
            presenter.getPlayerList(teamId)
        }
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.find(R.id.player_recyclerview)
        adapter = PlayerAdapter(context, players) { player ->
            startActivity<PlayerDetailActivity>(PlayerDetailActivity.PLAYER_EXTRA to player)
        }
        recyclerView.adapter = adapter
    }
}
