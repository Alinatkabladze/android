package com.example.smartdeal

import android.content.DialogInterface
import android.content.DialogInterface.*
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_details.*


class ProductDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_details)
        setSupportActionBar(toolbar)

        val title=intent.getStringExtra("title")
        val photoUrl=intent.getStringExtra("photo_url")

        product_name.text=title
        Picasso.get().load(photoUrl).into(photo)

        availability.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("$title Offer is Valid")
                .setPositiveButton("OK") { dialog, which -> }.create().show()

                }

        }


}
