package nz.ac.op.soften2016.security;


import nz.ac.op.soften2016.service.Dao;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thoml on 12/09/2016.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private Dao dao;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
/*        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(User.class);
        List<User> users  = criteria.list();*/
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (name.equals("root") && password.equals("P@ssw0rd")) {
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
            return auth;
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}