package be.technifutur.projet_android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EmptyMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        var isLogged = false

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_main)

        val activityIntent: Intent = if (isLogged) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        startActivity(activityIntent)
        finish()
    }
}