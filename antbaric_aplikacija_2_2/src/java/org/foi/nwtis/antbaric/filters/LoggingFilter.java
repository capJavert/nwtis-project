package org.foi.nwtis.antbaric.filters;

import java.io.IOException;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.foi.nwtis.antbaric.beans.Dnevnik;
import org.foi.nwtis.antbaric.beans.DnevnikFacade;
import org.foi.nwtis.antbaric.beans.UserAuth;

@WebFilter("*.xhtml")
public class LoggingFilter implements Filter {

    @EJB
    DnevnikFacade dnevnikFacade;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Long start = System.currentTimeMillis();

        Dnevnik log = new Dnevnik();
        log.setData(((HttpServletRequest) request).getRequestURL().toString().replace("http://localhost:8080/antbaric_aplikacija_2_2", ""));
        log.setIpadresa(request.getRemoteAddr());
        UserAuth userAuth = (UserAuth) ((HttpServletRequest) request).getSession().getAttribute("user");
        if(userAuth != null) {
            log.setKorisnik(userAuth.getUser().getUsername());
        } else {
            log.setKorisnik("PUBLIC");
        }
        log.setTrajanje((int) (System.currentTimeMillis()-start));
        log.setVrijeme(new Date(System.currentTimeMillis()));
        
        dnevnikFacade.create(log);

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
