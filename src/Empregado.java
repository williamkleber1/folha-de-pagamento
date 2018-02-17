
package folhaDePagamento;
import java.util.Map;
import java.util.Scanner;

public class Empregado {
	
	private String nome = new String();
	private String codigo;
	private String endereco = new String();
	private String Agenda;
	private boolean sindicato;
	private int tipoAgenda;
	private double taxaServico;
	private String servicos;
	private double taxaSindical;
	
	private final double salarioPorMes = 2000;
	private double salario;

	public Empregado (int cod) {
		setTaxaServico(0);
		Scanner scan = new Scanner(System.in);
		System.out.println("NOME: ");
		setNome(scan.nextLine());
		System.out.println("ENDEREÇO: ");
		setEndereco(scan.nextLine());
		System.out.println("ASSOCIAR AO SINDICATO?\n1-SIM    0-NÃO");
		if(scan.nextInt() == 1) {
			setSindicato(true);
			System.out.println("DIGITE A TAXA DO SINDICATO");
			setTaxaSindical(scan.nextDouble());
		}
		else
			setSindicato(false);
	}
	
	public void salario() {
		double s = getSalario();
		if(isSindicato() == true) {
			double taxa = s * (getTaxaSindical()/100);
			setSalario(s-taxa);
		}
		double servico = s * (getTaxaServico()/100);
		setSalario(s-servico);
	}
		
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public String getAgenda() {
		return Agenda;
	}

	public void setAgenda(String agenda) {
		Agenda = agenda;
	}
	
	public double getSalarioPorMes() {
		return salarioPorMes;
	}
	
	public boolean isSindicato() {
		return sindicato;
	}

	public void setSindicato(boolean sindicato) {
		this.sindicato = sindicato;
	}
	
	public int getTipoAgenda() {
		return tipoAgenda;
	}

	public void setTipoAgenda(int tipoAgenda) {
		this.tipoAgenda = tipoAgenda;
	}

	public double getTaxaServico() {
		return taxaServico;
	}

	public void setTaxaServico(int taxaServico) {
		this.taxaServico = taxaServico;
	}

	public String getServicos() {
		return servicos;
	}

	public void setServicos(String servicos) {
		this.servicos = servicos;
	}

	public double getTaxaSindical() {
		return taxaSindical;
	}

	public void setTaxaSindical(double taxaSindical) {
		this.taxaSindical = taxaSindical;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String sind;
		if(isSindicato()==true) {
			sind = "ASSOCIADO AO SINDICATO - TAXA -> " + getTaxaSindical();
		}else {
			sind = "SEM ASSOCIAÇÃO COM O SINDICATO";
		}
		return "NOME: " + this.nome + "\nCODIGO: " +  this.codigo + "\nENDERECO: " + this.endereco + "\n" + sind + "\n";
	}
	
	
	
	
}
