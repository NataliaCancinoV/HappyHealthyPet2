package uv.tc.happyhealthypet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import uv.tc.happyhealthypet.databinding.ActivityRegisterBinding
import uv.tc.happyhealthypet.modelo.DBHelper

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.btnRegistrar.setOnClickListener {
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        val nombre = binding.etNombre.text.toString()
        val correo = binding.etCorreo.text.toString()
        val password = binding.etContrasena.text.toString()
        val confirmarPassword = binding.etContrasenaConfirmacion.text.toString()

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            mostrarToast("Por favor, complete todos los campos")
            return
        }

        if (password != confirmarPassword) {
            mostrarToast("Las contraseñas no coinciden")
            return
        }

        val result = dbHelper.addUsuario(correo, password)
        if (result != -1L) {
            mostrarToast("Usuario registrado exitosamente")
            irPetProfileActivity()
        } else {
            mostrarToast("Error al registrar usuario")
        }
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    private fun irPetProfileActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
