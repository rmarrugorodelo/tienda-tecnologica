package dominio.fabrica;

public class GenerarGarantiaExtendidaMayorPrecioFactory extends GenerarGarantiaExtendidaFactory {

	@Override
	protected IGenerarGarantiaExtendida creaGarantiaExtendida() {
		return new GenerarGarantiaExtendidaMayorPrecio();
	}

}
