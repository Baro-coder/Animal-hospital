package pl.wat.animal_hospital.android_app.ui.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import pl.wat.animal_hospital.android_app.BASEURL
import pl.wat.animal_hospital.android_app.R

class VisitNotComplete : AppCompatActivity() {

    private lateinit var btnCnf: Button
    private lateinit var clientMail: String
    private lateinit var doctorMail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit_not_complete)

        fillTheVisit()

        btnCnf = findViewById(R.id.confirm_button)
        btnCnf.setOnClickListener(listener)
    }

    private val listener = View.OnClickListener { view ->
        when (view.id){
            R.id.confirm_button -> {

                val visitID = intent.getStringExtra("visitID")
                val url = "$BASEURL/visits/$visitID"
                val queue = Volley.newRequestQueue(this)

                val stringRequest = StringRequest(Request.Method.DELETE, url, { response ->

                },
                    { error ->
                        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                    })

                queue.add(stringRequest)

                val timeBox: TextView = findViewById(R.id.text_fulldate)
                val breedBox: EditText = findViewById(R.id.edit_breed)
                val ageBox: EditText = findViewById(R.id.edit_age)
                val descBox: EditText = findViewById(R.id.edit_description)

                if(breedBox.text.isEmpty() || ageBox.text.isEmpty() || descBox.text.isEmpty())
                {
                    breedBox.setText("")
                    ageBox.setText("")
                    descBox.setText("")
                    Toast.makeText(this, resources.getString(R.string._fillTheGapsPl), Toast.LENGTH_SHORT).show()
                }
                else
                {
                    var time = timeBox.text.toString()

                    val url2 = "$BASEURL/visits"
                    val params = HashMap<String, String>()
                    params["client"] = clientMail
                    params["doctor"] = doctorMail
                    params["animalAge"] = ageBox.text.toString()
                    params["animalBreed"] = breedBox.text.toString()
                    params["time"] = time
                    params["description"] = descBox.text.toString()
                    val jsonObject = JSONObject(params as Map<*, *>)
                    val request = JsonObjectRequest(Request.Method.POST, url2, jsonObject,
                        { response ->
                            Toast.makeText(this, resources.getString(R.string._visitConfirmedPL), Toast.LENGTH_SHORT).show()
                        }, { error ->
                            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                        })
                    queue.add(request)
                }
            }
        }
    }

    private fun fillTheVisit()
    {
        val visitID = intent.getStringExtra("visitID")
        val url = "$BASEURL/visits/$visitID"
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, url, { response ->
            val strRes = response.toString()
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
                if (list[i] == "time"){
                    time = list[i + 1].replace("T", ":")
                }
            }
            findViewById<TextView>(R.id.text_fulldate).text = time
        },
            { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            })

        queue.add(stringRequest)
    }
}