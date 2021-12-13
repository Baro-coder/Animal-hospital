package pl.wat.animal_hospital.android_app.ui.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import pl.wat.animal_hospital.android_app.BASEURL
import pl.wat.animal_hospital.android_app.R

class ChooseClient : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_client)

        val doctorMail = intent.getStringExtra("email")

        getClientsList(doctorMail.toString())
    }

    private fun getClientsList(doctorMail: String) {
        val url = "$BASEURL/users?accountType=KLIENT"
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            val strRes = response.toString()
            val fullname: MutableList<String> = ArrayList()
            val email: MutableList<String> = ArrayList()

            val newString = strRes.replace("}","").replace("{","").replace("[","").replace("]","").replace("\"" , "").replace(" " , "")
            val strs = newString.split("," , ":").toTypedArray()
            val n = strs.size
            var oneFulname = ""

            for (i in 0 until n){
                if (strs[i] == "email"){
                    email.add(strs[i+1])
                }
                if (strs[i] == "name"){
                    oneFulname = strs[i+1]
                }
                if (strs[i] == "surname"){
                    oneFulname = oneFulname + " " + strs[i+1]
                    fullname.add(oneFulname)
                }
            }

            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fullname)

            val clientsList = findViewById<ListView>(R.id.clientList)
            clientsList.adapter = arrayAdapter
            clientsList.setOnItemClickListener { parent, view, position, id ->
                val intent = Intent(this, CreateVisit_Doctor::class.java)
                intent.putExtra("clientMail", email[position])
                intent.putExtra("doctorMail", doctorMail)
                startActivity(intent)
                finish()
            }
        },
                { error ->
                    Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                })

        queue.add(stringRequest)
    }
}

