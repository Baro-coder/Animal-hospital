package pl.wat.animal_hospital.android_app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import pl.wat.animal_hospital.android_app.BASEURL
import pl.wat.animal_hospital.android_app.R
import pl.wat.animal_hospital.android_app.ui.client.ClientDashboard
import pl.wat.animal_hospital.android_app.ui.doctor.DoctorDashboard

class Login : AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        button = findViewById(R.id.button_toSignIn)
        button.setOnClickListener(listener)
    }

    private val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.button_toSignIn -> {

                val mailBox: EditText = findViewById(R.id.edit_mail)
                val passwdBox: EditText = findViewById(R.id.edit_passwd)

                if (mailBox.text.isEmpty() || passwdBox.text.isEmpty())
                {
                    mailBox.setText("")
                    passwdBox.setText("")
                    Toast.makeText(this, resources.getString(R.string._fillTheGapsPl), Toast.LENGTH_SHORT).show()
                }
                else {
                    val mail = mailBox.text.toString()
                    val passwd = passwdBox.text.toString()

                    mail.replace("@", "%40")

                    val url = "$BASEURL/users/login?email=$mail&password=$passwd"
                    val queue = Volley.newRequestQueue(this)

                    val stringRequest = StringRequest(Request.Method.GET, url, { response ->
                        val strRes = response.toString()
                        Toast.makeText(this, resources.getString(R.string._loginedPL), Toast.LENGTH_SHORT).show()

                        var email = ""
                        var name = ""
                        var surname = ""
                        var accountType = ""

                        val newString = strRes.replace("}","").replace("{","").replace("[","").replace("]","").replace("\"" , "")
                        val list = newString.split("," , ":").toTypedArray()
                        val n = list.size
                        for (i in 0 until n) {
                            if (list[i] == "email"){
                                email = list[i+1]
                            }
                            if (list[i] == "name"){
                                name = list[i+1]
                            }
                            if (list[i] == "surname") {
                                surname = list[i + 1]
                            }
                            if (list[i] == "accountType") {
                                accountType = list[i + 1]
                            }
                        }
                        if(accountType == "KLIENT")
                        {
                            val intent = Intent(this, ClientDashboard::class.java)
                            intent.putExtra("name", name)
                            intent.putExtra("surname", surname)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()
                        }
                        else
                        {
                            val intent = Intent(this, DoctorDashboard::class.java)
                            intent.putExtra("name", name)
                            intent.putExtra("surname", surname)
                            intent.putExtra("email", email)
                            startActivity(intent)
                            finish()
                        }
                    },
                            { error ->
                                mailBox.setText("")
                                passwdBox.setText("")
                                Toast.makeText(this, resources.getString(R.string._wrongCredentialsPL), Toast.LENGTH_LONG).show()
                            })

                    queue.add(stringRequest)
                }
            }
        }
    }
}
