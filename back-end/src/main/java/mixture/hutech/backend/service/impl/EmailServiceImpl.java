package mixture.hutech.backend.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.service.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final String url = "http://localhost:8080";

    @Override
    public void sendMailWithTokenRegister(String toEmail, String name, String token) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("Đăng ký thành công");

        Context thymeleafContext = new Context();
        String title = "Xác nhận đăng ký tài khoản thành công";
        String message = "Chào bạn, \n\nChúng tôi đã nhận được yêu cầu đăng ký tài khoản của bạn. Để hoàn tất quá trình đăng ký và sử dụng dịch vụ đặt lịch hẹn khám bệnh của chúng tôi, vui lòng xác nhận địa chỉ email của bạn bằng cách nhấp vào liên kết bên dưới.";
        String url = this.url + "/Notify?token=" + token + "&type=verifyEmailSuccess";

        thymeleafContext.setVariable("title", title);
        thymeleafContext.setVariable("message", message);
        thymeleafContext.setVariable("url", url);
        thymeleafContext.setVariable("name", name);
        thymeleafContext.setVariable("textBtn", "Kích hoạt tài khoản");

        String htmlBody = templateEngine.process("mail-template", thymeleafContext);
        mimeMessageHelper.setText(htmlBody, true);

        mailSender.send(mimeMessage);
    }

    @Override
    public void sendMailWithTokenResetPassword(String toEmail, String name, String token) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("Quên mật khẩu");

        Context thymeleafContext = new Context();
        String title = "Quên mật khẩu";
        String message = "Bạn đã yêu cầu đặt lại mật khẩu. Vui lòng nhấp vào nút bên dưới để đặt lại mật khẩu.";
        String url = this.url + "/change-password?token=" + token;

        thymeleafContext.setVariable("title", title);
        thymeleafContext.setVariable("message", message);
        thymeleafContext.setVariable("url", url);
        thymeleafContext.setVariable("name", name);
        thymeleafContext.setVariable("textBtn", "Đặt lại mật khẩu");

        String htmlBody = templateEngine.process("mail-template", thymeleafContext);
        mimeMessageHelper.setText(htmlBody, true);

        mailSender.send(mimeMessage);
    }
}
