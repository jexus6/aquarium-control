package jf.aquarium;

import jf.aquarium.R;
import jf.aquarium.bd.Acuario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GraficosActivity extends Activity {
	public static final int FECHA_ANNO = 10;
	public static final int FECHA_MES = 11;
	public static final int FECHA_DIA = 12;
	public static final int FECHA_HORA = 13;
	public static final int FECHA_MINUTO = 14;
	public static final int FECHA_SEGUNDO = 15;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graficos_layout);
    }
    
    public void graficoPhAccion(View view){
    	Grafico grafico = new Grafico();
        Intent intentGrafico = grafico.getIntent(this, Acuario.PH);
        startActivity(intentGrafico);
    }
    public void graficoGhAccion(View view){
    	Grafico grafico = new Grafico();
        Intent intentGrafico = grafico.getIntent(this, Acuario.GH);
        startActivity(intentGrafico);
    }
    public void graficoNo2Accion(View view){
    	Grafico grafico = new Grafico();
        Intent intentGrafico = grafico.getIntent(this, Acuario.NO2);
        startActivity(intentGrafico);
    }
    public void graficoNh3Accion(View view){
    	Grafico grafico = new Grafico();
        Intent intentGrafico = grafico.getIntent(this, Acuario.NH3);
        startActivity(intentGrafico);
    }
    
}