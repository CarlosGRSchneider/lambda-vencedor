package lambda;

import entidade.Email;
import entidade.LambdaDto;
import entidade.Vencedor;
import ses.SesMailer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import sql.Query;
import utils.MailerUtil;

public class Main {

    public void selecionaVencedor(LambdaDto lambdaDto) {

        String emailSes = System.getenv("EMAIL_SES");
        String emailAdm = System.getenv("EMAIL_ADM");

        SesClient sesClient = SesClient.builder()
                .region(Region.SA_EAST_1)
                .build();
        SesMailer mailer = new SesMailer(sesClient);

        Query query = new Query();
        Vencedor vencedor = query.consultaVencedor(lambdaDto.getDeslocamentoDeDias());

        if (vencedor == null) {
            Email emailSemSorteio = MailerUtil.montaEmailNotificacaoFracasso(emailAdm, emailSes);
            mailer.enviaEmail(emailSemSorteio);
            return;
        }

        Email emailVencedor = MailerUtil.montaEmailVencedor(vencedor, lambdaDto.getPremio(), emailSes);
        mailer.enviaEmail(emailVencedor);

        Email emailPremioSorteado = MailerUtil.montaEmailNotificacaoSucesso(vencedor, emailAdm, emailSes);
        mailer.enviaEmail(emailPremioSorteado);
    }
}