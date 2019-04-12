package dominio.factory;

public class GarantiaExtendidaMayorPrecioFactory extends GarantiaExtendidaFactory {

	@Override
	protected GarantiaExtendidaAbstract creaGarantiaExtendida() {
		return new GarantiaExtendidaMayorPrecio();
	}

}
