package com.example.lista1

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        val timeInput = findViewById<EditText>(R.id.editTextTime)
        val button = findViewById<Button>(R.id.Inicializador)
        val timerTextView = findViewById<TextView>(R.id.timerTextView)

        button.setOnClickListener {
            val inputText = timeInput.text.toString()
            val timeInMinutes = inputText.toIntOrNull() ?: 25
            val timeInMillis = (timeInMinutes * 60 * 1000).toLong()

            iniciarContagem(timeInMillis, timerTextView)
        }
    }

    private fun iniciarContagem(tempo: Long, timerTextView: TextView) {
        timer?.cancel()

        timer = object : CountDownTimer(tempo, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                vibrar(this@MainActivity, 1000)
                timerTextView.text = "00:00"
            }
        }.start()
    }

    private fun vibrar(context: Context, tempo: Long) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationEffect = VibrationEffect.createOneShot(tempo, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(tempo)
        }
    }
}
