import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.whereyou.R
import com.example.whereyou.datos.Contacto

class DisplayChat(context: Context, private val contacts: List<Contacto>) :
    ArrayAdapter<Contacto>(context, R.layout.list_item_layout, contacts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.list_item_layout, parent, false)

        val contact = contacts[position]

        val contactImage: ImageView = contactView.findViewById(R.id.imageView)
        val contactName: TextView = contactView.findViewById(R.id.contactName)
        val contactDescription: TextView = contactView.findViewById(R.id.descripcion)

        contactImage.setImageResource(contact.imagenId)
        contactName.text = contact.nombre
        contactDescription.text = contact.descripcion

        return contactView
    }
}