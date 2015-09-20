/**
 * Copyright (c) 2011-2015, Mobangjack 莫帮杰 (mobangjack@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wechat.test;

import com.wechat.core.Api;
import com.wechat.core.Config;
import com.wechat.core.Handler;

/**
 * WechatApi
 * @author 帮杰
 *
 */
public class WechatApi extends Api {
	
	public static final Config CONFIG = new Config().loadPropertiesFile("wechat.properties");
	public static final Handler HANDLER = new WechatHandler();
	
	@Override
	protected Config getConfig() {
		return CONFIG;
	}
	
	@Override
	protected Handler getHandler() {
		return HANDLER;
	}


}
