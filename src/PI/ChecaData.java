package PI;

//Importando as classes que serão utilizadas nessa tela
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Classe para retornar os dias entre o dia atual e o dia da viagem
public class ChecaData {

	public ChecaData() {

	}
	
	// Método para retornar as próximas datas com viagens disponíveis
		public String[] proxDatas () {
			String [] datas = new String[3];
			String dataFormatada;
			
			int dia, mes, ano;
			
			LocalDate data = LocalDate.now();	
			
			for (int i = 0; i < datas.length; i++) {
				
				data = data.plusDays(2);
				
				dia = data.getDayOfMonth();
				mes = data.getMonthValue();
				ano = data.getYear();
				
				dataFormatada = dia + "/" + mes + "/" + ano;
				
				datas[i] = dataFormatada;
			}
			
			return datas;
		}
		
		// Método para retornar os dias entre o dia atual e o dia da viagem
		public long difDias (String dataViagem) {
			String[] dataVetor = dataViagem.split("/");
			int dia = Integer.parseInt(dataVetor[0]);
			int mes = Integer.parseInt(dataVetor[1]);
			int ano = Integer.parseInt(dataVetor[2]);
			
			LocalDate dataAtual = LocalDate.now();
			
			LocalDate dataFinal = LocalDate.of(ano, mes, dia);
			
			long diferencaEmDias = ChronoUnit.DAYS.between(dataAtual, dataFinal);
			
			return diferencaEmDias;
		}

	}

