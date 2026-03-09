import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Aluno {

    private String nome;
    private LocalDate dataNascimento;
    private Turma turma;
    private boolean ativo;
    protected static final ArrayList<Aluno> listaAlunos = new ArrayList<Aluno>();

    public Aluno(String nome, LocalDate dataNascimento, Turma turma) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.turma = turma;
        this.ativo = true;
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

    //MÉTODOS

    public static LocalDate convertorParaData() {
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
                boolean teste = nascimento.matches(
                    "^(?:0[1-9]|[12]\\d|3[01])([/.-])(?:0[1-9]|1[012])\\1(?:19|20)\\d\\d$"
                );

                if (!teste) {
                    System.out.println("Esse ano ai é muito antigo pra vc ter nascido");
                    nascimento = Leitura.dados("\nDigite a data de nascimento do aluno. dd/mm/year");
                }
                Period periodo = Period.between(nascimentoCerto, dataAtual);
                System.out.println("Idade: " + periodo.getYears());
                if (periodo.getYears() < 10 || periodo.getYears() > 25) {
                    System.out.println("Idade não permitida para o aluno mínimo 10 e máximo 25");
                    System.out.println("Redirecionando ao menu...");
                    Main.menuAlunos();
                }
            } catch (DateTimeParseException e) {
                System.out.println("ESSE É O ERRO:" + e);
            }
        }
        return nascimentoCerto;
    }

    protected static DadosModificados atualizarParcialAluno(String atributo, int idAtualizar) {
        String newNome = null;
        Turma turmaSelecionada = null;

        LocalDate newData = null;
        boolean quebraLoop = true;
        while (quebraLoop) {
            String opcao = Leitura.dados("\nDeseja modificar " + atributo + " ? (S/N): ").toUpperCase();
            switch (opcao) {
                case "S":
                    switch (atributo) {
                        case "nome":
                            String nomeReal = atualizaNome();
                            System.out.println("");
                            break;
                        case "data":
                            newData = Aluno.convertorParaData();
                            break;
                        case "turma":
                            Turma turmaNova = Main.atualizarTurma(idAtualizar);
                            break;
                        default:
                            System.out.println("Que opção merda");
                    }
                    System.out.println(atributo + " atualizado com sucesso!");
                    quebraLoop = false;
                    break;
                case "N":
                    quebraLoop = false;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha S para SIM ou N para NÃO");
            }
        }
        return new DadosModificados(newNome, newData, turmaSelecionada);
    }

    protected static String atualizaNome() {
        String newNome = Leitura.dados("Digite o nome do Aluno: ");
        while (!Main.isCharacter(newNome)) {
            System.out.println("Nome do Aluno inválido! Não use números ou caracteres especiais, por favor");
            newNome = Leitura.dados("Digite o nome do Aluno: ");
        }
        return newNome;
    }
}
