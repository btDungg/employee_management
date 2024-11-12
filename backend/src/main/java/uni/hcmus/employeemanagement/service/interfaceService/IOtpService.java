package uni.hcmus.employeemanagement.service.interfaceService;

public interface IOtpService {
    void storeOtp(String email, String otp);
    boolean verifyOtp(String otp, String email);
}