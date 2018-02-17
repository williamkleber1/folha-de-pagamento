
package folhaDePagamento;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static void outrasOps(FolhaDePagamento<Horista> H, FolhaDePagamento<Assalariados> A, FolhaDePagamento<Comissionado> C) {
		System.out.println("MAIS OPERAÇÕES\n"
				+ "1- Lançar Cartão de ponto\n"
				+ "2- Lançar relatorio de venda\n"
				+ "3- Lançar taxa de serviço");
		Scanner scan = new Scanner(System.in);
		Scanner scanC = new Scanner(System.in);
		int opcao = scan.nextInt();
		int index;
		String cod;
		if(opcao == 1) {
			System.out.println("INSIRA CODIGO DE EMPREGADO");
			cod = scanC.nextLine();
			index = H.verificarCod(cod);
			if(index!= -1) {
				Horista h = H.list.get(index);
				h.cartaoDePonto();
			}else
				System.out.println("EMPREGADO NÃO CADASTRADO");
			
		}else if (opcao == 2) {
			System.out.println("INSIRA CODIGO DE EMPREGADO");
			cod = scanC.nextLine();
			index = C.verificarCod(cod);
			if(index!= -1) {
				Comissionado c = C.list.get(index);
				c.vendas();
			}else
				System.out.println("EMPREGADO NÃO CADASTRADO");
			
		}else if (opcao == 3) {
			System.out.println("INSIRA O CODIGO DE EMPREGADO");
			cod = scanC.nextLine();
			if(cod.startsWith("H", 0)) {
				H.lancarTaxaDeServico(cod);
			}else if(cod.startsWith("A", 0)) {
				A.lancarTaxaDeServico(cod);
			}else if(cod.startsWith("C", 0)) {
				C.lancarTaxaDeServico(cod);
			}else {
				System.out.println("CODIGO INVALIDO!");
			}			
		}
	}
	
	public static void main (String[] args) {
							
		FolhaDePagamento<Horista> h = new FolhaDePagamento<Horista>();
		FolhaDePagamento<Assalariados> a = new FolhaDePagamento<Assalariados>();
		FolhaDePagamento<Comissionado> c = new FolhaDePagamento<Comissionado>();
		
		Scanner scanI = new Scanner(System.in);
		Scanner scanC = new Scanner(System.in);
		
		int opcao;
		int codigo = 0;		
			
			do {
				try {
					
					System.out.println("SELECIONE A OPERAÇÃO\n");
					System.out.println("1- Adicionar empregado\n"
							+ "2- Remover Empregado\n"
							+ "3- Alterar Atributos\n"
							+ "4- Agenda\n"
							+ "5- Folha de Pagamento\n"
							+ "6- Outras\n"
							+ "0- Sair");
					
					opcao = scanI.nextInt();
					
					if(opcao == 1) {
						System.out.println("SELECIONE O TIPO\n"
								+ "1- Horista\n"
								+ "2- Assalariado\n"
								+ "3- Comissionado");
						
						opcao = scanI.nextInt();
						if(opcao == 1) {
							h.adicionar(new Horista(++codigo));
						}else if (opcao == 2) {
							a.adicionar(new Assalariados(++codigo));
						}else if(opcao == 3) {
							c.adicionar(new Comissionado(++codigo));
						}
					}else if(opcao == 2) {
						System.out.println("INSIRA O CODIGO:");
						String cod = scanC.nextLine();
						if(cod.startsWith("H", 0)) {
							h.remover(cod);
						}else if(cod.startsWith("A", 0)) {
							a.remover(cod);
						}else if(cod.startsWith("C", 0)) {
							c.remover(cod);
						}else {
							System.out.println("CODIGO INVALIDO!");
						}					
					}else if(opcao == 3) {
						System.out.println("INSIRA O CODIGO DE EMPREGADO");
						String cod = scanC.nextLine();
						if(cod.startsWith("H", 0)) {
							h.alterarAtributos(cod);
						}else if(cod.startsWith("A", 0)) {
							a.alterarAtributos(cod);
						}else if(cod.startsWith("C", 0)) {
							c.alterarAtributos(cod);
						}else {
							System.out.println("CODIGO INVALIDO!");
						}			
					}else if(opcao == 4){
						System.out.println("1-Ver agendas\n2-Criar nova agenda");
						opcao = scanI.nextInt();
						if(opcao == 1) {
							System.out.println("HORISTAS:");
							h.agenda();
							System.out.println("ASSALARIADOS:");
							a.agenda();
							System.out.println("COMISSIONADOS:");
							c.agenda();
						}
						else if(opcao == 2) {
							System.out.println("INSIRA O CODIGO DO EMPREGADO");
							String cod = scanC.nextLine();
							if(cod.startsWith("H", 0)) {
								h.criarAgenda(cod);
							}else if(cod.startsWith("A", 0)) {
								a.criarAgenda(cod);
							}else if(cod.startsWith("C", 0)) {
								c.criarAgenda(cod);
							}else {
								System.out.println("CODIGO INVALIDO!");
							}
						}
					}else if (opcao == 5){
						System.out.println("PAGAMENTOS PARA HOJE:");
						h.emitirFolhaDePagamento();
						a.emitirFolhaDePagamento();
						c.emitirFolhaDePagamento();
					}else if(opcao == 6){
						outrasOps(h, a, c);
					}else if (opcao == 0)
						break;
								
				}catch( InputMismatchException e) {
					System.out.println("ENTRADA INVALIDA");
					
				}finally {
					System.out.println("DESEJA REALIZAR OUTRA OPERAÇÃO?\n1-Sim           0-Não");
					opcao = scanI.nextInt();
				}
			}while(opcao!=0);
			System.out.println("PROCESSO ENCERRADO");
		}
}
