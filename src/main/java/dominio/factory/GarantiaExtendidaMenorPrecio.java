package dominio.factory;

import java.util.Calendar;
import java.util.Date;

import dominio.GarantiaExtendida;
import dominio.Producto;

public class GarantiaExtendidaMenorPrecio extends GarantiaExtendidaAbstract {
	public static final int DIAS = 100;
	public static final int PORCENTAJE = 10;

	@Override
	public GarantiaExtendida generaGarantiaExtendida(Producto producto, String nombreCliente) {
		return new GarantiaExtendida(producto, new Date(), obtenerfechaFinGarantia(),
				calcularPrecioGarantia(producto.getPrecio()), nombreCliente);
	}

	@Override
	public Date obtenerfechaFinGarantia() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, DIAS - 1);
		return calendar.getTime();
	}

	@Override
	public double calcularPrecioGarantia(double precio) {
		return precio * (PORCENTAJE / 100);
	}
}
