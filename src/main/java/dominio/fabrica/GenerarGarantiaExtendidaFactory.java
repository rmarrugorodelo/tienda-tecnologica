package dominio.fabrica;

public abstract class GenerarGarantiaExtendidaFactory {
	
	public IGenerarGarantiaExtendida crear() {
		IGenerarGarantiaExtendida garantiaExtendida= creaGarantiaExtendida();
		return garantiaExtendida;
	}
	
	protected abstract IGenerarGarantiaExtendida creaGarantiaExtendida();
}
