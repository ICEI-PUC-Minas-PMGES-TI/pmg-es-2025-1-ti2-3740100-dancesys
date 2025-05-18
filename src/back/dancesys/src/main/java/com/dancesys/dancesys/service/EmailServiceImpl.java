package com.dancesys.dancesys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.ClassPathResource;

@Service
public class EmailServiceImpl {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public String enviarEmailHtml(String destinatario, String senha) {
        try {
            String htmlContent = carregarTemplateEmail(destinatario, senha);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject("Bem-vindo ao Dancesys!");
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
            return "Email enviado com sucesso";

        } catch (MessagingException | IOException e) {
            return "Erro ao tentar enviar email: " + e.getLocalizedMessage();
        }
    }

    public String enviarEmailHtml(String destinatario, String tipo, String valor) {
        try {
            String htmlContent = carregarTemplateEmailDividendo(tipo, valor);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject("Bem-vindo ao Dancesys!");
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
            return "Email enviado com sucesso";

        } catch (MessagingException | IOException e) {
            return "Erro ao tentar enviar email: " + e.getLocalizedMessage();
        }
    }

    private String carregarTemplateEmail(String usuario, String senha) throws IOException {
        Path path = Paths.get(new ClassPathResource("templates/newuser.html").getURI());
        String content = new String(Files.readAllBytes(path));

        content = content.replace("${user}", usuario)
                .replace("${pass}", senha);

        return content;
    }

    private String carregarTemplateEmailDividendo (String tipo, String valor) throws IOException {
        Path path = Paths.get(new ClassPathResource("templates/newdividendo.html").getURI());
        String content = new String(Files.readAllBytes(path));

        content = content.replace("${type}", tipo)
                .replace("${valor}", valor);

        return content;
    }
}
