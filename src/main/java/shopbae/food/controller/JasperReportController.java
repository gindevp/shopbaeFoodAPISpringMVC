package shopbae.food.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import shopbae.food.model.Merchant;
import shopbae.food.model.Order;
import shopbae.food.model.OrderDetail;
import shopbae.food.model.Product;
import shopbae.food.service.order.IOrderService;
import shopbae.food.service.orderDetail.IOrderDetailService;

@Controller
@RequestMapping("/jasper")
public class JasperReportController {

    @Autowired
    IOrderDetailService detailService;
    @Autowired
    IOrderService orderService;

    @GetMapping("/report/{orderId}")
    public String generateReport(ModelMap modelMap, HttpSession httpSession, HttpServletRequest request,
            HttpServletResponse response, @PathVariable Long orderId) throws Exception {
        List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
        Order order = (Order) httpSession.getAttribute("order");
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
        m1.put("id_order", (Long) ((Order) httpSession.getAttribute("order")).getId());
        m1.put("merchant_name", (String) ((Merchant) httpSession.getAttribute("merchant")).getName());
        m1.put("merchant_phone", (String) ((Merchant) httpSession.getAttribute("merchant")).getPhone());
        m1.put("sum", (Double) httpSession.getAttribute("sum"));
        m1.put("time", (String) httpSession.getAttribute("time"));

        JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
        String jrxmlFile = request.getSession().getServletContext().getRealPath("/report/report.jrxml");
        Path path = Paths.get(jrxmlFile);
        String s = Files.readString(path, StandardCharsets.UTF_8);
        InputStream input = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
//    	InputStream input = new FileInputStream(new File(jrxmlFile));
        JasperReport jasperReport = JasperCompileManager.compileReport(input);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, m1, jrDataSource);

        String fileName = UUID.randomUUID().toString() + ".pdf";
        String filePath = "D:\\quyet\\storage\\";
        Path pdfPath = Paths.get(filePath + fileName);
        System.out.print("pdf: " + pdfPath);
        // Export PDF to the file path
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath.toString());
        Order order2 = orderService.findById(orderId);
        order2.setPdf(fileName);
        orderService.update(order2);
        return "merchant/view-report";
    }
}
