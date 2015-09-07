package tk.fjnugis.dangerserver.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 解决中文乱码的过滤器
 * Created by Xiangyang on 2014/12/1.
 */
public class CharsetEncodingFilter implements Filter{
    private String encoding;
    private final String defaultEncoding="UTF-8";
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        if(request.equals("post")){
            request.setCharacterEncoding(encoding);
        }else{
            request=new MyRequest(request);
        }
        response.setCharacterEncoding(encoding);
//        response.setContentType("text/html;charset=" + encoding);
        filterChain.doFilter(request,response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding=filterConfig.getInitParameter("Encoding");
        if(encoding==null||encoding.equals(""))
            this.encoding=defaultEncoding;
    }

    @Override
    public void destroy() {

    }

    private class MyRequest extends HttpServletRequestWrapper {
        public MyRequest(HttpServletRequest request) {
            super(request);
        }
        @Override
        public String[] getParameterValues(String name) {
            String pars[]=super.getParameterValues(name);
            for(int i=0;pars!=null&&i<pars.length;i++){
                try {
                    pars[i]=new String(pars[i].getBytes("iso8859-1"),encoding);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return pars;
        }

        @Override
        public String getParameter(String name) {
            name = super.getParameter(name);
            try {
                if(name!=null)
                    return new String(name.getBytes("iso8859-1"), encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
