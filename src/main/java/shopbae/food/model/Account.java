package shopbae.food.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String password;
    private boolean isEnabled;
    private String email;
    private String otp;
    @OneToOne(mappedBy = "account")
    private AppUser user;
    @OneToOne(mappedBy = "account")
    private Merchant merchant;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<AccountRoleMap> accountRoleMapSet;
    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "failed_attempt")
    private int failedAttempt;

    @Column(name = "lock_time")
    private Date lockTime;

    public Account() {
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public int getFailedAttempt() {
        return failedAttempt;
    }

    public void setFailedAttempt(int failedAttempt) {
        this.failedAttempt = failedAttempt;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Account(Long id, String userName, String password, boolean isEnabled, String email, String otp,
            Set<AccountRoleMap> accountRoleMapSet) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.isEnabled = isEnabled;
        this.email = email;
        this.otp = otp;
        this.accountRoleMapSet = accountRoleMapSet;
    }

    public Account(String userName, String password, boolean isEnabled, String email) {
        this.userName = userName;
        this.password = password;
        this.isEnabled = isEnabled;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Set<AccountRoleMap> getAccountRoleMapSet() {
        return accountRoleMapSet;
    }

    public void setAccountRoleMapSet(Set<AccountRoleMap> accountRoleMapSet) {
        this.accountRoleMapSet = accountRoleMapSet;
    }

}
