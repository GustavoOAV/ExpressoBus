package PI;

import javax.swing.*;

import java.awt.*;

@SuppressWarnings("serial")
public class CriaComponentes extends JFrame {

	public CriaComponentes() {

	}

	/* --- BOTÕES --- */

	public JButton criaBotao(String texto, char atalho) {
		JButton jb = new JButton(texto);
		jb.setMnemonic(atalho);

		return jb;
	}

	public JButton criaBotao(String texto, char atalho, Color cor, Color corTexto) {
		JButton jb = new JButton(texto);

		jb.setMnemonic(atalho);
		jb.setBackground(cor);
		jb.setForeground(corTexto);

		return jb;
	}
	
	public JButton sair(String texto, char atalho, Color cor, Color corTexto, ImageIcon sair) {
	    JButton jb = new JButton(texto);

	    jb.setMnemonic(atalho);
	    jb.setBackground(cor);
	    jb.setForeground(corTexto);
	    jb.setIcon(sair);

	    return jb;
	}
	
	public JButton perfil(String texto, char atalho, Color cor, Color corTexto, ImageIcon perfil2) {
	    JButton jb = new JButton(texto);

	    jb.setMnemonic(atalho);
	    jb.setBackground(cor);
	    jb.setForeground(corTexto);
	    jb.setIcon(perfil2);

	    return jb;
	}

	/* --- TEXTFIELDS --- */

	public JTextField criaTextField(String font, int fonte, int tamanho) {
		JTextField jt = new JTextField();
		jt.setFont(new Font(font, fonte, tamanho));
		return jt;
	}

	/* --- PASSWORDFIELDS --- */

	public JPasswordField criaPassowdField(String font, int fonte, int tamanho) {
		JPasswordField jp = new JPasswordField();
		jp.setFont(new Font(font, fonte, tamanho));
		return jp;
	}

	/* --- LABELS --- */

	public JLabel criaJLabel(String texto, int fonte, int tamanho) {
		JLabel jl = new JLabel(texto);

		jl.setFont(new Font("Arial", fonte, tamanho));

		return jl;
	}

	/* --- PANELS --- */

	public JPanel criaJPanel(Color cor) {
		JPanel jp = new JPanel();
		jp.setBackground(cor);
		return jp;
	}

	/* --- RADIOS BUTTONS --- */

	public JRadioButton criaRadioButton(String texto, boolean cond, Color corFundo) {
		JRadioButton jrb = new JRadioButton(texto, cond);
		jrb.setBackground(corFundo);
		return jrb;
	}

	/* --- TEXT AREAS --- */

	public JTextArea criaTextArea(String texto, Color cor) {
		JTextArea txa = new JTextArea(18, 30);
		txa.setText(texto);
		txa.setBackground(cor);
		txa.setEditable(false);
		return txa;
	}

	
}
