package pl.wat.animal_hospital.android_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import pl.wat.animal_hospital.android_app.BASEURL
import pl.wat.animal_hospital.android_app.R

class VisitComplete : AppCompatActivity() {

    private lateinit var btnDel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit_complete)

        btnDel = findViewById(R.id.button_deleteVisit)
        btnDel.setOnClickListener(listener)

        fillTheVisit()
    }

    private val listener = View.OnClickListener { view ->
        when (view.id){
            R.id.button_deleteVisit -> {

                val visitID = intent.getStringExtra("visitID")
                val url = "$BASEURL/visits/$visitID"
                val queue = Volley.newRequestQueue(this)

                val stringRequest = StringRequest(Request.Method.DELETE, url, { response ->
                    Toast.makeText(this, resources.getString(R.string._visitDeletedPL), Toast.LENGTH_LONG).show()
                },
                        { error ->
                            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                        })

                queue.add(stringRequest)
                finish()
            }
        }
    }

    private fun fillTheVisit()
    {
        val visitID = intent.getStringExtra("visitID")
        val url = "$BASEURL/visits/$visitID"
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            val strRes = response.toString()

            var clientMail = ""
            var doctorMail = ""
            var animalAge = ""
            var animalBreed = ""
            var description = ""
            var time = ""

            val newString = strRes.replace("}","").replace("{","").replace("[","").replace("]","").replace("\"" , "")
            val list = newString.split("," , ":").toTypedArray()
            val n = list.size
            for (i in 0 until n) {
                if (list[i] == "client"){
                    clientMail = list[i + 1]
                }
                if (list[i] == "doctor"){
                    doctorMail = list[i + 1]
                }
                if (list[i] == "animalAge"){
                    animalAge = list[i + 1]
                }
                if (list[i] == "animalBreed"){
                    animalBreed = list[i + 1]
                }
                if (list[i] == "description"){
                    description = list[i + 1]
                }
                if (list[i] == "time"){
                    time = list[i + 1].replace("T", ":")
                }
            }

            findViewById<TextView>(R.id.text_client).text = clientMail
            findViewById<TextView>(R.id.text_doctor).text = doctorMail
            findViewById<TextView>(R.id.text_breed).text = animalBreed
            findViewById<TextView>(R.id.text_age).text = animalAge
            findViewById<TextView>(R.id.text_description).text = description
            findViewById<TextView>(R.id.text_fulldate).text = time
        },
                { error ->
                    Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                })

        queue.add(stringRequest)
    }
}