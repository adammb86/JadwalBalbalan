package com.example.adammb.jadwalbalbalan.teamdetail

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.adammb.jadwalbalbalan.R
import com.example.adammb.jadwalbalbalan.model.team.Team
import com.example.adammb.jadwalbalbalan.shared.TabAdapter
import com.example.adammb.jadwalbalbalan.teamdetail.overview.OverviewFragment
import com.example.adammb.jadwalbalbalan.teamdetail.player.PlayerFragment
import kotlinx.android.synthetic.main.activity_team_detail.*

class TeamDetailActivity : AppCompatActivity(),
        TeamDetailContract.TeamDetailView {

    private lateinit var presenter: TeamDetailPresenter
    private var menuItem: Menu? = null
    private var isFavorited: Boolean = false
    private lateinit var team: Team

    companion object {
        const val TEAM_EXTRA = "team-extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        team = intent.extras.getParcelable(TeamDetailActivity.TEAM_EXTRA)

        setupToolbar()
        setupCollapsingToolbar()
        setupViewPager()

        presenter = TeamDetailPresenter(this)
        isFavorited = presenter.getFavoriteState(team.teamId.toString())
        showFavoriteState(isFavorited)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        showFavoriteState(isFavorited)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                if (isFavorited)
                    presenter.removeFromFavorite(team.teamId.toString())
                else
                    presenter.addToFavorite(team)

                isFavorited = !isFavorited
                showFavoriteState(isFavorited)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun getContext(): Context {
        return this@TeamDetailActivity
    }

    override fun showFavoriteState(isFavorited: Boolean) {
        if (isFavorited)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_white_24dp)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_border_white_24dp)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbarTeamDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_team_detail)
    }

    private fun setupCollapsingToolbar() {
        Glide.with(this)
                .load(team.teamBadge)
                .into(imageViewTeamDetailLogo)

        textViewTeamDetailName.text = team.teamName
        textViewTeamDetailYear.text = team.teamFormedYear
        textViewTeamDetailStadium.text = team.teamStadium
    }

    private fun setupViewPager() {
        val tabAdapter = TabAdapter(supportFragmentManager)
        tabAdapter.addFragment(OverviewFragment.newInstance(team.teamDescription), getString(R.string.title_overview))
        tabAdapter.addFragment(PlayerFragment.newInstance(team.teamId), getString(R.string.title_player))

        viewPagerTeamDetail.adapter = tabAdapter
        tabTeamDetail.setupWithViewPager(viewPagerTeamDetail)
    }
}
