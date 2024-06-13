package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Response;
import models.User;
import utils.Authentication;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = "/*")
public class AuthenticationFilter extends BaseFilter implements Filter {

    private static final String REDIRECT_PAGE = "../home_page";
    

    private ServletContext context;

    public void init(FilterConfig config) {
        this.context = config.getServletContext();
        System.out.println("Init Check Authen");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String fullRequestUrl = request.getRequestURI();
        String requestPath = getRequestPath(fullRequestUrl, request.getContextPath());
        if (requestPath.contains(".jsp")) {
            response.sendRedirect(REDIRECT_PAGE);
        } else {
            Map<String, String> urlPatters = getMappingUrl(context);
            
            if (!checkAnnotation(requestPath, urlPatters, Authentication.class)) {
                chain.doFilter(request, response);
                return;
            }
            
            boolean isAuthen = getAuthenticationController(requestPath, urlPatters);
            
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("currUser");

            if (isAuthen && user == null) {
                response.setStatus(401);
                String check = request.getHeader("Is-Ajax-Request");
                if (check != null && check.equals("1")) {
                    Response responseData = new Response("false", "401 - Please login to access this resource!");
                    printJson(request, response, (javax.xml.ws.Response) responseData);
                    return;
                }
                request.setAttribute("error_code", "401 - Please login to access this resource!");
                request.getRequestDispatcher("/common/unauthorization.jsp")
                        .forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    private boolean getAuthenticationController(String requestPath, Map<String, String> urlPatters) {
        boolean isAuthen = false;
        for (Map.Entry<String, String> entry : urlPatters.entrySet()) {
            if (entry.getKey().equals(requestPath)) {
                try {
                    Class<?> clazz = Class.forName(entry.getValue());
                    Authentication authentication = clazz.getDeclaredAnnotation(Authentication.class);
                    isAuthen = Optional.ofNullable(authentication)
                            .map(Authentication::isAuthen)
                            .orElse(false);
                    return isAuthen;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return isAuthen;
    }
}
