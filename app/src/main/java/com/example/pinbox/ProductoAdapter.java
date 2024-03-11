package com.example.pinbox;

import android.widget.ArrayAdapter;
import com.example.pinbox.Producto;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ProductoAdapter extends ArrayAdapter<Producto> {
    public ProductoAdapter(Context context, ArrayList<Producto> productos) {
        super(context, 0, productos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        notifyDataSetChanged();
        Producto producto = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
        }
        if (producto.getCantidad()==0) {
            TextView textViewListItem = convertView.findViewById(R.id.nombreTextView);
            textViewListItem.setTextColor(getContext().getResources().getColor(R.color.red, getContext().getTheme()));
            textViewListItem = convertView.findViewById(R.id.cantidadTextView);
            textViewListItem.setTextColor(getContext().getResources().getColor(R.color.red, getContext().getTheme()));
        }
        TextView nombreTextView = convertView.findViewById(R.id.nombreTextView);
        TextView cantidadTextView = convertView.findViewById(R.id.cantidadTextView);

        nombreTextView.setText(producto.getNombre());
        cantidadTextView.setText("Cantidad: " + producto.getCantidad());

        return convertView;
    }
}
