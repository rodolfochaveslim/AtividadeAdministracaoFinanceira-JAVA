import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal {

    private JFrame janela;
    private JTextField txtValor, txtTaxaJuros, txtPeriodos;
    private JLabel lblResultado;
    private JComboBox<String> cbTipoCalculo;
    private CalculadoraFinanceira calculadora;

    public JanelaPrincipal() {
        calculadora = new CalculadoraFinanceira(); // Instância da lógica de cálculo
        configurarJanela();
        criarComponentes();
    }

    private void configurarJanela() {
        janela = new JFrame("Calculadora Financeira");
        janela.setSize(400, 450);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(new BorderLayout());
    }

    private void criarComponentes() {
        // Painel de entrada de dados
        JPanel painelEntrada = new JPanel(new GridLayout(4, 2, 5, 5));
        painelEntrada.setBorder(BorderFactory.createTitledBorder("Dados de Entrada"));

        txtValor = criarCampoEntrada(painelEntrada, "Valor (VP ou VF):");
        txtTaxaJuros = criarCampoEntrada(painelEntrada, "Taxa de Juros (%):");
        txtPeriodos = criarCampoEntrada(painelEntrada, "Períodos:");

        // Tipo de cálculo (VP ou VF)
        painelEntrada.add(new JLabel("Tipo de Cálculo:"));
        cbTipoCalculo = new JComboBox<>(new String[]{"Valor Futuro (VF)", "Valor Presente (VP)"});
        painelEntrada.add(cbTipoCalculo);

        janela.add(painelEntrada, BorderLayout.NORTH);

        // Painel de resultado
        JPanel painelResultado = new JPanel();
        painelResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));
        lblResultado = new JLabel("");
        painelResultado.add(lblResultado);
        janela.add(painelResultado, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnCalcular = new JButton("Calcular");
        painelBotoes.add(btnCalcular);

        btnCalcular.addActionListener(e -> calcular());
        janela.add(painelBotoes, BorderLayout.SOUTH);
    }

    private JTextField criarCampoEntrada(JPanel painel, String label) {
        painel.add(new JLabel(label));
        JTextField campo = new JTextField();
        painel.add(campo);
        return campo;
    }

    private void calcular() {
        try {
            double valor = Double.parseDouble(txtValor.getText());
            double taxaJuros = Double.parseDouble(txtTaxaJuros.getText()) / 100;
            int periodos = Integer.parseInt(txtPeriodos.getText());
            String tipoCalculo = (String) cbTipoCalculo.getSelectedItem();
            double resultado;

            if ("Valor Futuro (VF)".equals(tipoCalculo)) {
                resultado = calculadora.calcularValorFuturo(valor, taxaJuros, periodos);
                lblResultado.setText(formatarResultado("Valor Futuro", resultado, "Valor Presente", valor, taxaJuros, periodos));
            } else {
                resultado = calculadora.calcularValorPresente(valor, taxaJuros, periodos);
                lblResultado.setText(formatarResultado("Valor Presente", resultado, "Valor Futuro", valor, taxaJuros, periodos));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(janela, "Por favor, insira valores numéricos válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatarResultado(String tipo, double resultado, String valorTipo, double valor, double taxaJuros, int periodos) {
        return String.format("<html>%s: R$ %.2f<br>Dados utilizados:<br>%s: R$ %.2f<br>Taxa de Juros: %.2f%%<br>Períodos: %d</html>",
                tipo, resultado, valorTipo, valor, taxaJuros * 100, periodos);
    }

    public void exibir() {
        janela.setVisible(true);
    }
}
