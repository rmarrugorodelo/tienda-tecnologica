package dominio.fabrica;

import java.util.Date;

import dominio.GarantiaExtendida;
import dominio.Producto;

public interface IGenerarGarantiaExtendida {
	GarantiaExtendida generaGarantiaExtendida(Producto producto,String nombreCliente);
	Date obtenerfechaFinGarantia(Date fechaSolicitud);
	double calcularPrecioGarantia(double precio);
}
