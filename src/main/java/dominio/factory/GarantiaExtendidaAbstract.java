package dominio.factory;

import java.util.Date;

import dominio.GarantiaExtendida;
import dominio.Producto;

public abstract class GarantiaExtendidaAbstract {
	public abstract GarantiaExtendida generaGarantiaExtendida(Producto producto,String nombreCliente);
	public abstract Date obtenerfechaFinGarantia();
	public abstract double calcularPrecioGarantia(double precio);
}
