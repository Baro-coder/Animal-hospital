package pl.wat.animal_hospital.android_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import pl.wat.animal_hospital.android_app.ui.Login
import pl.wat.animal_hospital.android_app.ui.Register

const val BASEURL = "http://192.168.56.1:8081"

class MainActivity : AppCompatActivity() {

    private lateinit var btn1: Button
    private lateinit var btn2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn1 = findViewById(R.id.button_signIn)
        btn2 = findViewById(R.id.button_register)
        btn1.setOnClickListener(listener)
        btn2.setOnClickListener(listener)
    }

    private val listener = View.OnClickListener { view ->
        when (view.id){
            R.id.button_signIn -> {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
            R.id.button_register -> {
                val intent = Intent(this, Register::class.java)
                startActivity(intent)
            }
        }
    }
}