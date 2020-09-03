package be.technifutur.projet_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import be.technifutur.projet_android.firebase_api.UserHelper
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    companion object {
        private const val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (isCurrentUserLogged()) {
            Log.d("bite", getCurrentUser().toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        signInButton.setOnClickListener {
            this.startSignInActivity()
        }

    }

    private fun startSignInActivity() {

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(listOf(AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()))
                .setLogo(R.drawable.teammateslogo)
                .setTheme(R.style.AppTheme_NoActionBar)
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

    // 1 - Http request that create user in firestore
    private fun createUserInFirestore() {
        getCurrentUser()?.let { user ->
            val urlPicture = user.photoUrl.toString()
            val username = user.displayName
            val uid = user.uid
            UserHelper.createUser(uid, username!!, urlPicture)
                .addOnFailureListener(onFailureListener())
        }
    }

    private fun handleResponseAfterSignIn(requestCode: Int, resultCode: Int, data: Intent){

        val response: IdpResponse = IdpResponse.fromResultIntent(data)!!

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // 2 - CREATE USER IN FIRESTORE
                this.createUserInFirestore();
                showSnackBar(getString(R.string.connexion_success))
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                if (response == null) {
                    showSnackBar(getString(R.string.error_auth_canceled))
                } else if (response.error?.errorCode == ErrorCodes.NO_NETWORK) {
                    showSnackBar(getString(R.string.error_no_internet))
                } else if (response.error?.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackBar(getString(R.string.error_unknown_error))
                }
            }
        }
    }
}