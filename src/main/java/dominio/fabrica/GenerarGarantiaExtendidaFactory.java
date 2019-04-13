package dominio.fabrica;

import dominio.Producto;

public class GenerarGarantiaExtendidaFactory {
	public static final double PRECIO_TOPE = 500000;

	public IGenerarGarantiaExtendida crear(Producto producto) {
		if (producto.getPrecio() > PRECIO_TOPE) {
			return new GenerarGarantiaExtendidaMayorPrecio();
		} else {
			return new GenerarGarantiaExtendidaMenorPrecio();
		}
	}
	
}
