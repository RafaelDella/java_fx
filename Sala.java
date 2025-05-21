import java.util.ArrayList;

public class Sala {
    private ArrayList<Aluno> lista_reserva = new ArrayList<Aluno>();
    private int id_sala;
    private boolean sala_reservada;

    private void Reserva(Aluno aluno){
        if(lista_reserva.size() < 8){
            lista_reserva.add(aluno);
            System.out.print("Aluno Adicionado!");
        } else {
            System.out.print("Sala de Estudo LOTADA");
            System.out.print("Selecione outra sala!");
        }
    }
}
