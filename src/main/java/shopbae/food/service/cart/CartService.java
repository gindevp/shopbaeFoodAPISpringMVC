package shopbae.food.service.cart;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import shopbae.food.model.AppUser;
import shopbae.food.model.Cart;
import shopbae.food.model.Product;
import shopbae.food.repository.cart.ICartRepository;
import shopbae.food.service.favorite.IFavoriteService;
import shopbae.food.service.order.IOrderService;
import shopbae.food.service.orderDetail.IOrderDetailService;
import shopbae.food.service.product.IProductService;
import shopbae.food.service.user.IAppUserService;

@Service
public class CartService implements ICartService {
    @Autowired
    ICartRepository cartRepository;
    @Autowired
    IProductService productService;
    @Autowired
    IAppUserService appUserService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderDetailService orderDetailService;
    @Autowired
    IFavoriteService favoriteService;
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//    @Autowired
//    private MessageSource messageSource;

    @Override
    public Cart findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public void save(Cart t) {
        cartRepository.save(t);
    }

    @Override
    public void update(Cart t) {
        cartRepository.update(t);
    }

    @Override
    public void delete(Cart t) {
        cartRepository.delete(t);
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public List<Cart> findAllByUser(Long id) {
        return cartRepository.findAllByUser(id);
    }

    @Override
    public Cart findByProduct(Long id) {
        return findByProduct(id);
    }

    @Override
    public Cart findByProductIdAndUserId(Long product_id, Long user_id) {
        return cartRepository.findByProductIdAndUserId(product_id, user_id);
    }

    @Override
    public void setProductCart(Long cart_id, Long product_id) {
        cartRepository.setProductCart(cart_id, product_id);
    }

    @Override
    public void updateQuantity(int quantity, Long cart_id) {
        cartRepository.updateQuantity(quantity, cart_id);
    }

    @Override
    public void deletesByUser(Long id) {
        cartRepository.deletesByUser(id);
    }

    @Override
    public void addToCart(Cart cart) {
        Optional<Cart> cartOptional = Optional
                .ofNullable(cartRepository.findByProductIdAndUserId(cart.getProduct().getId(), cart.getUser().getId()));
        if (cartOptional.isPresent()) {
            this.updateQuantityCart(cart.getProduct().getId(), cart.getUser().getId());
        } else {
            int quantity = 1;
            cart.setQuantity(quantity);
            this.saveCart(quantity, cart.getPrice(), cart.getUser().getId(), cart.getProduct().getId(),
                    cart.getTotalPrice());
        }
    }

    public void saveCart(int quantity, double price, Long userID, Long productId, double totalPrice) {
        Cart cart = new Cart();
        cart.setQuantity(quantity);
        cart.setPrice(price);
        AppUser appUser = new AppUser();
        appUser.setId(userID);
        cart.setUser(appUser);
        Product product = new Product();
        product.setId(productId);
        cart.setProduct(product);
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
        System.out.println(cartRepository.findByProductIdAndUserId(productId, userID));
        Optional<Cart> cart1 = Optional.ofNullable(cartRepository.findByProductIdAndUserId(productId, userID));
        this.setProductCart(cart1.get().getId(), productId);
    }

    private void updateQuantityCart(Long id, Long id2) {
        Optional<Cart> cart = Optional.ofNullable(cartRepository.findByProductIdAndUserId(id, id2));
        int quantity = cart.get().getQuantity() + 1;
        cartRepository.updateQuantity(quantity, cart.get().getId());
    }

    @Override
    public Cart findByProductAndFlag(Long id) {
        return cartRepository.findByProductAndFlag(id);
    }

    @Override
    public void addCart(Long productid, Long userid) {
        Cart cart = new Cart();
        AppUser apUser = appUserService.findById(userid);
        Product product = productService.findById(productid);
        cart.setPrice(product.getNewPrice());
        cart.setProduct(product);
        cart.setUser(apUser);
        this.addToCart(cart);
    }

    @Override
    public void cartDetail(Model model, Long userId, HttpSession session) {
//        long merId = (long) session.getAttribute("merchantId");
//        List<Cart> cart1 = this.findAllByUser(userId);
//        List<Cart> cart2 = cart1.stream().filter(c -> c.getProduct().getMerchant().getId() == merId)
//                .collect(Collectors.toList());
//        for (Cart cart : cart2) {
//            Favorite favorite = favoriteService.findByUserAndPro(appUserService.findById(userId), cart.getProduct());
//            if (favorite != null) {
//                cart.setFavorite(true);
//            } else {
//                cart.setFavorite(false);
//            }
//            cartRepository.update(cart);
//        }
//        System.out.println("cart2: " + cart2);
//        model.addAttribute("products", cart2);
//        double sum = 0;
//        for (Cart x : cart2) {
//            sum += x.getTotalPrice();
//        }
//        model.addAttribute("sum", sum);
//        List<Order> orders = orderService.findByAppUserAndMer(userId, (Long) session.getAttribute("merchantId"));
//        model.addAttribute("orders", orders);
//        if (cart2.isEmpty()) {
//            model.addAttribute("message", "khong co du lieu");
//        } else {
//            model.addAttribute("message", "co du lieu");
//        }
//        model.addAttribute("page", "cart.jsp");
    }

    @Override
    public List<Product> ordeing(Long userId, Long merchantId, String note, String address, double sum) {
//        List<Cart> carts = this.findAllByUser(userId);
//        System.out.println("cart by userId:" + carts);
//        List<Cart> oderDetail = carts.stream().filter(a -> a.getProduct().getMerchant().getId() == (long) merchantId)
//                .collect(Collectors.toList());
//        List<Product> productFail = new ArrayList<>();
//        for (Cart cart : oderDetail) {
//            if ((cart.getProduct().getQuantity() - cart.getQuantity()) < 0) {
//                productFail.add(cart.getProduct());
//            }
//            ;
//        }
//        if (productFail.size() == 0) {
//            Order oder = new Order();
//            oder.setOrderdate(java.time.LocalDateTime.now());
//            oder.setNote(note);
//            oder.setStatus(OrderStatus.MERCHANT_PENDING.toString());
//            oder.setTotalPrice(sum);
//            oder.setAppUser(appUserService.findById(userId));
//            oder.setMerchant_id(merchantId);
//            oder.setDeliveryAddress(address);
//            Long orderId = (Long) orderService.savee(oder);
//
//            System.out.println("cart by userId and merId:" + oderDetail);
//            for (Cart cart : oderDetail) {
//                OrderDetail orderDetail = new OrderDetail();
//                oder.setId(orderId);
//                orderDetail.setOrder(oder);
//                orderDetail.setProduct(cart.getProduct());
//                orderDetail.setQuantity(cart.getQuantity());
//                orderDetailService.save(orderDetail);
//                cart.setDeleteFlag(false);
//                cartRepository.update(cart);
//
//                cart.getProduct().setQuantity(cart.getProduct().getQuantity() - cart.getQuantity());
//                productService.update(cart.getProduct());
//
//            }
//            String mess = messageSource.getMessage("order_new", null, LocaleContextHolder.getLocale());
//            String status = messageSource.getMessage(oder.getStatus(), null, LocaleContextHolder.getLocale());
//            OrderDTO dto = new OrderDTO();
//            dto.setId(orderId);
//            dto.setImage(appUserService.findById(userId).getAvatar());
//            dto.setName(appUserService.findById(userId).getName());
//            dto.setSdt(appUserService.findById(userId).getPhone());
//            dto.setAddress(address);
//            dto.setTime(String.valueOf(oder.getOrderdate()));
//            dto.setNote(note);
//            dto.setStatus(status);
//            dto.setMessage(mess);
//            dto.setUser(appUserService.findById(userId).getName());
//
//            messagingTemplate.convertAndSend("/topic/ordeing/" + merchantId, dto);
//            return productFail;
//        } else {
//            return productFail;
//        }
        return null;
    }

    @Override
    public List<Cart> findAllByProduct(Long id) {
        List<Cart> list = cartRepository.findAll().stream().filter(c -> c.getProduct().getId() == (long) id)
                .collect(Collectors.toList());
        System.out.println("listCart" + list);
        return list;
    }
}
