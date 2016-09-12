package com.wdyx.weixin.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.wdyx.weixin.menu.Menu;
import com.wdyx.weixin.thread.ScheduledTask;

@WebServlet(urlPatterns="/InitServlet",loadOnStartup=0)
public class InitServlet extends HttpServlet {    
    private static final long serialVersionUID = 1024328452214879L;       
    
    @Override
	public void init() throws ServletException {         
    	new Thread(new ScheduledTask()).start();
    	Menu.main(null);
    }    
}    
