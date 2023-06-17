public class SistemaReservasHotel {
  private static final int NUM_QUARTOS = 10; // Número total de quartos do hotel
  private static final String ARQUIVO_DADOS = "reservas.txt"; // Nome do arquivo de texto para persistência dos dados
  private static String[][] reservas = new String[NUM_QUARTOS][2]; // Matriz para armazenar as reservas (nome do hóspede e número do quarto)
  
  public static void main(String[] args){
    carregarReservasDoArquivo(); // Carrega as reservas do arquivo de texto
    
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
        cadastrarReserva();
      } else if (opcao == 2) {
        consultarReserva();
      } else if (opcao == 3) {
        atualizarReserva();
      } else if (opcao == 4) {
        cancelarReserva();
      } else if (opcao == 5) {
        exibirRelatorio();
      } else {
        Entrada.leiaString("Opção inválida. Tente novamente.");
      }
      opcao = Entrada.leiaInt(menu);
    }
    
    salvarReservasNoArquivo(); // Salva as reservas no arquivo de texto antes de encerrar o programa
  }
  
  private static void cadastrarReserva() {
    //Entrada.leiaString("===== Cadastro de Reserva =====\n"+);
    
    // Verifica se há quartos disponíveis
    if (quantidadeQuartosDisponiveis() == 0) {
      Entrada.leiaString("Desculpe, não há quartos disponíveis.");
      return;
    }
    
    String nomeHospede = Entrada.leiaString("===== Cadastro de Reserva (1/2)=====\n"+
                                            "Digite o nome do hóspede: ");
    
    int numeroQuarto = Entrada.leiaInt("===== Cadastro de Reserva (2/2)=====\n"+
                                       "Digite o número do quarto: ");
    
    // Verifica se o número do quarto já está ocupado
    if (verificarQuartoOcupado(numeroQuarto)) {
      Entrada.leiaString("O quarto já está ocupado.");
      return;
    }
    
    // Cadastra a reserva na matriz de reservas
    reservas[numeroQuarto - 1][0] = nomeHospede;
    reservas[numeroQuarto - 1][1] = Integer.toString(numeroQuarto);
    
    Entrada.leiaString("Reserva cadastrada com sucesso.");
  }
  
  private static void consultarReserva() {
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
  
  private static void atualizarReserva() {
    int numeroQuarto = Entrada.leiaInt("===== Atualização de Reserva =====\n"+
                                       "Digite o número do quarto: ");
    
    // Verifica se o número do quarto está dentro do intervalo válido
    if (numeroQuarto < 1 || numeroQuarto > NUM_QUARTOS) {
      Entrada.leiaString("Número de quarto inválido.");
      return;
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
  }
  
  private static void cancelarReserva() {
    int numeroQuarto = Entrada.leiaInt("===== Cancelamento de Reserva =====\n"+
                                       "Digite o número do quarto: ");
    
    // Verifica se o número do quarto está dentro do intervalo válido
    if (numeroQuarto < 1 || numeroQuarto > NUM_QUARTOS) {
      Entrada.leiaString("Número de quarto inválido.");
      return;
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
  }
  
  private static void exibirRelatorio() {    
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
                       "Quartos locados: "+(NUM_QUARTOS-quantidadeQuartosDisponiveis())+"\n"+
                       "Quartos livres: "+quantidadeQuartosDisponiveis());
  }
  
  private static int quantidadeQuartosDisponiveis() {
    int contador = 0;
    
    for (int i = 0; i < NUM_QUARTOS; i++) {
      if (reservas[i][0] == null) {
        contador++;
      }
    }
    
    return contador;
  }
  
  private static boolean verificarQuartoOcupado(int numeroQuarto) {
    return reservas[numeroQuarto - 1][0] != null;
  }
  
  private static void carregarReservasDoArquivo(){
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
  
  private static void salvarReservasNoArquivo(){
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