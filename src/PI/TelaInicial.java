package PI;

//Importando as classes que serão utilizadas nessa tela
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
// Classe Tela Inicial é herença de JFrame e estende o ActionListener 
public class TelaInicial extends JFrame implements ActionListener {

	// Instanciamento de objetos para criação de componentes e uso do SQL
	CriaComponentes cp = new CriaComponentes();
	EventosSQL sql = new EventosSQL();

	// Atributo privado para salvar o id do cliente
	private int idCliente;

	// Instanciamento da cor de fundo e de objetos do tipo JFrame que serão
	// utilizados nessa tela
	Color corFundo = new Color(222, 222, 222), corBotoes = new Color(101, 101, 101);
	private JLabel textMenuPrincial;
	private JButton botaoCompraPassagem, botaoViagensProgramadas, botaoHistoricoPassagens, botaoPerfil, botaoSair;
	private JPanel painelBotoesPassagens, painelBotoesAcoes;

	// Método construtor para construir a janela
	public TelaInicial(int id) {
		// Seta o id do cliente
		this.idCliente = id;

		// Definições de visualização da página
		setTitle("Tela inicial");
		setSize(750, 550);
		getContentPane().setBackground(corFundo);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		// Criação dos elementos gráficos através de métodos de uma classe criada apenas
		// para fazer componentes
		// Definindo a localização dos elementos através do método "setBounds"

		// Titulo na parte de cima da janela
		textMenuPrincial = cp.criaJLabel("Menu Principal", Font.BOLD, 24);
		textMenuPrincial.setBounds(291, 52, 300, 29);
		add(textMenuPrincial);
		
		// Adicionando e ajustando o tamanho da imagem
		ImageIcon menuIcon = new ImageIcon("Imagens/Menu.png");
		menuIcon.setImage(menuIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		JLabel menuLabel = new JLabel(menuIcon);
		menuLabel.setBounds(460, 45, 45, 39); 
		add(menuLabel);

		// Painel com layout do tipo grid para colocar os botões relacionados as
		// passagens
		painelBotoesPassagens = cp.criaJPanel(corFundo);
		painelBotoesPassagens.setLayout(new GridLayout(3, 1, 0, 30));
		painelBotoesPassagens.setBounds(167, 118, 413, 243);
		add(painelBotoesPassagens);

		// Botão para comprar passagem
		botaoCompraPassagem = cp.criaBotao("Comprar Passagem", 'C', corBotoes, Color.white);
		botaoCompraPassagem.setFont(new Font("Arial", Font.PLAIN, 30));
		painelBotoesPassagens.add(botaoCompraPassagem);
		botaoCompraPassagem.addActionListener(this);

		// Botão para ver viagens programadas
		botaoViagensProgramadas = cp.criaBotao("Viagens Programadas", 'V', corBotoes, Color.white);
		botaoViagensProgramadas.setFont(new Font("Arial", Font.PLAIN, 30));
		painelBotoesPassagens.add(botaoViagensProgramadas);
		botaoViagensProgramadas.addActionListener(this);
		
		// Botão para ver o histórico de passagens
		botaoHistoricoPassagens = cp.criaBotao("Histórico de Passagens", 'H', corBotoes, Color.white);
		botaoHistoricoPassagens.setFont(new Font("Arial", Font.PLAIN, 30));
		painelBotoesPassagens.add(botaoHistoricoPassagens);
		botaoHistoricoPassagens.addActionListener(this);
		
		// Painel com layout do tipo de grid para colocar os botões de perfil e sair
		painelBotoesAcoes = cp.criaJPanel(corFundo);
		painelBotoesAcoes.setLayout(new GridLayout(1, 2, 117, 0));
		painelBotoesAcoes.setBounds(170, 443, 411, 44);
		add(painelBotoesAcoes);
			
		// Adicionando imagem no botão Perfil
		ImageIcon perfil2 = new ImageIcon("Imagens/Perfil2.png");
		perfil2.setImage(perfil2.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		botaoPerfil = cp.perfil("Perfil", 'P', corBotoes, Color.white, perfil2);
		botaoPerfil.setFont(new Font("Arial", Font.PLAIN, 22));
		botaoPerfil.setHorizontalTextPosition(SwingConstants.LEFT); 
		painelBotoesAcoes.add(botaoPerfil);
		botaoPerfil.addActionListener(this);
		
		// Adicionando imagem no botão Sair
		ImageIcon sair = new ImageIcon("Imagens/Sair.png");
		sair.setImage(sair.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		botaoSair = cp.sair("Sair", 'S', corBotoes, Color.white, sair);
		botaoSair.setFont(new Font("Arial", Font.PLAIN, 22));
		botaoSair.setHorizontalTextPosition(SwingConstants.LEFT);
		painelBotoesAcoes.add(botaoSair);
		botaoSair.addActionListener(this);

		// Setando visibilidade e método de fechamento padrão
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// Sobrescrita do método actionPerformed para realizar as ações de acordo com a
	// escolha do usuário
	@Override
	public void actionPerformed(ActionEvent e) {
		// Direcionando o cliente para tela de acordo com a escolha dele
		if (e.getSource() == botaoCompraPassagem) {
			new CompraPassagem(idCliente);
			dispose();
		}

		if (e.getSource() == botaoViagensProgramadas) {
			new ViagensProgramadas(idCliente);
			dispose();
		}

		if (e.getSource() == botaoHistoricoPassagens) {
			new HistoricoPassagens(idCliente);
			dispose();
		}

		if (e.getSource() == botaoPerfil) {
			new Perfil(idCliente);
			dispose();
		}

		if (e.getSource() == botaoSair) {
			new Login();
			dispose();
		}

	}
}
