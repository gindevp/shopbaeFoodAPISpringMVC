package shopbae.food.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shopbae.food.jwt.JwtUtility;
import shopbae.food.model.Account;
import shopbae.food.model.AccountDetails;
import shopbae.food.model.AppUser;
import shopbae.food.model.Mail;
import shopbae.food.model.Merchant;
import shopbae.food.model.Product;
import shopbae.food.model.dto.AccountRegisterDTO;
import shopbae.food.model.dto.AccountToken;
import shopbae.food.model.dto.ApiResponse;
import shopbae.food.model.dto.LoginRequest;
import shopbae.food.service.account.IAccountService;
import shopbae.food.service.mail.MailService;
import shopbae.food.service.merchant.IMerchantService;
import shopbae.food.service.product.IProductService;
import shopbae.food.service.role.IRoleService;
import shopbae.food.service.user.IAppUserService;
import shopbae.food.util.AccountStatus;
import shopbae.food.util.Email;

@RestController
@CrossOrigin
public class AuthenControllerHome {

    @Autowired
    private MailService mailService;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAppUserService userService;

    @Autowired
    private IMerchantService merchantService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IProductService productService;

    @GetMapping("/merchantp/all")
    public ResponseEntity<?> allMerchant() {
        List<Merchant> listMerchants = merchantService.getAllByMerchantStatus(AccountStatus.ACTIVE.toString());
        if (listMerchants == null) {
            return new ResponseEntity<>(new ApiResponse("null"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(listMerchants), HttpStatus.OK);
    }

    @GetMapping("/merchantp/detail")
    public ResponseEntity<?> merchantDetail(@RequestParam Long id) {
        List<Product> products = productService.getAllByDeleteFlagTrueAndMerchant(id);
        if (products == null) {
            return new ResponseEntity<>(new ApiResponse("null"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(products), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Tạo ra 1 đối tượng Authentication.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtility.createJwTToken(loginRequest.getUserName());
            AccountDetails userDetails = (AccountDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            Account account = accountService.findByName(loginRequest.getUserName());
            if (account.getMerchant() != null) {
                String status = account.getMerchant().getStatus();
                if ("PENDING".equals(status)) {
                    return ResponseEntity.ok(new ApiResponse("Admin chưa phê duyệt đăng ký merchant"));
                }
                if ("BLOCK".equals(status)) {
                    return ResponseEntity.ok(new ApiResponse("Tài khoản của bạn đang bị khóa"));
                }
                if ("REFUSE".equals(status)) {
                    return ResponseEntity.ok(new ApiResponse("Admin đã từ chối đăng ký merchant"));
                }
            }
            if (account.getUser() != null) {
                String status = account.getUser().getStatus();
                if ("BLOCK".equals(status)) {
                    return ResponseEntity.ok(new ApiResponse("Tài khoản của bạn đang bị khóa"));
                }
                if ("REFUSE".equals(status)) {
                    return ResponseEntity.ok(new ApiResponse("Admin đã từ chối đăng ký user"));
                }
                if ("PENDING".equals(status)) {
                    return ResponseEntity.ok(new ApiResponse("Admin chưa phê duyệt đăng ký user"));
                }
            }

            return new ResponseEntity<>(new ApiResponse(new AccountToken(account.getId(), account.getUserName(), token,
                    roles, account.getMerchant(), account.getUser())), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("sai roi"), HttpStatus.OK);
        }
    }

    @PostMapping("/forgotpass")
    public ResponseEntity<?> fogot(@RequestParam String username) {
        Account account = accountService.findByName(username);

        if (account != null) {
            double randomDouble = Math.random();
            randomDouble = randomDouble * 1000000 + 1;
            int OTP = (int) randomDouble;
            account.setOtp(String.valueOf(OTP));
            accountService.update(account);
            Mail mail = new Mail();
            mail.setMailTo(account.getEmail());
            mail.setMailFrom(Email.MAIL);
            mail.setMailSubject(Email.CONFIRM);
            mail.setMailContent(Email.messageOTP(String.valueOf(OTP)));
            mailService.sendEmail(mail);
            return new ResponseEntity<>(new ApiResponse(mail), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(new ApiResponse(Email.USER_EMPTY), HttpStatus.OK);
        }

    }

    @PostMapping("/forgotpass/confirm")
    public ResponseEntity<?> confirmOtp(@RequestParam String otp, String pass, String username) {
        Account account = accountService.findByName(username);
        if (otp.equals(account.getOtp())) {
            account.setPassword(encoder.encode(pass));
            account.setOtp(null);
            accountService.update(account);
            return new ResponseEntity<>(new ApiResponse(account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(Email.USER_ERORR), HttpStatus.OK);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody AccountRegisterDTO accountRegisterDTO, @RequestParam String role) {
        try {

            Account account3 = accountService.findByName(accountRegisterDTO.getUserName());
            if (account3 == null) {
                boolean isEnabled = true;
                String pass = encoder.encode(accountRegisterDTO.getPassword());
                Account account = new Account(accountRegisterDTO.getUserName(), pass, isEnabled,
                        accountRegisterDTO.getEmail());
                account.setAccountNonLocked(isEnabled);
                accountService.save(account);
            } else {
                return new ResponseEntity<>(new ApiResponse("faile"), HttpStatus.OK);
            }
            Account account2 = accountService.findByName(accountRegisterDTO.getUserName());
            if (role.equals("user")) {
                roleService.setDefaultRole(account2.getId(), 2L);
                String avatar = "tet.jpg";
                userService.save(new AppUser(accountRegisterDTO.getName(), accountRegisterDTO.getAddress(),
                        accountRegisterDTO.getPhone(), avatar, AccountStatus.PENDING.toString(), account2));
            } else {
                roleService.setDefaultRole(account2.getId(), 3L);
                String avatar = "tet.jpg";
                merchantService.save(new Merchant(accountRegisterDTO.getName(), accountRegisterDTO.getPhone(),
                        accountRegisterDTO.getAddress(), avatar, AccountStatus.PENDING.toString(), account2));
            }

            // TODO: code socket realtime to notification
            if (!accountRegisterDTO.getEmail().isEmpty()) {
                Mail mail = new Mail();
                mail.setMailTo(accountRegisterDTO.getEmail());
                mail.setMailFrom(Email.MAIL);
                mail.setMailSubject(Email.MESS);
                mail.setMailContent(Email.MESSAGE);
                mailService.sendEmail(mail);
            }
            return new ResponseEntity<>(new ApiResponse(accountRegisterDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("faile"), HttpStatus.OK);
        }

    }
}
