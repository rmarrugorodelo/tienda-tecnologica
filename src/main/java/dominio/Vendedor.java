package dominio;

import dominio.repositorio.RepositorioProducto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import dominio.excepcion.GarantiaExtendidaException;
import dominio.repositorio.RepositorioGarantiaExtendida;

public class Vendedor {

	public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantia extendida";
	public static final String PRODUCTO_NO_CUENTA_CON_GARANTIA = "Este producto no cuenta con garantía extendida";
	public static final String PRODUCTO_NO_EXISTE = "Este producto no existe";

	public static final double PRECIO_TOPE = 500000D;
	public static final int DIAS_PARA_PRECIO_MAYOR_A_TOPE = 200;
	public static final int DIAS_PARA_PRECIO_MENOR_A_TOPE = 100;
	public static final int PORCENTAJE_PARA_PRECIO_MAYOR_A_TOPE = 20;
	public static final int PORCENTAJE_PARA_PRECIO_MENOR_A_TOPE = 10; 

	private RepositorioProducto repositorioProducto;
	private RepositorioGarantiaExtendida repositorioGarantia;

	public Vendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia) {
		this.repositorioProducto = repositorioProducto;
		this.repositorioGarantia = repositorioGarantia;

	}

	public void generarGarantia(String codigo,String nombre) {
		if (tieneGarantia(codigo)) {
			throw new GarantiaExtendidaException(EL_PRODUCTO_TIENE_GARANTIA);
		} else if (tieneTresVocales(codigo)) {
			throw new GarantiaExtendidaException(PRODUCTO_NO_CUENTA_CON_GARANTIA);
		} else {
			Producto producto = repositorioProducto.obtenerPorCodigo(codigo);
			if(producto!=null) {
				Date fechaSolicitudGarantia = new Date();
				Date fechaFinGarantia = null;
				double precioGarantia = 0d;
				if (producto.getPrecio() > PRECIO_TOPE) {
					fechaFinGarantia = obtenerfechaFinGarantia(fechaSolicitudGarantia, DIAS_PARA_PRECIO_MAYOR_A_TOPE);
					precioGarantia = producto.getPrecio() * (PORCENTAJE_PARA_PRECIO_MAYOR_A_TOPE / 100);
				} else {
					fechaFinGarantia = obtenerfechaFinGarantia(fechaSolicitudGarantia, DIAS_PARA_PRECIO_MENOR_A_TOPE);
					precioGarantia = producto.getPrecio() * (PORCENTAJE_PARA_PRECIO_MENOR_A_TOPE / 100);
				}
				GarantiaExtendida garantiaExtendida = new GarantiaExtendida(producto, fechaSolicitudGarantia,
						fechaFinGarantia, precioGarantia, nombre);
				repositorioGarantia.agregar(garantiaExtendida);
			}else {
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

	public Date obtenerfechaFinGarantia(Date fechaActual, int cantidadDeDias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaActual);
		if (DIAS_PARA_PRECIO_MAYOR_A_TOPE == cantidadDeDias) {
			while (cantidadDeDias > 0) {
				if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
					cantidadDeDias--;
				}
				calendar.add(Calendar.DAY_OF_YEAR, 1);
			}
			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
					|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				calendar.add(Calendar.DAY_OF_YEAR, 1);
			}
		} else {
			calendar.add(Calendar.DAY_OF_YEAR, cantidadDeDias - 1);
		}
		return calendar.getTime();
	}

}
