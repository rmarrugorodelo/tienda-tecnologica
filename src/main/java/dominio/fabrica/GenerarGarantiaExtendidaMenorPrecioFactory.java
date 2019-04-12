package dominio.fabrica;

public class GenerarGarantiaExtendidaMenorPrecioFactory extends GenerarGarantiaExtendidaFactory {

	@Override
	protected IGenerarGarantiaExtendida creaGarantiaExtendida() {
		return new GenerarGarantiaExtendidaMenorPrecio();
	}

}
