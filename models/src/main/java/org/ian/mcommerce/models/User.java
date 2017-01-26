package org.ian.mcommerce.models;

import javax.persistence.*;

/**
 * Created by jiangkun on 17/1/25.
 */
@Entity
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //    @Index(name = "index_user_account", unique = true,columnList = "account")
    @Column(unique = true, nullable = false)
//    @NotEmpty(message = "请填写帐号")
    private String account;

    private String name;

    @Column(nullable = false)
//    @NotEmpty(message = "请填写密码")
    private String password;

    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


