package com.example.adammb.jadwalbalbalan.team

import android.content.Context
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
import com.example.adammb.jadwalbalbalan.model.team.Team
import org.jetbrains.anko.*

class TeamAdapter(private val context: Context?,
                  private val teams: List<Team>,
                  private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    class TeamUI : AnkoComponent<ViewGroup> {
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
                    id = R.id.team_imageview_logo
                }.lparams(width = dip(36), height = dip(36))

                textView {
                    id = R.id.team_textview_name
                }.lparams {
                    gravity = Gravity.CENTER_VERTICAL
                    horizontalMargin = dip(16)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewTeamLogo: ImageView = view.find(R.id.team_imageview_logo)
        val textViewTeamName: TextView = view.find(R.id.team_textview_name)

        fun bindItem(team: Team, listener: (Team) -> Unit) {
            textViewTeamName.text = team.teamName
            Glide.with(itemView.context)
                    .load(team.teamBadge)
                    .apply(RequestOptions().override(100, 100))
                    .into(imageViewTeamLogo)

            itemView.setOnClickListener {
                listener(team)
            }
        }
    }
}