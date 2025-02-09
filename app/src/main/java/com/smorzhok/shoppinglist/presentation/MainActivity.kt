package com.smorzhok.shoppinglist.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.smorzhok.shoppinglist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setRV()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this){
            adapter.shopList = it
        }
    }

    private fun setRV(){
        adapter = ShopListAdapter()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.recycledViewPool
            .setMaxRecycledViews(ShopListAdapter.IS_ENABLED, ShopListAdapter.MAX_POOL_SIZE)
        recyclerView.recycledViewPool
            .setMaxRecycledViews(ShopListAdapter.IS_DISABLED, ShopListAdapter.MAX_POOL_SIZE)
    }
}