package PI;

//Importando as classes que serão utilizadas nessa tela
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

// Classe CompraPassagem é herança de JFrame e estende ActionListener e ItemListener
@SuppressWarnings("serial")
public class CompraPassagem extends JFrame implements ActionListener, ItemListener {

	// Instanciamento de objetos para criação de componentes e uso do SQL e geração
	// de número aleatorio
	CriaComponentes cp = new CriaComponentes();
	EventosSQL sql = new EventosSQL();
	ChecaData cd = new ChecaData();
	Random aleatorio = new Random();

	// Setando cor de fundo e botões
	Color corFundo = new Color(222, 222, 222), corBotoes = new Color(101, 101, 101);

	// Vetor com todos os assentos e o tipo de assento padrão
	String[] assentos = { "01-A", "01-B", "02-A", "02-B", "03-1", "03-B", "04-A", "04-B", "05-A", "05-B", "06-A",
			"06-B", "07-A", "07-B", "08-B", "09-A", "09-B", "10-A", "10-B", "11-A", "11-B", "12-A", "12-B", "13-A",
			"13-B", "14-A", "14-B", "15-A", "15-B" };
	String tipoAssento = "Comum";

	// Atributo privado para salvar o id do cliente
	private int idCliente;

	// Instanciamento da cor de fundo e de objetos do tipo JFrame que serão
	// utilizados nessa tela
	private JLabel textCompPassagem, textOrigem, textDestino, textTipoAssento, textData, textHorario, textAssento,
			textPreco, textPreco2;
	private JRadioButton rbuttonComum, rbuttonLeitoCama;
	private JComboBox<String> comboOrigem, comboDestino, comboData, comboHorario, comboAssento;
	private ButtonGroup grupoRadios = new ButtonGroup();
	private JButton botaoComprar, botaoVoltar;

	// Método construtor para construir a janela
	public CompraPassagem(int id) {
		// Setando id do cliente
		this.idCliente = id;

		// Definições de visualização da página
		setTitle("Compra de passagem");
		setSize(750, 550);
		getContentPane().setBackground(corFundo);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		// Criação dos elementos gráficos através de métodos de uma classe criada apenas
		// para fazer componentes
		// Definindo a localização dos elementos através do método "setBounds"

		// Titulo na parte de cima da janela
		textCompPassagem = cp.criaJLabel("Compra de Passagem", Font.BOLD, 24);
		textCompPassagem.setBounds(240, 54, 249, 29);
		add(textCompPassagem);
		
		// Adicionando e ajustando o tamanho da imagem
		ImageIcon passagemIcon = new ImageIcon("Imagens/Passagem.png");
		passagemIcon.setImage(passagemIcon.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH));
		JLabel passagemLabel = new JLabel(passagemIcon);
		passagemLabel.setBounds(495, 45, 50, 39); 
		add(passagemLabel);

		// Texto de origem
		textOrigem = cp.criaJLabel("Origem:", Font.PLAIN, 20);
		textOrigem.setBounds(149, 148, 100, 24);
		add(textOrigem);

		// Combo Box com os possíveis estados de origem
		String[] origemDestino = { "Espirito Santo", "Minas Gerais", "Paraná", "Rio de Janeiro", "Rio Grande do Sul",
				"Santa Catarina", "São Paulo" };
		comboOrigem = new JComboBox<String>(origemDestino);
		comboOrigem.setBounds(236, 140, 120, 40);
		comboOrigem.addItemListener(this);
		add(comboOrigem);

		// Texto de destino
		textDestino = cp.criaJLabel("Destino:", Font.PLAIN, 20);
		textDestino.setBounds(392, 148, 78, 24);
		add(textDestino);

		// Combo Box com os possíveis estados de destino
		comboDestino = new JComboBox<String>(origemDestino);
		comboDestino.setBounds(479, 140, 120, 40);
		comboDestino.addItemListener(this);
		add(comboDestino);

		// Texto da data
		textData = cp.criaJLabel("Data:", Font.PLAIN, 20);
		textData.setBounds(45, 305, 50, 24);
		add(textData);

		// Combo Box com as três próximas datas disponíveis
		String[] datas = cd.proxDatas();
		comboData = new JComboBox<String>(datas);
		comboData.setBounds(100, 296, 120, 40);
		add(comboData);
				
		// Texto dos assentos
		textAssento = cp.criaJLabel("Assento:", Font.PLAIN, 20);
		textAssento.setBounds(250, 305, 83, 24);
		add(textAssento);

		// Combo Box com os assentos disponíveis
		// Por padrão ele será colocado os 20 primeiro assentos para o tipo "Comum"
		comboAssento = new JComboBox<String>();
		for (int i = 0; i < assentos.length - 10; i++) {
			comboAssento.addItem(assentos[i]);
		}
		comboAssento.setBounds(340, 296, 120, 40);
		add(comboAssento);

		// Texto do horario
		textHorario = cp.criaJLabel("Horário: ", Font.PLAIN, 20);
		textHorario.setBounds(485, 305, 76, 24);
		add(textHorario);

		// Combo Box com os horários disponíveis
		String[] horarios = { "08:00", "14:00", "20:00" };
		comboHorario = new JComboBox<String>(horarios);
		comboHorario.setBounds(566, 296, 118, 40);
		add(comboHorario);

		// Texto do tipo de assento
		textTipoAssento = cp.criaJLabel("Tipo de assento:", Font.PLAIN, 20);
		textTipoAssento.setBounds(149, 228, 158, 24);
		add(textTipoAssento);

		// Os radio buttons com as opções "Comum" e "Leito Cama"
		rbuttonComum = cp.criaRadioButton("Comum", true, corFundo);
		rbuttonComum.setFont(new Font("Arial", Font.PLAIN, 18));
		rbuttonComum.setBounds(354, 231, 100, 20);
		rbuttonComum.addItemListener(this);
		grupoRadios.add(rbuttonComum);
		add(rbuttonComum);

		rbuttonLeitoCama = cp.criaRadioButton("Leito Cama", false, corFundo);
		rbuttonLeitoCama.setFont(new Font("Arial", Font.PLAIN, 18));
		rbuttonLeitoCama.setBounds(492, 231, 150, 20);
		rbuttonLeitoCama.addItemListener(this);
		grupoRadios.add(rbuttonLeitoCama);
		add(rbuttonLeitoCama);

		// Texto do preço
		textPreco = cp.criaJLabel("Preço: R$ ", Font.PLAIN, 20);
		textPreco.setBounds(322, 386, 118, 24);
		add(textPreco);

		// Texto do preço onde estará o valor
		textPreco2 = cp.criaJLabel("0.0", Font.PLAIN, 20);
		textPreco2.setBounds(418, 386, 100, 24);
		add(textPreco2);

		// Botão de comprar
		botaoComprar = cp.criaBotao("Comprar", 'C', corBotoes, Color.white);
		botaoComprar.setBounds(257, 449, 100, 30);
		add(botaoComprar);
		botaoComprar.addActionListener(this);

		// Botão de voltar
		botaoVoltar = cp.criaBotao("Voltar", 'V', corBotoes, Color.white);
		botaoVoltar.setBounds(412, 449, 100, 30);
		add(botaoVoltar);
		botaoVoltar.addActionListener(this);

		// Setando visibilidade e método de fechamento padrão
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// Sobrescrita do método actionPerformed
	@Override
	public void actionPerformed(ActionEvent e) {

		// Caso o usuário clique no botão voltar
		if (e.getSource() == botaoComprar) {

			// Se o usuário selecionar o mesmo local de origem e destino, ele é notificado
			if (comboOrigem.getSelectedItem().equals(comboDestino.getSelectedItem())) {

				JOptionPane.showMessageDialog(null, "Viagens para o mesmo estado indisponíveis.", "Erro de rota:",
						JOptionPane.WARNING_MESSAGE);

			} else {

				// Se não, ele é notificado com todas as informações da passagem e questiondo se
				// confirma a compra
				if (JOptionPane.showConfirmDialog(null,
						"Confirma a compra da passagem? \nORIGEM: " + comboOrigem.getSelectedItem() + "\nDESTINO: "
								+ comboDestino.getSelectedItem() + "\nDATA: " + comboData.getSelectedItem()
								+ "\nHORÁRIO: " + comboHorario.getSelectedItem() + "\nASSENTO: "
								+ comboAssento.getSelectedItem() + "\nTIPO DE ASSENTO: " + tipoAssento + "\nVALOR: R$ "
								+ textPreco2.getText()) == 0) {

					// Gera um valor aleatório para servir como código de pagamento
					int codigoPagamento = aleatorio.nextInt(900000000) + 100000000;

					// Verifica se o código já não existe, se existir, é gerado outro
					while (!sql.valida(idCliente, codigoPagamento)) {
					    codigoPagamento = aleatorio.nextInt(900000000) + 100000000;
					}
					
					// Cadastra a passagem no banco de dados
					sql.cadastraPassagem(codigoPagamento, idCliente, "" + comboOrigem.getSelectedItem(),
							"" + comboDestino.getSelectedItem(), "" + comboData.getSelectedItem(),
							"" + comboHorario.getSelectedItem(), "" + comboAssento.getSelectedItem(), tipoAssento,
							Double.parseDouble(textPreco2.getText()));

					// Notifica o usuário do código de pagamento
					JOptionPane.showMessageDialog(null, "Seu código para pagamento é: " + codigoPagamento);

					// Usuário é redirecionado a Tela Inicial
					new TelaInicial(idCliente);
					dispose();

				}

			}
		}

		// Se o usuário clicar em voltar, ele volta para a Tela Inicial
		if (e.getSource() == botaoVoltar) {
			new TelaInicial(idCliente);
			dispose();
		}

	}

	// Sobrescrita do método itemStateChanged
	@Override
	public void itemStateChanged(ItemEvent e) {
		// Define uma variável String e uma double para o preço
		String preco;
		double precoD;

		// Altera o preço exibido automaticamente conforme o usuário muda a origem ou o
		// destino
		textPreco2.setText(sql.getPreco("" + comboOrigem.getSelectedItem(), "" + comboDestino.getSelectedItem()));

		// Se o usuario seleciona a opção "Comum"
		if (e.getSource() == rbuttonComum) {

			// altera o valor do atributo tipoAssento
			tipoAssento = rbuttonComum.getText();

			// Remove todos os itens do Combo Box de assentos e logo em seguida preenche
			// novamente apenas com os assentos do tipo comum
			comboAssento.removeAllItems();

			for (int i = 0; i < assentos.length - 10; i++) {
				comboAssento.addItem(assentos[i]);
			}
			
		// Se o usuário seleciona apção "Leito Cama"
		} else if (e.getSource() == rbuttonLeitoCama) {

			// altera o valor do atributo tipoAssento
			tipoAssento = rbuttonLeitoCama.getText();

			// Remove todos os itens do Combo Box de assentos e logo em seguida preenche
						// novamente apenas com os assentos do tipo Leito Cama
			comboAssento.removeAllItems();

			for (int i = 19; i < assentos.length; i++) {
				comboAssento.addItem(assentos[i]);

			}
			
			// Exibe o preço apenas se a origem e o destino selecionados forem diferentes
			if (!comboOrigem.getSelectedItem().equals(comboDestino.getSelectedItem())) {

				preco = textPreco2.getText();
				precoD = Double.parseDouble(preco) + 100;
				textPreco2.setText(precoD + "");

			}
		}
	}
}

