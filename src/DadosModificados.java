import java.time.LocalDate;

public record DadosModificados(String nome, LocalDate dataAniversario, Turma turma) {
    public DadosModificados withName(String nome) {
        return new DadosModificados(nome, this.dataAniversario, this.turma);
    }
    public DadosModificados withName(LocalDate data) {
        return new DadosModificados(this.nome, data, this.turma);
    }
    public DadosModificados withName(Turma turmaNova) {
        return new DadosModificados(nome, this.dataAniversario, turmaNova);
    }


}
