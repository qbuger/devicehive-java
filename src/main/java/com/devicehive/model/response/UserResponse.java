package com.devicehive.model.response;

import com.devicehive.json.strategies.JsonPolicyDef;
import com.devicehive.model.*;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static com.devicehive.json.strategies.JsonPolicyDef.Policy.*;
import static com.devicehive.json.strategies.JsonPolicyDef.Policy.USERS_LISTED;
import static com.devicehive.json.strategies.JsonPolicyDef.Policy.USER_PUBLISHED;

/**
 * @author  Nikolay Loboda
 * @since 30.07.13
 */
public class UserResponse implements HiveEntity {

    @SerializedName("id")
    @JsonPolicyDef({COMMAND_TO_CLIENT, COMMAND_TO_DEVICE, USER_PUBLISHED, USERS_LISTED})
    private Long id;

    @SerializedName("login")
    @JsonPolicyDef({USER_PUBLISHED, USERS_LISTED})
    private String login;

    @Column(name = "login_attempts")
    private Integer loginAttempts;

    @SerializedName("role")
    private UserRole role;

    @SerializedName("status")
    @JsonPolicyDef({USER_PUBLISHED, USERS_LISTED})
    private UserStatus status;

    @JsonPolicyDef({USER_PUBLISHED})
    private Set<UserNetworkResponse> networks;

    @SerializedName("lastLogin")
    @JsonPolicyDef({USER_PUBLISHED, USERS_LISTED})
    private Timestamp lastLogin = new Timestamp(0);


    public static UserResponse createFromUser(User u){
        UserResponse response = new UserResponse();
        response.setId(u.getId());
        response.setLogin(u.getLogin());
        response.setLoginAttempts(u.getLoginAttempts());
        response.setRole(u.getRole());
        response.setStatus(u.getStatus());
        response.networks = new HashSet<>();
        for(Network n:u.getNetworks()){
            response.networks.add(UserNetworkResponse.fromNetwork(n));
        }
        response.setLastLogin(u.getLastLogin());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Set<UserNetworkResponse> getNetworks() {
        return networks;
    }

    public void setNetworks(Set<UserNetworkResponse> networks) {
        this.networks = networks;
    }

    public Integer getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }
}