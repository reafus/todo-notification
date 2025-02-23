package deeper.into.you.todo_app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.properties.from}")
    private String fromAddress;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        logger.info("Отправка письма: от={}, к={}, тема={}, текст={}", fromAddress, to, subject, text);

        try {
            mailSender.send(message);
            logger.info("Письмо успешно отправлено.");
        } catch (Exception e) {
            logger.error("Ошибка при отправке письма: {}", e.getMessage());
        }
    }
}
