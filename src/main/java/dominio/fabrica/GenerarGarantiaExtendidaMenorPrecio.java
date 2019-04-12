package dominio.fabrica;

import java.util.Calendar;
import java.util.Date;

import dominio.GarantiaExtendida;
import dominio.Producto;

public class GenerarGarantiaExtendidaMenorPrecio implements IGenerarGarantiaExtendida {
	public static final int DIAS = 100;
	public static final int PORCENTAJE = 10;
	public static final int CIEN = 100;

	@Override
	public GarantiaExtendida generaGarantiaExtendida(Producto producto, String nombreCliente) {
		Date fechaSolicitud = new Date();
		return new GarantiaExtendida(producto, fechaSolicitud, obtenerfechaFinGarantia(fechaSolicitud),
				calcularPrecioGarantia(producto.getPrecio()), nombreCliente);
	}

	@Override
	public Date obtenerfechaFinGarantia(Date fechaSolicitud) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaSolicitud);
		calendar.add(Calendar.DAY_OF_YEAR, DIAS - 1);
		return calendar.getTime();
	}

	@Override
	public double calcularPrecioGarantia(double precio) {
		return precio * (PORCENTAJE / CIEN);
	}
}
