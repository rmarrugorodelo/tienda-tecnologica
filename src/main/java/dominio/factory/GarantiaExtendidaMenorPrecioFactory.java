package dominio.factory;

public class GarantiaExtendidaMenorPrecioFactory extends GarantiaExtendidaFactory {

	@Override
	protected GarantiaExtendidaAbstract creaGarantiaExtendida() {
		return new GarantiaExtendidaMenorPrecio();
	}

}
