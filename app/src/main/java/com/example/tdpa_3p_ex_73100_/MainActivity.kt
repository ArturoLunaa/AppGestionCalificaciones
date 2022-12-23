package com.example.tdpa_3p_ex_73100_

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tdpa_3p_ex_73100_.databinding.ActivityMainBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalcular.setOnClickListener {
            mandarDatosCalcular()
        }

        binding.btnGuardar.setOnClickListener {
            alta()
        }

        binding.btnBuscarN.setOnClickListener {
            buscarPorNombre()
        }

        binding.btnEliminar.setOnClickListener {
            borrarAlumno()
        }

        binding.btnModificar.setOnClickListener {
            modificarAlumno()
        }
    }

    fun mandarDatosCalcular(){
        val intento = Intent(this, CalificacionesView::class.java);
        intento.putExtra("nombre", binding.txtNombre.text.toString());
        intento.putExtra("primerParcial", binding.txtPrimerP.text.toString().toDouble());
        intento.putExtra("segundoParcial", binding.txtSegundoP.text.toString().toDouble());
        startActivity(intento);
    }

    //Funciones SQLite
    fun alta(){
        val admin = AdminSQLiteOpenHelper(this, "administracion",null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("nombre", binding.txtNombre.text.toString())
        registro.put("materia", binding.txtMateria.text.toString())
        registro.put("primerP", binding.txtPrimerP.text.toString().toDouble())
        registro.put("segundoP", binding.txtSegundoP.text.toString().toDouble())
        bd.insert("alumnos", null, registro)
        bd.close()
        binding.txtNombre.setText("")
        binding.txtMateria.setText("")
        binding.txtPrimerP.setText("")
        binding.txtSegundoP.setText("")
        Toast.makeText(this, "Se insertó correctamente el alumno" , Toast.LENGTH_SHORT).show()
    }

    fun borrarAlumno(){
        val admin = AdminSQLiteOpenHelper(this, "administracion",null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("alumnos", "nombre='${binding.txtNombre.text.toString()}'", null)
        bd.close()
        if(cant == 1){
            Toast.makeText(this,"El alumno se borró", Toast.LENGTH_SHORT).show()
            binding.txtNombre.setText("")
            binding.txtMateria.setText("")
            binding.txtPrimerP.setText("")
            binding.txtSegundoP.setText("")
        }
        else{
            Toast.makeText(this,"No hubo coincidencias con ese nombre", Toast.LENGTH_SHORT).show()
        }
    }

    fun modificarAlumno(){
        val admin = AdminSQLiteOpenHelper(this, "administracion",null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("materia", binding.txtMateria.text.toString())
        registro.put("primerP", binding.txtPrimerP.text.toString().toDouble())
        registro.put("segundoP", binding.txtSegundoP.text.toString().toDouble())
        val cant = bd.update("alumnos", registro, "nombre='${binding.txtNombre.text.toString()}'", null)
        bd.close()
        if(cant == 1){
            Toast.makeText(this,"El alumno se actualizó", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"No hubo coincidencias con ese nombre", Toast.LENGTH_SHORT).show()
        }
    }

    fun buscarPorNombre(){
        val admin = AdminSQLiteOpenHelper(this, "administracion",null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT materia, primerP, segundoP FROM alumnos where nombre='${binding.txtNombre.text.toString()}'", null)
        if(fila.moveToFirst()){
            binding.txtMateria.setText(fila.getString(0))
            binding.txtPrimerP.setText(fila.getString(1))
            binding.txtSegundoP.setText(fila.getString(2))
        }
        else{
            Toast.makeText(this,"No hubo coincidencias con ese nombre", Toast.LENGTH_SHORT).show()
        }
    }
}