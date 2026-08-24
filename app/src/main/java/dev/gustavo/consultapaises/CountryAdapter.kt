package dev.gustavo.consultapaises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import java.text.NumberFormat
import java.util.Locale

class CountryAdapter(
    private var countries: List<Country>
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFlag: ImageView = itemView.findViewById(R.id.ivFlag)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvSubtitle: TextView = itemView.findViewById(R.id.tvSubtitle)
        val tvPopulation: TextView = itemView.findViewById(R.id.tvPopulation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        val capital = country.capitals?.firstOrNull()?.name ?: "—"
        val regiao = country.region ?: "—"
        val populacaoFormatada = country.population?.let {
            NumberFormat.getInstance(Locale("pt", "BR")).format(it)
        } ?: "—"

        holder.tvName.text = country.names.common
        holder.tvSubtitle.text = "$capital · $regiao"
        holder.tvPopulation.text = "${populacaoFormatada} habitantes"
        holder.ivFlag.load(country.flag.urlPng)
    }

    override fun getItemCount(): Int = countries.size

    fun atualizarLista(novaLista: List<Country>) {
        countries = novaLista
        notifyDataSetChanged()
    }
}
