package utils;

import entidade.Email;
import entidade.Vencedor;

public class MailerUtil {

    public static Email montaEmailVencedor(Vencedor vencendor, String premio, String remetente) {

        String assunto = "Menor lance unico";
        String mensagem = "Parabens!! \n\n O seu lance de $" + vencendor.getValor() + " foi o menor lance unico do sorteio.\n " +
                "Voce acaba de ganhar o seguinte premio:\n" + premio;

        return new Email(vencendor.getEmail(), remetente, assunto, mensagem);
    }

    public static Email montaEmailNotificacaoSucesso(Vencedor vencedor, String emailAdministrador, String remetente) {

        String assunto = "Sorteio concluido com sucesso";
        String mensagem = "Um sorteio foi concluido com sucesso.\n Email do vencedor: " + vencedor.getEmail();

        return new Email(emailAdministrador, remetente, assunto, mensagem);
    }


    public static Email montaEmailNotificacaoFracasso(String emailAdministrador, String remetente) {

        String assunto = "Sorteio concluido sem vencedor";
        String mensagem = "Um sorteio foi concluido e nao houve nenhum lance unico.";

        return new Email(emailAdministrador, remetente, assunto, mensagem);
    }
}
