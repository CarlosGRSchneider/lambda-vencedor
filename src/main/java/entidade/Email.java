package entidade;

public class Email {

    private String emailDestinatario;
    private String emailRemetente;
    private String assunto;
    private String mensagem;

    public Email(String emailDestinatario, String emailRemetente, String assunto, String mensagem) {
        this.emailDestinatario = emailDestinatario;
        this.emailRemetente = emailRemetente;
        this.assunto = assunto;
        this.mensagem = mensagem;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public String getEmailRemetente() {
        return emailRemetente;
    }

    public String getAssunto() {
        return assunto;
    }

    public String getMensagem() {
        return mensagem;
    }
}