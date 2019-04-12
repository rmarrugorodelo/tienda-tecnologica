package dominio.unitaria;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Test;

import dominio.Vendedor;
import dominio.fabrica.GenerarGarantiaExtendidaFactory;
import dominio.fabrica.GenerarGarantiaExtendidaMayorPrecioFactory;
import dominio.fabrica.GenerarGarantiaExtendidaMenorPrecioFactory;
import dominio.fabrica.IGenerarGarantiaExtendida;
import dominio.GarantiaExtendida;
import dominio.Producto;
import dominio.repositorio.RepositorioProducto;
import dominio.repositorio.RepositorioGarantiaExtendida;
import testdatabuilder.GarantiaTestDataBuilder;
import testdatabuilder.ProductoTestDataBuilder;

public class VendedorTest {
	private static final String CODIGO = "S0IU1H1AT51";
	private static final double PRECIO = 400000;
	private static final String FECHA_FIN = "2018-11-23";
	
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
		GarantiaExtendida garantiaExtendida = new GarantiaTestDataBuilder().conFechaFinGarantia(FECHA_FIN).build();
		GenerarGarantiaExtendidaFactory factory = new GenerarGarantiaExtendidaMenorPrecioFactory();
		IGenerarGarantiaExtendida garantiaExtendidaAbstract = factory.crear();
		Date fechaFin = garantiaExtendidaAbstract.obtenerfechaFinGarantia(garantiaExtendida.getFechaSolicitudGarantia());
		assertEquals(garantiaExtendida.getFechaFinGarantia(), fechaFin);
	}
	
	@Test
	public void generarFechaGarantiaMayorPrecioTest() {
		GarantiaExtendida garantiaExtendida = new GarantiaTestDataBuilder().build();
		GenerarGarantiaExtendidaFactory factory = new GenerarGarantiaExtendidaMayorPrecioFactory();
		IGenerarGarantiaExtendida garantiaExtendidaAbstract = factory.crear();
		Date fechaFin = garantiaExtendidaAbstract.obtenerfechaFinGarantia(garantiaExtendida.getFechaSolicitudGarantia());
		assertEquals(garantiaExtendida.getFechaFinGarantia(), fechaFin);
	}
	
	@Test
	public void generarObjetoGarantiaMayorPrecioTest() {
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoestDataBuilder.build(); 
		GarantiaExtendida garantiaExtendida = new GarantiaTestDataBuilder().build();
		GenerarGarantiaExtendidaFactory factory = new GenerarGarantiaExtendidaMayorPrecioFactory();
		IGenerarGarantiaExtendida garantiaExtendidaAbstract = factory.crear();
		garantiaExtendida = garantiaExtendidaAbstract.generaGarantiaExtendida(producto,garantiaExtendida.getNombreCliente());
		assertNotNull(garantiaExtendida);
	}
	@Test
	public void generarObjetoGarantiaMenorPrecioTest() {
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder().conPrecio(PRECIO);
		Producto producto = productoestDataBuilder.build(); 
		GarantiaExtendida garantiaExtendida = new GarantiaTestDataBuilder().build();
		GenerarGarantiaExtendidaFactory factory = new GenerarGarantiaExtendidaMenorPrecioFactory();
		IGenerarGarantiaExtendida garantiaExtendidaAbstract = factory.crear();
		garantiaExtendida = garantiaExtendidaAbstract.generaGarantiaExtendida(producto,garantiaExtendida.getNombreCliente());
		assertNotNull(garantiaExtendida);
	}
}
