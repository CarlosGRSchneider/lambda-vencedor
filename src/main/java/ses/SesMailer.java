package ses;

import entidade.Email;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

public class SesMailer {

    private SesClient sesClient;

    public SesMailer(SesClient sesClient) {
        this.sesClient = sesClient;
    }

    public void enviaEmail(Email informacoesDeEmail) {


        Destination emailVencedor = Destination.builder()
                .toAddresses(informacoesDeEmail.getEmailDestinatario())
                .build();

        Content assunto = Content.builder()
                .data(informacoesDeEmail.getAssunto())
                .build();

        Content corpoDaMensagem = Content.builder()
                .data(informacoesDeEmail.getMensagem())
                .build();

        Message mensagem = Message.builder()
                .subject(assunto)
                .body(Body.builder().text(corpoDaMensagem).build())
                .build();

        SendEmailRequest request = SendEmailRequest.builder()
                .source(informacoesDeEmail.getEmailRemetente())
                .destination(emailVencedor)
                .message(mensagem)
                .build();

        try {
            SendEmailResponse response = sesClient.sendEmail(request);
            System.out.println("Email enviado para: " + informacoesDeEmail.getEmailRemetente());
            System.out.println("Id do email: " + response.messageId());
        } catch (SesException e) {
            e.printStackTrace();
        }
    }
}