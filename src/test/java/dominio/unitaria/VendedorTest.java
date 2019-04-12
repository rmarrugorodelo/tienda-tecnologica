package dominio.unitaria;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import dominio.Vendedor;
import dominio.factory.GarantiaExtendidaAbstract;
import dominio.factory.GarantiaExtendidaFactory;
import dominio.factory.GarantiaExtendidaMayorPrecioFactory;
import dominio.factory.GarantiaExtendidaMenorPrecioFactory;
import dominio.GarantiaExtendida;
import dominio.Producto;
import dominio.repositorio.RepositorioProducto;
import dominio.repositorio.RepositorioGarantiaExtendida;
import testdatabuilder.GarantiaTestDataBuilder;
import testdatabuilder.ProductoTestDataBuilder;

public class VendedorTest {
	private static final String CODIGO = "S0IU1H1AT51";
	private static final double PRECIO = 800000;
	private static final double DELTA = 0.001;
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
	public void generarGarantiaMayorPrecioTest() {
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoestDataBuilder.build(); 
		GarantiaExtendida garantiaExtendida = new GarantiaTestDataBuilder().build();
		GarantiaExtendidaFactory factory = new GarantiaExtendidaMayorPrecioFactory();
		GarantiaExtendidaAbstract garantiaExtendidaAbstract = factory.crear();
		garantiaExtendida = garantiaExtendidaAbstract.generaGarantiaExtendida(producto,garantiaExtendida.getNombreCliente());
		double precioGarantia = garantiaExtendidaAbstract.calcularPrecioGarantia(producto.getPrecio());
		assertEquals(precioGarantia, garantiaExtendida.getPrecioGarantia(),0.2);
	}
	@Test
	public void generarGarantiaMenorPrecioTest() {
		ProductoTestDataBuilder productoestDataBuilder = new ProductoTestDataBuilder().conPrecio(PRECIO);
		Producto producto = productoestDataBuilder.build(); 
		GarantiaExtendida garantiaExtendida = new GarantiaTestDataBuilder().build();
		GarantiaExtendidaFactory factory = new GarantiaExtendidaMenorPrecioFactory();
		GarantiaExtendidaAbstract garantiaExtendidaAbstract = factory.crear();
		garantiaExtendida = garantiaExtendidaAbstract.generaGarantiaExtendida(producto,garantiaExtendida.getNombreCliente());
	}
}
