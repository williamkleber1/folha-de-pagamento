
package folhaDePagamento;

import java.awt.List;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class FolhaDePagamento<T> {
	
	LinkedList<T> list = new LinkedList<T>();

	public void adicionar (T func) {
		list.add(func);
	}
	
	public void remover (String cod) {
		int i = verificarCod(cod);
		if(i != -1)
			list.remove(i);
		else
			System.out.println("EMPREGADO NÃO CADASTRADO");
	}
	
	public int verificarCod (String cod) {
		int index = -1;
		int conf = 0;
		for (T i : list) {
			Empregado e = (Empregado) i;
			++index;
			if(cod.equals(e.getCodigo())) {
				conf = 1;
				break;
			}
		}
		
		if(conf == 1)
			return index;
		else
			return -1;
	}
	
	public void alterarAtributos (String cod) {
		
		int i = verificarCod(cod);
		int opcao;
		Scanner scan = new Scanner(System.in);
		Scanner scanC = new Scanner(System.in);
		
		if(i == -1)
			System.out.println("EMPREGADO NÃO CADASTRADO");
		else {
			Empregado e = (Empregado) list.get(i);
			System.out.println("O QUE DESEJA ALTERAR?\n"
					+ "1-Nome\n2-Endereço\n3-Associação Sindical");
			opcao = scan.nextInt();
			if(opcao == 1) {
				System.out.println("DIGITE O NOVO NOME: ");
				e.setNome(scanC.nextLine());
			}else if (opcao == 2) {
				System.out.println("DIGITE O NOVO ENDEREÇO: ");
				e.setEndereco(scanC.nextLine());
			}else if (opcao == 3) {
				System.out.println("ASSOCIAR(1) OU DESASSOCIAR(0)?");
				opcao = scan.nextInt();
				if(opcao == 1)
					e.setSindicato(true);
				else if(opcao == 0)
					e.setSindicato(false);
			}
		}
	}

	public void agenda() {
		for (T i : list) {
			System.out.println(i);
		}
	}
	
	public void criarAgenda (String cod) {
		int i = verificarCod(cod);
		if(i == -1)
			System.out.println("EMPREGADO NÃO CADASTRADO");
		else {
			String[] novaAgenda = {"Dia 1 de todo mês", "Dia 7 de todo mês", "Dia 7 de todo mês", "Último dia útil de todo mês", "Toda semana às segundas-feiras", "Toda semana às sextas-feiras", "A cada 2 semanas às segundas-feiras"};
		
			Empregado e = (Empregado) list.get(i);
			System.out.println("SELECIONE AS OPÇÕES:\n"
					+ "1- Mensal 1\n"
					+ "2- Mensal 7\n"
					+ "3- Mensal $\n"
					+ "4- Semanal 1 segunda\n"
					+ "5- Semanal 1 sexta\n"
					+ "6- Semanal 2 segunda\n");
			
			Scanner scan = new Scanner(System.in);
			int opcao = scan.nextInt();
			e.setAgenda(novaAgenda[opcao]);
			e.setTipoAgenda(opcao+4);
		}		
	}

	public void lancarTaxaDeServico(String cod) {
		int i = verificarCod(cod);
		if(i!= -1) {
			Scanner scanC = new Scanner(System.in);
			Scanner scanI = new Scanner (System.in);
			Empregado e = (Empregado) list.get(i);
			System.out.println("DIGITE O SERVIÇO: ");
			e.setServicos(scanC.nextLine());
			System.out.println("INSIRA A TAXA: ");
			e.setTaxaServico(scanI.nextInt());
		}else {
			System.out.println("EMPREGADO NÃO CADASTRADO");
		}
	}
	
	public void emitirFolhaDePagamento () {
		Calendar calendar = Calendar.getInstance();
		
		for (T i : list) {
			Empregado e = (Empregado) i; 
			e.salario();
			if(calendar.get(calendar.DAY_OF_WEEK) == 6 && e.getTipoAgenda() == 1)
				System.out.println(e.getCodigo() + " " + e.getNome() + " " +e.getSalario());
			else if(calendar.get(calendar.DAY_OF_MONTH) == 1 && e.getTipoAgenda() == 4)
				System.out.println(e.getCodigo() + " " + e.getNome() + " " +e.getSalario());
			else if(calendar.get(calendar.DAY_OF_MONTH) == 7 && e.getTipoAgenda() == 5)
				System.out.println(e.getCodigo() + " " + e.getNome() + " " +e.getSalario());
			else if(calendar.get(calendar.DAY_OF_WEEK) == 2 && e.getTipoAgenda() == 7)
				System.out.println(e.getCodigo() + " " + e.getNome() + " " +e.getSalario());
			else if(calendar.get(calendar.DAY_OF_WEEK) == 6 && (e.getTipoAgenda() == 8 || e.getTipoAgenda() == 6))
				System.out.println(e.getCodigo() + " " + e.getNome() + " " +e.getSalario());
			else if(calendar.get(calendar.DAY_OF_WEEK) == 2 && calendar.get(calendar.WEEK_OF_MONTH)%2 == 0 && e.getTipoAgenda() == 9) 
				System.out.println(e.getCodigo() + " " + e.getNome() + " " +e.getSalario());
				
		}
		
		System.out.println("");
	}

}
