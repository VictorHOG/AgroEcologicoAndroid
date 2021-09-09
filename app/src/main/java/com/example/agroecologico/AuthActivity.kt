package com.example.agroecologico

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import com.example.agroecologico.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.core.util.Pair
import com.google.firebase.firestore.FirebaseFirestore

class AuthActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityAuthBinding
    private val dataBase = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        session()

        // Setup
        setup()



        var signUp = mBinding.buttonSignUp

        var emailUser = mBinding.textInputLayoutAuthEmailUser
        var password = mBinding.textInputLayoutAuthPasswordUser
        var button = mBinding.buttonAuthLogin
        var view = mBinding.view
        var linearLayoutOptions = mBinding.linearLayoutOptions

        signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)

            val emailViewPair = Pair.create<View, String>(emailUser, "emailUserTransition")
            val passwordViewPair = Pair.create<View, String>(password, "passwordTransition")
            val buttonViewPair = Pair.create<View, String>(button, "buttonTransition")
            val viewViewPair = Pair.create<View, String>(view, "viewTransition")
            val layoutViewPair = Pair.create<View, String>(linearLayoutOptions, "linearLayoutTransition")

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,emailViewPair, passwordViewPair, buttonViewPair, viewViewPair, layoutViewPair)

            startActivity(intent, options.toBundle())
            //supportFinishAfterTransition()
        }
    }

    override fun onStart() {
        super.onStart()
        mBinding.linearLayoutAuth.visibility = View.VISIBLE
    }

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)

        if (email != null) {
            mBinding.linearLayoutAuth.visibility = View.INVISIBLE
            checkUserAccessLevel(email)
        }
    }

    private fun checkField() {

    }

    private fun setup() {

        val emailUser = mBinding.textInputEditTextAuthEmailUser
        val passwordUser = mBinding.textInputEditTextAuthPasswordUser

        mBinding.buttonAuthLogin.setOnClickListener {
            if (emailUser.text?.isNotEmpty() == true && passwordUser.text?.isNotEmpty() == true) {

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emailUser.text.toString(),
                        passwordUser.text.toString()).addOnCompleteListener {

                            if (it.isSuccessful) {
                                checkUserAccessLevel(it.result?.user?.email.toString())
                                //showHome(it.result?.user?.email.toString(), MainActivity.ProviderType.BASIC)
                            } else {
                                showAlert()
                            }
                    }
            }
        }
    }

    private fun checkUserAccessLevel(email: String) {
        dataBase.collection("Users").document(email).get().addOnSuccessListener {
            val typeUser = it.get("isUser") as String?
            if (typeUser == "0") {
                val intent = Intent(this, AdminActivity::class.java).apply {
                    putExtra("email", email)
                }
                startActivity(intent)
            }else if (typeUser == "1") {
                val intent = Intent(this, VendorActivity::class.java).apply {
                    putExtra("email", email)
                }
                startActivity(intent)
            }else if (typeUser == "2") {
                val intent = Intent(this, ShopperActivity::class.java).apply {
                    putExtra("email", email)
                }
                startActivity(intent)
            }
        }
    }

    private fun showAlert() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: MainActivity.ProviderType) {

        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}