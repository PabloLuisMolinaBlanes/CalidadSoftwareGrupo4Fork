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

public class ProductosOOS extends AppCompatActivity {

    private ListView listaProductosOOSView;

    private ArrayList<Producto> listaProductos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.hasExtra("all_products")) {
            listaProductos = (ArrayList<Producto>) intent.getSerializableExtra("all_products");
        }
        ArrayList<Producto> listaProductosOOS = new ArrayList<>(); //references Out Of Stock
        for (Producto product : listaProductos){
            if ((product.getCantidad())==0){
                listaProductosOOS.add(product);
            }
        }
        setContentView(R.layout.activity_productos_out_stock);
        listaProductosOOSView = findViewById(R.id.idListProductosOOSView);
        ArrayAdapter<Producto> adapter = new ProductoAdapter(this, listaProductosOOS);
        listaProductosOOSView.setAdapter(adapter);
        listaProductosOOSView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        //    listaProductos = (ArrayList<Producto>) intent.getSerializableExtra("all_products");
        //}
        this.listaProductos=GestorArchivos.load(getFilesDir().getPath() + "/" + "data.dat");
        ArrayList<Producto> listaProductosOOS = new ArrayList<>(); //references Out Of Stock
        for (Producto product : listaProductos) {
            if ((product.getCantidad()) == 0) {
                listaProductosOOS.add(product);
            }
        }
        setContentView(R.layout.activity_productos_out_stock);
        listaProductosOOSView = findViewById(R.id.idListProductosOOSView);
        ArrayAdapter<Producto> adapter = new ProductoAdapter(this, listaProductosOOS);
        listaProductosOOSView.setAdapter(adapter);
        listaProductosOOSView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();
                if (adapter instanceof ArrayAdapter) {
                    Producto item = (Producto) (((ArrayAdapter) adapter).getItem(i)); //No se si se puede hacer sin casteo
                    for (Producto p : listaProductos) {
                        if (p.getIdProducto() == item.getIdProducto()) {
                            i = listaProductos.indexOf(p);
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
