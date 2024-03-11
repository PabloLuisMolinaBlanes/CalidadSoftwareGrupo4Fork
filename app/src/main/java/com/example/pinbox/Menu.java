package com.example.pinbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        GestorArchivos.resetScanResult();

        Button btnVerProductos = findViewById(R.id.btnProductos);
        btnVerProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){verProductos();}
        });

        Button btnFindProd = findViewById(R.id.btnFindProd);
        btnFindProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){infoProducto();}
        });

        Button btnHistory = findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){verHistorial();}
        });

    }

    private void verProductos(){
        Intent navProductos = new Intent(this, Productos.class);
        startActivity(navProductos);
    }

    private void infoProducto(){
        Intent navScann = new Intent(this, ScannerActivity.class);
        navScann.putExtra("search",true);
        startActivity(navScann);
    }

    private void verHistorial(){
        Intent navHistorial = new Intent(this, ActivityLog.class);
        startActivity(navHistorial);
    }
}
