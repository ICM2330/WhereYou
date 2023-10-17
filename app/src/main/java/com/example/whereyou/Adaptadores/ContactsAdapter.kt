import android.annotation.SuppressLint
import com.example.whereyou.R
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

class ContactsAdapter(context: Context?, c: Cursor?, flags: Int) :
    CursorAdapter(context, c, flags) {
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val tvId = view!!.findViewById<TextView>(R.id.contactId)
        val tvName = view!!.findViewById<TextView>(R.id.contactName)
        val tvDescripcion = view!!.findViewById<TextView>(R.id.descripcion)
        val id = cursor!!.getInt(0)
        val name = cursor!!.getString(1)
        tvId.text=id.toString()
        tvName.text=name
        //tvDescripcion.text=descripcion
    }
}