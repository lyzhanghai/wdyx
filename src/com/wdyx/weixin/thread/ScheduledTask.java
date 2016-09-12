package com.wdyx.weixin.thread;

import java.util.List;

import org.apache.log4j.Logger;

import com.wdyx.weixin.service.DigitalLibraryServiceUtil;
import com.wdyx.weixin.service.EduInfoServiceUtil;
import com.wdyx.weixin.user.User;

/**
 * This class is used to do some scheduled task
 * @author 帮杰
 *
 */
public class ScheduledTask implements Runnable{
	private static final Logger logger = Logger.getLogger(ScheduledTask.class);
	@Override
	public void run() {
		try {
			Thread.sleep(60*1000);
		} catch (InterruptedException e1) {
			logger.error(e1);
			e1.printStackTrace();
		}
		while(true){
			//grab common user
			User.grabCommonUser();
			//EIS DLS
			List<User> users = User.getBoundUser();
			if(users!=null&&!users.isEmpty()){
				for(User user:users){
					EduInfoServiceUtil.updateDB(user.getOpenid());
					DigitalLibraryServiceUtil.updateDB(user.getOpenid());
				}
			}
			
			try {
				Thread.sleep(24*60*3600*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}

}
