package com.example.adammb.jadwalbalbalan.event

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.adammb.jadwalbalbalan.R
import com.example.adammb.jadwalbalbalan.model.event.Event
import com.example.adammb.jadwalbalbalan.util.DateUtil
import org.jetbrains.anko.*

class EventAdapter(private val context: Context?,
                   private val events: List<Event>,
                   private val isRemindable: Boolean,
                   private val listener: (Event) -> Unit,
                   private val listenerAddToCalendar: (Event) -> Unit)
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
                    bottomMargin = dip(4)
                }

                textView {
                    id = R.id.event_textview_time
                    textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    centerHorizontally()
                    below(R.id.event_textview_date)
                    bottomMargin = dip(8)
                }

                imageView {
                    id = R.id.event_imageview_addtocalendar
                    Glide.with(context)
                            .load(R.drawable.ic_notifications_none_black_24dp)
                            .into(this)
                    val typedValue = TypedValue()
                    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
                    backgroundResource = typedValue.resourceId
                }.lparams {
                    width = dip(24)
                    height = dip(24)
                    alignParentRight()
                }

                textView("VS") {
                    id = R.id.event_textview_vs
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    centerHorizontally()
                    below(R.id.event_textview_time)
                }

                textView {
                    id = R.id.event_textview_teamhomescore
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    below(R.id.event_textview_time)
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
                    below(R.id.event_textview_time)
                    leftOf(R.id.event_textview_teamhomescore)
                    horizontalMargin = dip(12)
                }

                textView {
                    id = R.id.event_textview_teamawayscore
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    below(R.id.event_textview_time)
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
                    below(R.id.event_textview_time)
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
        val event = events[position]
        holder.bindItem(event, listener, listenerAddToCalendar)

        if (!isRemindable) {
            holder.imageViewAddToCalendar.visibility = View.GONE
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewDate: TextView = view.find(R.id.event_textview_date)
        private val textViewTime: TextView = view.find(R.id.event_textview_time)
        val imageViewAddToCalendar: ImageView = view.find(R.id.event_imageview_addtocalendar)
        private val textViewTeamHomeScore: TextView = view.find(R.id.event_textview_teamhomescore)
        private val textViewTeamHomeName: TextView = view.find(R.id.event_textview_teamhomename)
        private val textViewTeamAwayScore: TextView = view.find(R.id.event_textview_teamawayscore)
        private val textViewTeamAwayName: TextView = view.find(R.id.event_textview_teamawayname)

        fun bindItem(event: Event, listener: (Event) -> Unit, listenerAddToCalendar: (Event) -> Unit) {
            if (event.time == null) {
                event.time = "00:00:00"
                textViewTime.visibility = View.INVISIBLE
            }

            val dateTime = "${event.date} ${event.time}"

            textViewDate.text = DateUtil.formatDate(dateTime)
            textViewTime.text = DateUtil.formatTime(dateTime)
            textViewTeamHomeScore.text = event.teamHomeScore
            textViewTeamHomeName.text = event.teamHomeName
            textViewTeamAwayScore.text = event.teamAwayScore
            textViewTeamAwayName.text = event.teamAwayName

            imageViewAddToCalendar.setOnClickListener {
                listenerAddToCalendar(event)
            }

            itemView.setOnClickListener {
                listener(event)
            }
        }
    }
}