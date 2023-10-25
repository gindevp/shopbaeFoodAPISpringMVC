package shopbae.food.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shopbae.food.model.AppUser;
import shopbae.food.model.Mail;
import shopbae.food.model.Merchant;
import shopbae.food.model.dto.ApiResponse;
import shopbae.food.model.dto.ClientManager;
import shopbae.food.service.mail.MailService;
import shopbae.food.service.merchant.IMerchantService;
import shopbae.food.service.user.IAppUserService;
import shopbae.food.util.AccountStatus;
import shopbae.food.util.Email;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IMerchantService merchantService;
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private MailService mailService;

    // manager merchant
    // display merchant active
    @GetMapping("/merchant")
    public ResponseEntity<?> merActive() {
        try {
            List<Merchant> merchantList = merchantService.getAllByMerchantStatus(AccountStatus.ACTIVE.toString());
            List<ClientManager> clientList = new ArrayList<>();
            for (Merchant mer : merchantList) {
                clientList.add(new ClientManager(mer.getId(), mer.getName(), mer.getPhone(), mer.getAddress(),
                        mer.getAvatar(), null, null, mer.getStatus()));
            }
            return new ResponseEntity<>(new ApiResponse(merchantList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

    // display merchant pending
    @GetMapping("/merchant/pending")
    public ResponseEntity<?> merPending() {
        try {
            List<Merchant> merchantList = merchantService.getAllByMerchantStatus(AccountStatus.PENDING.toString());
            List<ClientManager> clientList = new ArrayList<>();
            for (Merchant mer : merchantList) {
                clientList.add(new ClientManager(mer.getId(), mer.getName(), mer.getPhone(), mer.getAddress(),
                        mer.getAvatar(), null, null, mer.getStatus()));
            }
            return new ResponseEntity<>(new ApiResponse(merchantList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

    // display merchant block
    @GetMapping("/merchant/block")
    public ResponseEntity<?> merblock() {
        try {
            List<Merchant> merchantList = merchantService.getAllByMerchantStatus(AccountStatus.BLOCK.toString());
            List<ClientManager> clientList = new ArrayList<>();
            for (Merchant mer : merchantList) {
                clientList.add(new ClientManager(mer.getId(), mer.getName(), mer.getPhone(), mer.getAddress(),
                        mer.getAvatar(), null, null, mer.getStatus()));
            }
            return new ResponseEntity<>(new ApiResponse(merchantList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

    // perform active merchant
    @PutMapping("/merchant/active/{id}")
    public ResponseEntity<?> merSuccess(@PathVariable Long id) {
        try {
            Merchant merchant = merchantService.findById(id);
            merchant.setStatus(AccountStatus.ACTIVE.toString());
            Mail mail = new Mail();
            mail.setMailTo(merchant.getAccount().getEmail());
            mail.setMailFrom(Email.MAIL);
            mail.setMailSubject(Email.MESS);
            mail.setMailContent(Email.MESSAGE);
            mailService.sendEmail(mail);
            merchantService.update(merchant);
            return new ResponseEntity<>(new ApiResponse("Ok"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }

    }

    // Thực hiện việc từ chối thành merchant
    @PutMapping("/merchant/refuse/{id}")
    public ResponseEntity<?> merRefuse(@PathVariable Long id) {
        try {
            Merchant merchant = merchantService.findById(id);
            merchant.setStatus(AccountStatus.REFUSE.toString());
            merchantService.update(merchant);
            return new ResponseEntity<>(new ApiResponse("Ok"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

    // Thực hiện block merchant
    @PutMapping("/merchant/block/{id}")
    public ResponseEntity<?> merBlock(@PathVariable Long id) {
        try {
            Merchant merchant = merchantService.findById(id);

            merchant.setStatus(AccountStatus.BLOCK.toString());
            merchantService.update(merchant);
            return new ResponseEntity<>(new ApiResponse("Ok"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

    // Thực hiện bỏ block merchant
    @PutMapping("/merchant/unblock/{id}")
    public ResponseEntity<?> merUnblock(@PathVariable Long id) {
        try {
            Merchant merchant = merchantService.findById(id);
            merchant.setStatus(AccountStatus.ACTIVE.toString());
            merchantService.update(merchant);
            return new ResponseEntity<>(new ApiResponse("Ok"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

    // Manager user

    // Hiển thị user active
    @GetMapping("/user")
    public ResponseEntity<?> userActive() {
        try {
            List<AppUser> userList = appUserService.getAllByStatus(AccountStatus.ACTIVE.toString());
            List<ClientManager> clientList = new ArrayList<>();
            for (AppUser user : userList) {
                clientList.add(new ClientManager(user.getId(), user.getName(), user.getPhone(), user.getAddress(),
                        user.getAvatar(), null, null, user.getStatus()));
            }
            return new ResponseEntity<>(new ApiResponse(clientList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }

    }

    // Hiển thị user pending
    @GetMapping("/user/pending")
    public ResponseEntity<?> userPending() {
        try {
            List<AppUser> userList = appUserService.getAllByStatus(AccountStatus.PENDING.toString());
            List<ClientManager> clientList = new ArrayList<>();
            for (AppUser user : userList) {
                clientList.add(new ClientManager(user.getId(), user.getName(), user.getPhone(), user.getAddress(),
                        user.getAvatar(), null, null, user.getStatus()));
            }
            return new ResponseEntity<>(new ApiResponse(clientList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }

    }

// Hiển thị user block 
    @GetMapping("/user/block")
    public ResponseEntity<?> userblock() {
        try {
            List<AppUser> userList = appUserService.getAllByStatus(AccountStatus.BLOCK.toString());
            List<ClientManager> clientList = new ArrayList<>();
            for (AppUser user : userList) {
                clientList.add(new ClientManager(user.getId(), user.getName(), user.getPhone(), user.getAddress(),
                        user.getAvatar(), null, null, user.getStatus()));
            }
            return new ResponseEntity<>(new ApiResponse(clientList), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }

    }

    // THực hiện active cho user
    @PutMapping("/user/active/{id}")
    public ResponseEntity<?> userSuccess(@PathVariable Long id) {
        try {
            AppUser merchant = appUserService.findById(id);
            merchant.setStatus(AccountStatus.ACTIVE.toString());
            appUserService.update(merchant);
            Mail mail = new Mail();
            mail.setMailTo(merchant.getAccount().getEmail());
            mail.setMailFrom(Email.MAIL);
            mail.setMailSubject(Email.MESS);
            mail.setMailContent(Email.MESSAGE);
            mailService.sendEmail(mail);
            return new ResponseEntity<>(new ApiResponse("ok"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

    // Thực hiện từ chối thành user
    @PutMapping("/user/refuse/{id}")
    public ResponseEntity<?> userRefuse(@PathVariable Long id) {
        try {
            AppUser merchant = appUserService.findById(id);
            merchant.setStatus(AccountStatus.REFUSE.toString());
            appUserService.update(merchant);
            return new ResponseEntity<>(new ApiResponse("ok"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }

    }

    // Thực hiện block user
    @PutMapping("/user/block/{id}")
    public ResponseEntity<?> userBlock(@PathVariable Long id) {
        try {
            AppUser merchant = appUserService.findById(id);
            merchant.setStatus(AccountStatus.BLOCK.toString());
            appUserService.update(merchant);
            return new ResponseEntity<>(new ApiResponse("ok"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

    // Thực hiện việc bỏ block user
    @PutMapping("/user/unblock/{id}")
    public ResponseEntity<?> userUnblock(@PathVariable Long id) {
        try {
            AppUser merchant = appUserService.findById(id);
            merchant.setStatus(AccountStatus.ACTIVE.toString());
            appUserService.update(merchant);
            return new ResponseEntity<>(new ApiResponse("ok"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

}
