package com.example.adammb.jadwalbalbalan.teamdetail.overview


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.adammb.jadwalbalbalan.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView

class OverviewFragment : Fragment() {
    private var overview: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            overview = it.getString(OVERVIEW)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return OverviewFragmentUI()
                .createView(AnkoContext.create(container!!.context, container))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textViewContent: TextView = view.find(R.id.overview_textview_content)
        textViewContent.text = arguments?.getString(OVERVIEW)
    }

    class OverviewFragmentUI() : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            nestedScrollView {
                verticalLayout {
                    lparams {
                        width = matchParent
                        height = matchParent
                        padding = dip(16)
                    }

                    textView {
                        id = R.id.overview_textview_content
                    }
                }
            }
        }
    }

    companion object {
        const val OVERVIEW = "overview"

        @JvmStatic
        fun newInstance(overview: String?) =
                OverviewFragment().apply {
                    arguments = Bundle().apply {
                        putString(OVERVIEW, overview)
                    }
                }
    }
}
