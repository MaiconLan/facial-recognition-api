package br.com.zapelini.lanzendorf.facialrecognitionapi.resource.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/token")
public class OAuthTokenEndpoint {

    @DeleteMapping
    public void revoke(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setHttpOnly(Boolean.TRUE);
        cookie.setSecure(Boolean.FALSE);
        cookie.setPath(request.getContextPath() + "/oauth/token");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }

}
