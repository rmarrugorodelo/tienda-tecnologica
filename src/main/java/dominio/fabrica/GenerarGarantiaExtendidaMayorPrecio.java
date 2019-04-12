package dominio.fabrica;

import java.util.Calendar;
import java.util.Date;

import dominio.GarantiaExtendida;
import dominio.Producto;

public class GenerarGarantiaExtendidaMayorPrecio implements IGenerarGarantiaExtendida {
	public static final int DIAS = 200;
	public static final int PORCENTAJE = 20;

	@Override
	public GarantiaExtendida generaGarantiaExtendida(Producto producto, String nombreCliente) {
		return new GarantiaExtendida(producto, new Date(), obtenerfechaFinGarantia(),
				calcularPrecioGarantia(producto.getPrecio()), nombreCliente);
	}

	@Override
	public Date obtenerfechaFinGarantia() {
		Calendar calendar = Calendar.getInstance();
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
		return precio * (PORCENTAJE / 100);
	}

}
