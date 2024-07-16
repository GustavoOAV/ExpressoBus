package PI;

//Importando as classes que serão utilizadas nessa tela
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Classe HistoricoPassagens é herança de JFrame e estende ActionListener
@SuppressWarnings("serial")
public class HistoricoPassagens extends JFrame implements ActionListener {

	// Instanciamento de objetos para criação de componentes e uso do SQL
	CriaComponentes cp = new CriaComponentes();
	EventosSQL sql = new EventosSQL();

	// Atributo privado para salvar o id do cliente
	private int idCliente;

	// Instanciamento da cor de fundo e de objetos do tipo JFrame que serão
	// utilizados nessa tela
	Color corFundo = new Color(222, 222, 222), corBotoes = new Color(101, 101, 101);
	private JLabel textHistoricoPassagens;
	private JPanel painelTexto;
	private JTextArea areaTexto;
	private JButton botaoVoltar;
	private JScrollPane scroll;

	// Método construtor para construir a janela
	public HistoricoPassagens(int id) {
		// Seta id do cliente
		this.idCliente = id;

		// Definições de visualização da página
		setTitle("Histórico de Passagens");
		setSize(750, 550);
		getContentPane().setBackground(corFundo);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		// Criação dos elementos gráficos através de métodos de uma classe criada apenas
		// para fazer componentes
		// Definindo a localização dos elementos através do método "setBounds"

		// Titulo na parte de cima da janela
		textHistoricoPassagens = cp.criaJLabel("Histórico de Passagens", Font.BOLD, 24);
		textHistoricoPassagens.setBounds(228, 48, 300, 29);
		add(textHistoricoPassagens);

		// Adicionando e ajustando o tamanho da imagem
		ImageIcon historicoIcon = new ImageIcon("Imagens/Historico.png");
		historicoIcon.setImage(historicoIcon.getImage().getScaledInstance(43, 43, Image.SCALE_SMOOTH));
		JLabel historicoLabel = new JLabel(historicoIcon);
		historicoLabel.setBounds(487, 45, 50, 39); 
		add(historicoLabel);

		
		// Painel onde ficarão as informações das viagens programadas
		painelTexto = cp.criaJPanel(corFundo);
		painelTexto.setBounds(166, 100, 400, 300);
		add(painelTexto);

		// Área de texto das informações
		areaTexto = cp.criaTextArea(sql.getHistorico(idCliente), corFundo);
		
		// Scroll do texto
		scroll = new JScrollPane(areaTexto);
		painelTexto.add(scroll);

		// Botao para voltar a Tela Inicial
		botaoVoltar = cp.criaBotao("Voltar", 'V', corBotoes, Color.WHITE);
		botaoVoltar.setBounds(315, 450, 120, 30);
		add(botaoVoltar);
		botaoVoltar.addActionListener(this);

		// Setando visibilidade e método de fechamento padrão
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// Sobrescrita do método actionPerformed
	@Override
	public void actionPerformed(ActionEvent e) {

		// Se o usuário clicar em voltar, ele volta para Tela Inicial
		if (e.getSource() == botaoVoltar) {
			new TelaInicial(idCliente);
			dispose();
		}

	}

}
