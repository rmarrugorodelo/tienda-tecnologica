package testdatabuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dominio.GarantiaExtendida;
import dominio.Producto;

public class GarantiaTestDataBuilder {
	private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
	private static final String NOMBRE_CLIENTE = "Richard Marrugo";
	private Producto producto;
	private Date fechaSolicitudGarantia;
	private Date fechaFinGarantia;
	private double precioGarantia;
	private String nombreCliente;

	public GarantiaTestDataBuilder() {
		this.nombreCliente = NOMBRE_CLIENTE;

	}

	public GarantiaTestDataBuilder conProducto(Producto producto) {
		this.producto = producto;
		return this;
	}

	public GarantiaTestDataBuilder conFechaSolicitudGarantia(Date fechaSolicitudGarantia) {
		this.fechaSolicitudGarantia = fechaSolicitudGarantia;
		return this;
	}

	public GarantiaTestDataBuilder conFechaFinGarantia(Date fechaFinGarantia) {
		this.fechaFinGarantia = fechaFinGarantia;
		return this;
	}

	public GarantiaTestDataBuilder conPrecioGarantia(double precioGarantia) {
		this.precioGarantia = precioGarantia;
		return this;
	}

	public GarantiaTestDataBuilder conNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
		return this;
	}

	public GarantiaExtendida build() {
		return new GarantiaExtendida(this.producto, this.fechaSolicitudGarantia, this.fechaFinGarantia,
				this.precioGarantia, this.nombreCliente);
	}
}
