import java.time.*;

import java.util.ArrayList;

public class Main {

    private static final ArrayList<Turma> listaTurmas = Turma.listaTurmas;
    private static final ArrayList<Aluno> listaAlunos = Aluno.listaAlunos;

    public static void main(String[] args) {
        menuPrincipal();
    }

    public static void menuPrincipal() {
        System.out.println("\n==== Secretaria ====");
        System.out.println("1 - Alunos");
        System.out.println("2 - Turmas");
        System.out.println("3 - Sair");
        String opcao = Leitura.dados("Digite a opção desejada: ");
        switch (opcao) {
            case "1":
                menuAlunos();
                break;
            case "2":
                menuTurmas();
                break;
            case "3":
                System.out.println("Até breve...");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida! Tente novamente");
                menuPrincipal();
        }
    }

    private static void menuTurmas() {
        System.out.println("\n==== Turmas ====");
        System.out.println("1 - Listar Turmas");
        System.out.println("2 - Cadastrar Turma");
        System.out.println("3 - Atualizar Turma");
        System.out.println("4 - Excluir Turma");
        System.out.println("5 - Voltar ao menu principal");
        String opcao = Leitura.dados("Digite a opção desejada: ");
        switch (opcao) {
            case "1":
                listarTurmas();
                menuTurmas();
                break;
            case "2":
                cadastrarTurma();
                menuTurmas();
                break;
            case "3":
                atualizarTurma();
                menuTurmas();
                break;
            case "4":
                excluirTurma();
                menuTurmas();
                break;
            case "5":
                menuPrincipal();
                break;
            default:
                System.out.println("Opção inválida! Tente novamente");
                menuTurmas();
        }
    }

    protected static void menuAlunos() {
        System.out.println("\n==== Alunos ====");
        System.out.println("1 - Listar Alunos");
        System.out.println("2 - Cadastrar Aluno");
        System.out.println("3 - Atualizar Aluno");
        System.out.println("4 - Excluir Aluno");
        System.out.println("5 - Voltar ao menu principal");
        String opcao = Leitura.dados("Digite a opção desejada: ");
        switch (opcao) {
            case "1":
                listarAlunos();
                menuAlunos();
                break;
            case "2":
                cadastrarAluno();
                break;
            case "3":
                atualizarAluno();
                break;
            case "4":
                excluirAluno();
                break;
            case "5":
                menuPrincipal();
                break;
            default:
                System.out.println("Opção inválida! Tente novamente");
                menuAlunos();
        }
    }

    private static void excluirTurma() {
        if (isVazio()) {
            System.out.println("Não há turmas cadastradas");
            return;
        }
        Turma.listarTurmasIndiceSigla();
        int idExcluir = Turma.validaIdTurma();
        if (confirmaExclusao()) {
            //listaTurmas.remove(opcaoUsuario);
            Turma.listaTurmas.get(idExcluir).setAtivo(false);
            System.out.println("Turma excluída com sucesso!");
        } else {
            System.out.println("Operação cancelada");
        }
    }

    private static boolean isVazio() {
        if (listaTurmas.isEmpty()) return true;

        for (Turma turma : listaTurmas) {
            if (turma.isAtivo()) return false;
        }

        return true;
    }

    protected static boolean confirmaExclusao() {
        while (true) {
            String confirma = Leitura.dados("Você tem certeza? (S/N): ").toUpperCase();
            switch (confirma) {
                case "S":
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Opção inválida, digite S para sim ou N para não");
                    break;
            }
        }
    }

    protected static void atualizarTurma() {
        if (isVazio()) {
            System.out.println("Não há turmas cadastradas");
            return;
        }

        Turma.listarTurmasIndiceSigla();

        int idAtualizar = Turma.validaIdTurma();

        System.out.printf("O período atual é: %s", listaTurmas.get(idAtualizar).getPeriodo());
        Turma.atualizarParcial("período", idAtualizar);

        System.out.printf("O curso atual é: %s", listaTurmas.get(idAtualizar).getCurso());
        Turma.atualizarParcial("curso", idAtualizar);

        System.out.printf("A sigla atual é: %s", listaTurmas.get(idAtualizar).getSigla());
        Turma.atualizarParcial("sigla", idAtualizar);

        //        System.out.println("O período atual é: " + listaTurmas.get(idAtualizar).getPeriodo());
        //        System.out.printf("O período atual é: %s", listaTurmas.get(idAtualizar).getPeriodo());
        //        atualizarPeriodo(idAtualizar);
        //
        //        System.out.printf("O curso atual é: %s", listaTurmas.get(idAtualizar).getCurso());
        //        atualizarCurso(idAtualizar);
        //
        //        System.out.printf("A sigla atual é: %s", listaTurmas.get(idAtualizar).getSigla());
        //        atualizarSigla(idAtualizar);
    }

    private static void cadastrarTurma() {
        Periodo periodo = Turma.validarPeriodo();

        String curso = Leitura.dados("Digite o curso: ");
        while (isCharacter(curso)) {
            System.out.println("Nome de curso inválido! Não use números ou caracteres especiais, por favor");
            curso = Leitura.dados("Digite o curso: ");
        }

        String sigla = Leitura.dados("Digite a sigla: ");
        while (!Turma.validarSigla(sigla)) {
            System.out.println("Sigla inválida! Precisa conter texto e não pode ser repetida");
            sigla = Leitura.dados("Digite a sigla: ");
        }

        Turma turma = new Turma(curso, sigla, periodo);
        listaTurmas.add(turma);
        System.out.println("Turma adicionada com sucesso!!");
    }

    protected static boolean isCharacter(String texto) {
        String textoSemNumeros = texto.replaceAll("\\d", "");
        return texto.isBlank() || !texto.equals(textoSemNumeros);
    }

    private static void listarTurmas() {
        if (isVazio()) {
            System.out.println("Não há turmas cadastradas");
            return;
        }
        for (Turma t : listaTurmas) {
            if (t.isAtivo()) System.out.println(t);
        }
    }

    private static void excluirAluno() {}

    private static void atualizarAluno() {}

    private static void cadastrarAluno() {

        //TODO nome sem caracteres especiais ou espaços

        System.out.println("BEM VINDO AO CADASTRADOR DE ALUNOS");
        LocalDate dataFormatada;

        String nome = Aluno.atualizaNome();

        dataFormatada = Aluno.convertorParaData();

        Turma turmaSelecionada = Aluno.atualizaTurmaAluno();

        System.out.printf(
            """
            Confirme as informações do Aluno
            Nome: %s
            Data de Nascimento: %s
            Curso: %s""",
            nome,
            dataFormatada,
            turmaSelecionada
        );


        boolean quebraLoop = true;
        while (quebraLoop) {
            String confirma = Leitura.dados("\nAs informações do Aluno está correta? (S/N) ").toUpperCase();

            switch (confirma) {
                case "S":
                    break;
                case "N":
                    Aluno newAluno = Aluno.VerificadorDadosAlunos(nome,dataFormatada, turmaSelecionada);
                    listaAlunos.add(newAluno);
                    System.out.println("Aluno adicionado com sucesso!");
                    menuPrincipal();
                    quebraLoop = false;
                    break;
                default:
                    System.out.println("Opção inválida. Digite novamente!");

            }
            quebraLoop = false;
            break;
        }
        Aluno aluno = new Aluno(nome, dataFormatada, turmaSelecionada);
        listaAlunos.add(aluno);
        System.out.println("Aluno adicionado com sucesso!");
        menuPrincipal();
    }



    private static void listarAlunos() {
        if (Aluno.isVazioAlunos()) {
            System.out.println("Não há alunos cadastrados");
            return;
        }
        for (Aluno a : listaAlunos) {
            if (a.isAtivo()) System.out.printf(
                "1 - Aluno: %s Turma: %s Data de nascimento: %s ",
                a.getNome(),
                a.getTurma(),
                a.getDataNascimento()
            );
        }
    }


}
