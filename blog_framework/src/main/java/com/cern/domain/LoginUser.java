package com.cern.domain;

import com.cern.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// UserDetailsService返回的是一个UserDetails对象，但UserDetails是个接口
// 为此需要我们去实现这个接口，定制自己的返回类型
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private User user;

    private List<String> permissions;


    // TODO 待封装用户权限集合,权限是泛型指定类型，为此从数据库中查询出来的权限信息需要进一步封装成
    // TODO springSecurity需要的形式
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    // 判断登录状态是否过期。把这个改成true，表示永不过期
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    // 判断账号是否被锁定。把这个改成true，表示未锁定
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    // 判断登录凭证是否过期。把这个改成true，表示永不过期
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    // 判断用户是否可用。把这个改成true，表示可用状态
    public boolean isEnabled() {
        return true;
    }
}
