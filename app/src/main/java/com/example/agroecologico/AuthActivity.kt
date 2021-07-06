package com.example.agroecologico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import com.example.agroecologico.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.core.util.Pair

class AuthActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

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
                                showHome(it.result?.user?.email.toString(), MainActivity.ProviderType.BASIC)
                            } else {
                                showAlert()
                            }
                    }
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