public class Aluno implements Pessoa{
    private String curso;
    private String matricula;
    private boolean bolsista;

    @Override
    public int VerificarPendencia() {
        return 0;
    }

    @Override
    public void DevolverLivro() {

    }

    public void RealizarFeedbackProfessor(Professor professor){

    }
}
