import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


public class Main {

    private static final ArrayList<Aluno> listaAlunos = new ArrayList<>();
    private static final ArrayList<Turma> listaTurmas = new ArrayList<>();

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

    private static void menuAlunos() {
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
        if (isVazio(listaTurmas)) {
            System.out.println("Não há turmas cadastradas");
            return;
        }
        listarTurmasIndiceSigla();
        int idExcluir = validaIdTurma();
        if (confirmaExclusao()) {
            //listaTurmas.remove(opcaoUsuario);
            listaTurmas.get(idExcluir).setAtivo(false);
            System.out.println("Turma excluída com sucesso!");
        } else {
            System.out.println("Operação cancelada");
        }
    }

    private static boolean isVazio(ArrayList<Turma> listaTurmas) {
        if (listaTurmas.isEmpty()) return true;

        for (Turma turma : listaTurmas) {
            if (turma.isAtivo()) return false;
        }

        return true;
    }

    private static boolean confirmaExclusao() {
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

    private static int validarItemLista(String opcao) {
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

    private static boolean listarTurmasIndiceSigla() {
        if (listaTurmas.isEmpty()) {

            return false;
        }
        System.out.println("\nLista das Turmas:");
        for (int i = 0; i < listaTurmas.size(); i++) {
            if (listaTurmas.get(i).isAtivo())
                System.out.printf("\n%d - %s", i + 1, listaTurmas.get(i).getSigla());

        }
        return true;

    }

    private static void atualizarTurma() {
        if (isVazio(listaTurmas)) {
            System.out.println("Não há turmas cadastradas");
            return;
        }
        listarTurmasIndiceSigla();
        int idAtualizar = validaIdTurma();
        String periodo = listaTurmas.get(idAtualizar).getPeriodo().toString();
        String sigla = listaTurmas.get(idAtualizar).getSigla();
        String curso = listaTurmas.get(idAtualizar).getCurso();

        System.out.printf("\nO período atual é: %s ", periodo);

        while (true) {
            String opcaoPeriodo = Leitura.dados("Deseja modificar o periodo? (S/N)").toUpperCase();
            switch (opcaoPeriodo) {
                case "S":
                    listaTurmas.get(idAtualizar).setPeriodo(validarPeriodo());
                    break;
                case "N":
                    break;
                default:
                    System.out.println("Opção inválida, digite novamente");
                    continue;
            }
            break;
        }
        System.out.printf("\nO curso atual é: %s ", curso);

        atualizaTurma(idAtualizar, true);

        System.out.printf("\nA sigla atual é: %s ", sigla);

        atualizaTurma(idAtualizar, false);


    }

    private static void atualizaTurma(int idAtualizar, boolean auebaaaaaa) {
        if (auebaaaaaa) {

            while (true) {
                String opcaoCurso = Leitura.dados("Deseja modificar o curso? (S/N)").toUpperCase();
                switch (opcaoCurso) {
                    case "S":

                        String newCurso = Leitura.dados("Digite o curso: ");
                        while (!isCharacter(newCurso)) {
                            System.out.println("Nome de curso inválido! Não use números ou caracteres especiais, por favor");
                            newCurso = Leitura.dados("Digite o curso: ");
                        }

                        listaTurmas.get(idAtualizar).setCurso(newCurso);
                        break;
                    case "N":
                        break;
                    default:
                        System.out.println("Opção inválida, digite novamente");
                        continue;
                }
                break;
            }
        } else {
            while (true) {
                String opcaoSigla = Leitura.dados("Deseja modificar a sigla? (S/N)").toUpperCase();
                switch (opcaoSigla) {
                    case "S":
                        String newSigla = Leitura.dados("Digite a sigla: ");
                        while (!validarSigla(newSigla)) {
                            System.out.println("Sigla inválida! Precisa conter texto e não pode ser repetida");
                            newSigla = Leitura.dados("Digite a sigla: ");
                            System.out.println();
                        }
                        listaTurmas.get(idAtualizar).setSigla(newSigla);
                        break;
                    case "N":
                        break;
                    default:
                        System.out.println("Opção inválida, digite novamente");
                        continue;
                }
                break;
            }

        }
    }

    private static int validaIdTurma() {
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

    private static void cadastrarTurma() {
        Periodo periodo = validarPeriodo();

        String curso = Leitura.dados("Digite o curso: ");
        while (!isCharacter(curso)) {
            System.out.println("Nome de curso inválido! Não use números ou caracteres especiais, por favor");
            curso = Leitura.dados("Digite o curso: ");
        }

        String sigla = Leitura.dados("Digite a sigla: ");
        while (!validarSigla(sigla)) {
            System.out.println("Sigla inválida! Precisa conter texto e não pode ser repetida");
            sigla = Leitura.dados("Digite a sigla: ");
        }

        Turma turma = new Turma(curso, sigla, periodo);
        listaTurmas.add(turma);
    }

    private static boolean validarSigla(String sigla) {
        if (sigla.isBlank()) return false;

        for (Turma turma : listaTurmas) {
            if (turma.getSigla().equals(sigla)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCharacter(String texto) {
        String textoSemNumeros = texto.replaceAll("\\d", "");
        return !texto.isBlank() && texto.equals(textoSemNumeros);
    }


    private static Periodo validarPeriodo() {
        String opcaoPeriodo = Leitura.dados("""
                Digite o número do período escolhido:
                1 - Matutino
                2 - Vespertino
                3 - Noturno
                4 - Integral""");
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


    private static void listarTurmas() {
        if (isVazio(listaTurmas)) {
            System.out.println("Não há turmas cadastradas");
            return;
        }
        for (Turma t : listaTurmas) {
            if (t.isAtivo())
                System.out.println(t);
        }
    }

    private static void excluirAluno() {

    }

    private static void atualizarAluno() {

    }

    private static void cadastrarAluno() {
        System.out.println("BEM VINDO AO CADASTRADOR DE ALUNOS");
        LocalDate dataFormatada;

        String nome = Leitura.dados("Digite o nome do Aluno");

        while (!isCharacter(nome)){
            System.out.println("\nEscreve um nome ai bobão");
            nome = Leitura.dados("Digite o nome do Aluno");

        }
            dataFormatada = convertorParaData();



            System.out.println(dataFormatada);




        boolean aaa = !listarTurmasIndiceSigla();
        if (aaa) {
            System.out.println("Não há turmas na lista, crie uma turma para adicionar o aluno nela");
            Leitura.dados("Aperte Enter para continuar");
            System.out.println("Redirecionando ao menu principal...");
            menuPrincipal();

        }
        int idAtualizar = validaIdTurma();

        Turma turmaSelecionada = listaTurmas.get(idAtualizar);


        System.out.printf("""
                Confirme as informações do Aluno
                Nome: %s
                Data de Nascimento: %s
                Curso: %s""", nome, dataFormatada, turmaSelecionada.getCurso());

        while(true) {
            String confirma = Leitura.dados("\nAs informações do Aluno está correta? S/N").toUpperCase();

            switch (confirma) {
                case "S":

                    Aluno aluno = new Aluno(nome, dataFormatada, turmaSelecionada);
                    listaAlunos.add(aluno);
                    System.out.println("Aluno adicionado com sucesso!");
                    menuPrincipal();
                case "N":
                    String newNome = atualizaAluno().nome();
                    LocalDate newDate = atualizaAluno().dataAniversario();
                    Turma newTurma = atualizaAluno().turma();
                    System.out.println(newNome+ newDate +newTurma);

                    if(newNome == null){
                        newNome = nome;
                    }
                    if(newDate == null){
                        newDate = dataFormatada;
                    }
                    if(newTurma == null){
                        newTurma = turmaSelecionada;
                    }
                    Aluno newAluno = new Aluno(newNome, newDate, newTurma);
                    listaAlunos.add(newAluno);
                    System.out.println("Aluno adicionado com sucesso!");
                    menuPrincipal();


                default:
                    System.out.println("Opção inválida. Digite novamente!");
                    continue;
            }
        }

    }


    private static DadosModificados atualizaAluno() {

        String newNome = null;
        boolean quebreLoop =true;
        while (quebreLoop) {
            String opcaoNome = Leitura.dados("Deseja modificar o nome do Aluno? (S/N)").toUpperCase();
            switch (opcaoNome) {
                case "S":

                    newNome = Leitura.dados("Digite o nome do Aluno: ");
                    while (!isCharacter(newNome)) {
                        System.out.println("Nome do Aluno inválido! Não use números ou caracteres especiais, por favor");
                        newNome = Leitura.dados("Digite o nome do Aluno: ");
                    }
                    quebreLoop = false;
                    case "N":
                    quebreLoop = false;
                default:
                    System.out.println("Opção inválida, digite novamente");
                    continue;
            }
        }

        LocalDate newData = null;
        while (true) {
            String opcaoData = Leitura.dados("Deseja modificar a data de Aniversário do Aluno? (S/N)").toUpperCase();
            switch (opcaoData) {
                case "S":
                    newData = convertorParaData();
                    System.out.println("Data atualizada com sucesso!");
                    break;
                case "N":
                    break;
                default:
                    System.out.println("Opção inválida, digite novamente");
                    continue;
            }
            break;
        }
        Turma turmaSelecionada = null;
        while (true) {
            String opcaoTurma = Leitura.dados("Deseja modificar a turma do aluno? (S/N)").toUpperCase();
            switch (opcaoTurma) {
                case "S":

                    boolean aaa = !listarTurmasIndiceSigla();
                    if (aaa) {
                        System.out.println("Não há turmas na lista, crie uma turma para adicionar o aluno nela");
                        Leitura.dados("Aperte Enter para continuar");
                        System.out.println("Redirecionando ao menu principal...");
                        menuPrincipal();

                    }
                    int idAtualizar = validaIdTurma();

                    turmaSelecionada = listaTurmas.get(idAtualizar);
                    break;
                case "N":
                    break;
                default:
                    System.out.println("Opção inválida, digite novamente");
                    continue;

            }
            break;
        }
        return new DadosModificados(newNome, newData, turmaSelecionada);


    }


    private static LocalDate convertorParaData() {
        final LocalDate dataAtual = LocalDate.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nascimentoCerto = null;


        while (nascimentoCerto == null) {
            String nascimento = Leitura.dados("Digite a data de nascimento do aluno. dd/mm/year");
            try {
                nascimentoCerto = LocalDate.parse(nascimento, formatter);
                if (nascimentoCerto.isAfter(dataAtual)) {
                    System.out.println("Não pode nascer no futuro");
                    nascimento = Leitura.dados("\nDigite a data de nascimento do aluno. dd/mm/year");
                }
                boolean teste = nascimento.matches("^(?:0[1-9]|[12]\\d|3[01])([/.-])(?:0[1-9]|1[012])\\1(?:19|20)\\d\\d$");

                if (!teste) {
                    System.out.println("Esse ano ai é muito antigo pra vc ter nascido");
                    nascimento = Leitura.dados("\nDigite a data de nascimento do aluno. dd/mm/year");
                }
                Period periodo = Period.between(nascimentoCerto,dataAtual);
                System.out.println(periodo.getYears());
                if(periodo.getYears() < 10 || periodo.getYears() > 25){
                    System.out.println("Idade não permitida para o aluno mínimo 10 e máximo 25");
                    System.out.println("Redirecionando ao menu...");
                    menuAlunos();
                }

            } catch (DateTimeParseException e){
                System.out.println("ESSE É O ERRO:" + e);
            }

        }
        return nascimentoCerto;
    }

    private static void listarAlunos() {
        System.out.println(isVazioAlunos(listaAlunos));
        if(isVazioAlunos(listaAlunos)) {
            System.out.println("Não há alunos cadastradas");
            return;
        }
        for(Aluno a : listaAlunos){
            if (a.isAtivo())
                System.out.printf("1 - Aluno: %s Turma: %s Data de nascimento: %s ", a.getNome(), a.getTurma(), a.getDataNascimento());
        }
    }

    private static boolean isVazioAlunos(ArrayList<Aluno> listaAlunos) {
        if (listaAlunos.isEmpty()) return true;

        for (Aluno aluno : listaAlunos) {
            if (aluno.isAtivo()) return false;
        }

        return true;
    }
}