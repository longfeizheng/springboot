package cn.merryyou.entity;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created on 2016/9/18 0018.
 *
 * @author zlf
 * @since 1.0
 */
@Table(name="t_user")
public class User extends BaseEntity {
    @Id
    private int id;
    private String account;
    private String password;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
