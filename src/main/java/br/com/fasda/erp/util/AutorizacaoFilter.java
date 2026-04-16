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
        
        String requestURI = request.getRequestURI();
        
        // 1. Verifica se é a página de login
        boolean paginaLogin = requestURI.endsWith("/Login.xhtml");
        
        // 2. Verifica se é um recurso (CSS, JS, Imagens) do PrimeFaces ou do seu sistema
        // O JSF usa o caminho /javax.faces.resource/ para entregar esses arquivos
        boolean recursoJSF = requestURI.contains("/javax.faces.resource/");

        if (paginaLogin || recursoJSF || (loginBean != null && loginBean.getNomeUsuario() != null)) {
            // Se for login, recurso ou usuário logado, permite o acesso
            chain.doFilter(req, res);
        } else {
            // Caso contrário, manda para o login
            response.sendRedirect(request.getContextPath() + "/Login.xhtml");
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