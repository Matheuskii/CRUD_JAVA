import java.util.ArrayList;

public class Turma {

    protected static final ArrayList<Turma> listaTurmas = new ArrayList<>();

    private String curso;
    private String sigla;
    private Periodo periodo;
    private boolean ativo;

    public Turma(String curso, String sigla, Periodo periodo) {
        this.curso = curso;
        this.sigla = sigla;
        this.periodo = periodo;
        this.ativo = true;
    }

    public Turma() {
        this.curso = "";
        this.sigla = "";
        this.periodo = Periodo.MATUTINO;
        this.ativo = true;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Turma{" + "curso='" + curso + '\'' + ", sigla='" + sigla + '\'' + ", periodo=" + periodo + '}';
    }

    //MÉTODOS INSANOS

    public static int validarItemLista(String opcao) {
        if (opcao.isBlank()) return -1;

        int opcaoNumero = -1;

        try {
            opcaoNumero = Integer.parseInt(opcao);
        } catch (NumberFormatException e) {
            return -1;
        }

        int indiceLista = opcaoNumero - 1;
        return indiceLista >= 0 && listaTurmas.size() > indiceLista ? indiceLista : -1;
    }

    protected static boolean listarTurmasIndiceSigla() {
        if (listaTurmas.isEmpty()) {
            return false;
        }
        System.out.println("\nLista das Turmas:");
        for (int i = 0; i < listaTurmas.size(); i++) {
            if (listaTurmas.get(i).isAtivo()) System.out.printf("\n%d - %s", i + 1, listaTurmas.get(i).getSigla());
        }
        return true;
    }

    protected static void atualizarParcial(String atributo, int idAtualizar) {
        boolean rodarNovamente = true;
        while (rodarNovamente) {
            String opcao = Leitura.dados("\nDeseja modificar " + atributo + " ? (S/N): ").toUpperCase();
            switch (opcao) {
                case "S":
                    switch (atributo) {
                        case "período":
                            Periodo periodo = validarPeriodo();
                            listaTurmas.get(idAtualizar).setPeriodo(periodo);
                            break;
                        case "curso":
                            String curso = validarCurso();
                            listaTurmas.get(idAtualizar).setCurso(curso);
                            break;
                        case "sigla":
                            String sigla = validarSigla();
                            listaTurmas.get(idAtualizar).setSigla(sigla);
                            break;
                    }
                    System.out.println(atributo + " atualizado com sucesso!");
                    rodarNovamente = false;
                    break;
                case "N":
                    rodarNovamente = false;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha S para SIM ou N para NÃO");
            }
        }
    }

    protected static void atualizaPeriodo(int idAtualizar) {
        boolean rodarNovamente = false;
        while (rodarNovamente) {
            String opcaoPeriodo = Leitura.dados("Deseja modificar o periodo? (S/N)").toUpperCase();
            switch (opcaoPeriodo) {
                case "S":
                    listaTurmas.get(idAtualizar).setPeriodo(validarPeriodo());
                    rodarNovamente = false;
                    break;
                case "N":
                    rodarNovamente = false;
                    break;
                default:
                    System.out.println("Opção inválida, digite novamente");
                    continue;
            }
            break;
        }
    }

    protected static String validarSigla() {
        String sigla = Leitura.dados("Digite a sigla: ");
        while (!validarSigla(sigla)) {
            System.out.println("Sigla inválida! Precisa conter texto e não pode ser repetida");
            sigla = Leitura.dados("Digite a sigla: ");
        }
        return sigla;
    }

    protected static String validarCurso() {
        String curso = Leitura.dados("Digite o curso: ");
        while (Main.isCharacter(curso)) {
            System.out.println("Nome de curso inválido! Não use números ou caracteres especiais, por favor");
            curso = Leitura.dados("Digite o curso: ");
        }
        return curso;
    }

    protected static int validaIdTurma() {
        String opcao = Leitura.dados("\nDigite o número da turma desejada: ");
        int opcaoValida = -1;
        int opcaoUsuario = -1;
        while (opcaoValida == -1) {
            opcaoUsuario = validarItemLista(opcao);

            if (opcaoUsuario == -1) {
                System.out.println("Opção inválida! Digite novamente: ");
                opcao = Leitura.dados("Digite o número da turma desejada: ");
            } else {
                opcaoValida = opcaoUsuario;
            }
        }
        return opcaoValida;
    }

    protected static boolean validarSigla(String sigla) {
        if (sigla.isBlank()) return false;

        for (Turma turma : listaTurmas) {
            if (turma.getSigla().equals(sigla)) {
                return false;
            }
        }
        return true;
    }

    protected static Periodo validarPeriodo() {
        String opcaoPeriodo = Leitura.dados(
            """
            Digite o número do período escolhido:
            1 - Matutino
            2 - Vespertino
            3 - Noturno
            4 - Integral"""
        );
        switch (opcaoPeriodo) {
            case "1":
                return Periodo.MATUTINO;
            case "2":
                return Periodo.VESPERTINO;
            case "3":
                return Periodo.NOTURNO;
            case "4":
                return Periodo.INTEGRAL;
            default:
                System.out.println("Opção inválida, digite novamente");
                return validarPeriodo();
        }
    }
}
