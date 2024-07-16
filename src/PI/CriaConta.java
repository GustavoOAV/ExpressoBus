package PI;

// Importando as classes que serão utilizadas nessa tela
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Classe CriaConta é herança de JFrame e estende ActionListener
@SuppressWarnings("serial")
public class CriaConta extends JFrame implements ActionListener {

	// Instanciamento de objetos para criação de componentes, ações do SQL e
	// validação de RG
	CriaComponentes cp = new CriaComponentes();
	EventosSQL sql = new EventosSQL();
	ValidaRG vrg = new ValidaRG();

	// Instanciamento da cor de fundo e de objetos do tipo JFrame que serão
	// utilizados nessa tela
	Color corFundo = new Color(222, 222, 222), corBotoes = new Color(101, 101, 101);
	private JPanel painelTextCampos, painelInputsCampos;
	private JLabel textCriandoConta, textNome, textRg, textTelefone, textEmail, textSenha;
	private JTextField inputNome, inputRg, inputTelefone, inputEmail, inputSenha;
	private JButton botaoCriar, botaoVoltar;

	// Método construtor para construir a janela
	public CriaConta() {
		// Definições de visualização da página
		setTitle("Criar conta");
		setSize(750, 550);
		getContentPane().setBackground(corFundo);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		// Criação dos elementos gráficos através de métodos de uma classe criada apenas
		// para fazer componentes
		// Definindo a localização dos elementos através do método "setBounds"

		// Titulo na parte de cima da janela
		textCriandoConta = cp.criaJLabel("Criando sua Conta", Font.BOLD, 24);
		textCriandoConta.setBounds(252, 54, 300, 29);
		add(textCriandoConta);
		

		// Adicionando e ajustando o tamanho da imagem
		ImageIcon historicoIcon = new ImageIcon("Imagens/CriandoConta.png");
		historicoIcon.setImage(historicoIcon.getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH));
		JLabel historicoLabel = new JLabel(historicoIcon);
		historicoLabel.setBounds(467, 45, 50, 39); 
		add(historicoLabel);


		// Painel com layout do tipo grid onde ficarão os textos da janela
		painelTextCampos = cp.criaJPanel(corFundo);
		painelTextCampos.setLayout(new GridLayout(5, 1, 0, 47));
		painelTextCampos.setBounds(166, 128, 86, 295);
		add(painelTextCampos);

		// Texto do nome
		textNome = cp.criaJLabel("Nome:", Font.PLAIN, 20);
		painelTextCampos.add(textNome);

		// Texto do RG
		textRg = cp.criaJLabel("RG:", Font.PLAIN, 20);
		painelTextCampos.add(textRg);

		// Texto do telefone
		textTelefone = cp.criaJLabel("Telefone:", Font.PLAIN, 20);
		painelTextCampos.add(textTelefone);

		// Texto do email
		textEmail = cp.criaJLabel("E-mail:", Font.PLAIN, 20);
		painelTextCampos.add(textEmail);

		// Texto da senha
		textSenha = cp.criaJLabel("Senha:", Font.PLAIN, 20);
		painelTextCampos.add(textSenha);

		// Painel com layout do tipo grid para colocar os campos de entrada de texto
		painelInputsCampos = cp.criaJPanel(corFundo);
		painelInputsCampos.setLayout(new GridLayout(5, 1, 0, 33));
		painelInputsCampos.setBounds(262, 120, 316, 306);
		add(painelInputsCampos);
		
		// Entrada do nome
		inputNome = cp.criaTextField("Arial", Font.PLAIN, 14);
		painelInputsCampos.add(inputNome);
		
		// Entrada do RG
		inputRg = cp.criaTextField("Arial", Font.PLAIN, 14);
		painelInputsCampos.add(inputRg);
		
		// Entrada do telefone
		inputTelefone = cp.criaTextField("Arial", Font.PLAIN, 14);
		painelInputsCampos.add(inputTelefone);

		// Entrada do Email
		inputEmail = cp.criaTextField("Arial", Font.PLAIN, 14);
		painelInputsCampos.add(inputEmail);
		
		// Etrada da senha
		inputSenha = cp.criaPassowdField("Arial", Font.PLAIN, 14);
		painelInputsCampos.add(inputSenha);
		
		// Botão para Criar Conta localizado na parte de baixo da janela
		botaoCriar = cp.criaBotao("Criar", 'C', corBotoes, Color.white);
		botaoCriar.setBounds(286, 465, 100, 30);
		add(botaoCriar);
		botaoCriar.addActionListener(this);
		
		// Botão voltar logo ao lado do botão Criar
		botaoVoltar = cp.criaBotao("Voltar", 'V', corBotoes, Color.white);
		botaoVoltar.setBounds(414, 465, 100, 30);
		add(botaoVoltar);
		botaoVoltar.addActionListener(this);

		// Setando visibilidade e método de fechamento padrão
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// Sobrescrita do método actionPerformed para realizar as ações de acordo com a
	// escolha do usuário
	@Override
	public void actionPerformed(ActionEvent e) {

		// Salva todas as informações inseridas pelo usuário para salvar no banco de
		// dados
		String inputNomeS = inputNome.getText(), inputRgS = inputRg.getText(), inputTelefoneS = inputTelefone.getText(),
				inputEmailS = inputEmail.getText();
		// Convertendo o que foi digitado no PasswordField para String
		char[] senhaChars = ((JPasswordField) inputSenha).getPassword();
		String inputSenhaS = new String(senhaChars);

		// Se o usuário clicar no botao "Criar Conta"
		if (e.getSource() == botaoCriar) {

			// Se algum campo não estiver preenchido, o usuário é notificado e orientado a
			// preencher todos os campos
			if (inputNomeS.length() == 0 || inputRgS.length() == 0 || inputTelefoneS.length() == 0
					|| inputEmailS.length() == 0 || inputSenhaS.length() == 0) {

				JOptionPane.showMessageDialog(null, "Preencha todos os campos.");

			} else {

				// Bloco try catch para tratar a excessão se o usuário colocar caracteres
				// inválidos no campo RG
				try {

					// Se o retorno do método de válidar RG for verdadeiro, segue com o cadastro
					if (vrg.validar(inputRgS)) {

						// Método criado para cadastrar clientes
						// Após a criação de conta, o usuário é redirecionado para a tela de Login
						sql.cadastra(inputNomeS, inputRgS, inputTelefoneS, inputEmailS, inputSenhaS);
						JOptionPane.showMessageDialog(null, "Conta Criada!");
						new Login();
						dispose();

					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Digite apenas números no campo do RG.", "Erro de validação:",
							JOptionPane.WARNING_MESSAGE);
				}

			}

		}

		// Se o usuário clicar no botão "Voltar", ele volta para a tela de Login
		if (e.getSource() == botaoVoltar) {
			new Login();
			dispose();
		}

	}

}
