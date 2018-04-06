package sistema;

import java.util.Calendar;


public class AgendaPagamento {

    int id;
    String titulo;
    boolean ativo;

    boolean mensal = false;
    int diaMes = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

    boolean semanal = false;
    int intervaloSemanas = 1;
    int diaSemana = Calendar.FRIDAY;

}
