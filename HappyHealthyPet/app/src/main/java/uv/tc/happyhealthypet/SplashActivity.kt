package uv.tc.happyhealthypet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uv.tc.happyhealthypet.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.ivSplash.alpha = 0f
        binding.ivSplash.animate().setDuration(2000).alpha(1f).withEndAction{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}