package shopbae.food.model.dto;

import java.util.List;

import lombok.Builder;
import shopbae.food.model.AppUser;
import shopbae.food.model.Merchant;

@Builder
public class AccountToken {
    private long id;
    private String username;
    private String token;

    private List<String> roles;

    private Merchant merchant;
    private AppUser user;

    public AccountToken(long id, String username, String token, List<String> roles, Merchant merchant, AppUser user) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.roles = roles;
        this.merchant = merchant;
        this.user = user;
    }

    public AccountToken() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}