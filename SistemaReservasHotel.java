import java.io.*;
import java.util.Scanner;

public class SistemaReservasHotel {
    private static final int NUM_QUARTOS = 10; // N�mero total de quartos do hotel
    private static final String ARQUIVO_DADOS = "reservas.txt"; // Nome do arquivo de texto para persist�ncia dos dados
    private static Scanner scanner = new Scanner(System.in); // Inst�ncia do Scanner para leitura de entrada do usu�rio
    private static String[][] reservas = new String[NUM_QUARTOS][2]; // Matriz para armazenar as reservas (nome do h�spede e n�mero do quarto)

    public static void main(String[] args) {
        carregarReservasDoArquivo(); // Carrega as reservas do arquivo de texto

        boolean sair = false;
        while (!sair) {
            exibirMenu();
            int opcao = lerOpcao();

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
            } else if (opcao == 6) {
                sair = true;
            } else {
                System.out.println("Op��o inv�lida. Tente novamente.");
            }
        }

        salvarReservasNoArquivo(); // Salva as reservas no arquivo de texto antes de encerrar o programa
    }

    private static void exibirMenu() {
        System.out.println("\n===== Sistema de Reservas de Hotel =====");
        System.out.println("1. Cadastrar reserva");
        System.out.println("2. Consultar reserva");
        System.out.println("3. Atualizar reserva");
        System.out.println("4. Cancelar reserva");
        System.out.println("5. Exibir relat�rio de reservas");
        System.out.println("6. Sair do sistema");
    }

    private static int lerOpcao() {
        System.out.print("\nDigite o n�mero da op��o desejada: ");
        return scanner.nextInt();
    }

    private static void cadastrarReserva() {
        System.out.println("\n===== Cadastro de Reserva =====");

        // Verifica se h� quartos dispon�veis
        if (quantidadeQuartosDisponiveis() == 0) {
            System.out.println("Desculpe, n�o h� quartos dispon�veis.");
            return;
        }

        scanner.nextLine(); // Limpa o buffer do scanner

        System.out.print("Digite o nome do h�spede: ");
        String nomeHospede = scanner.nextLine();

        System.out.print("Digite o n�mero do quarto: ");
        int numeroQuarto = scanner.nextInt();

        // Verifica se o n�mero do quarto j� est� ocupado
        if (verificarQuartoOcupado(numeroQuarto)) {
            System.out.println("O quarto j� est� ocupado.");
            return;
        }

        // Cadastra a reserva na matriz de reservas
        reservas[numeroQuarto - 1][0] = nomeHospede;
        reservas[numeroQuarto - 1][1] = Integer.toString(numeroQuarto);

        System.out.println("Reserva cadastrada com sucesso.");
    }

    private static void consultarReserva() {
        System.out.println("\n===== Consulta de Reserva =====");

        System.out.print("Digite o n�mero do quarto: ");
        int numeroQuarto = scanner.nextInt();

        // Verifica se o n�mero do quarto est� dentro do intervalo v�lido
        if (numeroQuarto < 1 || numeroQuarto > NUM_QUARTOS) {
            System.out.println("N�mero de quarto inv�lido.");
            return;
        }

        // Verifica se o quarto est� ocupado
        if (reservas[numeroQuarto - 1][0] != null) {
            System.out.println("H�spede: " + reservas[numeroQuarto - 1][0]);
            System.out.println("Quarto: " + reservas[numeroQuarto - 1][1]);
        } else {
            System.out.println("Quarto vazio.");
        }
    }

    private static void atualizarReserva() {
        System.out.println("\n===== Atualiza��o de Reserva =====");

        System.out.print("Digite o n�mero do quarto: ");
        int numeroQuarto = scanner.nextInt();

        // Verifica se o n�mero do quarto est� dentro do intervalo v�lido
        if (numeroQuarto < 1 || numeroQuarto > NUM_QUARTOS) {
            System.out.println("N�mero de quarto inv�lido.");
            return;
        }

        // Verifica se o quarto est� ocupado
        if (reservas[numeroQuarto - 1][0] != null) {
            scanner.nextLine(); // Limpa o buffer do scanner

            System.out.print("Digite o novo nome do h�spede: ");
            String nomeHospede = scanner.nextLine();

            // Atualiza o nome do h�spede na matriz de reservas
            reservas[numeroQuarto - 1][0] = nomeHospede;

            System.out.println("Reserva atualizada com sucesso.");
        } else {
            System.out.println("Quarto vazio. N�o � poss�vel atualizar a reserva.");
        }
    }

    private static void cancelarReserva() {
        System.out.println("\n===== Cancelamento de Reserva =====");

        System.out.print("Digite o n�mero do quarto: ");
        int numeroQuarto = scanner.nextInt();

        // Verifica se o n�mero do quarto est� dentro do intervalo v�lido
        if (numeroQuarto < 1 || numeroQuarto > NUM_QUARTOS) {
            System.out.println("N�mero de quarto inv�lido.");
            return;
        }

        // Verifica se o quarto est� ocupado
        if (reservas[numeroQuarto - 1][0] != null) {
            // Remove a reserva da matriz de reservas
            reservas[numeroQuarto - 1][0] = null;
            reservas[numeroQuarto - 1][1] = null;

            System.out.println("Reserva cancelada com sucesso.");
        } else {
            System.out.println("Quarto vazio. N�o � poss�vel cancelar a reserva.");
        }
    }

    private static void exibirRelatorio() {
        System.out.println("\n===== Relat�rio de Reservas =====");

        for (int i = 0; i < NUM_QUARTOS; i++) {
            if (reservas[i][0] != null) {
                System.out.println("H�spede: " + reservas[i][0]);
                System.out.println("Quarto: " + reservas[i][1]);
                System.out.println("------------");
            }
        }
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

    private static void carregarReservasDoArquivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_DADOS))) {
            String linha;
            int indice = 0;

            while ((linha = reader.readLine()) != null) {
                String[] dadosReserva = linha.split(",");
                reservas[indice][0] = dadosReserva[0];
                reservas[indice][1] = dadosReserva[1];
                indice++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void salvarReservasNoArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_DADOS))) {
            for (int i = 0; i < NUM_QUARTOS; i++) {
                if (reservas[i][0] != null) {
                    writer.write(reservas[i][0] + "," + reservas[i][1]);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}