package pl.wat.animal_hospital.android_app.ui.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import pl.wat.animal_hospital.android_app.BASEURL
import pl.wat.animal_hospital.android_app.R
import pl.wat.animal_hospital.android_app.ui.VisitComplete

class ClientDashboard : AppCompatActivity() {

    private lateinit var btnAddVisit: Button

    override fun onResume() {
        super.onResume()
        val mail = intent.getStringExtra("email").toString()
        getVisitList(mail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_dashboard)

        val mail = intent.getStringExtra("email").toString()
        val name: TextView = findViewById(R.id.text_name)
        val surname: TextView = findViewById(R.id.text_surname)

        name.text = intent.getStringExtra("name")
        surname.text = intent.getStringExtra("surname")

        btnAddVisit = findViewById(R.id.button_addVisit)
        btnAddVisit.setOnClickListener {
            val intent = Intent(this, ChooseDoctor::class.java)
            intent.putExtra("email", mail)
            startActivity(intent)
        }

        getVisitList(mail)
    }

    private fun getVisitList(mail: String){
        val url = "$BASEURL/visits?email=$mail"
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            val strRes = response.toString()
            val list = strRes.split("},{")
            val x = list.size
            val time: MutableList<String> = ArrayList()
            val visitID: MutableList<String> = ArrayList()
            val complete: MutableList<Int> = ArrayList()

            for (i in 0 until x){
                val newString = list[i].replace("}","").replace("{","").replace("}","").replace("]","").replace("[","").replace(" ","").replace("\"","")
                val strs = newString.split("," , ":").toTypedArray()

                val y = strs.size
                for (k in 0 until y){
                    if (strs[k] == "id"){
                        visitID.add(strs[k+1])
                    }
                    if (strs[k] == "description"){
                        if (strs[k+1] == ""){
                            complete.add(1)
                        }
                        else{
                            complete.add(0)
                        }
                    }
                    if (strs[k] == "time"){
                        strs[k+1] = strs[k+1].replace("T", ":")
                        strs[k+1] += "\t\t\t\t\t\t\t\t\t\t\t\t"
                        time.add(strs[k+1])
                    }
                }
                if(complete[i] == 0){
                    time[i] += resources.getString(R.string._showDetails)
                }
                else{
                    time[i] += resources.getString(R.string._fillTheVisitPL)
                }
            }

            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, time)

            val visitList = findViewById<ListView>(R.id.visitList)
            visitList.adapter = arrayAdapter

            visitList.setOnItemClickListener { parent, view, position, id ->
                if (complete[position] == 0){
                    val intent = Intent(this, VisitComplete::class.java)
                    intent.putExtra("visitID", visitID[position])
                    startActivity(intent)
                }
                else{
                    val intent = Intent(this, VisitNotComplete::class.java)
                    intent.putExtra("visitID", visitID[position])
                    startActivity(intent)
                }
            }
        },
                { error ->
                    Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                })

        queue.add(stringRequest)
    }
}
