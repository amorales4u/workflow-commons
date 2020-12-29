package dev.c20.workflow.commons.filters;

import dev.c20.workflow.commons.CommonsConfig;
import dev.c20.workflow.commons.tools.PathUtils;
import dev.c20.workflow.commons.tools.StringUtils;
import dev.c20.workflow.commons.tools.WFException;
import org.apache.commons.io.IOUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "ContentCachingFilter", urlPatterns = "/*")
public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("IN  ContentCachingFilter ");
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpServletRequest);

        String restService = PathUtils.splitPath(httpServletRequest.getRequestURI())[1];
        if( !isOpenServices(restService) ) {
            // validate token
            logger.info("==>/" + restService + "/<== Is not open service, we need Validate token");
            String tokenData = revalidateToken(httpServletRequest,httpServletResponse);
            httpServletResponse.setHeader(CommonsConfig.HEADER_AUTHORIZATION, CommonsConfig.HEADER_AUTHORIZATION_TOKEN + tokenData);
        }
        String body = IOUtils.toString(cachedBodyHttpServletRequest.getReader());
        body.replaceAll("(?i)<(/?script[^>]*)>", "&lt;$1&gt;");
        System.err.println("Cache Request:[" + httpServletRequest.getRequestURI() + "] =>" + IOUtils.toString(cachedBodyHttpServletRequest.getReader()));
        filterChain.doFilter(cachedBodyHttpServletRequest, httpServletResponse);

    }

    public String revalidateToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String token = httpServletRequest.getHeader(CommonsConfig.HEADER_AUTHORIZATION);

        if( token == null || token.equals(""))
            throw new RuntimeException("No se recibio el Token");

        logger.info("Token:" + token);
        token = token.substring(CommonsConfig.HEADER_AUTHORIZATION_TOKEN.length());
        logger.info(token);

        Map<String,Object> tokenData = StringUtils.readToken(token);
        tokenData.put("dummy", StringUtils.randomString(20));
        long validTo = (Long)tokenData.get("validTo");

        if( System.currentTimeMillis() > validTo )
            throw new RuntimeException("Token caduco");
        HttpSession session =  httpServletRequest.getSession(true);
        if( session.getAttribute("otp") != null ) {
            String otp = (String)session.getAttribute("otp");
            if( tokenData.get("otp").equals(otp) ) {
                otp = StringUtils.randomString(5);
                session.setAttribute("otp", otp);
                tokenData.put("otp", otp);
            } else {
                throw  new RuntimeException("Token invalido");
            }
        }

        return StringUtils.getToken(tokenData);
    }

    public boolean isOpenServices( String restService) {
        /*
        for(int i = 0; i < WorkflowApplication.SERVICES_WITHOUT_AUTH.length; i ++ ) {
            if( WorkflowApplication.SERVICES_WITHOUT_AUTH[i].equals(restService) )
                return true;
        }
        */
        return true;

    }
}
