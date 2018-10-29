package com.example.adammb.jadwalbalbalan.teamdetail.playerdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.adammb.jadwalbalbalan.R
import com.example.adammb.jadwalbalbalan.model.player.Player
import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var player: Player

    companion object {
        const val PLAYER_EXTRA = "player-extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        player = intent.extras.getParcelable(PlayerDetailActivity.PLAYER_EXTRA)

        setupToolbar()
        showPlayer(player)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbarPlayerDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_player_detail)
    }

    private fun showPlayer(player: Player) {
        Glide.with(this)
                .load(player.playerAvatar)
                .into(imageViewPlayerDetailAvatar)
        textViewPlayerDetailName.text = player.playerName
        textViewPlayerDetailPosition.text = player.playerPosition
        textViewPlayerDetailHeight.text = player.playerHeight
        textViewPlayerDetailWeight.text = player.playerWeight
        textViewPlayerDetailDescription.text = player.playerDescription
    }
}
