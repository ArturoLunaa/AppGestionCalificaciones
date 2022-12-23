package com.example.tdpa_3p_ex_73100_

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tdpa_3p_ex_73100_.databinding.ActivityCalificacionesBinding
import com.squareup.picasso.Picasso

class CalificacionesView : AppCompatActivity() {
    private lateinit var binding: ActivityCalificacionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalificacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras;
        val name = bundle?.getString("nombre");
        val p1 = bundle?.getDouble("primerParcial");
        val p2 = bundle?.getDouble("segundoParcial");
        val acum = p1.toString().toDouble() * 2.0 + p2.toString().toDouble() * 2.0
        val restaAcum = 60 - acum

        val requiredToSix = (restaAcum * 10) / 60
        if (acum < 35){
            binding.txtAlumno.setText(name)
            binding.txtSeis.setText(requiredToSix.toString())
            binding.txtDiez.setText("YA NO SE LOGRÓ")

        }else{
            binding.txtAlumno.setText(name)
            binding.txtSeis.setText(requiredToSix.toString())
            binding.txtDiez.setText("AÚN SE PUEDE")
            val img = binding.imgAPI
            val urlImage:String = "https://picsum.photos/400"
            val urlParse:Uri = Uri.parse(urlImage)
            //Usar picasso para obtener la imagen
            Picasso.get().load(urlParse).into(img)
        }
        binding.btnFinalizar.setOnClickListener {
            finish()
        }
    }
}