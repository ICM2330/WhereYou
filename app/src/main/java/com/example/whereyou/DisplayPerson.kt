package com.example.whereyou

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.whereyou.datos.Person

class DisplayPerson(context: Context, private val people: List<Person>) :
        ArrayAdapter<Person>(context, R.layout.list_horizontal_layout, people){
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val inflater = LayoutInflater.from(context)
                val personView = inflater.inflate(R.layout.list_horizontal_layout, parent, false)

                val person = people[position]

                val personImage: ImageButton = personView.findViewById(R.id.personButton)
                val personName: TextView = personView.findViewById(R.id.personName)

                personImage.setImageResource(person.imagenId)
                personName.text = person.nombre

                return personView
        }

}