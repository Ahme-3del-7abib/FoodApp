package com.simplx.apps.ysolutiontask.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.simplx.apps.ysolutiontask.R
import com.simplx.apps.ysolutiontask.pojo.Food
import com.simplx.apps.ysolutiontask.ui.details.DetailsActivity
import com.simplx.apps.ysolutiontask.utils.FoodUtils
import com.simplx.apps.ysolutiontask.utils.showToastMessage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), FoodAdapter.OnFoodClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: FoodViewModel
    private lateinit var adapter: FoodAdapter

    private lateinit var preferences: SharedPreferences

    private var foodList: ArrayList<Food> = ArrayList()
    private var newFoodList: ArrayList<Food> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setView()

        if (FoodUtils.isNetworkConnected(this)) {
            loadData()
        } else {
            loadEmptyView()
        }

        searchViewId.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handleSearchView(newText)
                return true
            }
        })

        settingId.setOnClickListener(View.OnClickListener {
            sortDialog()
        })
    }

    private fun handleSearchView(newText: String?) {

        searchViewId.queryHint = "Search"

        if (newText!!.isNotEmpty()) {
            newFoodList.clear()
            val search = newText.toLowerCase(Locale.getDefault())
            foodList.forEach {

                if (it.name.toLowerCase(Locale.getDefault()).contains(search)) {
                    newFoodList.add(it)
                    adapter.setList(newFoodList)
                }

                recyclerId.adapter!!.notifyDataSetChanged()

            }
        } else {
            newFoodList.clear()
            newFoodList.addAll(foodList)
            recyclerId.adapter!!.notifyDataSetChanged()
        }
    }

    private fun loadEmptyView() {

        searchViewId.isEnabled = false
        progressBar.visibility = View.GONE
        recyclerId.visibility = View.GONE
        noInternetId.visibility = View.VISIBLE
    }

    private fun loadData() {

        recyclerId.visibility = View.VISIBLE
        noInternetId.visibility = View.GONE

        viewModel.getFoods().observe(this, Observer {
            progressBar.visibility = View.GONE
            foodList = it
            automaticSorting(adapter, foodList)
        })
    }

    private fun setView() {

        swipeRefresh.setOnRefreshListener(this)

        viewModel = ViewModelProviders.of(this).get(FoodViewModel::class.java)
        adapter = FoodAdapter(this)

        recyclerId.layoutManager = LinearLayoutManager(this)
        recyclerId.adapter = adapter
    }

    override fun onFoodClick(position: Int) {
        sendFoodData(position)
    }

    private fun sendFoodData(position: Int) {

        var intent: Intent = Intent(this, DetailsActivity::class.java)

        intent.putExtra("Img", foodList[position].image)
        intent.putExtra("Name", foodList[position].name)
        intent.putExtra("HeadLine", foodList[position].headline)
        intent.putExtra("Desc", foodList[position].description)
        intent.putExtra("Calories", foodList[position].calories)
        intent.putExtra("Fats", foodList[position].fats)
        intent.putExtra("proteins", foodList[position].proteins)

        startActivity(intent)
    }

    override fun onRefresh() {
        swipeRefresh.isRefreshing = false
        if (FoodUtils.isNetworkConnected(this)) {
            loadData()
        } else {
            loadEmptyView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJob()
    }

    private fun sortDialog() {

        val options = arrayOf("Fats", "Calories")

        val builder = AlertDialog
            .Builder(this)
            .setTitle("Sort your list by :  ")
            .setItems(options) { _, which ->

                if (which == 0) {
                    val editor: SharedPreferences.Editor = preferences.edit()
                    editor.putString("Sort", "Fats")
                    editor.apply()
                    sortBasedOnFats(adapter, foodList)
                    this.showToastMessage("List Sorted By Fats")
                }

                if (which == 1) {
                    val editor: SharedPreferences.Editor = preferences.edit()
                    editor.putString("Sort", "Calories")
                    editor.apply()
                    sortBasedOnCalories(adapter, foodList)
                    this.showToastMessage("List Sorted By Calories")
                }
            }

        builder.create().show()


    }

    private fun sortBasedOnFats(adapter: FoodAdapter, list: ArrayList<Food>) {
        list.sortWith(compareBy { it.fats })
        adapter.setList(list)
        adapter.notifyDataSetChanged()
    }

    private fun sortBasedOnCalories(adapter: FoodAdapter, list: ArrayList<Food>) {
        list.sortWith(compareBy { it.calories })
        adapter.setList(list)
        adapter.notifyDataSetChanged()
    }

    private fun noSorting(adapter: FoodAdapter, list: ArrayList<Food>) {
        adapter.setList(list)
        adapter.notifyDataSetChanged()
    }

    private fun automaticSorting(adapter: FoodAdapter, foodList: ArrayList<Food>) {

        preferences = getSharedPreferences("My_pref", Context.MODE_PRIVATE)
        val sortSetting = preferences.getString("Sort", "No_Sorting")

        when (sortSetting) {
            "Fats" -> {
                sortBasedOnFats(adapter, foodList)
            }
            "Calories" -> {
                sortBasedOnCalories(adapter, foodList)
            }
            "No_Sorting" -> {
                noSorting(adapter, foodList)
            }
        }
    }
}