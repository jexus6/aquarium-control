package jf.aquarium;

import jf.aquarium.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AquariumControl extends TabActivity {
	    /** Called when the activity is first created. */
	private TabHost tabHost;
	
	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	 
	        tabHost = getTabHost();
	 
	        // Tab para entrada de datos
	        TabSpec entradaspec = tabHost.newTabSpec("Entrada");
	        // poniendo el titulo y el icono para la pestaña
	        entradaspec.setIndicator(getResources().getString(R.string.tituloDatos), getResources().getDrawable(R.drawable.ic_tab_entrada));
	        Intent entradaIntent = new Intent(this, EntradaDatosActivity.class);
	        entradaspec.setContent(entradaIntent);
	 
	        // Tab para ver los datos de días anteriores
	        TabSpec datosspec = tabHost.newTabSpec("Datos");
	        datosspec.setIndicator(getResources().getString(R.string.tituloHistorial), getResources().getDrawable(R.drawable.ic_tab_historial));
	        Intent visualizacionIntent = new Intent(this, VisualizacionDatosActivity.class);
	        datosspec.setContent(visualizacionIntent);
	 
	        // Tab para ver las graficas del progreso
	        TabSpec graficosspec = tabHost.newTabSpec("Graficos");
	        graficosspec.setIndicator(getResources().getString(R.string.tituloGraficos), getResources().getDrawable(R.drawable.ic_tab_grafico));
	        Intent graficosIntent = new Intent(this, GraficosActivity.class);
	        graficosspec.setContent(graficosIntent);
	        
	        // Tab para ver configurar los parametros óptimos del acuario
	        TabSpec configuracionspec = tabHost.newTabSpec("Configuracion");
	        configuracionspec.setIndicator(getResources().getString(R.string.tituloConfiguracion), getResources().getDrawable(R.drawable.ic_tab_configuracion));
	        Intent configuracionIntent = new Intent(this, ConfiguracionActivity.class);
	        configuracionspec.setContent(configuracionIntent);
	 
	        // Se añaden todas las pestañas el TabHost
	        tabHost.addTab(entradaspec); 
	        tabHost.addTab(datosspec); 
	        tabHost.addTab(graficosspec);
	        tabHost.addTab(configuracionspec);
	   
	    }
	    
	    public void switchTab(int tab){
            tabHost.setCurrentTab(tab);
	    }


	
}
