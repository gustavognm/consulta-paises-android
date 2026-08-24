package dev.gustavo.consultapaises

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.gustavo.consultapaises.network.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var btnSearch: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView
    private lateinit var rvResults: RecyclerView
    private lateinit var adapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val rootView = findViewById<View>(R.id.main)
        val extraPadding = (16 * resources.displayMetrics.density).toInt()
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + extraPadding,
                systemBars.top + extraPadding,
                systemBars.right + extraPadding,
                systemBars.bottom + extraPadding
            )
            insets
        }

        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)
        progressBar = findViewById(R.id.progressBar)
        tvError = findViewById(R.id.tvError)
        rvResults = findViewById(R.id.rvResults)

        adapter = CountryAdapter(emptyList())
        rvResults.layoutManager = LinearLayoutManager(this)
        rvResults.adapter = adapter

        btnSearch.setOnClickListener {
            val nome = etSearch.text.toString().trim()
            if (nome.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_field_empty), Toast.LENGTH_SHORT).show()
            } else {
                buscarPaises(nome)
            }
        }
    }

    private fun buscarPaises(nome: String) {
        mostrarCarregando()

        lifecycleScope.launch {
            try {
                val resposta = RetrofitClient.api.searchByName(nome)
                val resultado = resposta.data.objects

                if (resultado.isEmpty()) {
                    mostrarErro(getString(R.string.error_not_found))
                } else {
                    adapter.atualizarLista(resultado)
                    rvResults.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                }
            } catch (e: Exception) {
                mostrarErro(getString(R.string.error_generic))
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun mostrarErro(mensagem: String) {
        adapter.atualizarLista(emptyList())
        rvResults.visibility = View.GONE
        tvError.visibility = View.VISIBLE
        tvError.text = mensagem
    }

    private fun mostrarCarregando() {
        progressBar.visibility = View.VISIBLE
        tvError.visibility = View.GONE
        rvResults.visibility = View.GONE
    }
}
