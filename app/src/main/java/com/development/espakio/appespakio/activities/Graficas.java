package com.development.espakio.appespakio.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.development.espakio.appespakio.R;
import com.development.espakio.appespakio.presenter.TestPresenter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/*import com.androidplot.series.XYSeries;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import java.util.Arrays;//*/
//https://www.programcreek.com/java-api-examples/index.php?api=com.androidplot.xy.XYPlot

public class Graficas extends AppCompatActivity {

    private XYPlot myXYPlot;
    TestPresenter testPresenterst = new TestPresenter(this);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas);

       

        //Inicializamos el panel de la grafica
        myXYPlot = (XYPlot) findViewById(R.id.myXYPlot);
        myXYPlot.setDomainLabel("TIEMPO");
        myXYPlot.setRangeLabel("AVANCE");
        myXYPlot.setDomainStep(StepMode.SUBDIVIDE, 5);
        myXYPlot.setDomainRightMax(this.getMes());
        myXYPlot.setDomainLeftMin(this.getMes()-4);

        //Array de Datos a Graficar

        Number[] Datos1 = testPresenterst.getDatos(Integer.toString(this.getMes()));
        Number[] Datos2 = testPresenterst.getDatos(Integer.toString(this.getMes()-1));
        Number[] Datos3 = testPresenterst.getDatos(Integer.toString(this.getMes()-2));

        //Grafica una lina con los datos del arrglo 1
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(Datos1),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                "MEMORIA");//Nombre de la linea 1

        //Grafica otra lina con los datos del arrglo 2
        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(Datos2),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                "ATENCION");//Nombre de la linea 2

        XYSeries series3 = new SimpleXYSeries(
                Arrays.asList(Datos3),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                "AGILIDAD");//Nombre de la linea 1


        //Se da color a las lineas puntos y relleno de cada serie de datos
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(230,19,230),//Color de Linea
                Color.rgb(250,0,250),//Color del Punto
                null, null);//Color del Relleno
        LineAndPointFormatter series2Format = new LineAndPointFormatter(
                Color.rgb(0,0,200),//Color de Linea
                Color.rgb(0,0,100),//Color del Punto
                null, null);//Color del Relleno

        LineAndPointFormatter series3Format = new LineAndPointFormatter(
                Color.rgb(0,200,0),//Color de Linea
                Color.rgb(0,100,0),//Color del Punto
                null, null);//Color del Relleno

        //Se agregan las series con sus respectivos formatos al panel al panel
        myXYPlot.addSeries(series1,series1Format);
        myXYPlot.addSeries(series2,series2Format);
        myXYPlot.addSeries(series3,series3Format);
        //*/
    }
    
    private int getMes(){        
        
        String fecha=this.getFecha();
        String[] fechaPartes = fecha.split("-");
        int mes = Integer.parseInt(fechaPartes[1]);
        
        return mes;
    }
    
    private String getFecha(){
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        return fecha;
    }

}
