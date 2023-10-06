package shopbae.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
//	@GetMapping("/user/{userId}")
//	public String userCart(Model model, @PathVariable Long userId, HttpSession session) {
//		cartService.cartDetail(model, userId, session);
//		Long id = (Long) session.getAttribute("merchantId");
//		session.setAttribute("merId", id);
//		return "page/home-layout";
//	}
//
//	// Đặt hàng
//	@PostMapping("/order/user/{userId}/merchant/{merchantId}")
//	public String oder(Model model, @PathVariable Long userId, @PathVariable Long merchantId, @RequestParam String note,
//			String address, double sum, HttpSession session) {
//		List<Product> productFail = cartService.ordeing(userId, merchantId, note, address, sum);
//		String fail = "";
//		for (Product product : productFail) {
//			fail += "- " + product.getName() + "\\n";
//		}
//		System.out.println(fail);
//		if (productFail.size() > 0) {
//			session.setAttribute("ss", "false");
//			session.setAttribute("productFail", fail);
//		} else {
//			session.setAttribute("ss", "true");
//		}
//		return "redirect:/cart/user/" + userId;
//	}
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
//	//giảm  rỏ hàng
//	@GetMapping("/product/{productid}/user/{userid}")
//	public String ReduceCart(@PathVariable Long productid, @PathVariable Long userid, HttpSession session) {
//		Cart cart= cartService.findByProductAndFlag(productid);
//		cart.setQuantity(cart.getQuantity()-1);
//		cartService.update(cart);
//		return "redirect:/cart/user/"+session.getAttribute("merchantId");
//	}
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
//	// Xóa rỏ hàng theo id sản phẩm
//	@GetMapping("/remove-item/{id}")
//	public String removeItem(@PathVariable Long id, HttpSession session) {
//		Cart cart = cartService.findByProductAndFlag(id);
//		cart.setDeleteFlag(false);
//		cartService.update(cart);
//		Long userId = (Long) session.getAttribute("userId");
//		return "redirect:/cart/user/" + userId;
//	}
//
//	/*
//	 * Xác nhận cho đơn hàng người mua đã nhận được và tăng số lượng sản phẩm đã
//	 * được bán theo từng sản phẩm riêng biệt để thống kê cho người bán xem
//	 */
//	@GetMapping("/received/{id}")
//	public String received(@PathVariable Long id, HttpSession session) {
//		Order order = orderService.findById(id);
//		List<OrderDetail> o = orderDetailService.findByOrder(id);
//		for (OrderDetail orderDetail : o) {
//			Product pro = orderDetail.getProduct();
//			pro.setNumberOrder(orderDetail.getProduct().getNumberOrder() + orderDetail.getQuantity());
//			productService.update(pro);
//		}
//		order.setStatus("USER_RECEIVED");
//		orderService.update(order);
//		Long userId = (Long) session.getAttribute("userId");
//		OrderDTO dto= new OrderDTO();
//		String status = messageSource.getMessage(order.getStatus(), null, LocaleContextHolder.getLocale());
//		dto.setUser(appUserService.findById(userId).getName());
//		dto.setMessage(status);
//		messagingTemplate.convertAndSend("/topic/ordeing/"+session.getAttribute("merchantId"), dto);
//		return "redirect:/cart/user/" + userId;
//	}
//
//	// Xác nhận người mua đã từ chối nhận đơn hàng
//	@GetMapping("/refuse/{id}")
//	public String refused(Model model, @PathVariable Long id, HttpSession session) {
//		Order order = orderService.findById(id);
//		order.setStatus("USER_REFUSE");
//		orderService.update(order);
//		Long userId = (Long) session.getAttribute("userId");
//
//		List<OrderDetail> details = detailService.findByOrder(id);
//
//		for (OrderDetail orderDetail : details) {
//			Product product = orderDetail.getProduct();
//			product.setQuantity(product.getQuantity() + orderDetail.getQuantity());
//			productService.update(product);
//		}
//		OrderDTO dto= new OrderDTO();
//		String status = messageSource.getMessage(order.getStatus(), null, LocaleContextHolder.getLocale());
//		dto.setUser(appUserService.findById(userId).getName());
//		dto.setMessage(status);
//		messagingTemplate.convertAndSend("/topic/ordeing/"+session.getAttribute("merchantId"), dto);
//		return "redirect:/cart/user/" + userId;
//	}
//
//	// Xem chi tiết đơn hàng ở phần lịch sử đơn, thông tin gồm những sản phẩm đã mua
//	// giá tiền ...
//	@GetMapping("/order/detail/{id}")
//	public String oderDetail(Model model, @PathVariable Long id, HttpSession session) {
//		List<OrderDetail> details = detailService.findByOrder(id);
//		String status= orderService.findById(id).getStatus();
//		double sum = 0;
//		for (OrderDetail orderDetail : details) {
//			sum += orderDetail.getQuantity() * orderDetail.getProduct().getNewPrice();
//		}
//		Long id1 = (Long) session.getAttribute("merchantId");
//		session.setAttribute("merId", id1);
//		session.setAttribute("orderId", id);
//		model.addAttribute("sum", sum);
//		model.addAttribute("oderDetail", details);
//		model.addAttribute("page2", "order-detail.jsp");
//		model.addAttribute("page", "cart.jsp");
//		model.addAttribute("a", 2);
//		model.addAttribute("status", status);
//		model.addAttribute("message", "khong co du lieu");
//		return "page/home-layout";
//	}
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
