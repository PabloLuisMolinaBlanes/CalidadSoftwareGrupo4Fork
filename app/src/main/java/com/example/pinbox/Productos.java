package com.example.pinbox;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class Productos extends AppCompatActivity implements Serializable {
    private ListView listaProductosView;
    private ArrayList<Producto> listaProductos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        scheduleJob();
        listaProductosView = findViewById(R.id.idListProductosView);
        this.listaProductos=GestorArchivos.load(getFilesDir().getPath() + "/" + "data.dat");
        GestorArchivos.resetScanResult();
        ArrayList<Producto> listaProductosOS = new ArrayList<>(); //references On Stock
        for (Producto product : listaProductos){
            if ((product.getCantidad())!=0){
                listaProductosOS.add(product);
            }
        }
        ArrayAdapter<Producto> adapter = new ProductoAdapter(this, listaProductosOS);
        listaProductosView.setAdapter(adapter);

        //para darle click a los distintos productos y ver su info + editarla
        listaProductosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        //productos sin stock
        Button btn_filter = findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {verProductosOOS();}
        });

        //productos fav
        Button btnFav = findViewById(R.id.btnFav);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {verProductosFav();}
        });

    }
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_productos);
        scheduleJob();
        listaProductosView = findViewById(R.id.idListProductosView);
        this.listaProductos=GestorArchivos.load(getFilesDir().getPath() + "/" + "data.dat");
        GestorArchivos.resetScanResult();
        ArrayList<Producto> listaProductosOS = new ArrayList<>(); //references On Stock
        for (Producto product : listaProductos){
            if ((product.getCantidad())!=0){
                listaProductosOS.add(product);
            }
        }
        ArrayAdapter<Producto> adapter = new ProductoAdapter(this, listaProductosOS);
        listaProductosView.setAdapter(adapter);

        //para darle click a los distintos productos y ver su info + editarla
        listaProductosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        //productos sin stock
        Button btn_filter = findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {verProductosOOS();}
        });

        //productos fav
        Button btnFav = findViewById(R.id.btnFav);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {verProductosFav();}
        });
        //añadir producto
        Button btnAdd = findViewById(R.id.btnAddProd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {AnadirProducto();}
        });
    }


    private void verProductosOOS(){
        Intent navProductosOOS = new Intent(this, ProductosOOS.class);
        navProductosOOS.putExtra("all_products", this.listaProductos);
        startActivity(navProductosOOS);
    }

    private void verProductosFav(){
        Intent navProductosFav = new Intent(this, ProductosFav.class);
        navProductosFav.putExtra("all_products", this.listaProductos);
        startActivity(navProductosFav);
    }

    //ver informacion del producto concreto no funciona bien por lo que te comente del array que esta filtrado
    private void infoProducto(int i){
        Intent navInfoProducto = new Intent(this, GuardarProducto.class);
        navInfoProducto.putExtra("producto", listaProductos.get(i));
        navInfoProducto.putExtra("id",i);
        startActivity(navInfoProducto);
    }
    private void AnadirProducto(){
        Intent navAnadirProducto = new Intent(this, GuardarProducto.class);
        startActivity(navAnadirProducto);
    }
    private void scheduleJob() {
        ComponentName componentName = new ComponentName(this, NotificationJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(10058, componentName)
                .setPeriodic(5 * 60 * 1000) // Programar cada 5 minutos
                .setPersisted(true) // Tarea persistente incluso después de reiniciar el dispositivo
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
    }
}
