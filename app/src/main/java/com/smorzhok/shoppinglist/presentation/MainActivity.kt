package com.smorzhok.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.smorzhok.shoppinglist.R
import com.smorzhok.shoppinglist.data.ShopItem

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var ll:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ll = findViewById(R.id.ll)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this){
            showList(it)
        }
    }
    fun showList(list:List<ShopItem>){
        ll.removeAllViews()
        for (i in list){
            val layoutId =  if (i.enabled == true){
                R.layout.shop_item_enabled
            }
            else {
                R.layout.shop_item_disabled
            }
            val view = LayoutInflater.from(this).inflate(layoutId,ll,false)
            val nameTV = view.findViewById<TextView>(R.id.tv_name)
            val priceTV = view.findViewById<TextView>(R.id.tv_count)
            nameTV.text = i.name
            priceTV.text = i.count.toString()
            view.setOnLongClickListener{
                viewModel.changeEnableState(i)
                Log.d("Doing","Was clicked")
                true
            }
            ll.addView(view)
        }
    }
}