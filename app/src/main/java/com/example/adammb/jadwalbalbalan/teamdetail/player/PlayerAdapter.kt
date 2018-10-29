package com.example.adammb.jadwalbalbalan.teamdetail.player

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.adammb.jadwalbalbalan.R
import com.example.adammb.jadwalbalbalan.model.player.Player
import org.jetbrains.anko.*

class PlayerAdapter(private val context: Context?,
                    private val players: List<Player>,
                    private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    class PlayerUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams {
                    width = matchParent
                    height = wrapContent
                }
                horizontalPadding = dip(16)
                verticalPadding = dip(10)
                val typedValue = TypedValue()
                context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
                backgroundResource = typedValue.resourceId

                imageView {
                    id = R.id.player_imageview_avatar
                }.lparams(width = dip(36), height = dip(36))

                verticalLayout {
                    textView {
                        id = R.id.player_textview_name
                        textColor = ContextCompat.getColor(context, R.color.grey_900)
                    }.lparams {
                        gravity = Gravity.CENTER_VERTICAL
                        horizontalMargin = dip(16)
                    }

                    textView {
                        id = R.id.player_textview_position
                    }.lparams {
                        gravity = Gravity.CENTER_VERTICAL
                        horizontalMargin = dip(16)
                    }
                }.lparams {
                    gravity = Gravity.CENTER
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(PlayerUI().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewAvatar: ImageView = view.find(R.id.player_imageview_avatar)
        val textViewName: TextView = view.find(R.id.player_textview_name)
        val textViewPosition: TextView = view.find(R.id.player_textview_position)

        fun bindItem(player: Player, listener: (Player) -> Unit) {
            Glide.with(itemView.context)
                    .load(player.playerAvatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(imageViewAvatar)
            textViewName.text = player.playerName
            textViewPosition.text = player.playerPosition

            itemView.setOnClickListener {
                listener(player)
            }
        }
    }
}