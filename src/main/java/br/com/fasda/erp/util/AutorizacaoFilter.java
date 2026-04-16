package br.com.fasda.erp.util;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig; // Adicione este import
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.fasda.erp.controller.LoginBean;

@WebFilter("*.xhtml")
public class AutorizacaoFilter implements Filter {

    @Inject
    private LoginBean loginBean;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        // Se não estiver logado e não estiver na página de login, redireciona
        if (!request.getRequestURI().endsWith("/Login.xhtml") 
                && (loginBean == null || loginBean.getNomeUsuario() == null)) {
            response.sendRedirect(request.getContextPath() + "/Login.xhtml");
        } else {
            chain.doFilter(req, res);
        }
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Pode deixar vazio, mas o método PRECISA existir
    }

    @Override
    public void destroy() {
        // Pode deixar vazio, mas o método PRECISA existir
    }
}