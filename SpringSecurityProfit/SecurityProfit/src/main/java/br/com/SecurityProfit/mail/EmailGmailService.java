package br.com.SecurityProfit.mail;

import br.com.SecurityProfit.models.escolta.Escolta;
import br.com.SecurityProfit.models.pessoa.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Service
public class EmailGmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ResourceLoader resourceLoader;

    @Async
    public void enviarEmailHtml(Escolta escolta) throws Exception {

        try {
            Resource resource = resourceLoader.getResource("classpath:templates/template.html");
            InputStream inputStream = resource.getInputStream();
            String conteudo = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            conteudo = conteudo.replace("{nome.cliente}", escolta.getPessoaDestino().getNomeRazao());
            conteudo = conteudo.replace("{id.viagem}", String.valueOf(escolta.getId()));
            conteudo = conteudo.replace("{nome.empresa}", escolta.getEmpresa().getRazaoSocial());
            conteudo = conteudo.replace("{celular.empresa}", escolta.getEmpresa().getCelular());
            conteudo = conteudo.replace("{ano.atual}", String.valueOf(LocalDate.now().getYear()));

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(escolta.getPessoaDestino().getEmail());
            helper.setSubject(escolta.getEmpresa().getRazaoSocial() + " - Aberta Escolta n° " + escolta.getId());
            helper.setText(conteudo, true);

            javaMailSender.send(message);

            System.out.println("Sucesso no Envio do Email");

        } catch (IOException | MessagingException e) {
            System.out.println(e);
        }
    }


}
