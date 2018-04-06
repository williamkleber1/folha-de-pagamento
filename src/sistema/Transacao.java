package sistema;


public class Transacao {

    static final int ADICIONAR_EMPREGADO = 0, REMOVER_EMPREGADO = 1, LANCAR_PONTO = 2,
            LANCAR_VENDA = 3, LANCAR_TAXA = 4, ALTERAR_EMPREGADO = 5;

    int tipo;
    int registroId;

}
