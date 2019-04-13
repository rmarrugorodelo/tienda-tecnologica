package dominio.unitaria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Test;

import dominio.GarantiaExtendida;
import dominio.Producto;
import dominio.Vendedor;
import dominio.fabrica.GenerarGarantiaExtendidaFactory;
import dominio.fabrica.IGenerarGarantiaExtendida;
import dominio.repositorio.RepositorioGarantiaExtendida;
import dominio.repositorio.RepositorioProducto;
import testdatabuilder.GarantiaTestDataBuilder;
import testdatabuilder.ProductoTestDataBuilder;

public class VendedorTest {
	private static final double DELTA = 1e-15;
	private static final String CODIGO = "S0IU1H1AT51";
	private static final double PRECIO = 400000;
	private static final String FECHA_FIN = "2018-11-23";
	private static final double PRECIO_GARANTIA_MENOR_PRECIO = 40000;
	private static final double PRECIO_GARANTIA_MAYOR_PRECIO = 156000;
	
	@Test
	public void productoYaTieneGarantiaTest() {
		
		// arrange
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
		
		Producto producto = productoTestDataBuilder.build(); 
		
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(producto);
		
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
		
		// act 
		boolean existeProducto = vendedor.tieneGarantia(producto.getCodigo());
		
		//assert
		assertTrue(existeProducto);
	}
	
	@Test
	public void productoNoTieneGarantiaTest() {
		
		// arrange
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		
		Producto producto = productoestDataBuilder.build(); 
		
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		
		when(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo())).thenReturn(null);
		
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
		
		// act 
		boolean existeProducto =  vendedor.tieneGarantia(producto.getCodigo());
		
		//assert
		assertFalse(existeProducto);
	}
	
	@Test
	public void codigoDeProductoNoAplicaGarantiaTest() {
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder().conCodigo(CODIGO);
		Producto producto = productoestDataBuilder.build(); 
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
		boolean tieneTresVocales = vendedor.tieneTresVocales(producto.getCodigo());
		assertTrue(tieneTresVocales);
	}
	
	@Test
	public void codigoDeProductoAplicaGarantiaTest() {
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoestDataBuilder.build(); 
		RepositorioGarantiaExtendida repositorioGarantia = mock(RepositorioGarantiaExtendida.class);
		RepositorioProducto repositorioProducto = mock(RepositorioProducto.class);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
		boolean tieneTresVocales = vendedor.tieneTresVocales(producto.getCodigo());
		assertFalse(tieneTresVocales);
	}
	
	@Test
	public void generarFechaGarantiaMenorPrecioTest() {
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder().conPrecio(PRECIO);
		Producto producto = productoestDataBuilder.build(); 
		GarantiaExtendida garantiaExtendida = new GarantiaTestDataBuilder().conFechaFinGarantia(FECHA_FIN).build();
		GenerarGarantiaExtendidaFactory factory = new GenerarGarantiaExtendidaFactory();
		IGenerarGarantiaExtendida iGarantiaExtendida = factory.crear(producto);
		Date fechaFin = iGarantiaExtendida.obtenerfechaFinGarantia(garantiaExtendida.getFechaSolicitudGarantia());
		assertEquals(garantiaExtendida.getFechaFinGarantia(), fechaFin);
	}
	
	@Test
	public void generarFechaGarantiaMayorPrecioTest() {
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoestDataBuilder.build();
		GarantiaExtendida garantiaExtendida = new GarantiaTestDataBuilder().build();
		GenerarGarantiaExtendidaFactory factory = new GenerarGarantiaExtendidaFactory();
		IGenerarGarantiaExtendida iGarantiaExtendida = factory.crear(producto);
		Date fechaFin = iGarantiaExtendida.obtenerfechaFinGarantia(garantiaExtendida.getFechaSolicitudGarantia());
		assertEquals(garantiaExtendida.getFechaFinGarantia(), fechaFin);
	}
	
	@Test
	public void generarObjetoGarantiaMayorPrecioTest() {
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder().conCodigo(CODIGO);
		Producto producto = productoestDataBuilder.build(); 
		GarantiaExtendida garantiaExtendida = new GarantiaTestDataBuilder().build();
		GenerarGarantiaExtendidaFactory factory = new GenerarGarantiaExtendidaFactory();
		IGenerarGarantiaExtendida IgarantiaExtendida = factory.crear(producto);
		garantiaExtendida = IgarantiaExtendida.generaGarantiaExtendida(producto,garantiaExtendida.getNombreCliente());
		assertEquals(PRECIO_GARANTIA_MAYOR_PRECIO, garantiaExtendida.getPrecioGarantia(),DELTA);
	}
	@Test
	public void generarObjetoGarantiaMenorPrecioTest() {
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder().conPrecio(PRECIO);
		Producto producto = productoestDataBuilder.build(); 
		GarantiaExtendida garantiaExtendida = new GarantiaTestDataBuilder().build();
		GenerarGarantiaExtendidaFactory factory = new GenerarGarantiaExtendidaFactory();
		IGenerarGarantiaExtendida IgarantiaExtendida = factory.crear(producto);
		garantiaExtendida = IgarantiaExtendida.generaGarantiaExtendida(producto,garantiaExtendida.getNombreCliente());
		assertEquals(PRECIO_GARANTIA_MENOR_PRECIO, garantiaExtendida.getPrecioGarantia(),DELTA);
	}
}
