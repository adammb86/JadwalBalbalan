package com.example.adammb.jadwalbalbalan.event

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.adammb.jadwalbalbalan.R
import com.example.adammb.jadwalbalbalan.model.event.Event
import org.jetbrains.anko.*

class EventAdapter(private val context: Context?,
        private val events: List<Event>,
        private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    class EventUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            relativeLayout {
                lparams {
                    width = matchParent
                    height = wrapContent
                    horizontalMargin = dip(16)
                    verticalMargin = dip(4)
                }
                padding = dip(16)
                backgroundColor = ContextCompat.getColor(context, R.color.white)

                textView {
                    id = R.id.event_textview_date
                    textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    centerHorizontally()
                    bottomMargin = dip(8)
                }

                textView("VS") {
                    id = R.id.event_textview_vs
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    centerHorizontally()
                    below(R.id.event_textview_date)
                }

                textView {
                    id = R.id.event_textview_teamhomescore
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    below(R.id.event_textview_date)
                    leftOf(R.id.event_textview_vs)
                    horizontalMargin = dip(8)
                }

                textView {
                    id = R.id.event_textview_teamhomename
                    maxLines = 1
                    ellipsize = TextUtils.TruncateAt.END
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    below(R.id.event_textview_date)
                    leftOf(R.id.event_textview_teamhomescore)
                    horizontalMargin = dip(12)
                }

                textView {
                    id = R.id.event_textview_teamawayscore
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    below(R.id.event_textview_date)
                    rightOf(R.id.event_textview_vs)
                    horizontalMargin = dip(8)
                }

                textView {
                    id = R.id.event_textview_teamawayname
                    maxLines = 1
                    ellipsize = TextUtils.TruncateAt.END
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    below(R.id.event_textview_date)
                    rightOf(R.id.event_textview_teamawayscore)
                    horizontalMargin = dip(12)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(EventUI().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewDate: TextView = view.find(R.id.event_textview_date)
        private val textViewTeamHomeScore: TextView = view.find(R.id.event_textview_teamhomescore)
        private val textViewTeamHomeName: TextView = view.find(R.id.event_textview_teamhomename)
        private val textViewTeamAwayScore: TextView = view.find(R.id.event_textview_teamawayscore)
        private val textViewTeamAwayName: TextView = view.find(R.id.event_textview_teamawayname)

        fun bindItem(event: Event, listener: (Event) -> Unit) {
            textViewDate.text = event.date
            textViewTeamHomeScore.text = event.teamHomeScore
            textViewTeamHomeName.text = event.teamHomeName
            textViewTeamAwayScore.text = event.teamAwayScore
            textViewTeamAwayName.text = event.teamAwayName

            itemView.setOnClickListener {
                listener(event)
            }
        }
    }
}