package be.technifutur.projet_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener {
            this.startSignInActivity()
        }
    }

    private fun startSignInActivity() {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(listOf(AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()))
                .setIsSmartLockEnabled(false, true)
                .build(),
            RC_SIGN_IN);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let { safeData ->
            this.handleResponseAfterSignIn(requestCode, resultCode, safeData)
        }
    }
    private fun showSnackBar(message: String){
        Snackbar.make(loginConstraintLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun handleResponseAfterSignIn(requestCode: Int, resultCode: Int, data: Intent){

        val response: IdpResponse = IdpResponse.fromResultIntent(data)!!

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                showSnackBar("Connexion success !")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                if (response == null) {
                    showSnackBar("Authentification canceled")
                } else if (response.error?.errorCode == ErrorCodes.NO_NETWORK) {
                    showSnackBar("No Internet connexion")
                } else if (response.error?.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackBar("An unknown error has occurred")
                }
            }
        }
    }
}