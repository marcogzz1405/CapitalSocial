package com.example.capitalsocial

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capitalsocial.adapters.MyAdapter
import com.example.capitalsocial.bases.BaseActivity
import com.example.capitalsocial.models.CardViewData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    val stateArray: ArrayList<CardViewData> = ArrayList()

    val state = arrayOf(
        CardViewData("Papa John's", R.drawable.logo_papa_johns),
        CardViewData("Idea Interior", R.drawable.promo_idea),
        CardViewData("Burguer King", R.drawable.promo_burguer_king),
        CardViewData("Farmacia Benavides", R.drawable.promo_benavides),
        CardViewData("El Tizoncito", R.drawable.promo_tizoncito),
        CardViewData("Chilli's", R.drawable.promo_chilis),
        CardViewData("Zona Fitness", R.drawable.promo_zona_fitness),
        CardViewData("Cinepolis", R.drawable.promo_cinepolis),
        CardViewData("Italiannis", R.drawable.promo_italiannis),
        CardViewData("Wingstop", R.drawable.promo_wingstop)
    )

    companion object {
        fun getInstance(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //addState()
        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerview.adapter = MyAdapter(state, applicationContext)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_map, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_map -> {
                launchMapActivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
