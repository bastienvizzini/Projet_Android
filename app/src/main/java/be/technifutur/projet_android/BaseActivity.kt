package be.technifutur.projet_android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import be.technifutur.projet_android.R.string
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

abstract class BaseActivity: AppCompatActivity() {

    protected open fun onFailureListener(): OnFailureListener {
        return OnFailureListener {
            Toast.makeText(
                applicationContext,
                getString(string.error_unknown_error),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    protected open fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    protected open fun isCurrentUserLogged(): Boolean {
        return getCurrentUser() != null
    }
}