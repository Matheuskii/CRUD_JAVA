import java.time.*;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    protected static final ArrayList<Turma> listaTurmas = new ArrayList<>();
    protected static final ArrayList<Aluno> listaAlunos = new ArrayList<>();

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
        if (isVazio(2)) {
            System.out.println("Não há turmas cadastradas");
            return;
        }
        Turma.listarTurmasIndiceSigla();
        int idExcluir = Turma.validarId("turma");
        if (confirmaExclusao()) {
            //listaTurmas.remove(opcaoUsuario);
            listaTurmas.get(idExcluir).setAtivo(false);
            System.out.println("Turma excluída com sucesso!");
        } else {
            System.out.println("Operação cancelada");
        }
    }

    protected static boolean isVazio(int id) {
        switch (id) {
            case 1:
                if (Main.listaAlunos.isEmpty()) return true;

                for (Aluno aluno : Main.listaAlunos) {
                    if (aluno.isAtivo()) return false;
                }

                return true;
            case 2:
                if (listaTurmas.isEmpty()) return true;

                for (Turma turma : listaTurmas) {
                    if (turma.isAtivo()) return false;
                }

                return true;
        }
        return false;
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
        if (isVazio(2)) {
            System.out.println("Não há turmas cadastradas");
            return;
        }

        Turma.listarTurmasIndiceSigla();

        int idAtualizar = Turma.validarId("turma");

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

    protected static int validarItemLista(String opcao, String atributo) {
        if (opcao.isBlank()) return -1;

        int openNumero;

        try {
            openNumero = Integer.parseInt(opcao);
        } catch (NumberFormatException e) {
            return -1;
        }

        int indiceLista = openNumero - 1;
        if (Objects.equals(atributo, "turma")) {
            return indiceLista >= 0 && listaTurmas.size() > indiceLista ? indiceLista : -1;
        } else {
            return indiceLista >= 0 && listaAlunos.size() > indiceLista ? indiceLista : -1;
        }
    }

    private static void listarTurmas() {
        if (isVazio(2)) {
            System.out.println("Não há turmas cadastradas");
            return;
        }
        for (Turma t : listaTurmas) {
            if (t.isAtivo()) System.out.println(t);
        }
    }

    private static void excluirAluno() {
        if (Main.isVazio(1)) {
            System.out.println("Não há alunos cadastrados");
            menuAlunos();
        }
        Aluno.listarAlunosIndice();

        int idExcluir = Turma.validarId("aluna");
        if (confirmaExclusao()) {
            listaAlunos.get(idExcluir).setAtivo(false);
            System.out.println("Aluna excluída com sucesso!");
            menuPrincipal();
        } else {
            System.out.println("Operação cancelada");
            menuPrincipal();
        }
    }

    private static void atualizarAluno() {
        System.out.println("BEM VINDO AO ATUALIZAR DE ALUNOS");

        if (Main.isVazio(1)) {
            System.out.println("Não há alunos cadastrados");
            menuAlunos();
        }

        Aluno.listarAlunosIndice();

        int idAtualizar = Turma.validarId("aluno");

        String nome = listaAlunos.get(idAtualizar).getNome();
        LocalDate data = listaAlunos.get(idAtualizar).getDataNascimento();
        Turma turma = listaAlunos.get(idAtualizar).getTurma();

        System.out.printf(
            """
            Verifique as informações do Aluno
            Nome: %s
            Data de Nascimento: %s
            Curso: %s""",
            nome,
            data,
            turma
        );
        Aluno dados = Aluno.VerificadorDadosAlunos(nome, data, turma);
        System.out.println(dados.getNome());

        listaAlunos.get(idAtualizar).atualizarCom(dados);
        System.out.println("Aluno atualizado com sucess!!");
        menuAlunos();
    }

    private static void cadastrarAluno() {
        System.out.println("BEM VINDO AO CADASTRADOR DE ALUNOS");
        LocalDate dataNascimento;

        String nome = Aluno.cadastraNome();

        dataNascimento = Aluno.convertorParaData();

        Turma turma = Aluno.atualizaTurmaAluno();

        System.out.printf(
            """
            Confirme as informações do Aluno
            Nome: %s
            Data de Nascimento: %s
            Curso: %s""",
            nome,
            dataNascimento,
            turma
        );

        Aluno.confirmaInfoAluno(nome, dataNascimento, turma);
    }

    private static void listarAlunos() {
        if (Main.isVazio(1)) {
            System.out.println("Não há alunos cadastrados");
            return;
        }
        for (Aluno aluno : listaAlunos) {
            if (aluno.isAtivo()) System.out.println(aluno);
        }
    }
}
