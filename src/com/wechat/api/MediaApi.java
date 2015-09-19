/**
 * Copyright (c) 2011-2015, Mobangjack 莫帮�? (mobangjack@foxmail.com).
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
package com.wechat.api;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.wechat.util.http.ApiRequest;
import com.wechat.util.http.MultipartFormUploadRequest;
import com.wechat.util.json.JsonObject;
import com.wechat.util.json.JsonParser;

public class MediaApi {

	public static final String ADD_TEMP_MATERIAL_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	public static final String GET_TEMP_MATERIAL_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	public static final String ADD_PERMANENT_NEWS_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";
	public static final String ADD_PERMANENT_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";
	public static final String GET_PERMANENT_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	public static final String DELETE_PERMANENT_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";
	public static final String UPDATE_NEWS_URL = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=ACCESS_TOKEN";
	public static final String GET_MATERIAL_COUNT_URL = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
	public static final String GET_BATCH_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	
	public static final String MEDIA_TYPE_IMAGE = "image";
	public static final String MEDIA_TYPE_VOICE = "voice";
	public static final String MEDIA_TYPE_VIDEO = "video";
	public static final String MEDIA_TYPE_THUMB = "thumb";
	public static final String MEDIA_TYPE_NEWS = "news";
	
	/**
	 *<P>新增临时素材
	 *<p> 公众号经常有需要用到一些临时性的多媒体素材的场景，例如在使用接口特别是发送消息时，对多媒体文件、多媒体消息的获取和调用等操作，是通过media_id来进行的。素材管理接口对所有认证的订阅号和服务号开放。通过本接口，公众号可以新增临时素材（即上传临时多媒体文件）。

	 *<p>请注意：

	 *<p>1、对于临时素材，每个素材（media_id）会在开发者上传或粉丝发送到微信服务器3天后自动删除（所以用户发送给开发者的素材，若开发者需要，应尽快下载到本地），以节省服务器资源。
	 *<p>2、media_id是可复用的。
	 *<p>3、素材的格式大小等要求与公众平台官网一致。具体是，图片大小不超过2M，支持bmp/png/jpeg/jpg/gif格式，语音大小不超过5M，长度不超过60秒，支持mp3/wma/wav/amr格式
	 *<p>4、需使用https调用本接口。

	 *<p>本接口即为原“上传多媒体文件”接口。 
	 *
	 * @param accessToken 公众号access_token
	 * @param file 待上传的素材文件
	 * @param mediaType 媒体文件类型（image/voice/video/thumb/news）
	 * @return media_id
	 */
	public static String addTempMaterial(String accessToken,File file,String mediaType) {
		String url = ADD_TEMP_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", mediaType);
		MultipartFormUploadRequest request =  new MultipartFormUploadRequest(url);
		Map<String, Object> formDataMap = new HashMap<String, Object>();
		formDataMap.put("type", mediaType);
		formDataMap.put("media", file);
		request.setFormData(formDataMap);
		request.setMethod("POST");
		String mediaId = null;
		try {
			request.doRequest();
			String s = request.getResponseAsString();
			JsonObject jsonObject = JsonParser.parseJsonObject(s);
			System.out.println(s);
			if (jsonObject.containsKey("thumb_media_id")) {
				mediaId = jsonObject.getString("thumb_media_id");
			} else if (jsonObject.containsKey("media_id")) {
				mediaId = jsonObject.getString("media_id");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			request.close();
		}
		return mediaId;
	}
	
	/**
	 * 公众号可以使用本接口获取临时素材（即下载临时的多媒体文件）。请注意，视频文件不支持下载。
	 * @param accessToken 公众号access_token
	 * @param mediaId 素材的media_id
	 * @return
	 */
	public static byte[] getTempMaterial(String accessToken,String mediaId) {
		String url = GET_TEMP_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		return new ApiRequest(url).doPost().getResourceAsBytes();
	}
	
	public static boolean getTempImage(String accessToken,String mediaId,File dest) {
		String url = GET_TEMP_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		return new ApiRequest(url).doPost().getResourceAsFile(dest);
	}
	
	public static JsonObject addPermanetNews(String accessToken,String jsonNews) {
		String url = ADD_PERMANENT_NEWS_URL.replace("ACCESS_TOKEN", accessToken);
		return new ApiRequest(url).setOutData(jsonNews.getBytes()).doPost().getResourceAsJsonObject();
	}
	
	public static byte[] getPermanetMaterial(String accessToken,String mediaId) {
		String url = GET_PERMANENT_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);
		String out = "{\"media_id\":"+"\""+mediaId+"\"}";
		return new ApiRequest(url).setOutData(out.getBytes()).doPost().getResourceAsBytes();
	}
	
	public static JsonObject getPermanetNews(String accessToken,String mediaId) {
		String url = GET_PERMANENT_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);
		String out = "{\"media_id\":"+"\""+mediaId+"\"}";
		return new ApiRequest(url).setOutData(out.getBytes()).doPost().getResourceAsJsonObject();
	}
	
	public static JsonObject getPermanetVideo(String accessToken,String mediaId) {
		String url = GET_PERMANENT_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);
		String out = "{\"media_id\":"+"\""+mediaId+"\"}";
		return new ApiRequest(url).setOutData(out.getBytes()).doPost().getResourceAsJsonObject();
	}
	
	public static JsonObject deletePermanentMaterial(String accessToken,String mediaId) {
		String url = DELETE_PERMANENT_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);
		String out = "{\"media_id\":"+"\""+mediaId+"\"}";
		return new ApiRequest(url).setOutData(out.getBytes()).doPost().getResourceAsJsonObject();
	}
	
	public static JsonObject updateNews(String accessToken,String jsonUpdateNews) {
		String url = UPDATE_NEWS_URL.replace("ACCESS_TOKEN", accessToken);
		return new ApiRequest(url).setOutData(jsonUpdateNews.getBytes()).doPost().getResourceAsJsonObject();
	}
	
	public static JsonObject getMaterialCount(String accessToken) {
		String url = GET_MATERIAL_COUNT_URL.replace("ACCESS_TOKEN", accessToken);
		return new ApiRequest(url).doGet().getResourceAsJsonObject();
	}
	
	public static JsonObject getBatchMaterial(String accessToken,String type,int offset,int count) {
		String url = GET_BATCH_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);
		String out = "\"type\":\""+type+"\",\"offset\":\""+offset+"\",\"count\":\""+count+"\"";
		return new ApiRequest(url).setOutData(out.getBytes()).doPost().getResourceAsJsonObject();
	}
	
	public static void main(String[] args) {
		//String str = "http://www.xxx.com/1噶为欧冠奇偶位i二姐http://www.xxx.com/1/2乌黑欧冠我几乎http://www.xxx.com/1/2/3的稿费欧文和岗位i偶尔给http://www.xxx.com/1.jpg";
        //str = str.replaceAll("(?is)(http://[/\\.\\w]+\\.jpg)","<img src='$1'/>");
        //str = str.replaceAll("(?is)(?<!')(http://[/\\.\\w]+)","<a href='$1'>$1</a>");
        //System.out.println(str);
		String token = "xohaaockdFH0VC1tGyAHq63URTDClG2LCkzuBWIA_hHgF3lpPm60ZV69dh4GR8DUDEE9VX7nesgrkLaYfshMSZ8XJG0rWqHpsa2aIz5TZ4k";
		//System.out.println(getMaterialCountJsonStr(token));
		System.out.println(token);
		File file = new File("C:\\Users\\帮杰\\Pictures\\Camera Roll\\WIN_20150214_181258.MP4");
		System.out.println(addTempMaterial(token, file, MEDIA_TYPE_VIDEO));
		
	}
}
