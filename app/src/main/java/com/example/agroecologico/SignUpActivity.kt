package com.example.agroecologico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agroecologico.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var mBinding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}