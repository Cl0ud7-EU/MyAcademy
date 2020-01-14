package Model;

public class Pago {
	
	private String id_pago;
	private String cantidad;
	private String fecha;
	private String dni_alumno;
	
	public Pago(String id_pago, String cantidad, String fecha, String dni_alumno) {
		this.setId_pago(id_pago);
		this.setCantidad(cantidad);
		this.setFecha(fecha);
		this.setDni_alumno(dni_alumno);
	}
	
	public void setId_pago(String id_pago) {
		this.id_pago = id_pago;
	}
	
	public String getId_pago() {
		return id_pago;
	}
	
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getCantidad() {
		return cantidad;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setDni_alumno(String dni_alumno) {
		this.dni_alumno = dni_alumno;
	}
	
	public String getDni_alumno() {
		return dni_alumno;
	}
	
}
