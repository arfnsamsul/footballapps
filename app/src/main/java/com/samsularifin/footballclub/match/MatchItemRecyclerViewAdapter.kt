package com.samsularifin.footballclub.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.samsularifin.footballclub.R

import com.samsularifin.footballclub.model.Match
import com.samsularifin.footballclub.utils.toSimpleString

import kotlinx.android.synthetic.main.fragment_match_item.view.*
import org.jetbrains.anko.sdk15.coroutines.onClick
import java.text.SimpleDateFormat

class MatchItemRecyclerViewAdapter(
        private val mValues: List<Match>?,
        private val listener: (Match) -> Unit
        //private val mListener: OnListFragmentInteractionListener?
        )
    : RecyclerView.Adapter<MatchItemRecyclerViewAdapter.ViewHolder>() {

    //private val mOnClickListener: View.OnClickListener

    /*init {
        mOnClickListener = View.OnClickListener { v ->
            val match = v.tag as Match
            mListener?.onListFragmentInteraction(match)
        }
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_match_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mValues != null){
            val match = mValues[position]

            val formatBefore = SimpleDateFormat("yyyy-MM-dd")
            val matchDateBefore = formatBefore.parse(match.matchDate)

            val matchDateAfter = toSimpleString(matchDateBefore)

            holder.mDateView.text = matchDateAfter
            holder.mHomeTeamName.text = match.homeTeam
            holder.mAwayTeamName.text = match.awayTeam
            holder.mHomeScore.text = match.homeScore
            holder.mAwayScore.text = match.awayScore

            holder.mView.onClick { listener(match) }

            /*with(holder.mView) {
                tag = match
                setOnClickListener(mOnClickListener)
                //setOnClickListener(listener)
            }*/
        }
    }

    override fun getItemCount(): Int = mValues?.size ?: 0

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mDateView: TextView = mView.lbl_date
        val mHomeTeamName: TextView = mView.lbl_home_team
        val mAwayTeamName: TextView = mView.lbl_away_team
        val mHomeScore: TextView = mView.lbl_home_score
        val mAwayScore: TextView = mView.lbl_away_score

//        override fun toString(): String {
//            return super.toString() + " '" + mContentView.text + "'"
//        }
    }
}
