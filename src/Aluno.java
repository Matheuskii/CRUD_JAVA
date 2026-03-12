import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Aluno {

    private String nome;
    private LocalDate dataNascimento;
    private Turma turma;
    private boolean ativo;

    public Aluno(String nome, LocalDate dataNascimento, Turma turma) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.turma = turma;
        this.ativo = true;
    }

    public void atualizarCom(Aluno alunoNovo) {
        this.nome = alunoNovo.getNome();
        this.dataNascimento = alunoNovo.getDataNascimento();
        this.turma = alunoNovo.getTurma();
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format(
            "Aluno [Nome: %s | Nasc: %s | Turma: %-10s | Status: %s]",
            nome,
            dataNascimento.format(formatter),
            (turma != null ? turma : "Sem Turma"),
            (ativo ? "Ativo" : "Inativo")
        );
    }

    //MÉTODOS

    protected static LocalDate convertorParaData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        LocalDate nascimentoCerto = null;

        while (nascimentoCerto == null) {
            String nascimento = Leitura.dados("Digite a data de nascimento do aluno. dd/mm/yyyy");
            try {
                nascimentoCerto = LocalDate.parse(nascimento, formatter);

                String nascimentoBr = nascimentoCerto.format(formatter);
                validarData(nascimentoCerto);

                System.out.println(nascimentoBr);
            } catch (DateTimeParseException e) {
                System.out.println("A data não está formatada corretamente. dd/mm/yyyy ");
                System.out.println("Ou ce foi bobao e colocou 29/02 num ano q n é bissexto vacilaum");
            }
        }
        return nascimentoCerto;
    }

    private static void validarData(LocalDate nascimentoCerto) {
        final LocalDate dataAtual = LocalDate.now(ZoneOffset.UTC);
        System.out.println(nascimentoCerto.getMonth());
        System.out.println(nascimentoCerto.getDayOfMonth());
        if (nascimentoCerto.isAfter(dataAtual)) {
            System.out.println("A data não pode ser no dia de hj ou amanhã");
            Main.menuPrincipal();
        }
        Period periodo = Period.between(nascimentoCerto, dataAtual);
        System.out.println("Idade: " + periodo.getYears());
        if (periodo.getYears() < 14 || periodo.getYears() > 130) {
            System.out.println("Idade não permitida para o aluno mínimo 14 e máximo 130");
            System.out.println("Redirecionando ao menu...");
            Main.menuAlunos();
        }
    }

    protected static DadosModificados atualizarParcialAluno(String atributo) {
        String newNome = null;
        Turma newTurma = null;

        LocalDate newData = null;
        boolean quebraLoop = true;
        while (quebraLoop) {
            String opcao = Leitura.dados("\nDeseja modificar " + atributo + "? (S/N): ").toUpperCase();
            switch (opcao) {
                case "S":
                    switch (atributo) {
                        case "nome":
                            newNome = cadastraNome();
                            break;
                        case "data":
                            newData = Aluno.convertorParaData();
                            break;
                        case "turma":
                            newTurma = atualizaTurmaAluno();

                            break;
                    }
                    System.out.println(atributo + " modificado com sucesso!");
                    quebraLoop = false;
                    break;
                case "N":
                    quebraLoop = false;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha S para SIM ou N para NÃO");
            }
        }
        return new DadosModificados(newNome, newData, newTurma);
    }

    protected static Turma atualizaTurmaAluno() {
        if (!Turma.listarTurmasIndiceSigla()) {
            System.out.println("Não há turmas na lista, crie uma turma para adicionar o aluno nela");
            Leitura.dados("Aperte Enter para continuar");
            System.out.println("Redirecionando ao menu principal...");
            Main.menuPrincipal();
        }
        int idAtualizar = Turma.validarId("turma");

        return Main.listaTurmas.get(idAtualizar);
    }

    protected static String cadastraNome() {
        String newNome = Leitura.dados("Digite o nome do Aluno: ");
        while (!isName(newNome)) {
            newNome = Leitura.dados("Digite o nome do Aluno: ");
        }
        return newNome;
    }

    protected static boolean isName(String nome) {
        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$";

        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Erro: O campo nome está vazio!");
            return false;
        }

        if (nome.matches(regex)) {
            if (nome.contains("  ")) {
                System.out.println("Erro: O nome contém espaços duplos!");
                return false;
            } else {
                return true;
            }
        } else {
            if (nome.contains("-")) {
                System.out.println("Erro: Hífen não é permitido! Use apenas espaços.");
                return false;
            } else if (Main.isCharacter(nome)) {
                System.out.println("Erro: O nome não pode conter números!");
                return false;
            } else {
                System.out.println("Erro: O nome contém símbolos ou caracteres especiais inválidos!");
                return false;
            }
        }
    }

    protected static Aluno VerificadorDadosAlunos(String nome, LocalDate dataNascimento, Turma turma) {
        String newName = Aluno.atualizarParcialAluno("nome").nome();
        LocalDate newData = Aluno.atualizarParcialAluno("data").dataAniversario();
        Turma newTurma = Aluno.atualizarParcialAluno("turma").turma();

        if (newName == null) {
            System.out.println("Nome mantido com sucesso!");
            newName = nome;
        }
        if (newData == null) {
            System.out.println("Data mantida com sucesso!");
            newData = dataNascimento;
        }
        if (newTurma == null) {
            System.out.println("Turma mantida com sucesso!");

            newTurma = turma;
        }
        return new Aluno(newName, newData, newTurma);
    }

    protected static void listarAlunosIndice() {
        if (Main.isVazio(1)) {
            System.out.println("Tá vazio");
        }
        System.out.println("\nLista das Alunos:");
        for (int i = 0; i < Main.listaAlunos.size(); i++) {
            if (Main.listaAlunos.get(i).isAtivo()) System.out.printf(
                "\n%d - %s",
                i + 1,
                Main.listaAlunos.get(i).getNome()
            );
        }
    }

    protected static void confirmaInfoAluno(String nome, LocalDate dataNascimento, Turma turma) {
        boolean quebraLoop = true;
        while (quebraLoop) {
            String confirma = Leitura.dados("\nAs informações do Aluno está correta? (S/N) ").toUpperCase();

            switch (confirma) {
                case "S":
                    quebraLoop = false;
                    Aluno aluno = new Aluno(nome, dataNascimento, turma);
                    Main.listaAlunos.add(aluno);
                    System.out.println("Aluno adicionado com sucesso!");
                    Main.menuPrincipal();
                    break;
                case "N":
                    quebraLoop = false;
                    Aluno newAluno = Aluno.VerificadorDadosAlunos(nome, dataNascimento, turma);
                    Main.listaAlunos.add(newAluno);
                    System.out.println("Aluno adicionado com sucesso!");
                    Main.menuPrincipal();
                    break;
                default:
                    System.out.println("Opção inválida. Digite novamente!");
            }
        }
    }
}
