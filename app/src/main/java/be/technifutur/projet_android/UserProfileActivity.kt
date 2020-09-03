package be.technifutur.projet_android

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.projet_android.adapters.user.UserGameListAdapter
import be.technifutur.projet_android.firebase_api.UserHelper
import be.technifutur.projet_android.models.MyUser
import be.technifutur.projet_android.models.User
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.File


class UserProfileActivity : BaseActivity() {

    companion object {
        private const val FILE_NAME = "photo.jpg"
        private const val SIGN_OUT_TASK = 10
        private const val DELETE_USER_TASK = 20
        private const val UPDATE_USERNAME = 30
    }

    private lateinit var photoFile: File
    private var isCurrentUser: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        

        // quand ce sera un fragment, faire comme dans le projet Pokédex fragment avec bundle et args
        this.updateUIWhenCreating()
        // Custom Action Bar
        /*supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_actionbar_user)
        actionBar?.setDisplayHomeAsUpEnabled(true)*/
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayShowTitleEnabled(false) // No title in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // back on actionbar


    }



    private fun getPhotoFile(fileName: String): File {
        // Use 'getExternalFilesDir on Context to access package-specific directories
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 123 && resultCode == Activity.RESULT_OK){
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            Glide.with(this).load(takenImage).centerCrop().into(user_profile_picture)
            //user_profile_picture.setImageBitmap(takenImage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setUser(user: MyUser) {
        //user_profile_picture.setImageResource(user.mProfilePicture)
        Glide.with(this).load(user.profilePicture).centerCrop().into(user_profile_picture)
        userUsernameTextView.text = user.userName
    }

    private fun setupMainUserViews() {
        addFriendButton.setImageDrawable(getDrawable(R.drawable.ic_edit))
        sendMessageButton.text = getString(R.string.add_games_button_title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.changeUsernameAction -> {
                this.updateUsernameInFirebase()
                true
            }
            R.id.signOutAction -> {
                this.signOut()
                true
            }
            R.id.deleteAccountAction -> {
                this.deleteUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    // Ajouter permission pour écrire données?
    private fun isPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if ((checkSelfPermission(Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)
            ) {
                Log.v("bite", "Permission is granted")
                true
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.CAMERA
                    ), 1
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("bite", "Permission is granted")
            true
        }
    }

    private fun changeProfilePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFile(FILE_NAME)
        // Doesn't work for API >= 24 (2016)
        //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)
        val fileProvider =
            FileProvider.getUriForFile(this, "be.technifutur.fileprovider", photoFile)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

        if (takePictureIntent.resolveActivity(this.packageManager) != null) {
            startActivityForResult(takePictureIntent, 123)
        } else {
            Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            this.changeProfilePicture()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isCurrentUser) {
            menuInflater.inflate(R.menu.user_profile_menu, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun updateUsernameInFirebase(){
        usernameProgressBar.visibility = View.VISIBLE
        userUsernameTextView.text = ""
        this.createAlterDialogWithEditText()
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK, null))
    }

    private fun deleteUser() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.popup_message_confirmation_delete_acount))
            .setPositiveButton(
                getString(R.string.yes)
            ) { _, _ -> deleteUserFromFirebase() }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    private fun deleteUserFromFirebase() {
        // Supprimé de Firestore mais pas de Firebase si non connecté

            // Delete from Firestore
            getCurrentUser()?.let { user ->
                UserHelper.deleteUser(user.uid).addOnFailureListener(this.onFailureListener())
            }
            // Delete from Firebase
            AuthUI.getInstance()
                .delete(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK, null))
                .addOnFailureListener(this.onFailureListener())

            this.signOut()
    }

    private fun updateUIAfterRESTRequestsCompleted(origin: Int, username: String?): OnSuccessListener<Void> {
        return OnSuccessListener<Void> {
            when (origin) {
                SIGN_OUT_TASK -> {
                    finish()
                }
                DELETE_USER_TASK -> {
                    //finish()
                }
                UPDATE_USERNAME -> {
                    usernameProgressBar.visibility = View.INVISIBLE
                    userUsernameTextView.text = username
                }
                else -> { }
            }
        }
    }

    private fun createAlterDialogWithEditText() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Change username")
        val dialogLayout = inflater.inflate(R.layout.alert_dialog_edittext, null)
        val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
        builder.setView(dialogLayout)

        builder.setPositiveButton("OK") { _, _ ->
            val username: String = editText.text.toString()
            this.updateUsername(username)
            Toast.makeText(this, username, Toast.LENGTH_SHORT).show()
        }.setNegativeButton("Cancel", null)
        builder.show()
    }

    private fun updateUsername(username: String) {
        if (username.isNotBlank()) {
            getCurrentUser()?.let { user ->
                UserHelper.updateUsername(username, user.uid)
                    .addOnFailureListener(this.onFailureListener())
                    .addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_USERNAME, username))
            }
        } else {
            showSnackBar(getString(R.string.error_invalid_username))
        }
    }

    private fun showSnackBar(message: String){
        Snackbar.make(userProfileConstraintLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun updateUIWhenCreating() {

        // Main user
        intent.getParcelableExtra<MyUser>("USER_MAIN")?.let { mainUser ->
            //this.setUser(mainUser)
            val mAdapter = UserGameListAdapter(this, mainUser.games)
            games_recycler_view.adapter = mAdapter
            games_recycler_view.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            this.setupMainUserViews()
            this.getCurrentUser()?.let { currentUser ->

                if (currentUser.photoUrl != null) {
                    //Get picture URL from Firebase
                    Glide.with(this)
                        .load(currentUser.photoUrl)
                        .into(user_profile_picture)
                } else {
                    Glide.with(this)
                        .load(R.drawable.default_profile_pic)
                        .into(user_profile_picture)
                }

                UserHelper.getUser(currentUser.uid)
                    .addOnSuccessListener { documentSnapshot ->
                        val user = documentSnapshot.toObject(User::class.java)
                        val username = user?.username ?: "Username not found"
                        userUsernameTextView.text = username
                    }
            }

            // changeProfilePictureButton
            addFriendButton.setOnClickListener {
                if (isPermissionGranted()) {
                    this.changeProfilePicture()
                }
            }
        }

        // Selected user
        intent.getParcelableExtra<MyUser>("USER_SELECTED")?.let { currentUser ->
            isCurrentUser = false
            this.setUser(currentUser)
            val mAdapter = UserGameListAdapter(this, currentUser.games)
            games_recycler_view.adapter = mAdapter
            games_recycler_view.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            addFriendButton.setOnClickListener {
                val user = currentUser
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (!isCurrentUserLogged()) {
            finish()
        }
    }

}
