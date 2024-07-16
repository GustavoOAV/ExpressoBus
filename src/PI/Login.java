package PI;

// Importando as classes que serão utilizadas
import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Classe Login é herança de JFrame e estende ActionListener
@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener {
	
	// Instanciamento de objetos para criação de componentes e uso do SQL
	CriaComponentes cp = new CriaComponentes();
	EventosSQL sql = new EventosSQL();

	// Instanciamento da cor de fundo e de objetos do tipo JFrame que serão
	// utilizados nessa tela
	Color corFundo = new Color(222, 222, 222), corBotoes = new Color(101, 101, 101);
	private JButton botaoEntrar, botaoCriaConta;
	private JTextField inputEmail;
	private JPasswordField inputSenha;
	private JLabel textTitulo, textEmail, textSenha;

	// Método construtor para construir a janela
	public Login() {
		// Definições de visualização da página
		setTitle("Login");
		setSize(750, 550);
		getContentPane().setBackground(corFundo);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		// Criação dos elementos gráficos através de métodos de uma classe criada apenas
		// para fazer componentes
		// Definindo a localização dos elementos através do método "setBounds"
		
		// Titulo na parte de cima da janela
		textTitulo = cp.criaJLabel("ExpressoBus", Font.BOLD, 20);
		textTitulo.setBounds(275, 107, 218, 29);
		add(textTitulo);
		
		// Adicionando e ajustando o tamanho da imagem
		ImageIcon logoIcon = new ImageIcon("Imagens/ExpressoBus4.png");
		logoIcon.setImage(logoIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
		JLabel logoLabel = new JLabel(logoIcon);
		logoLabel.setBounds(375, 107, 105, 40); 
		add(logoLabel);
		
		// Texto do email
		textEmail = cp.criaJLabel("e-mail", Font.PLAIN + Font.ITALIC, 14);
		textEmail.setBounds(225, 176, 100, 17);
		add(textEmail);
		
		// Caixa de texto para inserir o email abaixo texto de email
		inputEmail = cp.criaTextField("Arial", Font.BOLD, 14);
		inputEmail.setBounds(225, 193, 300, 45);
		add(inputEmail);
		
		// Texto da senha
		textSenha = cp.criaJLabel("senha", Font.PLAIN + Font.ITALIC, 14);
		textSenha.setBounds(225, 254, 100, 17);
		add(textSenha);
		
		// Caixa de texto para inserir a senha abaixo do texto de senha
		inputSenha = cp.criaPassowdField("Arial", Font.BOLD, 14);
		inputSenha.setBounds(225, 271, 300, 45);
		add(inputSenha);
		
		// Botao para entrar na parte de baixo da jalela
		botaoEntrar = cp.criaBotao("Entrar", 'E', corBotoes, Color.white);
		botaoEntrar.setBounds(325, 364, 100, 30);
		botaoEntrar.addActionListener(this);
		add(botaoEntrar);
		
		// Botao Cria Conta imediatamente abaixo da caixa de texto da senha
		botaoCriaConta = cp.criaBotao("<html><i>Criar conta</i></html>", 'c', corFundo, Color.blue);
		botaoCriaConta.setBorder(null);
		botaoCriaConta.setBounds(440, 316, 100, 17);
		add(botaoCriaConta);
		botaoCriaConta.addActionListener(this);

		// Setando visibilidade e método de fechamento padrão
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	// Main por onde toda a aplicação iniciará
	public static void main(String[] args) {
		new Login();
	}

	// Sobrescrita do método actionPerformed para realizar as ações de acordo com a
	// escolha do usuário
	@Override
	public void actionPerformed(ActionEvent e) {

		// Salvando o email e senha informados pelo usuário em variáveis String para a
		// validação
		String inputEmailS = inputEmail.getText();
		char[] senhaChars = inputSenha.getPassword();
		String inputSenhaS = new String(senhaChars);

		// Caso o usuário clique no botão "Entrar"
		if (e.getSource() == botaoEntrar) {

			// Verifica se os campos de email e senha estão preenchidos
	        if (inputEmailS.length() != 0 && inputSenhaS.length() != 0) {
	            
	            // Se o método de validação retorna verdadeiro, procede com o login
	            if (sql.valida(inputEmailS, inputSenhaS)) {
	                // Se tudo estiver correto, vai para a Tela Inicial informando o ID do cliente
	                // (importante para realizar ações)
	                int id = sql.getIdCliente(inputEmailS);
	                new TelaInicial(id);
	                dispose();
	                
	            } else {
	                // Verifica se o email existe no banco de dados
	                boolean emailExiste = sql.emailExiste(inputEmailS);

	                // Se o email existe, mas a senha está incorreta
	                if (emailExiste) {
	                    JOptionPane.showMessageDialog(null, "Senha inválida! Tente novamente.", 
                                "Erro de login", JOptionPane.WARNING_MESSAGE);
	                } else {
	                	JOptionPane.showMessageDialog(null, "E-mail não encontrado. Crie uma conta.", 
                                "Erro de login", JOptionPane.WARNING_MESSAGE);
	                }

	                // Limpa os campos de entrada
	                inputEmail.setText("");
	                inputSenha.setText("");
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.", 
	                                          "Erro de login", JOptionPane.WARNING_MESSAGE);
	        }
		}

		// Se o usuário clicar em "Criar conta", ele será direcionado para a tela de
		// criação de conta
		if (e.getSource() == botaoCriaConta) {
			new CriaConta();
			dispose();
		}

	}
}
