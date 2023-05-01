package jf.aquarium;


import java.util.Date;
import java.util.Iterator;
import java.util.List;

import jf.aquarium.bd.Acuario;
import jf.aquarium.bd.BdAdaptador;

import org.achartengine.ChartFactory;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

public class Grafico {
	BdAdaptador datasource;
	
	public Intent getIntent(Context context, int grafico)
	{
		datasource = new BdAdaptador(context);
		Acuario acuario = null;
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		
		datasource.open();
		List<Acuario> valores = datasource.getAllParametros();
		datasource.close();
		
		TimeSeries seriesGrafica = new TimeSeries(Acuario.PARAMETROS.get(grafico));
		TimeSeries seriesMax = new TimeSeries(context.getResources().getString(R.string.literalMaximo));
		TimeSeries seriesMin = new TimeSeries(context.getResources().getString(R.string.literalMinimo));
		
		Iterator <Acuario>itValores = valores.iterator();
		while (itValores.hasNext()){
			acuario = (Acuario)itValores.next();
			if(getValor(grafico, acuario)!= null && !getValor(grafico, acuario).equalsIgnoreCase("")){
				seriesGrafica.add(getValorFecha(acuario),Double.valueOf(getValor(grafico, acuario)).doubleValue());
				seriesMax.add(getValorFecha(acuario),Double.valueOf((String)pref.getString(Acuario.PARAMETROS.get(grafico)+"_MAX", "0")).doubleValue());
				seriesMin.add(getValorFecha(acuario),Double.valueOf((String)pref.getString(Acuario.PARAMETROS.get(grafico)+"_MIN", "0")).doubleValue());
			}
		}
		
		XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
		dataSet.addSeries(seriesGrafica);
		dataSet.addSeries(seriesMax);
		dataSet.addSeries(seriesMin);
		
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.CYAN);
		renderer.setStroke(BasicStroke.SOLID);
		renderer.setFillPoints(true);
				
		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer2.setColor(Color.RED);
		
		XYSeriesRenderer renderer3 = new XYSeriesRenderer();
		renderer3.setColor(Color.parseColor(context.getResources().getString(R.color.Magenta)));
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle(context.getResources().getString(R.string.literalEvolucion));
		mRenderer.setChartTitleTextSize(25);
		mRenderer.setYTitle(Acuario.PARAMETROS.get(grafico));
		mRenderer.setYAxisMax(getMaxValor(valores, grafico)+getMaxValor(valores, grafico)/3);
		mRenderer.setYAxisMin(getMinValor(valores, grafico)-getMinValor(valores, grafico)/3);
		mRenderer.setXTitle(context.getResources().getString(R.string.literalFechas));
		mRenderer.setAxisTitleTextSize(20);
		mRenderer.setLegendTextSize(20);
		mRenderer.setFitLegend(true);
		mRenderer.setLegendHeight(10);
		mRenderer.setLabelsTextSize(18);
		mRenderer.setMargins(new int[] { 20, 30, 30, 30 });
		
				
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.addSeriesRenderer(renderer2);
		mRenderer.addSeriesRenderer(renderer3);
		
		Intent intent = ChartFactory.getTimeChartIntent(context, dataSet, mRenderer, null);
		return intent;
	}
	
	private Date getValorFecha(Acuario acuario){
		return new Date(Integer.parseInt(acuario.getFecha().substring(0, 4)),Integer.parseInt(acuario.getFecha().substring(5, 7)),
						Integer.parseInt(acuario.getFecha().substring(8,10)), Integer.parseInt(acuario.getFecha().substring(11, 13)),
						Integer.parseInt(acuario.getFecha().substring(14, 16)), Integer.parseInt(acuario.getFecha().substring(17, 19)));
		}
		
	private String  getValor(int campo, Acuario acuario){
			String valor="0";
			switch (campo) {		
			case Acuario.PH:
				valor=acuario.getPh();
				break;
			case Acuario.GH:
				valor=acuario.getGh();
				break;
			case Acuario.NO2:
				valor=acuario.getNo2();
				break;
			case Acuario.NH3:
				valor=acuario.getNh3();
				break;
			default:
				break;
		}
		return valor;
	} 
	
	private double getMaxValor(List<Acuario> valores, int parametro)
	{		
		Acuario acuario;
		double valor=0;
		double maxValor=0;
		
		Iterator <Acuario>itValores = valores.iterator();
		while (itValores.hasNext()){
			acuario = (Acuario)itValores.next();
			
			switch (parametro) {		
				case Acuario.PH:
					if (!acuario.getPh().equalsIgnoreCase(""))
						valor=Double.valueOf(acuario.getPh()).doubleValue();
					break;
				case Acuario.GH:
					if (!acuario.getGh().equalsIgnoreCase(""))
						valor=Double.valueOf(acuario.getGh()).doubleValue();
					break;
				case Acuario.NO2:
					if (!acuario.getNo2().equalsIgnoreCase(""))
						valor=Double.valueOf(acuario.getNo2()).doubleValue();
					break;
				case Acuario.NH3:
					if (!acuario.getNh3().equalsIgnoreCase(""))
						valor=Double.valueOf(acuario.getNh3()).doubleValue();
					break;
				default:
					break;
			}
			if (Double.compare(valor, maxValor) > 0) ;
				maxValor = valor;
		}
		return maxValor;
	}
	
	
	private double getMinValor(List<Acuario> valores, int parametro)
	{		
		Acuario acuario;
		double valor=0;
		double minValor=0;
		
		Iterator <Acuario>itValores = valores.iterator();
		while (itValores.hasNext()){
			acuario = (Acuario)itValores.next();
			
			switch (parametro) {		
				case Acuario.PH:
					if (!acuario.getPh().equalsIgnoreCase(""))
						valor=Double.valueOf(acuario.getPh()).doubleValue();
					break;
				case Acuario.GH:
					if (!acuario.getGh().equalsIgnoreCase(""))
						valor=Double.valueOf(acuario.getGh()).doubleValue();
					break;
				case Acuario.NO2:
					if (!acuario.getNo2().equalsIgnoreCase(""))
						valor=Double.valueOf(acuario.getNo2()).doubleValue();
					break;
				case Acuario.NH3:
					if (!acuario.getNh3().equalsIgnoreCase(""))
						valor=Double.valueOf(acuario.getNh3()).doubleValue();
					break;
				default:
					break;
			}
			if (Double.compare(valor, minValor) < 0) ;
				minValor = valor;
		}
		return minValor;
	}

}
