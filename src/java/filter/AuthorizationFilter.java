package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;

import java.io.IOException;
import java.util.Enumeration;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getServletPath();
        HttpSession session = httpRequest.getSession(false);

        if (session == null) {
            System.out.println("Session is null.");
        } else {
            System.out.println("Session ID: " + session.getId());
            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println("Session attribute: " + attributeName + " = " + session.getAttribute(attributeName));
            }
        }

        User user = (User) (session != null ? session.getAttribute("user") : null);
        String userRole = (user != null) ? user.getRole() : null;

        // Debug logging
        System.out.println("Requested path: " + path);
        System.out.println("User role: " + userRole);

        if (isProtectedPath(path, "Marketing") && (userRole == null || !userRole.equals("Marketing"))) {
            System.out.println("Unauthorized access attempt to protected path: " + path);
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/common/unauthorized.jsp");
            return;
        }
        if (isProtectedPath(path, "Admin") && (userRole == null || !userRole.equals("Admin"))) {
            System.out.println("Unauthorized access attempt to protected path: " + path);
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/common/unauthorized.jsp");
            return;
        }
        if (isProtectedPath(path, "Sale") && (userRole == null || !userRole.equals("Sale"))) {
            System.out.println("Unauthorized access attempt to protected path: " + path);
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/common/unauthorized.jsp");
            return;
        }

        if (userRole != null && userRole.equals("Marketing") && path.equals("/login")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/screens/MarketingCustomersList.jsp");
            return;
        }
        if (userRole != null && userRole.equals("Admin") && path.equals("/login")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/screens/admin_list.jsp");
            return;
        }
        if (userRole != null && userRole.equals("Sale") && path.equals("/login")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/screens/OrderListSale.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private boolean isProtectedPath(String path, String role) {
        if ("Marketing".equals(role)) {
            return path.startsWith("/screens/MarketingCustomersList.jsp");
        } else if ("Admin".equals(role)) {
            return path.startsWith("/screens/Admin.jsp");
        } else if ("Sale".equals(role)) {
            return path.startsWith("/screens/OrderListSale.jsp");
        }
        return false;
    }
}
