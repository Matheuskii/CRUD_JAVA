public class Turma {

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

    protected static boolean listarTurmasIndiceSigla() {
        if (Main.listaTurmas.isEmpty()) {
            return false;
        }
        System.out.println("\nLista das Turmas:");
        for (int i = 0; i < Main.listaTurmas.size(); i++) {
            if (Main.listaTurmas.get(i).isAtivo()) System.out.printf(
                "\n%d - %s",
                i + 1,
                Main.listaTurmas.get(i).getSigla()
            );
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
                            Main.listaTurmas.get(idAtualizar).setPeriodo(periodo);
                            break;
                        case "curso":
                            String curso = validarCurso();
                            Main.listaTurmas.get(idAtualizar).setCurso(curso);
                            break;
                        case "sigla":
                            String sigla = validarSigla();
                            Main.listaTurmas.get(idAtualizar).setSigla(sigla);
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
        boolean rodarNovamente = true;
        while (rodarNovamente) {
            String opcaoPeriodo = Leitura.dados("Deseja modificar o periodo? (S/N)").toUpperCase();
            switch (opcaoPeriodo) {
                case "S":
                    Main.listaTurmas.get(idAtualizar).setPeriodo(validarPeriodo());
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

    protected static int validarId(String atributo) {
        String opcao = Leitura.dados("\nDigite o número da " + atributo + " desejada: ");
        int opcaoValida = -1;
        int opcaoUsuario = -1;
        while (opcaoValida == -1) {
            opcaoUsuario = Main.validarItemLista(opcao, atributo);

            if (opcaoUsuario == -1) {
                System.out.println("Opção inválida! Digite novamente: ");
                opcao = Leitura.dados("\nDigite o número da " + atributo + " desejada: ");
            } else {
                opcaoValida = opcaoUsuario;
            }
        }
        return opcaoValida;
    }

    protected static boolean validarSigla(String sigla) {
        if (sigla.isBlank()) return false;

        for (Turma turma : Main.listaTurmas) {
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
        return switch (opcaoPeriodo) {
            case "1" -> Periodo.MATUTINO;
            case "2" -> Periodo.VESPERTINO;
            case "3" -> Periodo.NOTURNO;
            case "4" -> Periodo.INTEGRAL;
            default -> {
                System.out.println("Opção inválida, digite novamente");
                yield validarPeriodo();
            }
        };
    }
}
