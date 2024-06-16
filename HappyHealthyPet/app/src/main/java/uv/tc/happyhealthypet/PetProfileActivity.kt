package uv.tc.happyhealthypet

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import uv.tc.happyhealthypet.databinding.ActivityPetProfileBinding
import uv.tc.happyhealthypet.modelo.DBHelper

class PetProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetProfileBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.btnGuardar.setOnClickListener {
            agregarMascota()
        }
    }

    private fun agregarMascota() {
        val nombre = binding.etNombre.text.toString()
        val edad = binding.etEdad.text.toString().toIntOrNull() ?: 0
        val raza = binding.etRaza.text.toString()
        val sexo = binding.etSexo.text.toString()
        val fechaNacimiento = binding.etFechaNacimiento.text.toString()

        if (nombre.isEmpty() || raza.isEmpty() || sexo.isEmpty() || fechaNacimiento.isEmpty()) {
            mostrarToast("Por favor, complete todos los campos")
            return
        }

        val result = dbHelper.addMascota(nombre, edad, raza, sexo, fechaNacimiento)
        if (result != -1L) {
            mostrarToast("Mascota agregada exitosamente")
        } else {
            mostrarToast("Error al agregar mascota")
        }
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}
