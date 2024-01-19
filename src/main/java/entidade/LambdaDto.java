package entidade;

public class LambdaDto {

    private long deslocamentoDeDias;
    private String premio;

    public LambdaDto() {
    }

    public LambdaDto(long deslocamentoDeDias, String premio) {
        this.deslocamentoDeDias = deslocamentoDeDias;
        this.premio = premio;
    }

    public long getDeslocamentoDeDias() {
        return deslocamentoDeDias;
    }

    public String getPremio() {
        return premio;
    }

    public void setDeslocamentoDeDias(long deslocamentoDeDias) {
        this.deslocamentoDeDias = deslocamentoDeDias;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

}