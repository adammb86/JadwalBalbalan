package com.example.adammb.jadwalbalbalan.shared

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.adammb.jadwalbalbalan.model.league.League
import org.jetbrains.anko.find

class LeagueAdapter(private val context: Context?,
                    private val leagues: List<League>)
    : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item,
                    parent,
                    false)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.textViewContent.text = leagues[position].leagueName
        return view
    }

    override fun getItem(position: Int): Any {
        return leagues[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return leagues.size
    }

    class ViewHolder(view: View) {
        val textViewContent: TextView = view.find(android.R.id.text1)
    }
}