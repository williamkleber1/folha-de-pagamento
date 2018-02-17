package folhaDePagamento;
import java.util.Scanner;
public class Comissionado extends Empregado {

	private double comissao;
	private int vendas;
	
	public Comissionado(int cod) {
		super(cod);
		setCodigo("C" + cod);
		setSalario(getSalarioPorMes()/2);
		setAgenda("Bi-semanalmente");
		setTipoAgenda(3);
		Scanner scan = new Scanner(System.in);
		System.out.println("INSIRA A COMISSÃO: ");
		setComissao(scan.nextDouble());
	}
	
	public void vendas () {
		Scanner scan = new Scanner(System.in);
		System.out.println("NÚMERO DE VENDAS: ");
		setVendas(scan.nextInt());
		//setSalario(getSalario() + (getVendas() * (getComissao()/100)));
		double comissao = getVendas() * (getComissao()/100);
		setSalario(getSalario() + comissao);
	}
	
	public double getComissao() {
		return comissao;
	}

	public void setComissao(double comissao) {
		this.comissao = comissao;
	}

	public int getVendas() {
		return vendas;
	}

	public void setVendas(int vendas) {
		this.vendas = vendas;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "TIPO: Comisionado\nCOMISSÃO: " + this.comissao + "\nAGENDA: " + getAgenda() + "\n";
	}

}
