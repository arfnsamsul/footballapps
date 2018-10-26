package com.samsularifin.footballclub.playerdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.samsularifin.footballclub.R
import com.samsularifin.footballclub.model.Player
import com.samsularifin.footballclub.utils.Constant
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var playerData:Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        val gson = Gson()

        val intent = intent

        val strIntent = intent.getStringExtra(Constant.INTENT_PLAYER_DATA)
        playerData = gson.fromJson(strIntent, Player::class.java)

        supportActionBar?.title = playerData.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Picasso.get().load(playerData.imgFanart).into(img_photo)
        lbl_weight.text = playerData.weight
        lbl_height.text = playerData.height
        lbl_position.text = playerData.position
        lbl_desc.text = playerData.description
    }

}
