package dominio;

import dominio.repositorio.RepositorioProducto;

import java.util.HashMap;
import java.util.Map;

import dominio.excepcion.GarantiaExtendidaException;
import dominio.factory.GarantiaExtendidaAbstract;
import dominio.factory.GarantiaExtendidaFactory;
import dominio.factory.GarantiaExtendidaMayorPrecioFactory;
import dominio.factory.GarantiaExtendidaMenorPrecioFactory;
import dominio.repositorio.RepositorioGarantiaExtendida;

public class Vendedor {

	public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantia extendida";
	public static final String PRODUCTO_NO_CUENTA_CON_GARANTIA = "Este producto no cuenta con garantía extendida";
	public static final String PRODUCTO_NO_EXISTE = "Este producto no existe";

	public static final double PRECIO_TOPE = 500000;

	private RepositorioProducto repositorioProducto;
	private RepositorioGarantiaExtendida repositorioGarantia;

	public Vendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia) {
		this.repositorioProducto = repositorioProducto;
		this.repositorioGarantia = repositorioGarantia;

	}

	public void generarGarantia(String codigo, String nombreCliente) {
		if (tieneGarantia(codigo)) {
			throw new GarantiaExtendidaException(EL_PRODUCTO_TIENE_GARANTIA);
		} else if (tieneTresVocales(codigo)) {
			throw new GarantiaExtendidaException(PRODUCTO_NO_CUENTA_CON_GARANTIA);
		} else {
			Producto producto = repositorioProducto.obtenerPorCodigo(codigo);
			if (producto != null) {
				GarantiaExtendida garantiaExtendida = null;
				GarantiaExtendidaFactory factory = null;
				GarantiaExtendidaAbstract garantiaExtendidaAbstract = null;
				if (producto.getPrecio() > PRECIO_TOPE) {
					factory = new GarantiaExtendidaMayorPrecioFactory();
					garantiaExtendidaAbstract = factory.crear();
					garantiaExtendida = garantiaExtendidaAbstract.generaGarantiaExtendida(producto,nombreCliente);
				} else {
					factory = new GarantiaExtendidaMenorPrecioFactory();
					garantiaExtendidaAbstract = factory.crear();
					garantiaExtendida = garantiaExtendidaAbstract.generaGarantiaExtendida(producto,nombreCliente);
				}
				repositorioGarantia.agregar(garantiaExtendida);
			} else {
				throw new GarantiaExtendidaException(PRODUCTO_NO_EXISTE);
			}

		}

	}

	public boolean tieneGarantia(String codigo) {
		return repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo) != null;
	}

	public boolean tieneTresVocales(String codigo) {
		Map<Character, Character> mapaVocalesEncontradas = new HashMap<>();
		for (int i = 0; i < codigo.length(); i++) {
			char caracter = codigo.toLowerCase().charAt(i);
			if (caracter == 'a' || caracter == 'e' || caracter == 'i' || caracter == 'o' || caracter == 'u') {
				mapaVocalesEncontradas.put(caracter, caracter);
			}
		}
		return mapaVocalesEncontradas.size() > 2;
	}

}
