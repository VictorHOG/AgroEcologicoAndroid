package com.example.agroecologico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.agroecologico.databinding.ActivityAdminCreateStallBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminCreateStallActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAdminCreateStallBinding
    private val dataBase = FirebaseFirestore.getInstance()

    /*private var stallName: TextInputEditText? = null
    private var userID: TextInputEditText? = null
    private var email: TextInputEditText? = null
    private var phoneNumber: TextInputEditText? = null
    private var password: TextInputEditText? = null
    private var confirmPassword: TextInputEditText? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAdminCreateStallBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        /* stallName = mBinding.createStallStallName
         userID
         email
         phoneNumber
         password
         confirmPassword*/

        setupListeners()
        validateAndCreate()
    }

    private fun setupListeners() {
        mBinding.createStallStallName.addTextChangedListener(TextFieldValidation(mBinding.createStallStallName))
        mBinding.createStallUserID.addTextChangedListener(TextFieldValidation(mBinding.createStallUserID))
        mBinding.createStallEmail.addTextChangedListener(TextFieldValidation(mBinding.createStallEmail))
        mBinding.createStallPhoneNumber.addTextChangedListener(TextFieldValidation(mBinding.createStallPhoneNumber))
        mBinding.createStallPassword.addTextChangedListener(TextFieldValidation(mBinding.createStallPassword))
        mBinding.createStallConfirmPassword.addTextChangedListener(TextFieldValidation(mBinding.createStallConfirmPassword))
    }

    /**
     * Aplicar TextWatcher de texto en cada campo de texto
     */
    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (view.id) {
                R.id.createStallStallName -> {
                    validateStallName()
                }
                R.id.createStallUserID -> {
                    validateUserID()
                }
                R.id.createStallEmail -> {
                    validateEmail()
                }
                R.id.createStallPhoneNumber -> {
                    validatePhoneNumber()
                }
                R.id.createStallPassword -> {
                    validatePassword()
                }
                R.id.createStallConfirmPassword -> {
                    validateConfirmPassword()
                }
            }
        }
    }

    private fun validateAndCreate() {
        mBinding.buttonCreateStall.setOnClickListener {

            if (isValidate()) {

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    mBinding.createStallEmail.text.toString(),
                    mBinding.createStallPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {

                        Toast.makeText(
                            this,
                            "Se creo el puesto de venta y se registro al usuario como vendedor",
                            Toast.LENGTH_SHORT
                        ).show()

                        dataBase.collection("Users")
                            .document(mBinding.createStallEmail.text.toString()).set(
                                hashMapOf(
                                    "name" to "",
                                    "email" to mBinding.createStallEmail.text.toString(),
                                    "phoneNumber" to mBinding.createStallPhoneNumber.text.toString(),
                                    "isUser" to "1"
                                )// Especificando el tipo de usuario
                            )

                        dataBase.collection("Stalls")
                            .document(mBinding.createStallUserID.text.toString()).set(
                                hashMapOf(
                                    "email" to mBinding.createStallEmail.text.toString(),
                                    "phoneNumber" to mBinding.createStallPhoneNumber.text.toString(),
                                    "stallName" to mBinding.createStallStallName.text.toString(),
                                    "userID" to mBinding.createStallUserID.text.toString()
                                )
                            )
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }


    private fun isValidate(): Boolean =
        validateStallName() && validateUserID() && validateEmail() && validatePhoneNumber() && validatePassword() && validateConfirmPassword()


    private fun validateStallName(): Boolean {
        if (mBinding.createStallStallName.text.toString().trim().isEmpty()) {
            mBinding.textInputLayoutCreateStallStallName.error = "¡Campo requerido!"
            mBinding.createStallStallName.requestFocus()
            return false
        } else {
            mBinding.textInputLayoutCreateStallStallName.isErrorEnabled = false
        }
        return true
    }

    private fun validateUserID(): Boolean {
        if (mBinding.createStallUserID.text.toString().trim().isEmpty()) {
            mBinding.textInputLayoutCreateStallUserID.error = "¡Campo requerido!"
            mBinding.createStallUserID.requestFocus()
            return false
        } else {
            mBinding.textInputLayoutCreateStallUserID.isErrorEnabled = false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        if (mBinding.createStallEmail.text.toString().trim().isEmpty()) {
            mBinding.textInputLayoutCreateStallEmail.error = "¡Campo requerido!"
            mBinding.createStallEmail.requestFocus()
            return false
        } else if (!FieldValidators.isValidEmail(mBinding.createStallEmail.text.toString())) {
            mBinding.textInputLayoutCreateStallEmail.error = "¡Correo electrónico invalido!"
            mBinding.createStallEmail.requestFocus()
            return false
        } else {
            mBinding.textInputLayoutCreateStallEmail.isErrorEnabled = false
        }
        return true
    }

    private fun validatePhoneNumber(): Boolean {
        if (mBinding.createStallPhoneNumber.text.toString().trim().isEmpty()) {
            mBinding.textInputLayoutCreateStallPhoneNumber.error = "¡Campo requerido!"
            mBinding.createStallPhoneNumber.requestFocus()
            return false
        } else if (mBinding.createStallPhoneNumber.text.toString().length != 10) {
            mBinding.textInputLayoutCreateStallPhoneNumber.error = "¡Numero de telefono no valido!"
            mBinding.createStallPhoneNumber.requestFocus()
            return false
        } else {
            mBinding.textInputLayoutCreateStallPhoneNumber.isErrorEnabled = false
        }
        return true
    }


    private fun validatePassword(): Boolean {
        if (mBinding.createStallPassword.text.toString().trim().isEmpty()) {
            mBinding.textInputLayoutCreateStallPassword.error = "¡Campo requerido!"
            mBinding.createStallPassword.requestFocus()
            return false
        } else if (mBinding.createStallPassword.text.toString().length < 6) {
            mBinding.textInputLayoutCreateStallPassword.error =
                "La contraseña no puede ser menor que 6"
            mBinding.createStallPassword.requestFocus()
            return false
        } else {
            mBinding.textInputLayoutCreateStallPassword.isErrorEnabled = false
        }
        return true
    }

    private fun validateConfirmPassword(): Boolean {
        when {
            mBinding.createStallConfirmPassword.text.toString().trim().isEmpty() -> {
                mBinding.textInputLayoutCreateStallConfirmPassword.error = "¡Campo requerido!"
                mBinding.createStallConfirmPassword.requestFocus()
                return false
            }
            mBinding.createStallConfirmPassword.text.toString() != mBinding.createStallPassword.text.toString() -> {
                mBinding.textInputLayoutCreateStallConfirmPassword.error =
                    "Las contraseñas no coinciden"
                mBinding.createStallConfirmPassword.requestFocus()
                return false
            }
            else -> {
                mBinding.textInputLayoutCreateStallConfirmPassword.isErrorEnabled = false
            }
        }
        return true
    }

    private fun showAlert() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error registrando el puesto de venta.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}