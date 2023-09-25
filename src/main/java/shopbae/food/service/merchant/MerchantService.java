package shopbae.food.service.merchant;

import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import shopbae.food.model.Merchant;
import shopbae.food.repository.merchant.IMerchantRepository;
import shopbae.food.service.account.IAccountService;

@Service
public class MerchantService implements IMerchantService {
    @Autowired
    private IMerchantRepository merchantRepository;
    @Autowired
    IAccountService accountService;

    @Override
    public Merchant findById(Long id) {
        return merchantRepository.findById(id);
    }

    @Override
    public void save(Merchant t) {
        merchantRepository.save(t);
    }

    @Override
    public void update(Merchant t) {
        merchantRepository.update(t);
    }

    @Override
    public void delete(Merchant t) {
        merchantRepository.delete(t);
    }

    @Override
    public List<Merchant> findAll() {
        return merchantRepository.findAll();
    }

    @Override
    public Merchant findByName(String name) {
        return merchantRepository.findByName(name);
    }

    @Override
    public List<Merchant> getAllByMerchantStatus(String status) {
        return merchantRepository.getAllByMerchantStatus(status);
    }

    @Override
    public List<Merchant> findAllMerchantAndNameContainer(String name) {
        return merchantRepository.findAllMerchantAndNameContainer(name);
    }

    @Override
    public Merchant findByAccount(Long id) {
        return merchantRepository.findByAccount(id);
    }

    public boolean checkStatusTime(String openTime, String closeTime) {
        LocalTime openTime1 = LocalTime.parse(openTime);
        LocalTime closeTime1 = LocalTime.parse(closeTime);

        // Tạo đối tượng LocalTime cho thời gian hiện tại
        LocalTime now = LocalTime.now();
        return now.isAfter(openTime1) && now.isBefore(closeTime1);
    }

    @Override
    public String detailMer(Long id, Model model, HttpSession httpSession) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void homePage(Model model, HttpSession session, int page, int pageSize) {
        // TODO Auto-generated method stub

    }

//    @Override
//    public String detailMer(Long id, Model model, HttpSession httpSession) {
//        try {
//            Merchant merchant = findById(id);
//            // Tạo đối tượng LocalTime từ chuỗi định dạng hh:mm
//            String openTime = merchant.getOpenTime();
//            String closeTime = merchant.getCloseTime();
//
//            // Kiểm tra thời gian hiện tại có nằm trong khoảng thời gian mở cửa và đóng cửa
//            // hay không
//            if (checkStatusTime(openTime, closeTime)) {
//                // Thời gian hiện tại nằm trong khoảng thời gian mở cửa và đóng cửa
//                model.addAttribute("statusMerchant", "true");
//            } else {
//                // Thời gian hiện tại không nằm trong khoảng thời gian mở cửa và đóng cửa
//                model.addAttribute("statusMerchant", "false");
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//            model.addAttribute("statusMerchant", "false");
//        }
//
//        httpSession.setAttribute("merchantId", id);
//        if (httpSession.getAttribute("userId") == null) {
//            httpSession.setAttribute("userId", 0);
//        }
//        if (AccountStatus.ACTIVE.toString().equals(this.findById(id).getStatus())) {
//            model.addAttribute("merchant", this.findById(id));
//            model.addAttribute("products", productService.getAllByDeleteFlagTrueAndMerchant(id));
//            model.addAttribute("page", "merchant-detail.jsp");
//        } else {
//            return "redirect:/home";
//        }
//
//        return "page/home-layout";
//    }
//
//    // Kiểm tra đăng nhập của admin
//    public boolean isAdmin(HttpSession session) {
//        @SuppressWarnings("unchecked")
//        Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) session
//                .getAttribute("authorities");
//        List<String> roles = new ArrayList<String>();
//        for (GrantedAuthority a : authorities) {
//            roles.add(a.getAuthority());
//        }
//        if (roles.contains("ROLE_ADMIN")) {
//            return true;
//        }
//        return false;
//    }
//
//    public void homePage(Model model, HttpSession session, int page, int pageSize) {
//        String userName = (String) session.getAttribute("username");
//        Account account = accountService.findByName(userName);
//
//        String message = "";
//        String name = "";
//        String avatar = "";
//        String role = "";
//
//        if (account == null) {
//            message = "chua dang nhap";
//        } else {
//            if (account.getUser() != null) {
//                name = account.getUser().getName();
//                avatar = account.getUser().getAvatar();
//                role = "user";
//            }
//            if (new MerchantService().isAdmin(session)) {
//                name = account.getUser().getName();
//                avatar = account.getUser().getAvatar();
//                role = "admin";
//            }
//            if (account.getMerchant() != null) {
//                name = account.getMerchant().getName();
//                avatar = account.getMerchant().getAvatar();
//                role = "merchant";
//            }
//        }
//        List<Merchant> listMerchant = this.getAllByMerchantStatus(AccountStatus.ACTIVE.toString());
//        // tính toán số trang cần hiển thị
//        int totalPages = listMerchant.size() / pageSize;
//        if (listMerchant.size() % pageSize > 0) {
//            totalPages++;
//        }
//        session.setAttribute("name", name);
//        session.setAttribute("avatar", avatar);
//        session.setAttribute("role", role);
//        session.setAttribute("message", message);
//        model.addAttribute("merchants", new Page().paging(page, pageSize, listMerchant));
//        model.addAttribute("page", "home.jsp");
//        model.addAttribute("totalPages", totalPages);
//        model.addAttribute("currentPage", page);
//    }

}
