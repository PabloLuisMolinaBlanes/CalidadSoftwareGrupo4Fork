package com.example.pinbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class ScannerActivity extends AppCompatActivity {
    private TextView barCodeTest;
    private boolean searching;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        barCodeTest = findViewById(R.id.read);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("search")){
            this.searching=true;
        }else
            this.searching=false;
        this.startScanning();
        findViewById(R.id.Scann_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanning();
            }
        });
    }
    private void startScanning(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Escanea el código");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.getContents() != null){
            String barcode = result.getContents();
            System.out.println(barcode);
            ArrayList<Producto> prods = GestorArchivos.load(getFilesDir().getPath() + "/" + "data.dat");
            if (this.searching){ //Es una búsqueda por ID
                Producto producto= null;
                System.out.println("buscando por id     "+GestorArchivos.getScanResult());
                for (Producto prod : prods){
                    if (prod.getIdProducto()==Long.parseLong(barcode)){
                        producto=prod;
                        break;
                    }
                }
                if (producto!=null){
                    Intent navInfoProducto = new Intent(this, GuardarProducto.class);
                    navInfoProducto.putExtra("producto", GestorArchivos.load(getFilesDir().getPath() + "/" + "data.dat").get(prods.indexOf(producto)));
                    navInfoProducto.putExtra("id", prods.indexOf(producto));
                    startActivity(navInfoProducto);
                    onBackPressed(); //Para que vuelva atrás cuando se escanee algo, no se va a mostrar pantalla con esto

                }else
                    barCodeTest.setText("El id no corresponde a ningún producto");
            }else { //Se va a guardar el id leído

                boolean isSet = false;
                for (Producto prod : prods) {
                    if (prod.getIdProducto() == Long.parseLong(barcode)) {
                        isSet = true;
                        break;
                    }
                }
                if (isSet) {
                    barCodeTest.setText("El código ya está en uso");
                } else {
                    GestorArchivos.setScanResult(barcode);
                    barCodeTest.setText("Código leído: " + barcode);
                    onBackPressed(); //Para que vuelva atrás cuando se escanee algo, no se va a mostrar pantalla con esto
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
