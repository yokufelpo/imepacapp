package br.com.faculdade.imepac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Tela_Perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_perfil)
        getSupportActionBar()?.hide();
    }
}