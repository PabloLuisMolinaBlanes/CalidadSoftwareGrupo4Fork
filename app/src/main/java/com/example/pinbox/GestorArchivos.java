package com.example.pinbox;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import android.app.Activity;

public class GestorArchivos extends Activity {
    private static String scanResult;
    public static ArrayList<Producto> load(String directorio){
        System.out.println("Directorio:   "+directorio);
        ArrayList<Producto> listaProductos = new ArrayList<>();
        try {
            ObjectInputStream data = new ObjectInputStream(new FileInputStream(directorio));
            listaProductos = (ArrayList<Producto>) data.readObject();
            data.close();
            //intenta cargar los datos de la app y los asigna a la variable del array
        }catch (Exception ex1){
            try {
                listaProductos.add(new Producto("Camisetas", 10,false));
                listaProductos.add(new Producto("Pantalones", 5,false));
                listaProductos.add(new Producto("Zapatos", 20,true));
                listaProductos.add(new Producto("Pantuflas", 3,true));
                listaProductos.add(new Producto("Gorras", 0,false));
                listaProductos.add(new Producto("Bufandas", 0,false));
                GestorArchivos.save(directorio,listaProductos);
                return listaProductos;
                //ejecuta la creación local de los archivos por defecto y después el save() para salvaguardarlos
            }catch (Exception ex2){
                ex2.printStackTrace();
                //emite excepción
                return null;
            }
        }
        return listaProductos;
    }

    public static ArrayList<String> loadLog(String directorio){
        System.out.println("Directorio:   "+directorio);
        ArrayList<String> activityLog = new ArrayList<>();
        try {
            ObjectInputStream data = new ObjectInputStream(new FileInputStream(directorio));
            activityLog = (ArrayList<String>) data.readObject();
            data.close();
        }catch (Exception ex1){
            try {
                GestorArchivos.saveLog(directorio,activityLog);
                return activityLog;
            }catch (Exception ex2){
                ex2.printStackTrace();
                return null;
            }
        }
        return activityLog;
    }

    public static boolean save(String directorio, ArrayList<Producto> listaProductos){
        System.out.println("Directorio:   "+directorio);
        try {
            ObjectOutputStream data = new ObjectOutputStream(new FileOutputStream(directorio));
            data.writeObject(listaProductos);
            System.out.println("Guardado");
            data.close();
            //intenta guardar los datos de la app en el directorio C:\....  (hacer variable directorio)
        }catch (Exception ex1){
            //emite excepción
            ex1.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean saveLog(String directorio, ArrayList<String> activityLog){
        System.out.println("Directorio:   "+directorio);
        try {
            ObjectOutputStream data = new ObjectOutputStream(new FileOutputStream(directorio));
            data.writeObject(activityLog);
            System.out.println("Guardado");
            data.close();
        }catch (Exception ex1){
            ex1.printStackTrace();
            return false;
        }
        return true;
    }
    public static String getScanResult(){
        return scanResult;
    }
    public static void setScanResult(String result) {
        scanResult = result;
    }
    public static void resetScanResult(){
        scanResult = null;
    }
}
