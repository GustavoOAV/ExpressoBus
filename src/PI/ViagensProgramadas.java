package PI;

//Importando as classes que serão utilizadas nessa tela
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Classe ViagensProgramadas é herança de JFrame e estende ActionListener
@SuppressWarnings("serial")
public class ViagensProgramadas extends JFrame implements ActionListener {

	// Instanciamento de objetos para criação de componentes e uso do SQL e checar a
	// data
	CriaComponentes cp = new CriaComponentes();
	EventosSQL sql = new EventosSQL();
	ChecaData cd = new ChecaData();

	// Atributo privado para salvar o id do cliente
	private int idCliente;

	// Instanciamento da cor de fundo e de objetos do tipo JFrame que serão
	// utilizados nessa tela
	Color corFundo = new Color(222, 222, 222), corBotoes = new Color(101, 101, 101);
	private JLabel textViagensProgramadas;
	private JPanel painelTexto, painelBotoes;
	private JTextArea areaTexto;
	private JButton botaoFinalizaViagem, botaoReagendarViagem, botaoCancelaViagem, botaoVoltar;
	private JScrollPane scroll;

	// Método construtor para construir a janela
	public ViagensProgramadas(int id) {
		// Seta id do cliente
		this.idCliente = id;

		// Definições de visualização da página
		setTitle("Viagens Programadas");
		setSize(750, 550);
		getContentPane().setBackground(corFundo);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		// Criação dos elementos gráficos através de métodos de uma classe criada apenas
		// para fazer componentes
		// Definindo a localização dos elementos através do método "setBounds"

		// Titulo na parte de cima da janela
		textViagensProgramadas = cp.criaJLabel("Viagens Programadas", Font.BOLD, 24);
		textViagensProgramadas.setBounds(235, 54, 300, 29);
		add(textViagensProgramadas);

		// Adicionando e ajustando o tamanho da imagem
		ImageIcon viagensIcon = new ImageIcon("Imagens/ViagemProgramada.png");
		viagensIcon.setImage(viagensIcon.getImage().getScaledInstance(43, 43, Image.SCALE_SMOOTH));
		JLabel viagensLabel = new JLabel(viagensIcon);
		viagensLabel.setBounds(487, 45, 50, 39);
		add(viagensLabel);

		// Painel onde ficarão as informações das viagens programadas
		painelTexto = cp.criaJPanel(corFundo);
		painelTexto.setBounds(166, 100, 400, 300);
		add(painelTexto);

		// Área de texto das informações
		areaTexto = cp.criaTextArea(sql.getPassagens(idCliente), corFundo);

		// Scroll do texto
		scroll = new JScrollPane(areaTexto);
		painelTexto.add(scroll);

		// Painel onde ficarão os botões
		painelBotoes = cp.criaJPanel(corFundo);
		painelBotoes.setLayout(new GridLayout(1, 3, 10, 0));
		painelBotoes.setBounds(75, 450, 600, 30);
		add(painelBotoes);

		// Botão para finalizar uma viagem
		botaoFinalizaViagem = cp.criaBotao("Finalizar Viagem", 'F', corBotoes, Color.white);
		painelBotoes.add(botaoFinalizaViagem);
		botaoFinalizaViagem.addActionListener(this);

		// Botão para finalizar uma viagem
		botaoReagendarViagem = cp.criaBotao("Reagendar", 'E', corBotoes, Color.white);
		painelBotoes.add(botaoReagendarViagem);
		botaoReagendarViagem.addActionListener(this);

		// Botao para cancelar viagem
		botaoCancelaViagem = cp.criaBotao("Cancelar Viagem", 'C', corBotoes, Color.white);
		painelBotoes.add(botaoCancelaViagem);
		botaoCancelaViagem.addActionListener(this);

		// Botao para voltar a Tela Inicial
		botaoVoltar = cp.criaBotao("Voltar", 'V', corBotoes, Color.white);
		painelBotoes.add(botaoVoltar);
		botaoVoltar.addActionListener(this);

		// Setando visibilidade e método de fechamento padrão
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// Sobrescrita do método actionPerformed
	@Override
	public void actionPerformed(ActionEvent e) {

		// Se o usuário clicar em Finalizar Viagem
		if (e.getSource() == botaoFinalizaViagem) {

			// Bloco try catch para tratar a excessão caso o usuário não insira o valor
			// numérico
			try {

				// Variável para guarar o ID da passagem
				int idPassagem = Integer
						.parseInt(JOptionPane.showInputDialog(null, "Informe o ID da passagem que deseja finalizar:"));

				// Se a passagem existe no banco de dados
				if (sql.checaIdPassagem(idPassagem, idCliente)) {

					// Pergunta para o usuário se a viagem foi completada
					if (JOptionPane.showConfirmDialog(null,
							"Confirma que o processo da sua viagem foi finalizado?") == 0) {

						// Coloca a passagem no histórico de passagens
						sql.cadastraHistorico(idPassagem, idCliente);

						// Remove a passagem da tabela de viagens programadas
						sql.remove(idPassagem, idCliente);

						// Atualiza a area do texto
						areaTexto.setText(sql.getPassagens(idCliente));

						// Agradece ao cliente
						JOptionPane.showMessageDialog(null,
								"Agradecemos por viajar conosco e esperamos te rever em breve!");

					}

				} else {

					JOptionPane.showMessageDialog(null, "ID inválido!", "Erro de ID:", JOptionPane.WARNING_MESSAGE);

				}

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "ID inválido!", "Erro de ID:", JOptionPane.WARNING_MESSAGE);

			}

		}

		// Se o usuário clicar em Editar
		if (e.getSource() == botaoReagendarViagem) {

			// Bloco try catch para tratar a excessão caso o usuário não insira o valor
			// numérico
			try {

				// Variável para guarar o ID da passagem
				int idPassagem = Integer
						.parseInt(JOptionPane.showInputDialog(null, "Informe o ID da passagem que deseja reagendar:"));

				// Checa se a passagem existe
				if (sql.checaIdPassagem(idPassagem, idCliente)) {
					
					// Se existe, checa se o usuário está dentro do prazo para editar a viagem
					if (cd.difDias(sql.getDataViagem(idPassagem, idCliente)) > 0) {
						
						// Escolhe nova data
						String[] datas = cd.proxDatas();
						Object escolha1 = JOptionPane.showInputDialog(null, "Escolha uma nova data: ", "Nova Data",
								JOptionPane.WARNING_MESSAGE, null, datas, datas);

						// Escolhe novo horário
						String[] horarios = { "08:00", "14:00", "20:00" };
						Object escolha2 = JOptionPane.showInputDialog(null, "Escolha um novo horário: ", "Novo Horário",
								JOptionPane.WARNING_MESSAGE, null, horarios, horarios);
						
						// Edita passagem
						sql.reagendaPassagem(escolha1 + "", escolha2 + "", idPassagem, idCliente);

						// Notifica que a passagem foi alterada
						JOptionPane.showMessageDialog(null, "Passagem reagendada com sucesso!");

						// Atualiza texto
						areaTexto.setText(sql.getPassagens(idCliente));

					} else {

						JOptionPane.showMessageDialog(null, "Prazo para reagendar a passagem excedido!",
								"Prazo excedido:", JOptionPane.WARNING_MESSAGE);

					}

				} else {

					JOptionPane.showMessageDialog(null, "ID inválido!", "Erro de ID:", JOptionPane.WARNING_MESSAGE);

				}

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "ID inválido!", "Erro de ID:", JOptionPane.WARNING_MESSAGE);

			}
		}

		// Se o usuário clica no botão de Cancelar Viagem
		if (e.getSource() == botaoCancelaViagem) {

			// Bloco try catch para tratar a excessão caso o usuário não insira o valor
			// numérico
			try {

				// Variável para guarar o ID da passagem
				int idPassagem = Integer
						.parseInt(JOptionPane.showInputDialog(null, "Informe o ID da passagem que deseja cancelar: "));

				// Se a passagem existe no banco de dados
				if (sql.checaIdPassagem(idPassagem, idCliente)) {

					// Verifica se o usuário ainda está dentro do prazo para cancelar a passagem
					if (cd.difDias(sql.getDataViagem(idPassagem, idCliente)) > 0) {

						// Confirme com o cliente se ele realmente gostaria de cancelar a passagem
						if (JOptionPane.showConfirmDialog(null,
								"Deseja mesmo cancelar a passagem de ID " + idPassagem + "?") == 0) {

							// Se sim, notifica que o valor será estornado em até 3 dias úteis
							JOptionPane.showMessageDialog(null, "O valor de " + sql.getPreco(idPassagem, idCliente)
									+ " será estornado para a sua conta em até 3 dias úteis.");

							// Remove a passagem da tabela de viagens programadas
							sql.remove(idPassagem, idCliente);

							// Atualiza o texto
							areaTexto.setText(sql.getPassagens(idCliente));

						}

						// O usuário é notificado se já tiver excedido o prazo para cancelar a compra
					} else {

						JOptionPane.showMessageDialog(null, "Prazo para cancelar a passagem excedido!",
								"Prazo excedido:", JOptionPane.WARNING_MESSAGE);

					}

				} else {

					JOptionPane.showMessageDialog(null, "ID inválido!", "Erro de ID:", JOptionPane.WARNING_MESSAGE);

				}

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(null, "ID inválido!", "Erro de ID:", JOptionPane.WARNING_MESSAGE);

			}

		}

		// Se o usuário clicar em voltar, ele volta para Tela Inicial
		if (e.getSource() == botaoVoltar) {
			new TelaInicial(idCliente);
			dispose();
		}

	}
}
