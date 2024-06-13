package filter;

import com.google.gson.Gson;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.Response;
import utils.Authorization;


public class BaseFilter {
    protected Map<String, String> getMappingUrl(ServletContext servletContext) {
        //Key: url parttern, Value: class name
        Map<String, String> urlPatters = new TreeMap<>();
        //Get all servlet registration in app
        Map<String, ? extends ServletRegistration> servletsRegistrations = servletContext.getServletRegistrations();

        for (Map.Entry<String, ? extends ServletRegistration> servletsRegistration : servletsRegistrations.entrySet()) {
            //Get servlet registration
            ServletRegistration servletRegistration = servletsRegistration.getValue();
            //Get url mappings
            Collection<String> mappings = servletRegistration.getMappings();
            for (String url : mappings) {
                urlPatters.put(url, servletsRegistration.getValue().getClassName());
            }
        }
        return urlPatters;
    }

    protected String getRequestPath(String fullRequestUrl, String contextPath){
        return fullRequestUrl.replaceAll(contextPath, "");
    }
    
    protected boolean checkAnnotation(String requestPath, Map<String, String> urlPatters, Class checkClass) {
        for (Map.Entry<String, String> entry : urlPatters.entrySet()) {
            if(entry.getKey().equals(requestPath)){
                try {
                    Class<?> clazz = Class.forName(entry.getValue());
                    boolean isPresent = clazz.isAnnotationPresent(checkClass);
                    System.out.println(isPresent);
                    return isPresent;
                } catch (Exception ex) {
                    Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    
    protected void printJson(HttpServletRequest request, HttpServletResponse response, Response responseData) {
        try{
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            String message = gson.toJson(responseData);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(message);
            out.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
