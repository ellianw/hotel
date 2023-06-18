public class SistemasReservasHotel_3 {

  public static void main(String[] args) {
    int NUM_QUARTOS = 12;
    String[] nomes = new String[NUM_QUARTOS];
    String[] dtEntrada = new String[NUM_QUARTOS];
    String[] dtSaida = new String[NUM_QUARTOS];
    carregarReservasDoArquivo(nomes, dtEntrada, dtSaida); 

    String menu = "===== Sistema de Reservas de Hotel =====\n" +
        "1. Cadastrar reserva\n" +
        "2. Consultar reserva\n" +
        "3. Atualizar reserva\n" +
        "4. Cancelar reserva\n" +
        "5. Exibir relat�rio de reservas\n" +
        "6. Sair do sistema";
    int opcao = Entrada.leiaInt(menu);
    while (opcao != 6) {
      if (opcao == 1) {
        cadastrarReserva(nomes, dtEntrada, dtSaida);
      } else if (opcao == 2) {
        consultarReserva(nomes, dtEntrada, dtSaida);
      } else if (opcao == 3) {
        atualizarReserva(nomes, dtEntrada, dtSaida);
      } else if (opcao == 4) {
        cancelarReserva(nomes, dtEntrada, dtSaida);
      } else if (opcao == 5) {
        exibirRelatorio(nomes, dtEntrada, dtSaida);
      } else {
        Entrada.leiaString("Op��o inv�lida. Tente novamente.");
      }
      opcao = Entrada.leiaInt(menu);
    }

    salvarReservasNoArquivo(nomes, dtEntrada, dtSaida); 
  }

  private static void cadastrarReserva(String[] nomes, String[] dtEntrada, String[] dtSaida) {
    // Entrada.leiaString("===== Cadastro de Reserva =====\n"+);

    // Verifica se h� quartos dispon�veis
    if (quantidadeQuartosDisponiveis(nomes) == 0) {
      Entrada.leiaString("Desculpe, n�o h� quartos dispon�veis.");
      return;
    }

    String nomeHospede = Entrada.leiaString("===== Cadastro de Reserva (1/4)=====\n" +
        "Digite o nome do h�spede: ");

    int numeroQuarto = Entrada.leiaInt("===== Cadastro de Reserva (2/4)=====\n" +
        "Digite o n�mero do quarto: ");

    if (verificarQuartoOcupado(numeroQuarto, nomes)) {
      Entrada.leiaString("O quarto j� est� ocupado.");
      return;
    }
    
    nomes[numeroQuarto - 1] = nomeHospede;

    dtEntrada[numeroQuarto - 1] = Entrada.leiaString("===== Cadastro de Reserva (3/4)=====\n" +
        "Digite a data de entrada: ");

    dtSaida[numeroQuarto - 1] = Entrada.leiaString("===== Cadastro de Reserva (4/4)=====\n" +
        "Digite a data de sa�da: ");

    
    Entrada.leiaString("Reserva cadastrada com sucesso.");
    return;
  }

  private static void consultarReserva(String[] nomes, String[] entrada, String[] saida) {
    int numeroQuarto = Entrada.leiaInt("===== Consulta de Reserva =====\n" +
        "Digite o n�mero do quarto: ");

    // Verifica se o n�mero do quarto est� dentro do intervalo v�lido
    if (numeroQuarto < 1 || numeroQuarto > nomes.length) {
      Entrada.leiaString("N�mero de quarto inv�lido.");
      return;
    }

    // Verifica se o quarto est� ocupado
    if (nomes[numeroQuarto - 1] != null) {
      Entrada.leiaString("H�spede: " + nomes[numeroQuarto - 1] + "\n" +
          "Quarto: " + (numeroQuarto) + "\n" +
          "Entrada: " + entrada[numeroQuarto - 1] + "\n" +
          "Saida: " + saida[numeroQuarto - 1]);
    } else {
      Entrada.leiaString("Quarto vazio.");
    }
  }

  private static void atualizarReserva(String[] nomes, String[] dtEntrada, String[] dtSaida) {
    int numeroQuarto = Entrada.leiaInt("===== Atualiza��o de Reserva =====\n" +
        "Digite o n�mero do quarto: ");

    // Verifica se o n�mero do quarto est� dentro do intervalo v�lido
    if (numeroQuarto < 1 || numeroQuarto > nomes.length) {
      Entrada.leiaString("N�mero de quarto inv�lido.");
      return;
    }

    // Verifica se o quarto est� ocupado
    if (nomes[numeroQuarto - 1] != null) {
      String nomeHospede = Entrada.leiaString("===== Cadastro de Reserva (1/4)=====\n" +
          "Digite o nome do h�spede: ");

      nomes[numeroQuarto - 1] = nomeHospede;

      dtEntrada[numeroQuarto - 1] = Entrada.leiaString("===== Cadastro de Reserva (3/4)=====\n" +
          "Digite a data de entrada: ", "" + dtEntrada[numeroQuarto - 1]);

      dtSaida[numeroQuarto - 1] = Entrada.leiaString("===== Cadastro de Reserva (4/4)=====\n" +
          "Digite a data de sa�da: ", "" + dtSaida[numeroQuarto - 1]);

      Entrada.leiaString("Reserva atualizada com sucesso.");
    } else {
      Entrada.leiaString("Quarto vazio. N�o é poss�vel atualizar a reserva.");
    }
    return;
  }

  private static void cancelarReserva(String[] nomes, String[] dtEntrada, String[] dtSaida) {
    int numeroQuarto = Entrada.leiaInt("===== Cancelamento de Reserva =====\n" +
        "Digite o n�mero do quarto: ");
    if (numeroQuarto < 1 || numeroQuarto > nomes.length) {
      Entrada.leiaString("N�mero de quarto inv�lido.");
      return;
    }
    if (nomes[numeroQuarto - 1] != null) {
      nomes[numeroQuarto - 1] = null;
      dtEntrada[numeroQuarto - 1] = null;
      dtSaida[numeroQuarto - 1] = null;
      Entrada.leiaString("Reserva cancelada com sucesso.");
    } else {
      Entrada.leiaString("Quarto vazio. N�o é poss�vel cancelar a reserva.");
    }
    return;
  }

  private static void exibirRelatorio(String[] nomes, String[] entrada, String[] saida) {
    Entrada.leiaString("===== Relat�rio de Reservas =====\n" +
        "Quartos locados: " + (nomes.length - quantidadeQuartosDisponiveis(nomes)) + "\n" +
        "Quartos livres: " + quantidadeQuartosDisponiveis(nomes));
  }

  private static int quantidadeQuartosDisponiveis(String[] nomes) {
    int contador = 0;

    for (int i = 0; i < nomes.length; i++) {
      if (nomes[i] == null) {
        contador++;
      }
    }

    return contador;
  }

  private static boolean verificarQuartoOcupado(int numeroQuarto, String[] nomes) {
    return nomes[numeroQuarto - 1] != null;
  }

  private static void carregarReservasDoArquivo(String[] nomes, String[] entrada, String[] saida) {
    Arquivo arquivo = new Arquivo("reservas.csv");

    if (arquivo.abrirLeitura()) {
      nomes = arquivo.lerLinha().split(",");
      entrada = arquivo.lerLinha().split(",");
      saida = arquivo.lerLinha().split(",");
      for (int i = 0; i < nomes.length; i++) {
        System.out.println(nomes[i] + ", quarto " + i + " entrada: " + entrada[i] + " sa�da: " + saida[i]);
      }
    }
    arquivo.fecharArquivo();
  }

  private static void salvarReservasNoArquivo(String[] nomes, String[] entrada, String[] saida) {
    Arquivo arquivo = new Arquivo("reservas.csv");
    String aux = "";
    if (arquivo.abrirEscrita()) {
      for (String i : nomes) {
        aux = aux+i+",";
      }
      arquivo.escreverLinha(aux);
      aux = "";
      for (String i : entrada) {
        aux = aux+i+",";
      }
      arquivo.escreverLinha(aux);
      aux = "";
      for (String i : saida) {
        aux = aux+i+",";
      }
      arquivo.escreverLinha(aux);
      aux = "";
    }
    arquivo.fecharArquivo();
  }
}
