package uni.hcmus.employeemanagement.service.interfaceService;

public interface IEmailService {
    String generateOtp();
    void sendOtpEmail(String toEmail, String otp);

}
