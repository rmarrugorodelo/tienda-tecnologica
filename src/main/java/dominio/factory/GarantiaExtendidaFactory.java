package dominio.factory;

public abstract class GarantiaExtendidaFactory {
	
	public GarantiaExtendidaAbstract crear() {
		GarantiaExtendidaAbstract garantiaExtendida= creaGarantiaExtendida();
		return garantiaExtendida;
	}
	
	protected abstract GarantiaExtendidaAbstract creaGarantiaExtendida();
}
