package com.example.pinbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.pinbox.JPAProductos;
import com.example.pinbox.Producto;
import com.example.pinbox.ProductoAdapter;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProductosFav extends AppCompatActivity {

    private ListView listaProductosFavView;

    private ArrayList<Producto> listaProductos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.hasExtra("all_products")) {
            listaProductos = (ArrayList<Producto>) intent.getSerializableExtra("all_products");
        }
        ArrayList<Producto> listaProductosFav = new ArrayList<>();
        for (Producto product : listaProductos){
            if ((product.isFavorito())==true){
                listaProductosFav.add(product);
            }
        }
        setContentView(R.layout.activity_productos_favoritos);
        listaProductosFavView = findViewById(R.id.idListProductosFavView);
        ArrayAdapter<Producto> adapter = new ProductoAdapter(this, listaProductosFav);
        listaProductosFavView.setAdapter(adapter);
        listaProductosFavView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();
                if (adapter instanceof ArrayAdapter) {
                    Producto item = (Producto) (((ArrayAdapter) adapter).getItem(i)); //No se si se puede hacer sin casteo
                    for (Producto p : listaProductos){
                        if (p.getIdProducto()==item.getIdProducto()){
                            i=listaProductos.indexOf(p);
                        }
                    }
                    infoProducto(i);
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        //Intent intent = getIntent();
        //if (intent.hasExtra("all_products")) {
         //   listaProductos = (ArrayList<Producto>) intent.getSerializableExtra("all_products");
        //}
        this.listaProductos=GestorArchivos.load(getFilesDir().getPath() + "/" + "data.dat");
        ArrayList<Producto> listaProductosFav = new ArrayList<>();
        for (Producto product : listaProductos){
            if ((product.isFavorito())==true){
                listaProductosFav.add(product);
            }
        }
        setContentView(R.layout.activity_productos_favoritos);
        listaProductosFavView = findViewById(R.id.idListProductosFavView);
        ArrayAdapter<Producto> adapter = new ProductoAdapter(this, listaProductosFav);
        listaProductosFavView.setAdapter(adapter);
        listaProductosFavView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();
                if (adapter instanceof ArrayAdapter) {
                    Producto item = (Producto) (((ArrayAdapter) adapter).getItem(i)); //No se si se puede hacer sin casteo
                    for (Producto p : listaProductos){
                        if (p.getIdProducto()==item.getIdProducto()){
                            i=listaProductos.indexOf(p);
                        }
                    }
                    infoProducto(i);
                }
            }
        });
    }
    private void infoProducto(int i){
        Intent navInfoProducto = new Intent(this, GuardarProducto.class);
        navInfoProducto.putExtra("producto", listaProductos.get(i));
        navInfoProducto.putExtra("id",i);
        startActivity(navInfoProducto);
    }
}