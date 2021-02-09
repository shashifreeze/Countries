package com.hindicoder.countries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hindicoder.countries.model.Country
import com.hindicoder.countries.util.getProgressDrawable
import com.hindicoder.countries.util.loadImage
import kotlinx.android.synthetic.main.item_country.view.*

class CountriesListAdapter(var countries: ArrayList<Country>): RecyclerView.Adapter<CountriesListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries:List<Country>)
    {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country,parent,false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int {
       return countries.size
    }
    class CountryViewHolder(view : View): RecyclerView.ViewHolder(view) {

        private val countryName = view.nameTV
        private val imageView = view.imageView
        private val capital = view.capitalTV
        private val progressDrawable = getProgressDrawable(view.context)
        fun bind(country: Country)
        {
            countryName.text = country.countryName
            capital.text = country.capital
            imageView.loadImage(country.flag,progressDrawable)

        }
    }
}