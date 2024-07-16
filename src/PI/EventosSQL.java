package PI;

//Importando classe do SQL
import java.sql.*;

// Classe para tratar os eventos SQL, herenaça da classe de conexão com o SQL
public class EventosSQL extends ConectaMySQL {

	// Objetos que serão utilizados nessa classe
	private Connection cn;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement ps;

	// Construtor da classe
	public EventosSQL() {
		super();
		// herda a conexão da classe pai
		this.cn = openDB();
		this.st = null;
	}

	// Método que verifica se o e-mail existe no banco de dados
	public boolean emailExiste(String emailClientes) {
		boolean existe = false;
		String query = "SELECT COUNT(*) FROM clientes WHERE emailClientes = ?";

		try {
			// Declaração sql
			ps = cn.prepareStatement(query);
			// Define o email como parâmetro na consulta
			ps.setString(1, emailClientes);
			// Informa qual comando será executado
			rs = ps.executeQuery();
			// Verifica se há algum resultado na consulta e se existe pelo menos uma
			// ocorrência do e-mail na tabela
			if (rs.next() && rs.getInt(1) > 0) {
				// Se sim, define que o email existe
				existe = true;
			}
		} catch (SQLException e) {
			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();
		}
		return existe;
	}

	// Método para validar o login do usuário
	// recebendo como parametro o email e a senha
	public boolean valida(String usuario, String senha) {

		// Variaveis que serão utilizadas na comparação
		String usuarioBD = "";
		String senhaBD = "";

		// Bloco try catch para capturar qualquer erro de conexao
		try {

			// Cria o Statement
			st = cn.createStatement();

			// Informa qual comando será executado
			rs = st.executeQuery(
					"SELECT emailClientes, senhaClientes FROM clientes WHERE emailClientes LIKE '%" + usuario + "%'");

			// Guarda os valores presentes nos campos de usuário e senha da tabela cliente
			while (rs.next()) {
				usuarioBD = rs.getString("emailClientes");
				senhaBD = rs.getString("senhaClientes");
			}

			// Se os resultados forem iguais, retorna true
			if (usuario.equalsIgnoreCase(usuarioBD) && senha.equals(senhaBD)) {
				return true;
			}

		} catch (Exception e) {
			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();
		}

		// Se os resultados forem diferentes, retorna false
		return false;
	}

	// Sobrecarga do método valida
	// Recebendo a senha e o id do cliente, validará a edição de perfil
	public boolean valida(String senha, int id) {

		// Variável para guardar a senha do banco de dados
		String senhaBD = "";

		try {

			// Cria Statement
			st = cn.createStatement();

			// Comando que será executado
			rs = st.executeQuery("SELECT senhaClientes FROM clientes WHERE idClientes = " + id);

			// Guarda o valor presente no campo senha da tabela cliente
			while (rs.next()) {
				senhaBD = rs.getString("senhaClientes");
			}

			// Se a senha informada for igual a do Banco de Dados, retorna true
			if (senha.equals(senhaBD)) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();
		}

		// Se a senha for diferente, retorna false
		return false;
	}

	// Sobrecarga do método valida
	// Recebendo id do cliente e código de pagamento, verifica se o código já não
	// existe
	public boolean valida(int id, int codigo) {

		// Variavel para salvar o código presente no banco de dados
		int codigoBD;

		try {

			// Cria Statement
			st = cn.createStatement();

			// Comando que será executado
			rs = st.executeQuery("SELECT codPagamento FROM passagensCompradas WHERE idClientes = " + id);

			// Guarda o código presente no banco de dados na variavel codigoBD a cada volta
			while (rs.next()) {

				codigoBD = rs.getInt("codPagamento");

				// Se os códigos forem iguais, retorna false
				if (codigoBD == codigo)
					return false;

			}

		} catch (Exception e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}

		// Se os códigos forem diferentes, retorna true
		return true;

	}

	// Método para validar se o ID da passagem informado pelo cliente quando ele for
	// finalizar ou cancelar a viagem existe no banco
	public boolean checaIdPassagem(int idPassagem, int idCliente) {

		try {
			// Cria Statement
			st = cn.createStatement();

			// Comando que será executado
			rs = st.executeQuery("SELECT idPassagens FROM passagensCompradas WHERE idClientes = " + idCliente);

			// Laço de repetição para checar se o id está presente no banco e relacionado
			// com o cliente
			while (rs.next()) {

				int idPassagemBD = rs.getInt("idPassagens");

				// Se o ID for encontrado no banco, retorna true
				if (idPassagem == idPassagemBD)
					return true;

			}

		} catch (SQLException e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}

		// Se o ID não for encontrado, retorna false
		return false;

	}

	// Método para cadastrar o cliente
	// Recebe tudo que foi informado no momento do cadastro
	public void cadastra(String nome, String rg, String telefone, String email, String senha) {
		try {

			// Comando que será executado
			ps = cn.prepareStatement(
					"INSERT INTO clientes (nomeClientes, rgClientes, telefoneClientes, emailClientes, senhaClientes) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, nome);
			ps.setString(2, rg);
			ps.setString(3, telefone);
			ps.setString(4, email);
			ps.setString(5, senha);
			// Executa update
			ps.executeUpdate();

		} catch (SQLException e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}
	}

	// Método para cadastrar passagem
	// Recebe tudo que foi informado pelo cliente no momento da compra
	public void cadastraPassagem(int codPagamento, int idCliente, String origem, String destino, String data,
			String horario, String assento, String tipoAssento, double valor) {

		try {

			// Comando a ser excutado
			ps = cn.prepareStatement(
					"INSERT INTO passagensCompradas (codPagamento, idClientes, origem, destino, dataViagem, horarioViagem, assento, tipoAssento, valor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, codPagamento);
			ps.setInt(2, idCliente);
			ps.setString(3, origem);
			ps.setString(4, destino);
			ps.setString(5, data);
			ps.setString(6, horario);
			ps.setString(7, assento);
			ps.setString(8, tipoAssento);
			ps.setDouble(9, valor);
			// Executa o update
			ps.executeUpdate();

		} catch (Exception e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}

	}

	// Método para cadastrar no histórico, após a conclusão da viagem
	public void cadastraHistorico(int idPassagem, int idCliente) {

		try {
			// Comando a ser executado
			// Pega todos os valores presentes na tabela passagensCompradas na linha
			// correspondente aos parâmetros passados e adiciona ao histórico
			ps = cn.prepareStatement(
					"INSERT INTO historicoPassagens SELECT * FROM passagensCompradas WHERE idPassagens = " + idPassagem
							+ " AND idClientes = " + idCliente);
			ps.executeUpdate();

		} catch (SQLException e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}

	}

	// Método para remover uma passagem da ta tabela passagensCompradas caso o
	// cliente cancele a compra
	public void remove(int idPassagem, int idCliente) {

		try {

			// Comando a ser executado
			ps = cn.prepareStatement("DELETE FROM passagensCompradas WHERE idPassagens = " + idPassagem
					+ " AND idClientes = " + idCliente);
			ps.executeUpdate();

		} catch (SQLException e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}
	}

	// Método para realizar a alteração das informações no banco de dados quando o
	// cliente editar o perfil
	public void editaPerfil(String nome, String rg, String telefone, String email, String senha, int id) {
		try {

			// Comando a ser executado
			ps = cn.prepareStatement(
					"UPDATE clientes SET nomeClientes = ?, rgClientes = ?, telefoneClientes = ?, emailClientes = ?, senhaClientes = ? WHERE idClientes = "
							+ id);
			ps.setString(1, nome);
			ps.setString(2, rg);
			ps.setString(3, telefone);
			ps.setString(4, email);
			ps.setString(5, senha);
			// Executa o update
			ps.executeUpdate();

		} catch (SQLException e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}
	}

	// Método para editar passagem
	public void reagendaPassagem(String data, String horario, int idPassagem, int idCliente) {

		try {

			ps = cn.prepareStatement(
					"UPDATE passagensCompradas SET dataViagem = ?, horarioViagem = ? WHERE idPassagens = " + idPassagem
							+ " AND idClientes = " + idCliente);
			ps.setString(1, data);
			ps.setString(2, horario);
			ps.executeUpdate();

		} catch (SQLException e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}
	}

	// Método para pegar o id do cliente (usado assim que o usuário faz login)
	public int getIdCliente(String usuario) {

		// Variavel para guardar o ID
		int idCliente = 0;

		try {

			// Cria Statement
			st = cn.createStatement();

			// Comando a ser executado
			rs = st.executeQuery("SELECT idClientes FROM clientes WHERE emailClientes LIKE '%" + usuario + "%'");

			// Atribui o valor do ID a variável
			while (rs.next()) {
				idCliente = rs.getInt("idClientes");
			}

		} catch (SQLException e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}

		// Retorna o ID
		return idCliente;

	}

	// Método para pegar informações para exibir na tela de perfil
	public String getInfo(int indice, int id) {

		// Informações serão armazenadas num vetor de Strings
		String[] infos = new String[4];

		try {

			// Cria Statement
			st = cn.createStatement();

			// Comando a ser executado
			rs = st.executeQuery("SELECT * FROM clientes WHERE idClientes = " + id);

			// Salva as informações separadamente em cada índice do vetor
			while (rs.next()) {
				infos[0] = rs.getString("nomeClientes");
				infos[1] = rs.getString("rgClientes");
				infos[2] = rs.getString("telefoneClientes");
				infos[3] = rs.getString("emailClientes");
			}

		} catch (SQLException e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}

		// Retorna a informação referente ao índice informado no momento da chamada do
		// método
		return infos[indice];
	}

	// Método para pegar o preço da passagem de acordo com a origem e destino
	// Os valores estão salvos numa tabela "precos" no banco de dados
	public String getPreco(String origem, String destino) {

		// Variaveis para armazenar o preco
		double preco = 0;
		String precoS = "";

		try {

			// Cria Statement
			st = cn.createStatement();

			// Comando a ser executado
			rs = st.executeQuery("SELECT preco FROM precos WHERE origem LIKE '%" + origem + "%' AND destino LIKE '%"
					+ destino + "%'");

			// Salva o preço que está no banco de dados na variavel preco
			while (rs.next()) {
				preco = rs.getDouble("preco");
			}

			// Converte para String
			precoS = preco + "";

			// Retorna o preco
			return precoS;

		} catch (SQLException e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}
		return precoS;
	}

	// Sobrecarga no método getPreco
	// Para pegar o valor a ser estornado para o cliente quando ele cancelar uma
	// compra
	public String getPreco(int idPassagem, int idCliente) {

		// Variaveis para guardar o valor
		String precoS = "R$ ";
		double precoD = 0;

		try {

			// Cria Statement
			st = cn.createStatement();

			// Comando a ser executado
			rs = st.executeQuery("SELECT valor FROM passagensCompradas WHERE idPassagens = " + idPassagem
					+ " AND idClientes = " + idCliente);

			// Salva o valor presente no banco de dados na variavel precos
			while (rs.next()) {
				precoD = rs.getDouble("valor");
			}

			// Retorna o valor como String
			return precoS + precoD;

		} catch (SQLException e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}

		return precoS;

	}

	// Método para pegar todas as pasasgens compradas por um cliente
	// Usado para exibir passagens programadas
	public String getPassagens(int id) {

		// Variavel onde serão salvas as informações
		String passagens = "";

		try {

			// Cria Statement
			st = cn.createStatement();

			// Comando a ser executado
			rs = st.executeQuery(
					"SELECT idPassagens, codPagamento, origem, destino, dataViagem, horarioViagem, assento, tipoAssento, valor FROM passagensCompradas WHERE idClientes = "
							+ id + " ORDER BY idPassagens DESC");

			// Salva as informações
			while (rs.next()) {
				int idPassagem = rs.getInt("idPassagens");
				int codPagamento = rs.getInt("codPagamento");
				String origem = rs.getString("origem");
				String destino = rs.getString("destino");
				String dataViagem = rs.getString("dataViagem");
				String horaViagem = rs.getString("horarioViagem");
				String assento = rs.getString("assento");
				String tipoAssento = rs.getString("tipoAssento");
				double valor = rs.getDouble("valor");

				passagens = passagens + "ID da Passagem: " + idPassagem + "\n" + "Código de Pagamento: " + codPagamento
						+ "\n" + "Origem: " + origem + "\n" + "Destino: " + destino + "\n" + "Data da Viagem: "
						+ dataViagem + "\n" + "Hora da Viagem: " + horaViagem + "\n" + "Assento: " + assento + "\n"
						+ "Tipo de Assento: " + tipoAssento + "\n" + "Valor: " + valor + "\n\n";
			}

			// Retorna as passagens
			return passagens;

		} catch (SQLException e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}

		return passagens;

	}

	// Método para pegar o histórico de passagens já finalizadas
	// Funciona da mesma maneira que o método para pegar as passagens compradas
	public String getHistorico(int id) {
		String historico = "";

		try {

			st = cn.createStatement();

			rs = st.executeQuery(
					"SELECT * FROM historicoPassagens WHERE idClientes = " + id + " ORDER BY idPassagens DESC");

			while (rs.next()) {
				int idPassagem = rs.getInt("idPassagens");
				int codPagamento = rs.getInt("codPagamento");
				String origem = rs.getString("origem");
				String destino = rs.getString("destino");
				String dataViagem = rs.getString("dataViagem");
				String horaViagem = rs.getString("horarioViagem");
				String assento = rs.getString("assento");
				String tipoAssento = rs.getString("tipoAssento");
				double valor = rs.getDouble("valor");

				historico = historico + "ID da Passagem: " + idPassagem + "\n" + "Código de Pagamento: " + codPagamento
						+ "\n" + "Origem: " + origem + "\n" + "Destino: " + destino + "\n" + "Data da Viagem: "
						+ dataViagem + "\n" + "Hora da Viagem: " + horaViagem + "\n" + "Assento: " + assento + "\n"
						+ "Tipo de Assento: " + tipoAssento + "\n" + "Valor: " + valor + "\n\n";
			}

			return historico;

		} catch (SQLException e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}
		return historico;
	}

	// Método para pegar a data da viagem
	// Usado para validar se o cliente ainda está em tempo de cancelar a compra
	public String getDataViagem(int idPassagem, int idCliente) {
		String data = "";

		try {

			st = cn.createStatement();

			rs = st.executeQuery("SELECT dataViagem FROM passagensCompradas WHERE idPassagens = " + idPassagem
					+ " AND idClientes = " + idCliente);

			while (rs.next()) {
				data = rs.getString("dataViagem");
			}

			return data;

		} catch (SQLException e) {

			System.out.println("Falha ao realizar ação.");
			e.printStackTrace();

		}

		return data;

	}

}
