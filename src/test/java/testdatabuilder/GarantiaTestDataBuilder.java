package testdatabuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dominio.GarantiaExtendida;
import dominio.Producto;

public class GarantiaTestDataBuilder {
	private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
	private static final String NOMBRE_CLIENTE = "Richard Marrugo";
	private static final String FECHA_SOLICITUD = "2018-08-16";
	private static final String FECHA_FIN = "2019-04-06";

	private Producto producto;
	private Date fechaSolicitudGarantia;
	private Date fechaFinGarantia;
	private double precioGarantia;
	private String nombreCliente;

	public GarantiaTestDataBuilder() {
		this.nombreCliente = NOMBRE_CLIENTE;
		this.fechaSolicitudGarantia = obtenerFechaDeString(FECHA_SOLICITUD);
		this.fechaFinGarantia = obtenerFechaDeString(FECHA_FIN);

	}
	public GarantiaTestDataBuilder conFechaFinGarantia(String fechaFinGarantia) {
		this.fechaFinGarantia = obtenerFechaDeString(fechaFinGarantia);
		return this;
	}

	public GarantiaExtendida build() {
		return new GarantiaExtendida(this.producto, this.fechaSolicitudGarantia, this.fechaFinGarantia,
				this.precioGarantia, this.nombreCliente);
	}

	public Date obtenerFechaDeString(String fecha) {
		try {
			return formatoFecha.parse(fecha);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;
	}
}
