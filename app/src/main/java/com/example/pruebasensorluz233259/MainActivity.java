package com.example.pruebasensorluz233259;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager administradorSensores;
    private Sensor sensorLuz;
    private TextView textView;
    private View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        layout = findViewById(R.id.layout);

        administradorSensores = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorLuz = administradorSensores.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        administradorSensores.registerListener(this, sensorLuz, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        administradorSensores.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float intensidadLuz = event.values[0];
            if(intensidadLuz>=20){
               layout.setBackgroundColor(Color.WHITE);
            }else{
                int escalaRgbLuz = (int) Math.ceil(255*(intensidadLuz/20f));
                layout.setBackgroundColor(Color.rgb(escalaRgbLuz,escalaRgbLuz,escalaRgbLuz));
            }
            textView.setText(mensajeIntensidadLuz(intensidadLuz));
        }
    }

    private String mensajeIntensidadLuz(float intensidadLuz){
        return
                intensidadLuz >= 20 ? "Hay mucha luz en el entorno" :
                intensidadLuz >= 15 ? "Hay suficiente luz en el entorno" :
                intensidadLuz >= 10 ? "Hay luz regular en el entorno" :
                intensidadLuz >= 5 ? "Hay poquita luz en el entorno" : "No hay luz en el entorno";
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}