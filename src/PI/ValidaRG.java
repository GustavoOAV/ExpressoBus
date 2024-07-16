package PI;

import javax.swing.JOptionPane;

// Classe para validar o RG
public class ValidaRG {
	public ValidaRG() {

	}

	public boolean validar(String s) {
		String digitoRG = s.substring(s.length() - 1);
		int soma = 0;
		int val = s.length();
		int resto = 0;
		int digito = 0;
		String digx = "";
		boolean valida = false;
		// percorre o RG
		for (int i = 0; i < s.length() - 1; i++) {
			String letra = "" + s.charAt(i);
			int n = Integer.parseInt(letra);
			soma += (val * n);
			val--;
		}
		resto = (soma % 11);
		digito = resto;
		if (digito == 10) {
			digx = "X";
		} else

		{
			digx = "" + digito;
		}

		if (digx.equalsIgnoreCase(digitoRG)) {
			valida = true;
		} else {
			JOptionPane.showMessageDialog(null, "RG Inválido!", "Erro de validação: ", JOptionPane.WARNING_MESSAGE);
		}
		return valida;
	}
}
