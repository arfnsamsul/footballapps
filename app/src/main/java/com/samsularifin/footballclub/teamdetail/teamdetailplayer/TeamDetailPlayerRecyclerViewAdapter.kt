package com.samsularifin.footballclub.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.samsularifin.footballclub.R
import com.samsularifin.footballclub.model.Player
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_team_detail_player_item.view.*
import org.jetbrains.anko.sdk15.coroutines.onClick

class TeamDetailPlayerRecyclerViewAdapter(
        private val mValues: List<Player>?,
        private val listener: (Player) -> Unit
        )
    : RecyclerView.Adapter<TeamDetailPlayerRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_team_detail_player_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mValues != null){
            val data = mValues[position]

            holder.lblPlayerName.text = data.name
            holder.lblPLayerPosition.text = data.position
            Picasso.get().load(data.imgCutout).into(holder.imgPlayer)

            holder.mView.onClick { listener(data) }
        }
    }

    override fun getItemCount(): Int = mValues?.size ?: 0

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val lblPlayerName: TextView = mView.lbl_player_name
        val lblPLayerPosition: TextView = mView.lbl_player_position
        val imgPlayer: ImageView = mView.img_player
    }
}
