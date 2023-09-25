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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import shopbae.food.jwt.JwtUtility;
import shopbae.food.model.Account;
import shopbae.food.model.AccountDetails;
import shopbae.food.model.dto.AccountToken;
import shopbae.food.model.dto.ApiResponse;
import shopbae.food.model.dto.LoginRequest;
import shopbae.food.service.account.IAccountService;

@RestController
@CrossOrigin
public class AuthenController {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IAccountService accountService;

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
                if ("pending".equals(status)) {
                    return ResponseEntity.ok(new ApiResponse("Admin chưa phê duyệt đăng ký merchant"));
                }
                if ("block".equals(status)) {
                    return ResponseEntity.ok(new ApiResponse("Tài khoản của bạn đang bị khóa"));
                }
                if ("refuse".equals(status)) {
                    return ResponseEntity.ok(new ApiResponse("Admin đã từ chối đăng ký merchant"));
                }
            }
            if (account.getUser() != null) {
                String status = account.getUser().getStatus();
                if ("block".equals(status)) {
                    return ResponseEntity.ok(new ApiResponse("Tài khoản của bạn đang bị khóa"));
                }
            }

            return new ResponseEntity<>(new ApiResponse(new AccountToken(account.getId(), account.getUserName(), token,
                    roles, account.getMerchant(), account.getUser())), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("sai roi"), HttpStatus.OK);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody LoginRequest loginRequest) {
        boolean isEnabled = true;
        String pass = encoder.encode(loginRequest.getPassword());
        Account account = new Account(loginRequest.getUserName(), pass, isEnabled, null);
        accountService.save(account);
        return new ResponseEntity<>("okok", HttpStatus.OK);
    }

}
