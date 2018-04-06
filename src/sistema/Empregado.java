package sistema;

import java.util.Date;


public class Empregado {
	
	int id;
	String nome;
	String endereco;
	int tipo;	// 0: Horário, 1: Assalariado, 2: Comissionado
	double salario;
	int metodoPagamento;	// 0: Cheque pelos correios, 1: Cheque em mãos, 2: Depósito
	boolean pertenceSindicato;
	double taxaSindical;
	double comissao;
	Date proximoPagamento;
	int agendaPagamentoId;	// As agendas default são 0: semanal, 1: mensal, 2: bi-semanal
	String sindicatoId;
	boolean ativo;

	CartaoPonto[] ponto = new CartaoPonto[365];
	int pontoIndex = 0;
	int pontoUltimoPagamento = 0;

	Venda[] vendas = new Venda[365];
	int vendasIndex = 0;
	int vendaUltimoPagamento = 0;

	TaxaServico[] taxas = new TaxaServico[24];
	int taxasIndex = 0;
}
