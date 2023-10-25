package shopbae.food.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import shopbae.food.model.Cart;
import shopbae.food.model.Merchant;
import shopbae.food.model.Order;
import shopbae.food.model.OrderDetail;
import shopbae.food.model.Product;
import shopbae.food.model.dto.ApiResponse;
import shopbae.food.model.dto.ProductForm;
import shopbae.food.service.cart.ICartService;
import shopbae.food.service.order.IOrderService;
import shopbae.food.service.orderDetail.IOrderDetailService;
//import shopbae.food.service.account.IAccountService;
//import shopbae.food.service.cart.ICartService;
//import shopbae.food.service.order.IOrderService;
//import shopbae.food.service.orderDetail.IOrderDetailService;
import shopbae.food.service.product.IProductService;
import shopbae.food.util.OrderStatus;

@RestController
@CrossOrigin
@RequestMapping("/merchant")
public class MerchantPOController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderDetailService detailService;
    @Autowired
    private IProductService productService;

//    @Autowired
//    IAccountService accountService;
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    ICartService cartService;
//    @Value("${file-upload}")
//    private String fileUpload;

//// Hiển thị trang dashboard thống kê doanh số đã bán ra của từng sản phẩm 
//    @GetMapping
//    public String chart(Model model, HttpSession httpSession, @RequestParam(defaultValue = "bar") String type) {
//        List<Product> product = new ArrayList<>();
//        try {
//            product = productService
//                    .getAllByDeleteFlagTrueAndMerchant(((Merchant) httpSession.getAttribute("merchant")).getId());
//            List<Object> name = new ArrayList<>();
//            List<Object> num = new ArrayList<>();
//            product.stream().forEach(c -> name.add("'" + c.getName() + "'"));
//            product.stream().forEach(c -> num.add(c.getNumberOrder()));
//            model.addAttribute("name", name);
//            System.out.println(name);
//            model.addAttribute("num", num);
//            model.addAttribute("page", "dashboard.jsp");
//            model.addAttribute("nav", 1);
//            System.out.println("chart" + type);
//            model.addAttribute("chart", "'" + type + "'");
//            return "merchant/merchant-layout";
//        } catch (Exception e) {
//            return "redirect:/home";
//        }
//
//    }
//

//
//// Thực hiện thay đổi thông tin merchant cá nhân 
//	@PostMapping("/detail")
//	public String infoSave(@ModelAttribute ChangeDTO changeDTO, Model model, HttpSession httpSession)
//			throws IOException {
//		model.addAttribute("page", "merchant-info.jsp");
//		String fileName = new UploadFile().uploadFile(changeDTO.getAvatar(),
//				(String) httpSession.getAttribute("merchantAvt"),fileUpload);
//		Merchant merchant = (Merchant) httpSession.getAttribute("merchant");
//		Account account = merchant.getAccount();
//		merchant.setName(changeDTO.getName());
//		merchant.setPhone(changeDTO.getPhone());
//		merchant.setOpenTime(changeDTO.getOpenTime());
//		merchant.setCloseTime(changeDTO.getCloseTime());
//		merchant.setAvatar(fileName);
//		merchantService.update(merchant);
//		account.setEmail(changeDTO.getEmail());
//		accountService.update(account);
//		model.addAttribute("message", "done");
//		return "merchant/merchant-layout";
//	}

// Hiển thị trang list product 
    @GetMapping("/product")
    public ResponseEntity<?> product(@RequestParam Long merchantId) {
        try {
            List<Product> listProducts1 = productService.getAllByDeleteFlagTrueAndMerchant(merchantId);
            return new ResponseEntity<>(new ApiResponse(listProducts1), HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

// Thực hiện việc thêm sản phẩm
    @PostMapping("/product/save")
    public ResponseEntity<?> saveProduct(@RequestBody ProductForm productForm, @RequestParam Long merchantId)
            throws IOException {
        try {
            Product product = new Product(productForm.getName(), productForm.getShortDescription(),
                    productForm.getOldPrice(), productForm.getNewPrice(), productForm.getImage(),
                    productForm.getQuantity());
            product.setDeleteFlag(true);
            Merchant merchant = new Merchant();
            merchant.setId(merchantId);
            product.setMerchant(merchant);
            productService.save(product);
            return new ResponseEntity<>(new ApiResponse("ok"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("ok"), HttpStatus.BAD_REQUEST);
        }
    }

//
// Thực hiện việc xóa product
    @RequestMapping(value = "/product/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> doDeleter(@PathVariable Long id) {
        try {
            Product a = productService.findById(id);
            a.setId(id);
            a.setDeleteFlag(false);
            productService.update(a);
            List<Cart> cart = cartService.findAllByProduct(id);
            for (Cart cart2 : cart) {
                cart2.setDeleteFlag(false);
                cartService.update(cart2);
            }
//        Long merchantId = ((Merchant) httpSession.getAttribute("merchant")).getId();
//        messagingTemplate.convertAndSend("/topic/product/" + merchantId,
//                "Người bán đã xóa product có name: " + a.getName());
//        System.out.println("/topic/product/" + merchantId);
//        model.addAttribute("products", productService.findAll());
            return new ResponseEntity<>(new ApiResponse("ok"), HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

// Thực hiện sửa product
    @PostMapping("/product/edit")
    public ResponseEntity<?> editProduct(@RequestBody ProductForm productForm, @RequestParam Long merchantId)
            throws IOException {
        try {
            Product product = new Product(productForm.getId(), productForm.getName(), productForm.getShortDescription(),
                    productForm.getOldPrice(), productForm.getNewPrice(), productForm.getImage(),
                    productForm.getQuantity());
            Product product2 = productService.findById(productForm.getId());
            product.setNumberOrder(product2.getNumberOrder());
            product.setDeleteFlag(true);
            Merchant merchant = new Merchant();
            merchant.setId(merchantId);
            product.setMerchant(merchant);
            productService.update(product);
//		Long merchantId = ((Merchant) httpSession.getAttribute("merchant")).getId();
//		messagingTemplate.convertAndSend("/topic/product/" + merchantId,
//				"Người bán đã sửa product có name: " + product.getName());
            return new ResponseEntity<>(new ApiResponse("ok"), HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

//
// Thực hiện tìm kiểm product theo name gần đúng
//    @GetMapping("/product/search")
//    public String findProductByName(@RequestParam String name, @RequestParam(defaultValue = "0") int page, Model model,
//            HttpSession httpSession) {
//
//        int pageSize = 5; // số lượng phần tử trên mỗi trang
//        List<Product> listProducts = productService
//                .fAllByDeleFlagTAndMerAndNameContai(((Merchant) httpSession.getAttribute("merchant")).getId(), name);
//        // tính toán số trang cần hiển thị
//        int totalPages = listProducts.size() / pageSize;
//        if (listProducts.size() % pageSize > 0) {
//            totalPages++;
//        }
//
//        model.addAttribute("products", new Page().paging(page, pageSize, listProducts));
//        model.addAttribute("page", "product-list.jsp");
//        model.addAttribute("nav", 3);
//        model.addAttribute("totalPages", totalPages + 1);
//        model.addAttribute("currentPage", page);
//        return "merchant/merchant-layout";
//    }
//
// Hiển thị đơn hàng đang chờ merchant xác nhận
    @GetMapping("/order")
    public ResponseEntity<?> order(@RequestParam Long merchantId) {
        try {
            List<Order> orders = orderService.findByFlagAndStatus(OrderStatus.MERCHANT_PENDING.toString());
            List<Order> orders2 = orders.stream().filter(c -> c.getMerchant().getId() == (long) merchantId)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new ApiResponse(orders2), HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

// Thực hiện xác nhận cho đơn hàng pending thành người bán đã nhận 
    @PatchMapping(value = "/order/received/{orderId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> received(@PathVariable Long orderId, HttpServletRequest request) {
        try {
            Order order = orderService.findById(orderId);
            order.setStatus(OrderStatus.MERCHANT_RECEIVED.toString());

//        List<Order> orders = orderService.findByFlagAndStatus(OrderStatus.MERCHANT_RECEIVED.toString());
//        long merId = (long) ((Merchant) httpSession.getAttribute("merchant")).getId();
//        List<Order> orders2 = orders.stream().filter(c -> c.getMerchant_id() == merId).collect(Collectors.toList());

//        httpSession.setAttribute("a", 3);
//        httpSession.setAttribute("order", order);
//        httpSession.setAttribute("time", new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date()));
//        orderService.send(order, order.getAppUser().getId());
            List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
            List<OrderDetail> details = detailService.findByOrder(order.getId());
            List<Product> products = new ArrayList<>();
            for (OrderDetail orderDetail : details) {
                Map<String, Object> m = new HashMap<String, Object>();
                products.add(orderDetail.getProduct());
                m.put("product_name", orderDetail.getProduct().getName());
                m.put("product_price", orderDetail.getProduct().getNewPrice());
                m.put("product_quantity", orderDetail.getQuantity());
                m.put("product_totalPrice", orderDetail.getProduct().getNewPrice() * orderDetail.getQuantity());
                list.add(m);
            }
            Map<String, Object> m1 = new HashMap<>();
            m1.put("id_order", orderId);
            m1.put("merchant_name", order.getMerchant().getName());
            m1.put("merchant_phone", order.getMerchant().getPhone());
            m1.put("sum", order.getTotalPrice());
            m1.put("time", order.getOrderdate().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")));

            JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
            String jrxmlFile = request.getSession().getServletContext().getRealPath("/report/report.jrxml");
            Path path = Paths.get(jrxmlFile);
            String s = Files.readString(path, StandardCharsets.UTF_8);
            InputStream input = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
            JasperReport jasperReport = JasperCompileManager.compileReport(input);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, m1, jrDataSource);

            String fileName = UUID.randomUUID().toString() + ".pdf";
            String filePath = "D:\\quyet\\storage\\";
            Path pdfPath = Paths.get(filePath + fileName);
            System.out.print("pdf: " + pdfPath);
            // Export PDF to the file path
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath.toString());
            order.setPdf(fileName);
            orderService.update(order);

            Resource resource = new FileSystemResource(pdfPath);

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "test.pdf")
                    .contentType(MediaType.APPLICATION_PDF).body(resource);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

// Hiển thị đơn hàng người bán đã nhận
    @GetMapping("/order/received")
    public ResponseEntity<?> receivedP(@RequestParam Long merchantId) {
        try {
            List<Order> orders = orderService.findByFlagAndStatus(OrderStatus.MERCHANT_RECEIVED.toString());
            List<Order> orders2 = orders.stream().filter(c -> c.getMerchant().getId() == (long) merchantId)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new ApiResponse(orders2), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }

    }

// Hiển thị lịch sử đơn hàng
    @GetMapping("/order/history")
    public ResponseEntity<?> history(@RequestParam Long merchantId) {
        try {
            List<Order> orders = orderService.findByFlagAndStatus("");
            List<Order> orders2 = orders.stream().filter(c -> c.getMerchant().getId() == (long) merchantId)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new ApiResponse(orders2), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }

    }

// Thực hiện cập nhật đơn hàng thành ngươi bán từ chối
    @PatchMapping("/order/refuse/{orderId}")
    public ResponseEntity<?> refuse(@PathVariable Long orderId) {
        try {
            Order order = orderService.findById(orderId);
            order.setStatus(OrderStatus.MERCHANT_REFUSE.toString());
            orderService.update(order);
            // cập nhật số lượng hàng về như cũ
            List<OrderDetail> details = detailService.findByOrder(orderId);
            for (OrderDetail orderDetail : details) {
                Product product = orderDetail.getProduct();
                product.setQuantity(product.getQuantity() + orderDetail.getQuantity());
                productService.update(product);
            }
//        orderService.send(order, order.getAppUser().getId());
            return new ResponseEntity<>(new ApiResponse("ok"), HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("fail"), HttpStatus.BAD_REQUEST);
        }
    }

//// Hiển thị đơn hàng đã nhận, đã từ chối, đã đặt để thành lịch sử order
//    @GetMapping("/order/history")
//    public String history(Model model, HttpSession httpSession) {
//        List<Order> orders = orderService.findAll();
//        long merId = (long) ((Merchant) httpSession.getAttribute("merchant")).getId();
//        List<Order> orders2 = orders.stream().filter(
//                c -> ((c.getMerchant_id() == merId) && (c.getStatus().equals(OrderStatus.USER_RECEIVED.toString())
//                        || c.getStatus().equals(OrderStatus.USER_REFUSE.toString())
//                        || c.getStatus().equals(OrderStatus.MERCHANT_REFUSE.toString()))))
//                .collect(Collectors.toList());
//        model.addAttribute("orders", orders2);
//        model.addAttribute("page2", "order-history.jsp");
//        model.addAttribute("page", "order-layout.jsp");
//        model.addAttribute("nav", 2);
//        model.addAttribute("nav2", 4);
//        httpSession.setAttribute("a", 4);
//        return "merchant/merchant-layout";
//    }
//
//// Hiển thị đơn hàng người mua đã nhận 
//    @GetMapping("/order/send")
//    public String send(Model model, HttpSession httpSession) {
//        List<Order> orders = orderService.findByFlagAndStatus(OrderStatus.USER_RECEIVED.toString());
//        long merId = (long) ((Merchant) httpSession.getAttribute("merchant")).getId();
//        List<Order> orders2 = orders.stream().filter(c -> c.getMerchant_id() == merId).collect(Collectors.toList());
//        model.addAttribute("orders", orders2);
//        model.addAttribute("page2", "order-send.jsp");
//        model.addAttribute("page", "order-layout.jsp");
//        model.addAttribute("nav", 2);
//        model.addAttribute("nav2", 3);
//        httpSession.setAttribute("a", 5);
//        return "merchant/merchant-layout";
//    }
}
