package com.example.smartdeal

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.widget.Toast
import com.example.smartdeal.model.Product
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.support.v7.widget.GridLayoutManager
import android.util.Log.d
import android.view.MenuItem
import com.example.smartdeal.model.FragmentWoman
import kotlinx.android.synthetic.main.menu.*

class MainActivity : AppCompatActivity() {

    lateinit var providers:List<AuthUI.IdpConfig>
    val My_Request_Code:Int=7117


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, MainFragment())
            .commit()

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.actionHome -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, MainFragment())
                        .commit()
                }

                R.id.action_woman-> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, FragmentWoman())
                        .commit()

                }

                R.id.action_man->d("alina","man was pressed")


                R.id.action_kids-> d("alina","kids was pressed")
            }
            it.isChecked=true
            drawerLayout.closeDrawers()
           true

        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)

        }





        providers= Arrays.asList<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()
        )
        shopSignInOptions()
        btn_sign_out.setOnClickListener{
            AuthUI.getInstance().signOut(this@MainActivity)
                .addOnCompleteListener{
                    btn_sign_out.isEnabled=false
                    shopSignInOptions()
                }
                .addOnFailureListener {
                    e-> Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_SHORT).show()
                }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==My_Request_Code){
            val response=IdpResponse.fromResultIntent(data)
            if(resultCode== Activity.RESULT_OK){
                val user= FirebaseAuth.getInstance().currentUser
                Toast.makeText(this,""+user!!.email,Toast.LENGTH_SHORT).show()
                btn_sign_out.isEnabled=true
            }
            else {
                Toast.makeText(this,""+response!!.error!!.message,Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun shopSignInOptions() {

        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.MyTheme)
            .build(),My_Request_Code)

    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        drawerLayout.openDrawer(GravityCompat.START)
        return true
    }
}
