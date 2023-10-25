package shopbae.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shopbae.food.model.Cart;
import shopbae.food.model.Order;
import shopbae.food.model.OrderDetail;
import shopbae.food.model.Product;
import shopbae.food.model.dto.ApiResponse;
import shopbae.food.service.cart.ICartService;
import shopbae.food.service.favorite.IFavoriteService;
import shopbae.food.service.order.IOrderService;
import shopbae.food.service.orderDetail.IOrderDetailService;
import shopbae.food.service.product.IProductService;
import shopbae.food.service.user.IAppUserService;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    IOrderDetailService detailService;
    @Autowired
    ICartService cartService;
    @Autowired
    IProductService productService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderDetailService orderDetailService;
//    @Autowired
//    private MessageSource messageSource;
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    IAppUserService appUserService;
    @Autowired
    IFavoriteService favoriteService;

    // Thêm rỏ hàng cho người mua
    @PostMapping("/product/{productid}/user/{userid}")
    public ResponseEntity<?> cart(@PathVariable Long productid, @PathVariable Long userid) {
        try {
            cartService.addCart(productid, userid);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("lỗi"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse("Thêm thành công"), HttpStatus.OK);
    }

//	// Chuyển sang trang cart detail và xem các thông tin về đơn hàng
    @GetMapping("/user/{userId}/merchant/{merId}")
    public ResponseEntity<?> userCart(@PathVariable Long userId, @PathVariable Long merId) {
        List<Cart> cartlist = cartService.cartDetail(userId, merId);
        return new ResponseEntity<>(new ApiResponse(cartlist), HttpStatus.OK);
    }

    // Đặt hàng
    @PostMapping("/order/user/{userId}/merchant/{merchantId}")
    public ResponseEntity<?> oder(@PathVariable Long userId, @PathVariable Long merchantId, @RequestParam String note,
            String address, double sum) {
        try {
            List<Product> productFail = cartService.ordeing(userId, merchantId, note, address, sum);
//        String fail = "";
//        for (Product product : productFail) {
//            fail += "- " + product.getName() + "\\n";
//        }
//        System.out.println(fail);
//        if (productFail.size() > 0) {
//            session.setAttribute("ss", "false");
//            session.setAttribute("productFail", fail);
//        } else {
//            session.setAttribute("ss", "true");
//        }
            return new ResponseEntity<>(new ApiResponse(productFail), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/order/history/user/{userId}")
    public ResponseEntity<?> oderHis(@PathVariable Long userId) {
        try {
            List<Order> orders = orderService.findByAppUserAndMer(userId);
            return new ResponseEntity<>(new ApiResponse(orders), HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

//	//đặt lại
//	@GetMapping("/re-order/{order_id}")
//	public String ReOder(@PathVariable Long order_id, HttpSession session) {
//		Order order= orderService.findById(order_id);
//		order.setStatus(OrderStatus.MERCHANT_PENDING.toString());
//		order.setOrderdate(java.time.LocalDateTime.now());
//		Long id=(Long) orderService.savee(order);
//		List<OrderDetail> details= detailService.findByOrder(order_id);
//		for (OrderDetail orderDetail : details) {
//			orderDetail.setOrder(orderService.findById(id));
//			detailService.save(orderDetail);
//		}
//		session.setAttribute("ss", "true");
//		return "redirect:/cart/user/"+session.getAttribute("merchantId");
//	}
    // giảm rỏ hàng
    @GetMapping("/product/{productid}/user/{userid}")
    public ResponseEntity<?> ReduceCart(@PathVariable Long productid, @PathVariable Long userid) {
        try {
            Cart cart = cartService.findByProductAndFlag(productid);
            cart.setQuantity(cart.getQuantity() - 1);
            cartService.update(cart);
            return new ResponseEntity<>(new ApiResponse("ok"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

//	//Xóa khỏi rỏ khi rỏ còn 1
//	@DeleteMapping("/product/{productid}/user/{userid}")
//	public String deletCart(@PathVariable Long productid, @PathVariable Long userid, HttpSession session) {
//		Cart cart= cartService.findByProductAndFlag(productid);
//		if(cart.getQuantity()==1) {
//			cart.setQuantity(cart.getQuantity()-1);
//			cart.setDeleteFlag(false);
//			cartService.update(cart);
//		}
//		return "redirect:/cart/user/"+session.getAttribute("merchantId");
//	}
//	// Xóa đơn hàng theo userId
//	@GetMapping("/delete/order/{orderId}/user/{userId}")
//	public String deleteOrder(Model model, @PathVariable Long userId, @PathVariable Long orderId, HttpSession session) {
//		Order order = orderService.findById(orderId);
//		order.setFlag(false);
//		orderService.update(order);
//		OrderDTO dto= new OrderDTO();
//		dto.setUser(appUserService.findById(userId).getName());
//		dto.setMessage("người mua đã xóa 1 đơn hàng khỏi lịch sử");
//		messagingTemplate.convertAndSend("/topic/ordeing/"+session.getAttribute("merchantId"), dto);
//		return "redirect:/cart/user/" + userId;
//	}
//
    // Xóa rỏ hàng theo id sản phẩm
    @GetMapping("/product/{id}")
    public ResponseEntity<?> removeItem(@PathVariable Long id) {
        try {
            Cart cart = cartService.findByProductAndFlag(id);
            cart.setDeleteFlag(false);
            cartService.update(cart);
            return new ResponseEntity<>(new ApiResponse("ok"), HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }

    }

//
//	/*
//	 * Xác nhận cho đơn hàng người mua đã nhận được và tăng số lượng sản phẩm đã
//	 * được bán theo từng sản phẩm riêng biệt để thống kê cho người bán xem
//	 */
    @PatchMapping("/received/{orderId}")
    public ResponseEntity<?> received(@PathVariable Long orderId) {
        try {
            Order order = orderService.findById(orderId);
            List<OrderDetail> o = orderDetailService.findByOrder(orderId);
            for (OrderDetail orderDetail : o) {
                Product pro = orderDetail.getProduct();
                pro.setNumberOrder(orderDetail.getProduct().getNumberOrder() + orderDetail.getQuantity());
                productService.update(pro);
            }
            order.setStatus("USER_RECEIVED");
            orderService.update(order);
//		Long userId = (Long) session.getAttribute("userId");
//		OrderDTO dto= new OrderDTO();
//		String status = messageSource.getMessage(order.getStatus(), null, LocaleContextHolder.getLocale());
//		dto.setUser(appUserService.findById(userId).getName());
//		dto.setMessage(status);
//		messagingTemplate.convertAndSend("/topic/ordeing/"+session.getAttribute("merchantId"), dto);
            return new ResponseEntity<>(new ApiResponse("ok"), HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

    // Xác nhận người mua đã từ chối nhận đơn hàng
    @PatchMapping("/refuse/{orderId}")
    public ResponseEntity<?> refused(@PathVariable Long orderId) {
        try {
            Order order = orderService.findById(orderId);
            order.setStatus("USER_REFUSE");
            orderService.update(order);

            List<OrderDetail> details = detailService.findByOrder(orderId);

            for (OrderDetail orderDetail : details) {
                Product product = orderDetail.getProduct();
                product.setQuantity(product.getQuantity() + orderDetail.getQuantity());
                productService.update(product);
            }
//		OrderDTO dto= new OrderDTO();
//		String status = messageSource.getMessage(order.getStatus(), null, LocaleContextHolder.getLocale());
//		dto.setUser(appUserService.findById(userId).getName());
//		dto.setMessage(status);
//		messagingTemplate.convertAndSend("/topic/ordeing/"+session.getAttribute("merchantId"), dto);
//		return "redirect:/cart/user/" + userId;
            return new ResponseEntity<>(new ApiResponse("ok"), HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

    // Xem chi tiết đơn hàng ở phần lịch sử đơn, thông tin gồm những sản phẩm đã mua
    // giá tiền ...
//	//sở thích
//	@GetMapping("/favorite/{productId}")
//	public String favorite(Model model, @PathVariable Long productId, HttpSession session) {
//		Long userId= (Long) session.getAttribute("userId");
//		AppUser appUser= appUserService.findById(userId);
//		Product product= productService.findById(productId);
//		Favorite faOptional= favoriteService.findByUserAndPro(appUser, product);
//		if(faOptional!=null) {
//			favoriteService.delete(faOptional);
//		}else {
//		Favorite favorite= new Favorite();
//		favorite.setAppUser(appUser);
//		favorite.setProduct(product);
//		favoriteService.save(favorite);	
//		}
//		
//		return "redirect:/cart/user/" + userId;
//	}
}
