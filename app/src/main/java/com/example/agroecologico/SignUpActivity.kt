package com.example.agroecologico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.agroecologico.FieldValidators.isStringContainNumber
import com.example.agroecologico.FieldValidators.isStringContainSpecialCharacter
import com.example.agroecologico.FieldValidators.isStringLowerAndUpperCase
import com.example.agroecologico.FieldValidators.isValidEmail
import com.example.agroecologico.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {
    lateinit var mBinding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupListeners()

        mBinding.buttonSignUp.setOnClickListener {
            if (isValidate()) {
                Toast.makeText(this, "Validado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidate(): Boolean = validateUserName() && validateEmail() && validatePhoneNumber() && validatePassword() && validateConfirmPassword()

    private fun setupListeners() {
        mBinding.userName.addTextChangedListener(TextFieldValidation(mBinding.userName))
        mBinding.email.addTextChangedListener(TextFieldValidation(mBinding.email))
        mBinding.phoneNumber.addTextChangedListener(TextFieldValidation(mBinding.phoneNumber))
        mBinding.password.addTextChangedListener(TextFieldValidation(mBinding.password))
        mBinding.confirmPassword.addTextChangedListener(TextFieldValidation(mBinding.confirmPassword))
    }

    /**
    * Aplicar TextWatcher de texto en cada campo de texto
    */
    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            when (view.id) {
                R.id.userName -> {
                    validateUserName()
                }
                R.id.email -> {
                    validateEmail()
                }
                R.id.phoneNumber -> {
                    validatePhoneNumber()
                }
                R.id.password -> {
                    validatePassword()
                }
                R.id.confirmPassword -> {
                    validateConfirmPassword()
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}

    }
    /**El campo no debe estar vacío*/
    private fun validateUserName(): Boolean {
        if (mBinding.userName.text.toString().trim().isEmpty()) {
            mBinding.textInputLayoutUserName.error = "¡Campo requerido!"
            mBinding.userName.requestFocus()
            return false
        } else {
            mBinding.textInputLayoutUserName.isErrorEnabled = false
        }
        return true
    }

    /**
    * 1) el campo no debe estar vacío
    * 2) el texto debe coincidir con el formato de la dirección de correo electrónico
    */
    private fun validateEmail(): Boolean {
        if (mBinding.email.text.toString().trim().isEmpty()) {
            mBinding.textInputLayoutEmailUser.error = "¡Campo requerido!"
            mBinding.email.requestFocus()
            return false
        } else if (!isValidEmail(mBinding.email.text.toString())) {
            mBinding.textInputLayoutEmailUser.error = "¡Correo electrónico invalido!"
            mBinding.email.requestFocus()
            return false
        } else {
            mBinding.textInputLayoutEmailUser.isErrorEnabled = false
        }
        return true
    }

    /**
     * 1) El campo no debe estar vacío
     * 2) La longitud del numero debe ser igual a 10
     */
    private fun validatePhoneNumber(): Boolean {
        if (mBinding.phoneNumber.text.toString().trim().isEmpty()) {
            mBinding.textInputLayoutPhoneNumber.error = "¡Campo requerido!"
            mBinding.phoneNumber.requestFocus()
            return false
        } else if (mBinding.phoneNumber.text.toString().length != 10) {
            mBinding.textInputLayoutPhoneNumber.error = "¡Numero de telefono no valido!"
            mBinding.phoneNumber.requestFocus()
            return false
        } else {
            mBinding.textInputLayoutPhoneNumber.isErrorEnabled = false
        }
        return true
    }

    /**
    * 1) el campo no debe estar vacío
    * 2) la longitud de la contraseña no debe ser inferior a 6
    * 3) la contraseña debe contener al menos un dígito
    * 4) la contraseña debe contener al menos una letra mayúscula y una minúscula
    * 5) la contraseña debe contener al menos un carácter especial.
    */
    private fun validatePassword(): Boolean {
        if (mBinding.password.text.toString().trim().isEmpty()) {
            mBinding.textInputLayoutPassword.error = "¡Campo requerido!"
            mBinding.password.requestFocus()
            return false
        } else if (mBinding.password.text.toString().length < 6) {
            mBinding.textInputLayoutPassword.error = "La contraseña no puede ser menor que 6"
            mBinding.password.requestFocus()
            return false
        } else if (!isStringContainNumber(mBinding.password.text.toString())) {
            mBinding.textInputLayoutPassword.error = "Se requiere al menos 1 dígito"
            mBinding.password.requestFocus()
            return false
        } else if (!isStringLowerAndUpperCase(mBinding.password.text.toString())) {
            mBinding.textInputLayoutPassword.error = "La contraseña debe contener letras mayúsculas y minúsculas"
            mBinding.password.requestFocus()
            return false
        } else if (!isStringContainSpecialCharacter(mBinding.password.text.toString())) {
            mBinding.textInputLayoutPassword.error = "Se requiere 1 carácter especial"
            mBinding.password.requestFocus()
            return false
        } else {
            mBinding.textInputLayoutPassword.isErrorEnabled = false
        }
        return true
    }

    /**
    * 1) el campo no debe estar vacío
    * 2) la contraseña y la contraseña de confirmación deben ser las mismas
    */
    private fun validateConfirmPassword(): Boolean {
        when {
            mBinding.confirmPassword.text.toString().trim().isEmpty() -> {
                mBinding.textInputLayoutConfirmPassword.error = "¡Campo requerido!"
                mBinding.confirmPassword.requestFocus()
                return false
            }
            mBinding.confirmPassword.text.toString() != mBinding.password.text.toString() -> {
                mBinding.textInputLayoutConfirmPassword.error = "Las contraseñas no coinciden"
                mBinding.confirmPassword.requestFocus()
                return false
            }
            else -> {
                mBinding.textInputLayoutConfirmPassword.isErrorEnabled = false
            }
        }
        return true
    }




}