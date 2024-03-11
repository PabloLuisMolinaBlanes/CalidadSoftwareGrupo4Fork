package com.example.pinbox;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GuardarProducto extends AppCompatActivity {

    private EditText textInputNombre;
    private EditText numberInputCantidad;
    private TextView textViewID;
    private CheckBox checkboxFav;
    private ArrayList<Producto> listaProductos = new ArrayList<>();
    private ArrayList<String> activityLog = new ArrayList<>();
    private ArrayList<String> tempData = null;
    private boolean tempFav = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_producto);
        this.listaProductos=GestorArchivos.load(getFilesDir().getPath() + "/" + "data.dat");
        this.activityLog=GestorArchivos.loadLog(getFilesDir().getPath() + "/" + "log.dat");
        textInputNombre = findViewById(R.id.inputNombre);
        numberInputCantidad = findViewById(R.id.inputCantidad);
        checkboxFav = findViewById(R.id.isFav);
        Button btnSaveProd = findViewById(R.id.btnSaveProd);
        Button btnDeleteProd = findViewById(R.id.btnDeleteProd);
        textViewID = findViewById(R.id.textID);
        Button btnScan = findViewById(R.id.btnScan);

        //añade el boton de ir para atras a la barra de arriba (no funciona el parentactivity
        //ya que he unido dos actividades en una sola pantalla y se les llama desde distintas
        //esta el caso del menu (añadirproducto) y el caso de lista de productos (editarproducto)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //EDITAR NO FUNCIONA CORRECTAMENTE POR LO DE LOS IDS
        //si se le pasa informacion de un producto existente entraremos en el if, se mostrará la
        //información de dicho producto yel boton de save realizará la accion de editar
        //en el caso de que no se le pase nada pues se mostrará la plantilla vacia y se añadira el
        //un producto con la información puesta
        Intent intent = getIntent();
        if (intent.hasExtra("producto")) {
            btnSaveProd.setVisibility(View.GONE);
            int id;
            //coge la info del producto
            Producto producto = (Producto) intent.getSerializableExtra("producto");
            //coge la id
            id = (int) intent.getSerializableExtra("id");
            System.out.println(id);
            textInputNombre.setText(producto.getNombre());
            numberInputCantidad.setText(String.valueOf(producto.getCantidad()));
            checkboxFav.setChecked(producto.isFavorito());
            textViewID.setText(String.valueOf(producto.getIdProducto()));

            btnSaveProd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View w) {EditProd(id);}
            });
            btnDeleteProd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View w) {DeleteProd(id);}
            });
        } else {
            btnDeleteProd.setVisibility(View.GONE);
            btnSaveProd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View w) {AddProd();}
            });

        };
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {scannNow();}
        });
    }
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_info_producto);
        this.listaProductos=GestorArchivos.load(getFilesDir().getPath() + "/" + "data.dat");
        this.activityLog=GestorArchivos.loadLog(getFilesDir().getPath() + "/" + "log.dat");
        textInputNombre = findViewById(R.id.inputNombre);
        numberInputCantidad = findViewById(R.id.inputCantidad);
        checkboxFav = findViewById(R.id.isFav);
        Button btnSaveProd = findViewById(R.id.btnSaveProd);
        Button btnDeleteProd = findViewById(R.id.btnDeleteProd);
        textViewID = findViewById(R.id.textID);
        Button btnScan = findViewById(R.id.btnScan);

        //añade el boton de ir para atras a la barra de arriba (no funciona el parentactivity
        //ya que he unido dos actividades en una sola pantalla y se les llama desde distintas
        //esta el caso del menu (añadirproducto) y el caso de lista de productos (editarproducto)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //si se le pasa informacion de un producto existente entraremos en el if, se mostrará la
        //información de dicho producto yel boton de save realizará la accion de editar
        //en el caso de que no se le pase nada pues se mostrará la plantilla vacia y se añadira el
        //un producto con la información puesta
        Intent intent = getIntent();
        if (intent.hasExtra("producto")) {
            int id;
            //coge la info del producto
            Producto producto = (Producto) intent.getSerializableExtra("producto");
            //coge la id
            id = (int) intent.getSerializableExtra("id");
            System.out.println(id);
            textInputNombre.setText(producto.getNombre());
            numberInputCantidad.setText(String.valueOf(producto.getCantidad()));
            checkboxFav.setChecked(producto.isFavorito());
            if (GestorArchivos.getScanResult()!=null)
                textViewID.setText(GestorArchivos.getScanResult());
            else
                textViewID.setText(String.valueOf(producto.getIdProducto()));

            btnSaveProd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View w) {EditProd(id);}
            });
            btnDeleteProd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View w) {DeleteProd(id);}
            });
        } else {
            btnDeleteProd.setVisibility(View.GONE);
            if (this.tempData!=null){
                textInputNombre.setText(this.tempData.get(0));
                numberInputCantidad.setText(this.tempData.get(1));

            }
            if (GestorArchivos.getScanResult()!=null)
                textViewID.setText(GestorArchivos.getScanResult());
            checkboxFav.setChecked(tempFav);
            btnSaveProd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View w) {AddProd();}
            });

        };
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {scannNow();}
        });
    }

    private void AddProd(){
        String nombre = textInputNombre.getText().toString().trim();
        int cantidad = Integer.parseInt(numberInputCantidad.getText().toString());
        boolean favorito = checkboxFav.isChecked();
        Producto prod=new Producto(nombre,cantidad,favorito);
        if (GestorArchivos.getScanResult()!=null){
            prod.setIdProducto(Long.parseLong(GestorArchivos.getScanResult()));//Falta comprobar que el ID sea único
            GestorArchivos.resetScanResult();
        }
        this.listaProductos.add(prod);
        String formattedDate = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date());
        this.activityLog.add(formattedDate + " | Se ha añadido el producto: " + nombre);
        GestorArchivos.save(getFilesDir().getPath() + "/" + "data.dat", listaProductos);
        GestorArchivos.saveLog(getFilesDir().getPath() + "/" + "log.dat", activityLog);
        finish();
    }

    private void EditProd(int id){
        String nombre = textInputNombre.getText().toString().trim();
        int cantidad = Integer.parseInt(numberInputCantidad.getText().toString());
        boolean favorito = checkboxFav.isChecked();
        Producto prod=new Producto(nombre,cantidad,favorito);
        if (GestorArchivos.getScanResult()!=null){
            prod.setIdProducto(Long.parseLong(GestorArchivos.getScanResult())); //Falta comprobar que el ID sea único
            GestorArchivos.resetScanResult();
        }else {
            prod.setIdProducto(listaProductos.get(id).getIdProducto());
        }
        this.listaProductos.set(id, prod);
        String formattedDate = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date());
        String a;
        if (favorito = true) {
            a="Si";
        } else{
            a="No";
        }
        this.activityLog.add(formattedDate +  " | Se ha modificado el Producto: " + nombre + "\n" + "\t" + "-Cantidad: " + cantidad + "\n" + "\t" + "-Favorito: " + a + "\n" + "\t" +"-Código: "+prod.getIdProducto());
        GestorArchivos.save(getFilesDir().getPath() + "/" + "data.dat", listaProductos);
        GestorArchivos.saveLog(getFilesDir().getPath() + "/" + "log.dat", activityLog);
        finish();
    }
    private void DeleteProd(int id){ //LE PASA LO MISMO QUE AL EDITPROD
        String nombre = textInputNombre.getText().toString().trim();
        this.listaProductos.remove(id);
        String formattedDate = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date());
        this.activityLog.add(formattedDate + " | Se ha eliminado el producto: " + nombre);
        GestorArchivos.save(getFilesDir().getPath() + "/" + "data.dat", listaProductos);
        GestorArchivos.saveLog(getFilesDir().getPath() + "/" + "log.dat", activityLog);
        finish();

    }

    //permite que el boton creado realice la acción de ir para atrás
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
    private void scannNow(){
        this.tempData=new ArrayList<>();//Para guardar temporalmente los datos introducidos
        this.tempData.add(textInputNombre.getText().toString().trim());
        this.tempData.add(numberInputCantidad.getText().toString());
        tempFav=checkboxFav.isChecked();
        System.out.println("ESCANEAR");
        Intent navScann = new Intent(this, ScannerActivity.class);
        startActivity(navScann);
    }
}