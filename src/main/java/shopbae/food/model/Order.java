package shopbae.food.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "AppUser_id")
    private AppUser appUser;

    private String note;

    private LocalDateTime orderdate;

    private String status;
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    private double totalPrice;
    private String deliveryAddress;
    private String pdf;
    @Column(name = "flag")
    private boolean flag = true;

    public Order() {

    }

    public Order(Long id, AppUser appUser, String note, LocalDateTime orderdate, String status, Merchant merchant,
            double totalPrice) {
        super();
        this.id = id;
        this.appUser = appUser;
        this.note = note;
        this.orderdate = orderdate;
        this.status = status;
        this.merchant = merchant;
        this.totalPrice = totalPrice;
    }

    public Order(AppUser appUser, String note, LocalDateTime orderdate, String status, Merchant merchant,
            double totalPrice) {
        super();
        this.appUser = appUser;
        this.note = note;
        this.orderdate = orderdate;
        this.status = status;
        this.merchant = merchant;
        this.totalPrice = totalPrice;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(LocalDateTime orderdate) {
        this.orderdate = orderdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", appUser=" + appUser + ", note=" + note + ", orderdate=" + orderdate + ", status="
                + status + ", merchant_id=" + merchant + ", totalPrice=" + totalPrice + ", deliveryAddress="
                + deliveryAddress + ", flag=" + flag + "]";
    }

}
