package pl.wat.animal_hospital.android_app.ui.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import pl.wat.animal_hospital.android_app.BASEURL
import pl.wat.animal_hospital.android_app.R
import pl.wat.animal_hospital.android_app.ui.doctor.DoctorDashboard

class CreateVisit_Client : AppCompatActivity() {

    lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_visit__client)

        btnAdd = findViewById(R.id.button_add)
        btnAdd.setOnClickListener(listener)

        val cMail = intent.getStringExtra("clientMail")
        val dMail = intent.getStringExtra("doctorMail")
        Toast.makeText(this, "$cMail\n$dMail", Toast.LENGTH_LONG).show()
    }

    private val listener = View.OnClickListener { view ->
        when (view.id){
            R.id.button_add -> {
                val dateBox: EditText = findViewById(R.id.edit_date)
                val hoursBox: EditText = findViewById(R.id.edit_hours)
                val minutesBox: EditText = findViewById(R.id.edit_minutes)
                val breedBox: EditText = findViewById(R.id.edit_breed)
                val ageBox: EditText = findViewById(R.id.edit_age)
                val descBox: EditText = findViewById(R.id.edit_description)

                if(dateBox.text.isEmpty() || hoursBox.text.isEmpty() || minutesBox.text.isEmpty() || breedBox.text.isEmpty() || ageBox.text.isEmpty() || descBox.text.isEmpty())
                {
                    dateBox.setText("")
                    hoursBox.setText("")
                    minutesBox.setText("")
                    breedBox.setText("")
                    ageBox.setText("")
                    descBox.setText("")
                    Toast.makeText(this, resources.getString(R.string._fillTheGapsPl), Toast.LENGTH_SHORT).show()
                }
                else
                {
                    val clientMail = intent.getStringExtra("clientMail")
                    val doctorMail = intent.getStringExtra("doctorMail")
                    var time = dateBox.text.toString()
                    time += "T${minutesBox.text}:${hoursBox.text}:00.000Z"

                    val url = "$BASEURL/visits"
                    val queue = Volley.newRequestQueue(this)
                    val params = HashMap<String, String>()
                    params["client"] = clientMail.toString()
                    params["doctor"] = doctorMail.toString()
                    params["animalAge"] = ageBox.text.toString()
                    params["animalBreed"] = breedBox.text.toString()
                    params["time"] = time
                    params["description"] = descBox.text.toString()
                    val jsonObject = JSONObject(params as Map<*, *>)
                    val request = JsonObjectRequest(Request.Method.POST,url,jsonObject,
                            { response ->
                                Toast.makeText(this, resources.getString(R.string._clientVisitAddedPL), Toast.LENGTH_SHORT).show()
                            }, { error ->
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                    })
                    queue.add(request)
                }
            }
        }
    }
}