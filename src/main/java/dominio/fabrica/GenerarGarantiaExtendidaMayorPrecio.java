package dominio.fabrica;

import java.util.Calendar;
import java.util.Date;

import dominio.GarantiaExtendida;
import dominio.Producto;

public class GenerarGarantiaExtendidaMayorPrecio implements IGenerarGarantiaExtendida {
	public static final int DIAS = 200;
	public static final int PORCENTAJE = 20;
	public static final int CIEN = 100;

	@Override
	public GarantiaExtendida generaGarantiaExtendida(Producto producto, String nombreCliente) {
		Date fechaSolicitud =  new Date();
		return new GarantiaExtendida(producto,fechaSolicitud, obtenerfechaFinGarantia(fechaSolicitud),
				calcularPrecioGarantia(producto.getPrecio()), nombreCliente);
	}

	@Override
	public Date obtenerfechaFinGarantia(Date fechaSolicitud) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaSolicitud);
		int dias = DIAS;
		while (dias > 0) {
			if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
				dias--;
			}
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			calendar.add(Calendar.DAY_OF_YEAR, 2);
		}
		return calendar.getTime();
	}

	@Override
	public double calcularPrecioGarantia(double precio) {
		return precio * (PORCENTAJE / CIEN);
	}

}
