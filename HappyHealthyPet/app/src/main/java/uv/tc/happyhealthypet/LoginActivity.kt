package uv.tc.happyhealthypet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import uv.tc.happyhealthypet.databinding.ActivityLoginBinding
import uv.tc.happyhealthypet.modelo.DBHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.btnIniciarSesion.setOnClickListener {
            verificarCredenciales()
        }
    }

    private fun verificarCredenciales() {
        val correo = binding.etCorreo.text.toString()
        val password = binding.etContrasena.text.toString()

        val usuario = dbHelper.getUsuario(correo)
        if (usuario != null && usuario.password == password) {
            mostrarToast("Bienvenid@ a la aplicación...")
            irPantallaPrincipal(correo)
        } else {
            mostrarToast("Usuario y/o contraseña incorrectos")
        }
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    private fun irPantallaPrincipal(username: String) {
        val intent = Intent(this, RegisterActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
        finish()
    }
}
