public class SistemaReservasHotel_2 {
  
  public static void main(String[] args){
    int NUM_QUARTOS = 10; // Número total de quartos do hotel
    String ARQUIVO_DADOS = "reservas.txt"; // Nome do arquivo de texto para persistência dos dados
    String[][] reservas = new String[NUM_QUARTOS][2]; // Matriz para armazenar as reservas (nome do hóspede e número do quarto)
    carregarReservasDoArquivo(reservas); // Carrega as reservas do arquivo de texto
    
    String menu = "===== Sistema de Reservas de Hotel =====\n"+
      "1. Cadastrar reserva\n"+
      "2. Consultar reserva\n"+
      "3. Atualizar reserva\n"+
      "4. Cancelar reserva\n"+
      "5. Exibir relatório de reservas\n"+
      "6. Sair do sistema";
    int opcao = Entrada.leiaInt(menu);
    while (opcao!=6) {
      if (opcao == 1) {
        reservas = cadastrarReserva(NUM_QUARTOS, reservas);
      } else if (opcao == 2) {
        consultarReserva(NUM_QUARTOS, reservas);
      } else if (opcao == 3) {
        reservas = atualizarReserva(NUM_QUARTOS, reservas);
      } else if (opcao == 4) {
        reservas = cancelarReserva(NUM_QUARTOS, reservas);
      } else if (opcao == 5) {
        exibirRelatorio(NUM_QUARTOS, reservas);
      } else {
        Entrada.leiaString("Opção inválida. Tente novamente.");
      }
      opcao = Entrada.leiaInt(menu);
    }
    
    salvarReservasNoArquivo(NUM_QUARTOS, reservas); // Salva as reservas no arquivo de texto antes de encerrar o programa
  }
  
  private static String [][] cadastrarReserva(int NUM_QUARTOS, String [][]reservas) {
    //Entrada.leiaString("===== Cadastro de Reserva =====\n"+);
    
    // Verifica se há quartos disponíveis
    if (quantidadeQuartosDisponiveis(NUM_QUARTOS, reservas) == 0) {
      Entrada.leiaString("Desculpe, não há quartos disponíveis.");
      return reservas;
    }
    
    String nomeHospede = Entrada.leiaString("===== Cadastro de Reserva (1/2)=====\n"+
                                            "Digite o nome do hóspede: ");
    
    int numeroQuarto = Entrada.leiaInt("===== Cadastro de Reserva (2/2)=====\n"+
                                       "Digite o número do quarto: ");
    
    // Verifica se o número do quarto já está ocupado
    if (verificarQuartoOcupado(numeroQuarto, reservas)) {
      Entrada.leiaString("O quarto já está ocupado.");
      return reservas;
    }
    
    // Cadastra a reserva na matriz de reservas
    reservas[numeroQuarto - 1][0] = nomeHospede;
    reservas[numeroQuarto - 1][1] = Integer.toString(numeroQuarto);
    
    Entrada.leiaString("Reserva cadastrada com sucesso.");
    return reservas;
  }
  
  private static void consultarReserva(int NUM_QUARTOS, String [][] reservas) {
    int numeroQuarto = Entrada.leiaInt("===== Consulta de Reserva =====\n"+
                                       "Digite o número do quarto: ");
    
    // Verifica se o número do quarto está dentro do intervalo válido
    if (numeroQuarto < 1 || numeroQuarto > NUM_QUARTOS) {
      Entrada.leiaString("Número de quarto inválido.");
      return;
    }
    
    // Verifica se o quarto está ocupado
    if (reservas[numeroQuarto - 1][0] != null) {
      Entrada.leiaString("Hóspede: " + reservas[numeroQuarto - 1][0]+"\n"+
                         "Quarto: " + reservas[numeroQuarto - 1][1]);
    } else {
      Entrada.leiaString("Quarto vazio.");
    }
  }
  
  private static String [][] atualizarReserva(int NUM_QUARTOS, String [][] reservas) {
    int numeroQuarto = Entrada.leiaInt("===== Atualização de Reserva =====\n"+
                                       "Digite o número do quarto: ");
    
    // Verifica se o número do quarto está dentro do intervalo válido
    if (numeroQuarto < 1 || numeroQuarto > NUM_QUARTOS) {
      Entrada.leiaString("Número de quarto inválido.");
      return reservas;
    }
    
    // Verifica se o quarto está ocupado
    if (reservas[numeroQuarto - 1][0] != null) {
      String nomeHospede = Entrada.leiaString("Digite o novo nome do hóspede: ");
      
      // Atualiza o nome do hóspede na matriz de reservas
      reservas[numeroQuarto - 1][0] = nomeHospede;
      
      Entrada.leiaString("Reserva atualizada com sucesso.");
    } else {
      Entrada.leiaString("Quarto vazio. Não é possível atualizar a reserva.");
    }
    return reservas;
  }
  
  private static String [][] cancelarReserva(int NUM_QUARTOS, String [][] reservas) {
    int numeroQuarto = Entrada.leiaInt("===== Cancelamento de Reserva =====\n"+
                                       "Digite o número do quarto: ");
    
    // Verifica se o número do quarto está dentro do intervalo válido
    if (numeroQuarto < 1 || numeroQuarto > NUM_QUARTOS) {
      Entrada.leiaString("Número de quarto inválido.");
      return reservas;
    }
    
    // Verifica se o quarto está ocupado
    if (reservas[numeroQuarto - 1][0] != null) {
      // Remove a reserva da matriz de reservas
      reservas[numeroQuarto - 1][0] = null;
      reservas[numeroQuarto - 1][1] = null;
      
      Entrada.leiaString("Reserva cancelada com sucesso.");
    } else {
      Entrada.leiaString("Quarto vazio. Não é possível cancelar a reserva.");
    }
    return reservas;
  }
  
  private static void exibirRelatorio(int NUM_QUARTOS, String [][] reservas) {    
    int cont = 0;
    for (int i = 0; i < NUM_QUARTOS; i++) {
      if (reservas[i][0] != null) {
        cont++;
        /*Entrada.leiaString("Hóspede: " + reservas[i][0]+"\n"+
         "Quarto: " + reservas[i][1]+"\n"+
         "------------");*/
      }
    }
    Entrada.leiaString("===== Relatório de Reservas =====\n"+
                       "Quartos locados: "+(NUM_QUARTOS-quantidadeQuartosDisponiveis(NUM_QUARTOS, reservas))+"\n"+
                       "Quartos livres: "+ quantidadeQuartosDisponiveis(NUM_QUARTOS, reservas));
  }
  
  private static int quantidadeQuartosDisponiveis(int NUM_QUARTOS, String [][] reservas) {
    int contador = 0;
    
    for (int i = 0; i < NUM_QUARTOS; i++) {
      if (reservas[i][0] == null) {
        contador++;
      }
    }
    
    return contador;
  }
  
  private static boolean verificarQuartoOcupado(int numeroQuarto, String [][]reservas) {
    return reservas[numeroQuarto - 1][0] != null;
  }
  
  private static void carregarReservasDoArquivo(String [][] reservas){
    Arquivo arquivo = new Arquivo("reservas.csv");
    
    if (arquivo.abrirLeitura()) {
      String linha = arquivo.lerLinha();
      int indice = 0;
      while (linha!=null) {
        reservas[indice][0] = linha.split(",")[0];
        reservas[indice][1] = linha.split(",")[1];
        indice++;
        linha = arquivo.lerLinha();
      }
    }
    arquivo.fecharArquivo();
  }
  
  private static void salvarReservasNoArquivo(int NUM_QUARTOS, String [][] reservas){
    Arquivo arquivo = new Arquivo("reservas.csv");
    if (arquivo.abrirEscrita()) {
      for (int i = 0; i < NUM_QUARTOS; i++) {
        if (reservas[i][0] != null) {
          arquivo.escreverLinha(reservas[i][0] + "," + reservas[i][1]);
        }
      }
    }
    arquivo.fecharArquivo();
  }
}
