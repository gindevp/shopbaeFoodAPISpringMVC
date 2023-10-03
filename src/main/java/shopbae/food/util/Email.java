package shopbae.food.util;

import java.text.MessageFormat;

public class Email {
    public static final String MAIL = "nguyenhuuquyet07092001@gmail.com";
    public static final String USER_ERORR = "sai otp ròi mời nhập lại";
    public static final String CONFIRM = "Mã xác nhận OTP";
    public static final String THANKS = "CẢM ƠN ĐÃ ĐĂNG KÝ";
    public static final String MESS = "THÔNG BÁO ĐĂNG KÝ";
    public static final String MESSAGE = "BẠN ĐÃ ĐĂNG KÝ CHỜ ADMIN PHÊ DUYỆT";
    public static final String USER_EMPTY = "username bạn nhập không tồn tại";

    public static String messageOTP(String OTP) {
        return MessageFormat.format(
                "Mã OTP của bạn là: {0}  \nVui lòng không chia sẻ với ai\nMời nhấp link bên dưới để đến trang xác nhận OTP\nhttps://localhost:8443/ShobaeFood/forgotpass/confirm",
                OTP);
    }
}
