package PI;

//Importando as classes que serão utilizadas nessa tela
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Classe Perfil é herança de JFrame e estende ActionListener
@SuppressWarnings("serial")
public class Perfil extends JFrame implements ActionListener {

	// Instanciamento de objetos para criação de componentes e uso do SQL
	CriaComponentes cp = new CriaComponentes();
	EventosSQL sql = new EventosSQL();

	// Atributo privado para salvar o id do cliente
	private int idCliente;

	// Instanciamento da cor de fundo e de objetos do tipo JFrame que serão
	// utilizados nessa tela
	Color corFundo = new Color(222, 222, 222), corBotoes = new Color(101, 101, 101);
	private JPanel painelTextCampos, painelInputsCampos;
	private JLabel textCriandoConta, textNome, textRg, textTelefone, textEmail, textSenha;
	private JTextField inputNome, inputRg, inputTelefone, inputEmail, inputSenha;
	private JButton botaoEditar, botaoVoltar, botaoConfirma;
	private JPasswordField senha;
	private JFrame confirma;

	// Método construtor para construir a janela
	public Perfil(int id) {
		// Seta id do cliente
		this.idCliente = id;

		// Definições de visualização da página
		setTitle("Perfil");
		setSize(750, 550);
		getContentPane().setBackground(corFundo);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		// Criação dos elementos gráficos através de métodos de uma classe criada apenas
		// para fazer componentes
		// Definindo a localização dos elementos através do método "setBounds"

		// Titulo na parte de cima da janela
		textCriandoConta = cp.criaJLabel("Perfil", Font.BOLD, 24);
		textCriandoConta.setBounds(345, 54, 300, 29);
		add(textCriandoConta);
		
		// Adicionando e ajustando o tamanho da imagem
		ImageIcon perfilIcon = new ImageIcon("Imagens/Perfil.png");
		perfilIcon.setImage(perfilIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		JLabel perfilLabel = new JLabel(perfilIcon);
		perfilLabel.setBounds(405, 50, 50, 39); 
		add(perfilLabel);

		// Painel com layout do tipo grid para colocar os textos
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

		// Painel com layout do tipo grid para colocar os campos de entrada
		painelInputsCampos = cp.criaJPanel(corFundo);
		painelInputsCampos.setLayout(new GridLayout(5, 1, 0, 33));
		painelInputsCampos.setBounds(262, 120, 316, 306);
		add(painelInputsCampos);

		// Entrada do nome
		inputNome = cp.criaTextField("Arial", Font.PLAIN, 14);
		inputNome.setText(sql.getInfo(0, idCliente));
		painelInputsCampos.add(inputNome);

		// Entrada do RG
		inputRg = cp.criaTextField("Arial", Font.PLAIN, 14);
		inputRg.setText(sql.getInfo(1, idCliente));
		painelInputsCampos.add(inputRg);

		// Entrada do Telefone
		inputTelefone = cp.criaTextField("Arial", Font.PLAIN, 14);
		inputTelefone.setText(sql.getInfo(2, idCliente));
		painelInputsCampos.add(inputTelefone);

		// Entrada do Email
		inputEmail = cp.criaTextField("Arial", Font.PLAIN, 14);
		inputEmail.setText(sql.getInfo(3, idCliente));
		painelInputsCampos.add(inputEmail);

		// Entrada da senha
		inputSenha = cp.criaPassowdField("Arial", Font.PLAIN, 14);
		painelInputsCampos.add(inputSenha);

		// Botão para editar o perfil
		botaoEditar = cp.criaBotao("Editar", 'E', corBotoes, Color.white);
		botaoEditar.setBounds(286, 450, 100, 30);
		add(botaoEditar);
		botaoEditar.addActionListener(this);

		// Botão para voltar a tela inicial
		botaoVoltar = cp.criaBotao("Voltar", 'V', corBotoes, Color.white);
		botaoVoltar.setBounds(414, 450, 100, 30);
		add(botaoVoltar);
		botaoVoltar.addActionListener(this);

		// Setando visibilidade e método de fechamento padrão
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// Sobrescrita do método actionPerformed
	@Override
	public void actionPerformed(ActionEvent e) {

		// Salvando as informações em variáveis para colocar no banco de dados
		String inputNomeS = inputNome.getText(), inputRgS = inputRg.getText(), inputTelefoneS = inputTelefone.getText(),
				inputEmailS = inputEmail.getText();
		char[] senhaChars = ((JPasswordField) inputSenha).getPassword();
		String inputSenhaS = new String(senhaChars);

		// Se o usuário clicar no botão editar
		if (e.getSource() == botaoEditar) {

			// Se algum campo não está preenchido, o usuário será notificado
			if (inputNomeS.length() == 0 || inputRgS.length() == 0 || inputTelefoneS.length() == 0
					|| inputEmailS.length() == 0 || inputSenhaS.length() == 0) {

				JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos.");

				// Se não, altera as informações
			} else {

				// Solicita que o usuário insira a senha para confirmar as alterações
				confirma = new JFrame();
				confirma.setSize(300, 200);
				confirma.setLayout(null);
				JLabel textConfirma = new JLabel("Insira sua senha para confirmar as alterações");
				textConfirma.setBounds(5, 20, 300, 30);
				confirma.add(textConfirma);
				senha = new JPasswordField();
				senha.setBounds(5, 60, 250, 30);
				botaoConfirma = new JButton("Confirmar");
				botaoConfirma.setBounds(5, 100, 100, 30);
				botaoConfirma.addActionListener(this);
				confirma.add(botaoConfirma);
				confirma.add(senha);
				confirma.setResizable(false);
				confirma.setLocationRelativeTo(null);
				confirma.setVisible(true);

			}
		}

		// Após o usuário clicar a confirmar alterações
		if (e.getSource() == botaoConfirma) {

			// Converte a senha para String
			char[] senhaConfirmaChars = senha.getPassword();
			String senhaConfima = new String(senhaConfirmaChars);

			// Se a senha estiver correta, as informações são alteradas
			if (senhaConfima.length() != 0 && sql.valida(senhaConfima, idCliente)) {

				sql.editaPerfil(inputNomeS, inputRgS, inputTelefoneS, inputEmailS, inputSenhaS, idCliente);
				confirma.dispose();
				JOptionPane.showMessageDialog(null, "Informações alteradas!");
				inputNome.setText(sql.getInfo(0, idCliente));
				inputRg.setText(sql.getInfo(1, idCliente));
				inputTelefone.setText(sql.getInfo(2, idCliente));
				inputEmail.setText(sql.getInfo(3, idCliente));

			} else {

				JOptionPane.showMessageDialog(null, "Senha inválida!");

			}
		}

		// Se o usuário clicar em voltar, ele volta para a Tela Inicial
		if (e.getSource() == botaoVoltar) {
			new TelaInicial(idCliente);
			dispose();
		}

	}
}
