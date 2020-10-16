package com.simplx.apps.ysolutiontask.ui.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.simplx.apps.ysolutiontask.R
import com.simplx.apps.ysolutiontask.ui.main.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setView()

    }

    private fun setView() {

        Picasso.get()
            .load(intent.extras?.getString("Img"))
            .into(foodImg)

        NameId.text = intent.extras?.getString("Name")
        headLineId.text = intent.extras?.getString("HeadLine")
        descId.text = intent.extras?.getString("Desc")
        caloriesId.text = intent.extras?.getString("Calories")
        fatsId.text = intent.extras?.getString("Fats")
        proteinsId.text = intent.extras?.getString("proteins")

        if (intent.extras?.getString("Calories") == "") {
            linear.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

}