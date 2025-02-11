package com.smorzhok.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.smorzhok.shoppinglist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShopListAdapter
    private lateinit var button: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.floatingActionButton)
        setRV()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }
        button.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddMode(this)
            startActivity(intent)
        }
    }

    private fun setRV() {
        adapter = ShopListAdapter()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.IS_ENABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.IS_DISABLED,
            ShopListAdapter.MAX_POOL_SIZE
        )
        adapter.onShopItemShortClickListener ={
            val intent = ShopItemActivity.newIntentEditMode(this,it.id)
            startActivity(intent)
        }
        setupLongClickListener()
        setupShortClickListener()
        setUpSwipeListener()

    }

    private fun setupLongClickListener() {
        adapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun setupShortClickListener() {
        adapter.onShopItemShortClickListener = {
            Log.d("Doing", "Short click was performed")
        }
    }

    private fun setUpSwipeListener() {
        val simpleCallBack = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
