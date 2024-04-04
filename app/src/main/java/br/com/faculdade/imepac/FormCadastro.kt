package br.com.faculdade.imepac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FormCadastro : AppCompatActivity() {
    // Declarações de variáveis
    private lateinit var edit_nome: EditText
    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var btnCadastrar: Button
    private lateinit var usuarioID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)
        supportActionBar?.hide()

        // Inicialização das variáveis
        edit_nome = findViewById(R.id.edit_nome)
        edit_email = findViewById(R.id.edit_email)
        edit_senha = findViewById(R.id.edit_senha)
        btnCadastrar = findViewById(R.id.bt_cadastrar)

        // Listener do botão de cadastro
        btnCadastrar.setOnClickListener {
            val nome = edit_nome.text.toString().trim()
            val email = edit_email.text.toString().trim()
            val senha = edit_senha.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                val mensagemErro = "Campos não preenchidos, tente novamente"
                exibirSnackbar(mensagemErro)
            } else {
                cadastrarUsuario(it)
            }
        }
    }

    // Função para cadastrar o usuário
    private fun cadastrarUsuario(view: View) {
        val email = edit_email.text.toString().trim()
        val senha = edit_senha.text.toString().trim()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    SalvarDadosUsuario()
                    val mensagemOk = "Cadastro realizado com sucesso"
                    exibirSnackbar(mensagemOk)
                } else {
                    val mensagemErro = "Erro ao cadastrar: ${task.exception?.message}"
                    exibirSnackbar(mensagemErro)
                }
            }
    }

    // Função para exibir Snackbar
    private fun exibirSnackbar(mensagem: String) {
        val snackbar = Snackbar.make(btnCadastrar, mensagem, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    fun SalvarDadosUsuario() {
        val nome = edit_nome.text.toString().trim()

        val db = FirebaseFirestore.getInstance()
        val usuarios = hashMapOf(
            "nome" to nome
        )
        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid

        if (usuarioID != null) {
            db.collection("usuarios")
                .add(usuarios)
                .addOnSuccessListener { documentReference ->
                    println("Documento adicionado com ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    println("Erro ao adicionar documento: $e")
                }
        } else {
            println("Erro na Autenticação")
        }
    }
}
