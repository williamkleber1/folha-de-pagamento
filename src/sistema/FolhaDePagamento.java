package sistema;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class FolhaDePagamento {

    static Scanner read = new Scanner(System.in);

    static final int QUANTIDADE_BASE        = 200;
	static final String[] TIPOS_EMPREGADO   = { "Horário", "Assalariado", "Comissionado" };
	static final String[] METODO_PAGAMENTO	= { "Cheque pelos correios", "Cheque em mãos", "Depósito" };

	static String alerta 						= null;
	static int totalEmpregados      			= 0;
	static int empregadosIndex      			= 0;
	static Empregado[] empregados   			= new Empregado[QUANTIDADE_BASE];
	static int agendasPagamentoIndex 			= 0;
	static AgendaPagamento[] agendasPagamento 	= new AgendaPagamento[25];

	static Transacao[] transacoes = new Transacao[7];
	static Empregado empregadoAntigo = null;

	public static void main(String[] args) {
		configurarAgendasDePagamento();
		char opcaoMenu = '0';

		while(opcaoMenu != '8') {
			menuPrincipal();
			opcaoMenu = read.next().charAt(0);

			switch (opcaoMenu) {
			case '1':
				listarEmpregados();
				break;
			case '2':
				rodarFolhaDePagamento();
				break;
			case '3':
				lancarCartaoPonto();
				break;
			case '4':
				lancarVenda();
				break;
			case '5':
				lancarTaxaDeServico();
				break;
			case '6':
				listarAgendasDePagamento();
				break;
				case '7':
					refazerTransacoes();
					break;
			case '8':
				System.exit(0);
				break;
			default:
				alerta = "Opção Inválida!";
				break;
			}
		}
	}

	public static void refazerTransacoes() {
		System.out.println("\n\n \t\t Refazer/Desfazer\n");

		System.out.println(Transacao.ADICIONAR_EMPREGADO +" - Adicionar Empregado");
		System.out.println(Transacao.REMOVER_EMPREGADO +" - Remover Empregado");
		System.out.println(Transacao.LANCAR_PONTO +" - Lançar Cartão de Ponto");
		System.out.println(Transacao.LANCAR_VENDA +" - Lançar Resultado de Venda");
		System.out.println(Transacao.LANCAR_TAXA +" - Lançar Taxa de Serviço");
		System.out.println(Transacao.ALTERAR_EMPREGADO +" - Alterar Empregado");

		System.out.print("\n>> ");
		int opcao = read.nextInt();

		int index;
		switch (opcao) {
			case 0:
				if (transacoes[opcao] == null) {
					alerta = "Sem registro de transações para refazer/desfazer!";
					break;
				}

				index = transacoes[opcao].registroId - 1;

				empregados[index].ativo = false;
				--totalEmpregados;

				alerta = "Removido ultimo empregado adicionado!";
				registrarTransacao(Transacao.REMOVER_EMPREGADO, empregados[index].id);
				break;

			case 1:
				if (transacoes[opcao] == null) {
					alerta = "Sem registro de transações para refazer/desfazer!";
					break;
				}

				index = transacoes[opcao].registroId - 1;

				empregados[index].ativo = true;
				++totalEmpregados;

				alerta = "Adicionado ultimo empregado removido!";
				registrarTransacao(Transacao.ADICIONAR_EMPREGADO, empregados[index].id);
				break;

			case 2:
				if (transacoes[opcao] == null) {
					alerta = "Sem registro de transações para refazer/desfazer!";
					break;
				}

				index = transacoes[opcao].registroId - 1;
				int pontoIndex = empregados[index].pontoIndex - 1;
				empregados[index].ponto[pontoIndex].ativo = false;

				alerta = "Removido ultimo cartão de ponto lançado!";
				break;

			case 3:
				if (transacoes[opcao] == null) {
					alerta = "Sem registro de transações para refazer/desfazer!";
					break;
				}

				index = transacoes[opcao].registroId - 1;
				int vendaIndex = empregados[index].vendasIndex - 1;
				empregados[index].vendas[vendaIndex].ativo = false;

				alerta = "Removida ultima venda lançada!";
				break;

			case 4:
				if (transacoes[opcao] == null) {
					alerta = "Sem registro de transações para refazer/desfazer!";
					break;
				}

				index = transacoes[opcao].registroId - 1;
				int taxaIndex = empregados[index].taxasIndex - 1;
				empregados[index].taxas[taxaIndex].ativo = false;

				alerta = "Removida ultima taxa de serviço lançada!";
				break;

			case 5:
				if (transacoes[opcao] == null) {
					alerta = "Sem registro de transações para refazer/desfazer!";
					break;
				}

				index = transacoes[opcao].registroId - 1;
				empregados[index] = empregadoAntigo;

				alerta = "Restaurado último empregado alterado!";
				break;

			default:
				alerta = "Opção Inválida!";
				break;
		}
	}

	public static void configurarAgendasDePagamento() {
		AgendaPagamento semanal = new AgendaPagamento();
		semanal.id = 1;
		semanal.titulo = "semanal";
		semanal.semanal = true;
		semanal.ativo = true;

		AgendaPagamento mensal = new AgendaPagamento();
		mensal.id = 2;
		mensal.titulo = "mensal";
		mensal.mensal = true;
		mensal.ativo = true;

		AgendaPagamento biSemanal = new AgendaPagamento();
		biSemanal.id = 3;
		biSemanal.titulo = "bi-semanal";
		biSemanal.semanal = true;
		biSemanal.intervaloSemanas = 2;
		biSemanal.ativo = true;

		agendasPagamento[0] = semanal;
		agendasPagamento[1] = mensal;
		agendasPagamento[2] = biSemanal;
		agendasPagamentoIndex = 3;
	}

	public static void menuPrincipal() {
		System.out.println("\n\n \t\t Folha de Pagamento");
		mostrarAlerta();

		System.out.println("\n1 - Gerenciar Empregados");
		System.out.println("2 - Rodar Folha de Pagamento");
		System.out.println("3 - Lançar Cartão de Ponto");
		System.out.println("4 - Lançar Resultado de Venda");
		System.out.println("5 - Lançar Taxa de Serviço");
		System.out.println("6 - Genreciar Agendas de Pagamento");
		System.out.println("7 - Refazer/Desfazer");
		System.out.println("8 - Sair");

		System.out.print("\n>> ");
	}

	public static void mostrarAlerta() {
		if (alerta != null) {
			System.out.println("\n" +alerta);

			alerta = null;
		}
	}

	public static int gerarId() {
		return empregadosIndex + 1;
	}

	public static void registrarTransacao(int tipo, int registroId) {
		Transacao transacao = new Transacao();
		transacao.tipo = tipo;
		transacao.registroId = registroId;

		transacoes[tipo] = transacao;
	}

	public static void listarEmpregados() {
		char opcaoMenu = '0';

		while (opcaoMenu != '4') {
			System.out.println("\n\n \t\t Empregados");
			mostrarAlerta();

			if (totalEmpregados == 0) {
				System.out.println("\nSem registros");
			} else {
				for (Empregado empregado : empregados) {
					if (empregado == null) break;
					if (empregado.ativo == true) printEmpregadoInfo(empregado);
				}
			}

			System.out.println("\n1 - Adicionar \t 2 - Editar \t 3 - Remover \t 4 - Voltar");
			System.out.print(">> ");
			opcaoMenu = read.next().charAt(0);

			switch (opcaoMenu) {
			case '1':
				novoEmpregado();
				break;

			case '2':
				editarEmpregado();
				break;

			case '3':
				removerEmpregado();
				break;

			case '4':
				return;

			default:
				alerta = "Opção Inválida!";
				break;
			}
		}
	}

	public static Empregado buscarEmpregado() {
		char opcao = '0';

		while (opcao != '3') {
			System.out.println("\nBuscar empregado: ");
			mostrarAlerta();

			System.out.println("1 - Buscar pelo nome \t 2 - Buscar pelo Id \t 3 - Cancelar");
			System.out.print(">> ");
			opcao = read.next().charAt(0);

			switch (opcao) {
				case '1':
					System.out.print("\nInforme o nome do empregado: ");
					read.nextLine();
					String nome = read.nextLine();

					for (Empregado empregado : empregados) {
						if (empregado == null) return null;
						if (empregado.ativo == false) continue;

						if (empregado.nome.toUpperCase().equals(nome.toUpperCase()))
							return empregado;
					}

					break;

				case '2':
					System.out.print("\nInforme o Id do empregado: ");
					int id = read.nextInt();

					if (id <= 0) {
						alerta = "Id inválido!";
						continue;
					}

					return empregados[id - 1];

				case '3':
					return null;

				default:
					alerta = "Opção Inválida!";
			}
		}

		return null;
	}

	public static void printEmpregadoInfo(Empregado empregado) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("\nId: " + empregado.id);
		System.out.println("Nome: " + empregado.nome);
		System.out.println("Endereço: " + empregado.endereco);
		System.out.println("Tipo: " + TIPOS_EMPREGADO[empregado.tipo]);

		System.out.println("Salário: R$ " + empregado.salario);
		if (empregado.tipo == 2) {
			System.out.println("Comissão: " + empregado.comissao +" %");
		}

		System.out.println("Método de pagamento: " + METODO_PAGAMENTO[empregado.metodoPagamento]);
		System.out.print("Pertence ao sindicato? ");
		if (empregado.pertenceSindicato) {
			System.out.println("Sim");
			System.out.println("Identificação no sindicato: " +empregado.sindicatoId);
			System.out.println("Taxa Sindical: R$ " +empregado.taxaSindical);
		}
		else System.out.println("Não");
		System.out.println("Próximo pagamento: " +df.format(empregado.proximoPagamento));
	}

	public static void novoEmpregado() {
		Empregado empregado = new Empregado();

		System.out.println("\n\n \t\t Adicionar Empregado");
		mostrarAlerta();

		System.out.print("\nNome: ");
		read.nextLine();
		empregado.nome =  read.nextLine();

		System.out.print("Endereço: ");
		empregado.endereco = read.nextLine();

		char tipo = '4';
		while (tipo != '0' && tipo != '1' && tipo != '2') {
			mostrarAlerta();
			alerta = "Tipo Inválido!";

			System.out.println("\n0 - " + TIPOS_EMPREGADO[0] +" \t 1 - " + TIPOS_EMPREGADO[1] +" \t 2 - " + TIPOS_EMPREGADO[2]);
			System.out.print("Selecione o tipo de empregado: ");
			tipo = read.next().charAt(0);
		}
		alerta = null;

		System.out.print("Salário: R$ ");
		empregado.salario= read.nextDouble();

		if (tipo == '0') {
			empregado.tipo = 0;
			empregado.agendaPagamentoId = 1;
		} else if (tipo == '1') {
			empregado.tipo = 1;
			empregado.agendaPagamentoId = 2;
		} else if (tipo == '2') {
			empregado.tipo = 2;
			empregado.agendaPagamentoId = 3;

			System.out.print("Comissão: ");
			empregado.comissao = read.nextDouble();
		}

		char metodo = '4';
		while (metodo != '0' && metodo != '1' && metodo != '2') {
			mostrarAlerta();
			alerta = "Método de pagamento Inválido!";

			System.out.println("\n0 - " + METODO_PAGAMENTO[0] +" \t 1 - " + METODO_PAGAMENTO[1] +" \t 2 - " + METODO_PAGAMENTO[2]);
			System.out.print("Selecione o método de pagamento: ");
			metodo = read.next().charAt(0);
		}

		if (tipo == '0') empregado.metodoPagamento = 0;
		else if (tipo == '1') empregado.metodoPagamento = 1;
		else if (tipo == '2') empregado.metodoPagamento = 2;

		System.out.print("O empregado pertence ao sindicato? (s/n) ");
		char sindicato = read.next().charAt(0);

		if (sindicato == 's' || sindicato == 'S') {
			empregado.pertenceSindicato = true;

			System.out.print("Identificação no sindicato: ");
			read.nextLine();
			empregado.sindicatoId = read.nextLine();

			System.out.print("Taxa sindical: R$ ");
			empregado.taxaSindical = read.nextDouble();
		} else {
			empregado.pertenceSindicato = false;
		}

		empregado.id = gerarId();
		empregado.ativo = true;
		empregado.proximoPagamento = proximoPagamento(new Date(), empregado);
		salvarEmpregado(empregado);

		registrarTransacao(Transacao.ADICIONAR_EMPREGADO, empregado.id);
		alerta = "Empregado salvo com sucesso!";
	}

	public static Date proximoPagamento(Date ultimoPagameento, Empregado empregado) {
		AgendaPagamento agenda = agendasPagamento[empregado.agendaPagamentoId - 1];

		Calendar proximoPagamento = Calendar.getInstance();
		proximoPagamento.setTime(ultimoPagameento);
		proximoPagamento.set(Calendar.HOUR_OF_DAY, 0);
		proximoPagamento.set(Calendar.MINUTE, 0);
		proximoPagamento.set(Calendar.SECOND, 0);
		proximoPagamento.set(Calendar.MILLISECOND, 0);

		if (agenda.mensal) {
			proximoPagamento.add(Calendar.MONTH, 1);

			if (proximoPagamento.getActualMaximum(Calendar.DAY_OF_MONTH) < agenda.diaMes)
				proximoPagamento.set(Calendar.DAY_OF_MONTH, proximoPagamento.getActualMaximum(Calendar.DAY_OF_MONTH));
			else
				proximoPagamento.set(Calendar.DAY_OF_MONTH, agenda.diaMes);
		} else {
			proximoPagamento.add(Calendar.DAY_OF_YEAR, agenda.intervaloSemanas * 7);
			proximoPagamento.set(Calendar.DAY_OF_WEEK, agenda.diaSemana);
		}

		return proximoPagamento.getTime();
	}

	public static void salvarEmpregado(Empregado empregado) {
		if (empregadosIndex == empregados.length) {
			Empregado[] empregadosAux = new Empregado[empregadosIndex + QUANTIDADE_BASE];

			for (int i = 0; i < empregadosIndex; i++) {
				empregadosAux[i] = empregados[i];
			}

			empregados = empregadosAux;
		}

		empregados[empregadosIndex++] = empregado;
		++totalEmpregados;
	}

	public static void editarEmpregado() {
		System.out.println("\n\n \t\t Editar Empregado");
		mostrarAlerta();

		Empregado empregado = buscarEmpregado();

		if (empregado == null) {
			alerta = "Empregado não encontrado!";
			return;
		}

		criarCopia(empregado);
		int index = empregado.id - 1;
		char opcao = '0';
		while (opcao != '5') {
			mostrarAlerta();

			printEmpregadoInfo(empregado);
			System.out.println("\n1 - Nome \t 2 - Endereço \t 3 - Tipo \t 4 - Salário \t 5 - Método de Pagamento \t " +
					"6 - Informações do Sindicato \t 7 - Voltar");
			System.out.print(">> ");
			opcao = read.next().charAt(0);

			switch (opcao) {
				case '1':
					System.out.print("Novo nome: ");
					read.nextLine();
					empregados[index].nome = read.nextLine();
					break;

				case '2':
					System.out.print("Novo endreço: ");
					read.nextLine();
					empregados[index].endereco = read.nextLine();
					break;

				case '3':
					System.out.println("0 - " + TIPOS_EMPREGADO[0] +" \t 1 - " + TIPOS_EMPREGADO[1] +" \t 2 - " + TIPOS_EMPREGADO[2]);
					System.out.print("Selecione o tipo: ");

					empregados[index].tipo = read.nextInt();

					if (empregados[index].tipo == 2) {
						System.out.print("Comissão: ");
						empregados[index].comissao = read.nextDouble();
					}

					break;

				case '4':
					System.out.print("Novo salário: ");
					empregados[index].salario = read.nextDouble();

					if (empregados[index].tipo == 2) {
						System.out.print("Nova comissão: ");
						empregados[index].comissao = read.nextDouble();
					}
					break;

				case '5':
					System.out.println("\n0 - " + METODO_PAGAMENTO[0] +" \t 1 - " + METODO_PAGAMENTO[1] +" \t 2 - " + METODO_PAGAMENTO[2]);
					System.out.print("Selecione o método de pagamento: ");
					empregados[index].metodoPagamento = read.nextInt();
					break;

				case '6':
					if (empregados[index].pertenceSindicato)
						System.out.println("\n1 - Sair do Sindicato \t 2 - Alterar taxa sindical \t 3 - Voltar");
					else
						System.out.println("\n1 - Entrar no Sindicato \t 2 - Voltar");

					System.out.print(">> ");
					char opcaoSindicato = read.next().charAt(0);

					if (opcaoSindicato == '1') {
						if (empregados[index].pertenceSindicato) {
							empregados[index].pertenceSindicato = false;
							empregados[index].taxaSindical = 0;
						} else {
							empregados[index].pertenceSindicato = true;

							System.out.print("Taxa sindical: ");
							empregados[index].taxaSindical = read.nextDouble();
						}
					} else if (opcaoSindicato == '2') {
						if (!empregados[index].pertenceSindicato) break;

						System.out.print("Nova taxa sindical: ");
						empregados[index].taxaSindical = read.nextDouble();
					}

					break;

				case '7':
					registrarTransacao(Transacao.ALTERAR_EMPREGADO, empregado.id);
					return;

				default:
					alerta = "Opção Inválida!";
					break;
			}
		}
	}

	public static void criarCopia(Empregado empregado) {
		Empregado empregadoCopia = new Empregado();

		empregadoCopia.id = empregado.id;
		empregadoCopia.nome = empregado.nome;
		empregadoCopia.endereco = empregado.endereco;
		empregadoCopia.tipo = empregado.tipo;
		empregadoCopia.salario = empregado.salario;
		empregadoCopia.metodoPagamento = empregado.metodoPagamento;
		empregadoCopia.pertenceSindicato = empregado.pertenceSindicato;
		empregadoCopia.taxaSindical = empregado.taxaSindical;
		empregadoCopia.comissao = empregado.comissao;
		empregadoCopia.proximoPagamento = empregado.proximoPagamento;
		empregadoCopia.agendaPagamentoId = empregado.agendaPagamentoId;
		empregadoCopia.ativo = empregado.ativo;

		empregadoCopia.ponto = empregado.ponto;
		empregadoCopia.pontoIndex = empregado.pontoIndex;
		empregadoCopia.pontoUltimoPagamento = empregado.pontoUltimoPagamento;

		empregadoCopia.vendas = empregado.vendas;
		empregadoCopia.vendasIndex = empregado.vendasIndex;
		empregadoCopia.vendaUltimoPagamento = empregado.vendaUltimoPagamento;

		empregadoCopia.taxas = empregado.taxas;
		empregadoCopia.taxasIndex = empregado.taxasIndex;

		empregadoAntigo = empregadoCopia;
	}

	public static void removerEmpregado() {
		System.out.println("\n\n \t\t Remover Empregado ");
		mostrarAlerta();

		Empregado empregado = buscarEmpregado();

		if(empregado == null || empregado.ativo == false) {
            alerta = "Emregado não encontrado!";
        } else {
			System.out.println("\nO empregado buscado foi: ");
			printEmpregadoInfo(empregado);

			System.out.print("\nTem certeza que dseja removê-lo? (s/n) ");
			char resposta = read.next().charAt(0);

			if (resposta == 's' || resposta == 'S') {
				empregado.ativo = false;
				--totalEmpregados;

				registrarTransacao(Transacao.REMOVER_EMPREGADO, empregado.id);
				alerta = "Empregado removido com sucesso!";
			}
		}
	}

	public static void rodarFolhaDePagamento() {
		System.out.println("\n\n \t\t Rodando Folha de Pagamento");
		mostrarAlerta();
		int totalPagamentos = 0;

		if (totalEmpregados == 0) alerta = "Sem empregados cadastrados!";

		System.out.println("\n1 - Usar data atual \t 2 - Digitar data \t 3 - Voltar");
		System.out.print(">> ");
		int opcao = read.nextInt();
		Date data;

		switch (opcao) {
			case 1:
				data = new Date();
				break;

			case 2:
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				System.out.print("Digite a data (formato: dd/mm/yyyy): ");
				read.nextLine();
				String dataStr = read.nextLine();

				try {
					data = df.parse(dataStr);
				} catch (ParseException e) {
					alerta = "Formato de data inválida!";
					return;
				}

				break;

			default:
				alerta = "Opção inválida!";
				return;
		}

		for (Empregado empregado : empregados) {
			if (empregado == null) break;
			if (empregado.ativo == false) continue;

			if (empregado.proximoPagamento.equals(data)) {
				realizarPagamento(empregado);
				++totalPagamentos;
				empregados[empregado.id - 1].proximoPagamento = proximoPagamento(data, empregado);
			}
		}

		if (totalPagamentos == 0) {
			alerta = "Sem pagametos para a data selecionada!";
		} else {
			alerta = "Foram realizados " +totalPagamentos +" pagamentos";
		}
	}

	public static void realizarPagamento(Empregado empregado) {
		double salario = 0;

		if (empregado.tipo == 0) {
			for (int i = empregado.pontoUltimoPagamento; i <= empregado.pontoIndex; ++i) {
				CartaoPonto ponto = empregado.ponto[i];

				if (ponto == null) {
					empregado.pontoUltimoPagamento = i;
					break;
				} else {
					if (ponto.horas > 8) {
						salario += 8 * empregado.salario;
						salario += (ponto.horas - 8) * (empregado.salario * 1.5);
					} else {
						salario += ponto.horas * empregado.salario;
					}
				}
			}
		} else if (empregado.tipo == 2) {
			salario = empregado.salario / 2;

			double totalVendas = 0;
			for (int i = empregado.vendaUltimoPagamento; i <= empregado.vendasIndex; ++i) {
				Venda venda = empregado.vendas[i];

				if (venda == null) {
					empregado.vendaUltimoPagamento = i;
					break;
				} else {
					totalVendas += venda.valor;
				}
			}

			salario += totalVendas * (empregado.comissao/100);
		} else {
			salario += empregado.salario;
		}

		salario -= calcularTaxasSindicais(empregado);

		System.out.println("\nEmpregado: " +empregado.nome);
		System.out.println("Salário: " +salario);
		System.out.println("Método de pagamenmto: " +METODO_PAGAMENTO[empregado.metodoPagamento]);
	}

	public static double calcularTaxasSindicais(Empregado empregado) {
		if (empregado.pertenceSindicato) {
			double taxas = 0;

			taxas += empregado.taxaSindical;

			for (TaxaServico taxa : empregado.taxas) {
				if (taxa == null) break;

				if (taxa.mes == Calendar.getInstance().get(Calendar.MONTH)) {
					taxas += taxa.valor;
				}
			}

			return taxas;
		} else {
			return 0;
		}
	}

	public static void lancarCartaoPonto() {
		CartaoPonto ponto = new CartaoPonto();

		System.out.println("\n\n \t\t Lançar Cartão e Ponto");
		mostrarAlerta();

		Empregado empregado = buscarEmpregado();

		if (empregado != null) {
			System.out.println("\nO empregado buscado foi: ");
			printEmpregadoInfo(empregado);

			System.out.print("\nHoras trabalhadas: ");
			ponto.horas =  read.nextInt();

			ponto.empregadoId 	= empregado.id;
			ponto.ativo 		= true;
			ponto.data 			= new Date();

			System.out.println("\nRevisar informações: ");
			printCartaoPontoInfo(ponto);
			System.out.print("\nDeseja salvar? (s/n) ");
			char resposta = read.next().charAt(0);

			if (resposta == 's' || resposta == 'S') {
				empregado.ponto[empregado.pontoIndex++] = ponto;

				registrarTransacao(Transacao.LANCAR_PONTO, empregado.id);
				alerta = "Cartão de ponto salvo com sucesso!";
			}
		} else {
			alerta = "Empregado não encontrado!";
			return;
		}
	}

	public static void printCartaoPontoInfo(CartaoPonto ponto) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		System.out.println("\nEmpregado: " +empregados[ponto.empregadoId-1].nome);
		System.out.println("Horas Trabalhadas: " +ponto.horas);
		System.out.println("Data: " +df.format(ponto.data));
	}

	public static void lancarVenda() {
        Venda venda = new Venda();

        System.out.println("\n\n \t\t Lançar Venda");
		mostrarAlerta();

		Empregado empregado = buscarEmpregado();

		if (empregado != null) {
			System.out.println("\nO empregado buscado foi: ");
			printEmpregadoInfo(empregado);

			System.out.print("\nValor da venda: R$ ");
			venda.valor	= read.nextDouble();

			venda.empregadoId 	= empregado.id;
			venda.ativo 		= true;
			venda.data 			= new Date();

			System.out.println("\nRevisar informações: ");
			printVendaInfo(venda);
			System.out.print("\nDeseja salvar? (s/n) ");
			char resposta = read.next().charAt(0);

			if (resposta == 's' || resposta == 'S') {
				empregado.vendas[empregado.vendasIndex++] = venda;

				registrarTransacao(Transacao.LANCAR_VENDA, empregado.id);
				alerta = "Venda salva com sucesso!";
			}
		} else {
			alerta = "Empregado não encontrado!";
			return;
		}
    }

	public static void printVendaInfo(Venda venda) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		System.out.println("\nEmpregado: " +empregados[venda.empregadoId-1].nome);
		System.out.println("Valor da venda: R$ " +venda.valor);
		System.out.println("Data: " +df.format(venda.data));
	}

	public static void lancarTaxaDeServico() {
		TaxaServico taxaServico = new TaxaServico();

		System.out.println("\n\n \t\t Lançar Taxa de Serviço");
		mostrarAlerta();

		Empregado empregado = buscarEmpregado();

		if (empregado != null) {
			System.out.println("\nO empregado buscado foi: ");
			printEmpregadoInfo(empregado);

			System.out.print("\nValor da taxa: R$ ");
			taxaServico.valor = read.nextDouble();

			System.out.print("Mês da taxa (em números): ");
			taxaServico.mes = read.nextInt();

			taxaServico.empregadoId = empregado.id;
			taxaServico.ativo 			= true;

			System.out.println("\nRevisar informações: ");
			printTaxaServicoInfo(taxaServico);
			System.out.print("\nDeseja salvar? (s/n) ");
			char resposta = read.next().charAt(0);

			if (resposta == 's' || resposta == 'S') {
				empregado.taxas[empregado.taxasIndex++] = taxaServico;

				registrarTransacao(Transacao.LANCAR_TAXA, empregado.id);
				alerta = "Taxa de servico salva com sucesso!";
			}
		} else {
			alerta = "Empregado não encontrado!";
			return;
		}
	}

	public static void printTaxaServicoInfo(TaxaServico taxa) {
		System.out.println("\nEmpregado: " +empregados[taxa.empregadoId-1].nome);
		System.out.println("Valor da taxa: R$ " +taxa.valor);
		System.out.println("Mês: " +new DateFormatSymbols().getMonths()[taxa.mes - 1]);
	}

	public static void listarAgendasDePagamento() {
		char opcao = '0';
		while (opcao != '2') {
			System.out.println("\n\n \t\t Agendas de Pagamento\n");
			mostrarAlerta();

			for (AgendaPagamento agenda : agendasPagamento) {
				if(agenda == null) break;

				System.out.println(agenda.id +" - " +agenda.titulo);
			}

			System.out.println("\n1 - Nova agenda \t 2 - Associar empregado a agenda \t 3 - Voltar");
			System.out.print(">> ");
			opcao = read.next().charAt(0);

			switch (opcao) {
				case '1':
					novaAgenda();
					break;
				case '2':
					associarAgenda();
					break;
				case '3':
					return;
				default:
					alerta = "Opção Inválida!";
			}
		}
	}

	public static void novaAgenda() {
		AgendaPagamento agenda = new AgendaPagamento();

		do {
			System.out.println("\n\n \t\t Nova Agendas de Pagamento\n");
			mostrarAlerta();

			System.out.print("Identificador da agenda: ");
			String periodo = read.next();

			agenda.id = agendasPagamentoIndex + 1;
			if (periodo.toLowerCase().equals("mensal")) {
				String dia = read.next();

				agenda.titulo = periodo +" " +dia;
				agenda.mensal = true;
				agenda.diaMes = Integer.parseInt(dia);
			} else if (periodo.toLowerCase().equals("semanal")) {
				String intervalo = read.next();
				String diaSemana = read.next();

				agenda.titulo = periodo +" " +intervalo +" " +diaSemana;
				agenda.semanal = true;
				agenda.intervaloSemanas = Integer.parseInt(intervalo);
				agenda.diaSemana = diaSemana(diaSemana);
			} else {
				alerta = "Identificador de agenda inválido!\n";
				break;
			}

			agendasPagamento[agendasPagamentoIndex++] = agenda;
		} while (alerta != null);

		if (alerta == null) alerta = "Agenda salva com sucesso!";
	}

	private static void associarAgenda() {
		System.out.println("\n\n \t\t Associar empregado a agenda\n");
		mostrarAlerta();

		Empregado empregado = buscarEmpregado();

		if (empregado == null) {
			alerta = "Empregado não encontrado!";
			return;
		}

		for (AgendaPagamento agenda : agendasPagamento) {
			if(agenda == null) break;

			System.out.println(agenda.id +" - " +agenda.titulo);
		}

		System.out.print("\nSelecione a nova agenda: ");
		int agendaId = read.nextInt();

		if (agendasPagamento[agendaId - 1] == null || agendasPagamento[agendaId - 1].ativo == false) {
			alerta = "Agenda inválida!";
		} else {
			empregados[empregado.id - 1].agendaPagamentoId = agendaId;
			alerta = "Agenda associada com sucesso!";
		}
	}

	public static int diaSemana(String diaSemana) {
		switch (diaSemana.toLowerCase()) {
			case "domingo":
				return Calendar.SUNDAY;

			case "segunda":
				return Calendar.MONDAY;

			case "terca":
			case "terça":
				return Calendar.TUESDAY;

			case "quarta":
				return Calendar.WEDNESDAY;

			case "quinta":
				return Calendar.THURSDAY;

			case "sexta":
				return Calendar.FRIDAY;

			case "sábado":
			case "sabado":
				return Calendar.SATURDAY;

			default:
				return Calendar.FRIDAY;
		}
	}

}
