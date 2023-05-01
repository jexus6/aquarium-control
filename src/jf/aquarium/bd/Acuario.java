package jf.aquarium.bd;

import java.util.HashMap;
import java.util.Map;



public class Acuario {
	private long id;
	private String ph= "";
    private String no2="";
	private String nh3="";
	private String gh="";
	private int abonoP = 0;
	private int abonoN = 0;
	private int abonoC = 0;
	public enum TipoAbonado {P, N, C};
	public final static String[] Abonado = {"P","N","C"};
	private String cambioAgua = "";
	private String observaciones="";
	private String foto="X";
	
	public final static String PH_MAX = "PH_MAX";
	public final static String PH_MIN = "PH_MIN";
	public final static String GH_MAX = "GH_MAX";
	public final static String GH_MIN = "GH_MIN";
	public final static String NO2_MAX = "NO2_MAX";
	public final static String NH3_MAX = "NH3_MAX";
	public final static String NO2_MIN = "NO2_MIN";
	public final static String NH3_MIN = "NH3_MIN";
    
	public static final int PH  = 2;
	public static final int GH  = 3;
	public static final int NO2 = 4;
	public static final int NH3 = 5;
		 	
	public static final Map<Integer,String> PARAMETROS = new HashMap<Integer,String>();
	static 
		{
		 PARAMETROS.put(PH,"PH");
		 PARAMETROS.put(GH,"GH");
		 PARAMETROS.put(NO2,"NO2");
		 PARAMETROS.put(NH3,"NH3");
		}
	
	private String fecha="";
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
		
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getNo2() {
		return no2;
	}
	public void setNo2(String no2) {
		this.no2 = no2;
	}
	public String getNh3() {
		return nh3;
	}
	public void setNh3(String nh3) {
		this.nh3 = nh3;
	}
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String obsrv) {
		this.observaciones = obsrv;
	}
	public String toString(){
		return fecha;
	}
	public String toStringTodo(){
		return fecha+"PH: "+ph;
	}
	public int getAbonoP() {
		return abonoP;
	}
	public void setAbonoP(int abonoPo) {
		this.abonoP = abonoPo;
	}
	public int getAbonoN() {
		return abonoN;
	}
	public void setAbonoN(int abonoNi) {
		this.abonoN = abonoNi;
	}
	public int getAbonoC() {
		return abonoC;
	}
	public void setAbonoC(int abonoC) {
		this.abonoC = abonoC;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getCambioAgua() {
		return cambioAgua;
	}
	public void setCambioAgua(String cambioAgua) {
		this.cambioAgua = cambioAgua;
	}
		
}
	
	
	


