package com.samsularifin.footballclub.matchdetail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.samsularifin.footballclub.*
import com.samsularifin.footballclub.R.drawable.ic_add_to_favorites
import com.samsularifin.footballclub.R.drawable.ic_added_to_favorites
import com.samsularifin.footballclub.R.id.add_to_favorite
import com.samsularifin.footballclub.R.menu.detail_menu
import com.samsularifin.footballclub.api.ApiRepository
import com.samsularifin.footballclub.db.FavoriteMatch
import com.samsularifin.footballclub.db.database
import com.samsularifin.footballclub.model.Match
import com.samsularifin.footballclub.utils.Constant
import com.samsularifin.footballclub.utils.invisible
import com.samsularifin.footballclub.utils.visible
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import java.text.SimpleDateFormat

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {


    private lateinit var presenter: MatchDetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var matchId: String
    private var match: Match? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        val intent = intent
        matchId = intent.getStringExtra(Constant.INTENT_MATCHID)


        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchDetailPresenter(this, request, gson)

        presenter.getMatchDetail(matchId)

        favoriteState()
    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
    }

    override fun attachData(data: Match?) {
        match = data

        presenter.getHomeTeamBadge(match?.homeTeamId)
        presenter.getAwayTeamBadge(match?.awayTeamId)

        val formatBefore = SimpleDateFormat("yyyy-MM-dd")
        val matchDateBefore = formatBefore.parse(match?.matchDate)

        val formatAfter = SimpleDateFormat("EEE, d MMM yyyy")
        val matchDateAfter = formatAfter.format(matchDateBefore)

        lbl_match_date.text = matchDateAfter
        lbl_home_score.text = match?.homeScore
        lbl_away_score.text = match?.awayScore
        lbl_home_team.text = match?.homeTeam
        lbl_away_team.text = match?.awayTeam

        lbl_home_goal_details.text = match?.homeGoalDetails?.replace("; ",";")?.replace(";","\n")
        lbl_away_goal_details.text = match?.awayGoalDetails?.replace("; ",";")?.replace(";","\n")
        lbl_home_shots.text = match?.homeShots
        lbl_away_shots.text = match?.awayShots
        lbl_home_goalkeeper.text = match?.homeGoalkeeper?.replace("; ",";")?.replace(";","\n")
        lbl_away_goalkeeper.text = match?.awayGoalkeeper?.replace("; ",";")?.replace(";","\n")
        lbl_home_defense.text = match?.homeDefense?.replace("; ",";")?.replace(";","\n")
        lbl_away_defense.text = match?.awayDefense?.replace("; ",";")?.replace(";","\n")
        lbl_home_midfield.text = match?.homeMidfield?.replace("; ",";")?.replace(";","\n")
        lbl_away_midfield.text = match?.awayMidfield?.replace("; ",";")?.replace(";","\n")
        lbl_home_forward.text = match?.homeForward?.replace("; ",";")?.replace(";","\n")
        lbl_away_forward.text = match?.awayForward?.replace("; ",";")?.replace(";","\n")
        lbl_home_substitutes.text = match?.homeSubstitutes?.replace("; ",";")?.replace(";","\n")
        lbl_away_substitutes.text = match?.awaySubstitutes?.replace("; ",";")?.replace(";","\n")
    }

    override fun attachHomeLogo(homeLogoUrl: String?) {
        try {
            Glide.with(this).load(homeLogoUrl).into(img_home_logo)
        }catch (e: Exception){
            Log.d("asd", e.message)
        }

    }

    override fun attachAwayLogo(awayLogoUrl: String?) {
        try {
            Glide.with(this).load(awayLogoUrl).into(img_away_logo)
        }catch (e: Exception){
            Log.d("asd", e.message)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                    .whereArgs("(${FavoriteMatch.MATCH_ID} = {id})",
                            "id" to matchId)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(FavoriteMatch.TABLE_FAVORITE_MATCH,
                        FavoriteMatch.MATCH_ID to match?.matchId,
                        FavoriteMatch.MATCH_DATE to match?.matchDate,
                        FavoriteMatch.HOME_TEAM_ID to match?.homeTeamId,
                        FavoriteMatch.HOME_TEAM_NAME to match?.homeTeam,
                        FavoriteMatch.HOME_SCORE to match?.homeScore,
                        FavoriteMatch.AWAY_TEAM_ID to match?.awayTeamId,
                        FavoriteMatch.AWAY_TEAM_NAME to match?.awayTeam,
                        FavoriteMatch.AWAY_SCORE to match?.awayScore)
            }
            snackbar(lbl_away_defense, "Added to FavoriteTeam").show()
        } catch (e: SQLiteConstraintException){
            snackbar(lbl_away_defense, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(
                        FavoriteMatch.TABLE_FAVORITE_MATCH, "(${FavoriteMatch.MATCH_ID} = {matchId})",
                        "matchId" to matchId)
            }
            snackbar(lbl_away_defense, "Removed from FavoriteTeam").show()
        } catch (e: SQLiteConstraintException){
            snackbar(lbl_away_defense, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}
