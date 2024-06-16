package uv.tc.happyhealthypet.modelo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2 // Incrementar si has agregado una tabla nueva
        private const val DATABASE_NAME = "BDMascotas.db"
        private const val TABLE_NAME_MASCOTAS = "Mascota"
        private const val TABLE_NAME_USUARIOS = "Usuario"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_EDAD = "edad"
        private const val COLUMN_RAZA = "raza"
        private const val COLUMN_SEXO = "sexo"
        private const val COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento"
        private const val COLUMN_CORREO = "correo"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableMascotas = ("CREATE TABLE $TABLE_NAME_MASCOTAS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NOMBRE TEXT, "
                + "$COLUMN_EDAD INTEGER, "
                + "$COLUMN_RAZA TEXT, "
                + "$COLUMN_SEXO TEXT, "
                + "$COLUMN_FECHA_NACIMIENTO TEXT)")
        db.execSQL(createTableMascotas)

        val createTableUsuarios = ("CREATE TABLE $TABLE_NAME_USUARIOS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_CORREO TEXT, "
                + "$COLUMN_PASSWORD TEXT)")
        db.execSQL(createTableUsuarios)

        // Insertar usuario predeterminado
        val values = ContentValues().apply {
            put(COLUMN_CORREO, "diego@gmail.com")
            put(COLUMN_PASSWORD, "12345")
        }
        db.insert(TABLE_NAME_USUARIOS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            val createTableUsuarios = ("CREATE TABLE $TABLE_NAME_USUARIOS ("
                    + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "$COLUMN_CORREO TEXT, "
                    + "$COLUMN_PASSWORD TEXT)")
            db.execSQL(createTableUsuarios)

            // Insertar usuario predeterminado
            val values = ContentValues().apply {
                put(COLUMN_CORREO, "diego@gmail.com")
                put(COLUMN_PASSWORD, "12345")
            }
            db.insert(TABLE_NAME_USUARIOS, null, values)
        }
    }

    fun addMascota(nombre: String, edad: Int, raza: String, sexo: String, fechaNacimiento: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOMBRE, nombre)
        values.put(COLUMN_EDAD, edad)
        values.put(COLUMN_RAZA, raza)
        values.put(COLUMN_SEXO, sexo)
        values.put(COLUMN_FECHA_NACIMIENTO, fechaNacimiento)
        val result = db.insert(TABLE_NAME_MASCOTAS, null, values)
        db.close()
        return result
    }

    fun addUsuario(correo: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_CORREO, correo)
        values.put(COLUMN_PASSWORD, password)
        val result = db.insert(TABLE_NAME_USUARIOS, null, values)
        db.close()
        return result
    }

    fun getUsuario(correo: String): Usuario? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_USUARIOS WHERE $COLUMN_CORREO = ?"
        val cursor = db.rawQuery(query, arrayOf(correo))
        var usuario: Usuario? = null
        if (cursor.moveToFirst()) {
            val userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val userCorreo = cursor.getString(cursor.getColumnIndex(COLUMN_CORREO))
            val userPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            usuario = Usuario(userId, userCorreo, userPassword)
        }
        cursor.close()
        db.close()
        return usuario
    }
}