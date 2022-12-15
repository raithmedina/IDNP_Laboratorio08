package com.example.laboratorio08;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declaro un ArrayList de objetos Datos (mismos que voy a guardar en un archivo y
    // al leer del archivo, los voy a almacenar aquí)
    List<Alumno> listaDatoes = new ArrayList<>();
    // Lista para el Spinner de nombres
    List<String> listaNombres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Método que guarda un nuevo objeto en el ArrayList
    public void GuardaEnLista(View v) {
        // Creo objetos Java y los vinculo con el layout
        EditText txtNombre = findViewById(R.id.txtNombre);
        EditText txtLugar = findViewById(R.id.txtApellidos);
        EditText txtfecNacimiento = findViewById(R.id.txtFechaNac);
        EditText txtcolProcedencia = findViewById(R.id.txtColProce);
        EditText txtPostula = findViewById(R.id.txtPostula);

        Spinner lstNombres = findViewById(R.id.lstNombres);

        // Agrego un elemento a mi ArrayList
        // (creo un nuevo objeto Datos y lo agrego)
        /*listaDatoes.add(new Alumno(txtNombre.getText().toString(),
                txtLugar.getText().toString()));
        */
        Alumno aux = new Alumno(
                        txtNombre.getText().toString(),
                        txtLugar.getText().toString(),
                        txtfecNacimiento.getText().toString(),
                        txtcolProcedencia.getText().toString(),
                        txtPostula.getText().toString());
        listaDatoes.add(aux);

        // Muestro mensaje
        Toast.makeText(this,"Agregado a la lista de Alumnos",Toast.LENGTH_SHORT).show();

        // Agrego nombre al spinner
        listaNombres.add(txtNombre.getText().toString());
        ArrayAdapter<String> llenaSpinner = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,listaNombres);
        llenaSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstNombres.setAdapter(llenaSpinner);

        // Limpio los campos
        txtNombre.setText("");
        txtLugar.setText("");
        txtfecNacimiento.setText("");
        txtcolProcedencia.setText("");
        txtPostula.setText("");
    }

    // Método que guarda la lista a un archivo interno
    public void GuardaEnArchivo(View v) {
        // El objeto File me permite acceder el archivo (en este caso, para escribir en él)
        // (obtengo la ruta donde almacenarlo; en la carpeta de la app)
        File ruta = getApplicationContext().getFilesDir();
        // Éste es el nombre del archivo
        String nombreArch = "archivo.tpo";

        // El acceso a archivo tiene que ir en un try catch por si sucede algo inesperado
        try {
            FileOutputStream escribirArch = new FileOutputStream(new File(ruta,nombreArch));
            // Tengo que usar un ObjectOutputStream porque el almacenamiento interno es un archivo de bytes
            // y este objeto es el que me permite convertir de objeto a byte. Si fuera un String u otra cosa,
            // bastaría escribirArch.write(lacadena.getBytes())
            // suponiendo que lacadena es un String que contiene el texto a guardar.
            ObjectOutputStream streamArch = new ObjectOutputStream(escribirArch);
            streamArch.writeObject(listaDatoes);
            streamArch.close();
        } catch (Exception e) {
            e.printStackTrace();        // Si hay error, que muestre datos sobre el fallo
        }
    }

    // Método que lee del archivo y lo pone en la lista
    public void LeeDelArchivo(View v) {
        // Obtengo referencia a los controles de la pantalla que voy a usar
        Spinner lstNombres = findViewById(R.id.lstNombres);

        // El objeto File con la ruta donde almacenarlo
        File ruta = getApplicationContext().getFilesDir();
        // Éste es el nombre del archivo
        String nombreArch = "archivo.tpo";

        // Borro la lista y borro lo que está en el spinner (el adapter será el arreglo vacío)
        listaNombres.clear();
        ArrayAdapter<String> llenaSpinner = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,listaNombres);
        lstNombres.setAdapter(llenaSpinner);

        // Leo los datos del archivo
        try {
            // FileInputStream me permite abri el archivo para leer de él
            FileInputStream leeArch = new FileInputStream (new File(ruta,nombreArch));
            // El ObjectInputStream me pemite traducir el arreglo de bytes al Arraylist
            ObjectInputStream streamArch = new ObjectInputStream (leeArch);
            // Leo todo y lleno la lista
            listaDatoes = (ArrayList<Alumno>) streamArch.readObject();
            // Cierro el stream
            streamArch.close();

            // Lleno la lista de nombres (strings) con los nombres de la lista de datos
            listaNombres.clear();
            for (int i = 0; i< listaDatoes.size(); i++) {
                listaNombres.add(listaDatoes.get(i).getNombre());
            }
            // Lleno el Spinner de la nueva lista
            llenaSpinner = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item,listaNombres);
            llenaSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            lstNombres.setAdapter(llenaSpinner);
        } catch (Exception e) {
            e.printStackTrace();        // Si hay error, que muestre datos sobre el fallo
        }
    }

    // Método que busca en la lista de datos y muestra información del que
    // se seleccionó en el Spinner
    public void MuestraDatos(View v) {
        // Obtengo referencia a los controles de la pantalla que voy a usar
        Spinner lstNombres = findViewById(R.id.lstNombres);

        // Creo un Alert Builder (ventana estándar que puedo usar)
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setTitle("Almacena");       // Pongo título a la ventana
        constructor.setPositiveButton("Aceptar",null);      // Agrego un botón

        // Si se seleccionó algo en el spinner, le sigo
        long index = lstNombres.getSelectedItemId();
        if (index > -1) {
            // Pongo mensaje a la ventana que voy a mostrar
            constructor.setMessage("        Datos del Alumno"
                   +"\n" +" - Nombres: " + listaDatoes.get((int) index).getNombre()
                   +"\n" + " - Apellidos: " + listaDatoes.get((int) index).getaPaterno()
                   +"\n" + " - Fecha de Nacimiento: " + listaDatoes.get((int) index).getFecNacimiento()
                   +"\n" + " - Colegio de Procedencia: " + listaDatoes.get((int) index).getColProcedencia()
                   +"\n" + " - Carrera que Postula: " + listaDatoes.get((int) index).getPostula()
            );     // El texto en la ventana
        } else {
            constructor.setMessage("Debe seleccionar un nombre de la lista");
        }

        // Creo y muestro la ventana
        AlertDialog ventanaMensaje = constructor.create();
        ventanaMensaje.show();
    }
}