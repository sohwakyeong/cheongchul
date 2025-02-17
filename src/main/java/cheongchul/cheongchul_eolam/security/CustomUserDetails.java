package cheongchul.cheongchul_eolam.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails  implements UserDetails {
    private final long memberId;
    private final String password;
    private final List<GrantedAuthority> authorities;


    public CustomUserDetails(long memberId,String password,List<GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.password = password;
        this.authorities = authorities;
    }

    public long getMemberId() {
        return memberId;
    }

    @Override
    public String getUsername() {
        return String.valueOf(memberId);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
}
