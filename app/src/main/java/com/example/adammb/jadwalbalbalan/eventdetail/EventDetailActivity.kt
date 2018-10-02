package com.example.adammb.jadwalbalbalan.eventdetail

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Gravity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.adammb.jadwalbalbalan.R
import com.example.adammb.jadwalbalbalan.api.ApiRepository
import com.example.adammb.jadwalbalbalan.model.event.Event
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.support.v4.nestedScrollView

class EventDetailActivity : AppCompatActivity(),
        EventDetailContract.EventDetailView {

    private lateinit var toolbar: Toolbar
    private lateinit var imageViewTeamHomeBadge: ImageView
    private lateinit var imageViewTeamAwayBadge: ImageView
    private lateinit var presenter: EventDetailPresenter

    companion object {
        val EVENT_EXTRA = "event-extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val event = intent.extras.getParcelable<Event>(EVENT_EXTRA)
        EventDetailActivityUI(event).setContentView(this)

        toolbar = find(R.id.eventdetail_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        imageViewTeamHomeBadge = find(R.id.eventdetail_imageview_teamhomebadge)
        imageViewTeamAwayBadge = find(R.id.eventdetail_imageview_teamawaybadge)

        val request = ApiRepository()
        val gson = Gson()
        presenter = EventDetailPresenter(this, request, gson)
        presenter.getTeam(event.teamHomeId, EventDetailPresenter.TEAM_HOME)
        presenter.getTeam(event.teamAwayId, EventDetailPresenter.TEAM_AWAY)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    class EventDetailActivityUI(val event: Event) : AnkoComponent<EventDetailActivity> {
        override fun createView(ui: AnkoContext<EventDetailActivity>) = with(ui) {
            coordinatorLayout {
                lparams {
                    width = matchParent
                    height = matchParent
                }
                backgroundColor = ContextCompat.getColor(context, R.color.grey_200)

                themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {
                    id = R.id.eventdetail_appbar

                    toolbar {
                        id = R.id.eventdetail_toolbar
                        setTitleTextColor(Color.WHITE)
                        title = "Match Detail"
                        popupTheme = R.style.AppTheme_PopupOverlay
                    }.lparams(width = matchParent) {
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                    }
                }.lparams(width = matchParent)

                nestedScrollView {
                    relativeLayout {
                        padding = dip(16)

                        textView(event.date) {
                            id = R.id.eventdetail_textview_date
                            textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            centerHorizontally()
                            bottomMargin = dip(16)
                        }

                        relativeLayout {
                            id = R.id.eventdetail_relativelayout_score

                            textView("VS") {
                                id = R.id.eventdetail_textview_vs
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                centerHorizontally()
                                centerVertically()
                            }

                            textView(event.teamHomeScore) {
                                id = R.id.eventdetail_textview_teamhomescore
                                typeface = Typeface.DEFAULT_BOLD
                                textSize = sp(12).toFloat()
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                leftOf(R.id.eventdetail_textview_vs)
                                horizontalMargin = dip(8)
                                centerVertically()
                            }

                            verticalLayout {
                                imageView {
                                    id = R.id.eventdetail_imageview_teamhomebadge
                                }

                                textView(event.teamHomeName) {
                                    id = R.id.eventdetail_textview_teamhomename
                                    maxLines = 1
                                    ellipsize = TextUtils.TruncateAt.END
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    horizontalMargin = dip(12)
                                }
                            }.lparams {
                                leftOf(R.id.eventdetail_textview_teamhomescore)
                                centerVertically()
                            }

                            textView(event.teamAwayScore) {
                                id = R.id.eventdetail_textview_teamawayscore
                                typeface = Typeface.DEFAULT_BOLD
                                textSize = sp(12).toFloat()
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                rightOf(R.id.eventdetail_textview_vs)
                                horizontalMargin = dip(8)
                                centerVertically()
                            }

                            verticalLayout {
                                imageView {
                                    id = R.id.eventdetail_imageview_teamawaybadge
                                }

                                textView(event.teamAwayName) {
                                    id = R.id.eventdetail_textview_teamawayname
                                    maxLines = 1
                                    ellipsize = TextUtils.TruncateAt.END
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    horizontalMargin = dip(12)
                                }
                            }.lparams {
                                rightOf(R.id.eventdetail_textview_teamawayscore)
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            centerHorizontally()
                            below(R.id.eventdetail_textview_date)
                            bottomMargin = dip(16)
                        }

                        view {
                            id = R.id.eventdetail_divider1
                            backgroundColor = ContextCompat.getColor(context, R.color.colorPrimary)
                        }.lparams(width = matchParent, height = dip(1)) {
                            below(R.id.eventdetail_relativelayout_score)
                            bottomMargin = dip(16)
                        }

                        relativeLayout {
                            id = R.id.eventdetail_relativelayout_goals

                            textView("Goals") {
                                id = R.id.eventdetail_textview_goals
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                centerHorizontally()
                            }

                            textView(event.teamHomeGoalDetails) {
                                id = R.id.eventdetail_textview_teamhomegoals
                                gravity = Gravity.LEFT
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                leftOf(R.id.eventdetail_textview_goals)
                                alignParentLeft()
                                centerVertically()
                                horizontalMargin = dip(8)
                            }

                            textView(event.teamAwayGoalDetails) {
                                id = R.id.eventdetail_textview_teamawaygoals
                                gravity = Gravity.RIGHT
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                rightOf(R.id.eventdetail_textview_goals)
                                horizontalMargin = dip(8)
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            centerHorizontally()
                            below(R.id.eventdetail_divider1)
                            bottomMargin = dip(16)
                        }

                        relativeLayout {
                            id = R.id.eventdetail_relativelayout_shots

                            textView("Shots") {
                                id = R.id.eventdetail_textview_shots
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                centerHorizontally()
                            }

                            textView(event.teamHomeShots) {
                                id = R.id.eventdetail_textview_teamhomeshots
                                gravity = Gravity.LEFT
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                leftOf(R.id.eventdetail_textview_shots)
                                alignParentLeft()
                                centerVertically()
                                horizontalMargin = dip(8)
                            }

                            textView(event.teamAwayShots) {
                                id = R.id.eventdetail_textview_teamawayshots
                                gravity = Gravity.RIGHT
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                rightOf(R.id.eventdetail_textview_shots)
                                horizontalMargin = dip(8)
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            centerHorizontally()
                            below(R.id.eventdetail_relativelayout_goals)
                            bottomMargin = dip(16)
                        }

                        view {
                            id = R.id.eventdetail_divider2
                            backgroundColor = ContextCompat.getColor(context, R.color.colorPrimary)
                        }.lparams(width = matchParent, height = dip(1)) {
                            below(R.id.eventdetail_relativelayout_shots)
                            bottomMargin = dip(16)
                        }

                        textView("Lineups") {
                            id = R.id.eventdetail_textview_lineups
                            textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            centerHorizontally()
                            below(R.id.eventdetail_divider2)
                            bottomMargin = dip(16)
                        }

                        relativeLayout {
                            id = R.id.eventdetail_relativelayout_goalkeeperlineups

                            textView("Goalkeeper") {
                                id = R.id.eventdetail_textview_goalkeeperlineups
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                centerHorizontally()
                            }

                            textView(event.teamHomeLineupGoalkeeper) {
                                id = R.id.eventdetail_textview_goalkeeperteamhomelineups
                                gravity = Gravity.LEFT
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                leftOf(R.id.eventdetail_textview_goalkeeperlineups)
                                alignParentLeft()
                                centerVertically()
                                horizontalMargin = dip(8)
                            }

                            textView(event.teamAwayLineupGoalkeeper) {
                                id = R.id.eventdetail_textview_goalkeeperteamawaylineups
                                gravity = Gravity.RIGHT
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                rightOf(R.id.eventdetail_textview_goalkeeperlineups)
                                horizontalMargin = dip(8)
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            centerHorizontally()
                            below(R.id.eventdetail_textview_lineups)
                            bottomMargin = dip(16)
                        }

                        relativeLayout {
                            id = R.id.eventdetail_relativelayout_defenselineups

                            textView("Defense") {
                                id = R.id.eventdetail_textview_defenselineups
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                centerHorizontally()
                            }

                            textView(event.teamHomeLineupDefense) {
                                id = R.id.eventdetail_textview_defenseteamhomelineups
                                gravity = Gravity.LEFT
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                leftOf(R.id.eventdetail_textview_defenselineups)
                                alignParentLeft()
                                centerVertically()
                                horizontalMargin = dip(8)
                            }

                            textView(event.teamAwayLineupDefense) {
                                id = R.id.eventdetail_textview_defenseteamawaylineups
                                gravity = Gravity.RIGHT
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                rightOf(R.id.eventdetail_textview_defenselineups)
                                horizontalMargin = dip(8)
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            centerHorizontally()
                            below(R.id.eventdetail_relativelayout_goalkeeperlineups)
                            bottomMargin = dip(16)
                        }

                        relativeLayout {
                            id = R.id.eventdetail_relativelayout_midfieldlineups

                            textView("Midfield") {
                                id = R.id.eventdetail_textview_midfieldlineups
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                centerHorizontally()
                            }

                            textView(event.teamHomeLineupMidfield) {
                                id = R.id.eventdetail_textview_midfieldteamhomelineups
                                gravity = Gravity.LEFT
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                leftOf(R.id.eventdetail_textview_midfieldlineups)
                                alignParentLeft()
                                centerVertically()
                                horizontalMargin = dip(8)
                            }

                            textView(event.teamAwayLineupMidfield) {
                                id = R.id.eventdetail_textview_midfieldteamawaylineups
                                gravity = Gravity.RIGHT
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                rightOf(R.id.eventdetail_textview_midfieldlineups)
                                horizontalMargin = dip(8)
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            centerHorizontally()
                            below(R.id.eventdetail_relativelayout_defenselineups)
                            bottomMargin = dip(16)
                        }

                        relativeLayout {
                            id = R.id.eventdetail_relativelayout_forwardlineups

                            textView("Forward") {
                                id = R.id.eventdetail_textview_forwardlineups
                                textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                centerHorizontally()
                            }

                            textView(event.teamHomeLineupForward) {
                                id = R.id.eventdetail_textview_forwardteamhomelineups
                                gravity = Gravity.LEFT
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                leftOf(R.id.eventdetail_textview_forwardlineups)
                                alignParentLeft()
                                centerVertically()
                                horizontalMargin = dip(8)
                            }

                            textView(event.teamAwayLineupForward) {
                                id = R.id.eventdetail_textview_forwardteamawaylineups
                                gravity = Gravity.RIGHT
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                rightOf(R.id.eventdetail_textview_forwardlineups)
                                horizontalMargin = dip(8)
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            centerHorizontally()
                            below(R.id.eventdetail_relativelayout_midfieldlineups)
                            bottomMargin = dip(16)
                        }
                    }
                }.lparams(width = matchParent, height = matchParent) {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }
            }
        }
    }

    override fun showLogo(url: String?, type: String?) {
        when (type) {
            EventDetailPresenter.TEAM_HOME -> Glide.with(this@EventDetailActivity)
                    .load(url)
                    .apply(RequestOptions().override(100, 100))
                    .into(imageViewTeamHomeBadge)
            EventDetailPresenter.TEAM_AWAY -> Glide.with(this@EventDetailActivity)
                    .load(url)
                    .apply(RequestOptions().override(100, 100))
                    .into(imageViewTeamAwayBadge)
        }
    }
}
