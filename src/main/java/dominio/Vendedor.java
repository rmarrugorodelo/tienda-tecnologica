package dominio;

import java.util.HashMap;
import java.util.Map;

import dominio.excepcion.GarantiaExtendidaException;
import dominio.fabrica.GenerarGarantiaExtendidaFactory;
import dominio.fabrica.IGenerarGarantiaExtendida;
import dominio.repositorio.RepositorioGarantiaExtendida;
import dominio.repositorio.RepositorioProducto;

public class Vendedor {

	public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantia extendida";
	public static final String PRODUCTO_NO_CUENTA_CON_GARANTIA = "Este producto no cuenta con garantía extendida";

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
			GarantiaExtendida garantiaExtendida = null;
			GenerarGarantiaExtendidaFactory factory = new GenerarGarantiaExtendidaFactory();
			IGenerarGarantiaExtendida iGarantiaExtendida = factory.crear(producto);
			garantiaExtendida = iGarantiaExtendida.generaGarantiaExtendida(producto, nombreCliente);
			repositorioGarantia.agregar(garantiaExtendida);
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
