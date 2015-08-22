/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.edu.fjnu.gis11.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class SetCharacterEncodingFilter implements Filter {

	private String defaultEncoding = "utf-8";
	private FilterConfig config = null;

	class sword extends HttpServletRequestWrapper {

		public sword(HttpServletRequest request) {
			super(request);
		}
		@Override
		public String[] getParameterValues(String name) {
			String pars[]=super.getParameterValues(name);
			for(int i=0;pars!=null&&i<pars.length;i++){
				try {
					pars[i]=new String(pars[i].getBytes("iso8859-1"),"utf-8");
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
				return new String(name.getBytes("iso8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		if (req.getMethod().equals("post")) {
			req.setCharacterEncoding("utf-8");
		} else {
			request = new sword(req);
		}

		String charset = config.getInitParameter("charset");
		if (charset == null) {
			charset = defaultEncoding;
		}
		request.setCharacterEncoding(charset);
		response.setCharacterEncoding(charset);
		response.setContentType("text/html;charset=" + charset);

		chain.doFilter(request, response);
	}

	public void destroy() {

	}

}