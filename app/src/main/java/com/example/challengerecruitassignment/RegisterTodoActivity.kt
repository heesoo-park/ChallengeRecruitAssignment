package com.example.challengerecruitassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.challengerecruitassignment.databinding.ActivityRegisterTodoBinding

class RegisterTodoActivity : AppCompatActivity() {

    private val binding: ActivityRegisterTodoBinding by lazy {
        ActivityRegisterTodoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.tbRegisterTodo)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnRegisterTodoRegister.setOnClickListener {
            val newIntent = Intent()
            newIntent.putExtra("todo", Todo(binding.etRegisterTodoTitle.text.toString(), binding.etRegisterTodoDescription.text.toString()))
            setResult(RESULT_OK, newIntent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}