public class CalculadoraFinanceira {

    public double calcularValorFuturo(double valorPresente, double taxaJuros, int periodos) {
        return valorPresente * Math.pow(1 + taxaJuros, periodos);
    }

    public double calcularValorPresente(double valorFuturo, double taxaJuros, int periodos) {
        return valorFuturo / Math.pow(1 + taxaJuros, periodos);
    }
}
