package org.mobangjack.wechat.api.material;

import java.io.IOException;
import java.io.InputStream;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.mobangjack.common.util.InputStreamUtil;
import org.mobangjack.wechat.util.HttpsUtil;

public class MaterialMgrApi {

	public static final String ADD_TEMP_MATERIAL_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	public static final String GET_TEMP_MATERIAL_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	public static final String ADD_PERMANENT_NEWS_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";
	public static final String ADD_PERMANENT_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";
	public static final String GET_PERMANENT_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	public static final String DELETE_PERMANENT_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";
	public static final String UPDATE_NEWS_URL = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=ACCESS_TOKEN";
	public static final String GET_MATERIAL_COUNT_URL = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
	public static final String GET_BATCH_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	
	public static String addTempMaterial(String accessToken,byte[] bytes,String mediaType) {
		String media_id = null;
		String url = ADD_TEMP_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", mediaType);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
		entityBuilder.addBinaryBody("media", bytes);
		HttpEntity entity = entityBuilder.build();
		try {
			httppost.setEntity(entity);
			HttpResponse response = httpclient.execute(httppost);
			EntityUtils.consume(entity);
			entity = response.getEntity();
			String json = EntityUtils.toString(entity);
			JSONObject jsonObj = JSONObject.fromObject(json);
			media_id = json.contains("thumb_media_id")?jsonObj.optString("thumb_media_id"):jsonObj.optString("media_id");
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return media_id;
	}
	
	public static String addTempMaterial(String accessToken,InputStream is,String mediaType) {
		return addTempMaterial(accessToken,InputStreamUtil.readBytes(is),mediaType);
	}
	
	public static InputStream getTempMaterial(String accessToken,String mediaId) {
		String url = GET_TEMP_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		return HttpsUtil.getInputStream(url, "POST", "");
	}
	
	public static String addPermanetNews(String accessToken,String jsonNews) {
		String url = ADD_PERMANENT_NEWS_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpsUtil.getJsonObject(url, "POST", jsonNews);
		return jsonObject.optString("media_id");
	}
	
	public static String addPermanetNews(String accessToken,BaseNews baseNews) {
		return addPermanetNews(accessToken,JSONObject.fromObject(baseNews).toString());
	}
	
	private static Object getPermanetMaterial(String accessToken,String mediaId,String type) {
		Object object = null;
		String url = GET_PERMANENT_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);
		InputStream inputStream = HttpsUtil.getInputStream(url, "POST", "{\"media_id\":"+"\""+mediaId+"\"}");
		if(type.equalsIgnoreCase(MaterialType.NEWS)||type.equalsIgnoreCase(MaterialType.VIDEO)){
			object = InputStreamUtil.readStr(inputStream, "UTF-8");
		}else {
			object = inputStream;
		}
		return object;
	}
	
	public static News getPermanetNews(String accessToken,String mediaId) {
		Object object = getPermanetMaterial(accessToken, mediaId, MaterialType.NEWS);
		JSONObject jsonObject = JSONObject.fromObject(object);
		News news = (News) JSONObject.toBean(jsonObject, News.class);
		return news;
	}
	
	public static Video getPermanetVideo(String accessToken,String mediaId) {
		Object object = getPermanetMaterial(accessToken, mediaId, MaterialType.VIDEO);
		JSONObject jsonObject = JSONObject.fromObject(object);
		Video video = (Video) JSONObject.toBean(jsonObject, Video.class);
		return video;
	}
	
	public static InputStream getPermanetImage(String accessToken,String mediaId) {
		Object object = getPermanetMaterial(accessToken, mediaId, MaterialType.IMAGE);
		return (InputStream) object;
	}
	
	public static InputStream getPermanetThumb(String accessToken,String mediaId) {
		Object object = getPermanetMaterial(accessToken, mediaId, MaterialType.THUMB);
		return (InputStream) object;
	}
	
	public static InputStream getPermanetVoice(String accessToken,String mediaId) {
		Object object = getPermanetMaterial(accessToken, mediaId, MaterialType.VOICE);
		return (InputStream) object;
	}
	
	public static boolean deletePermanentMaterial(String accessToken,String mediaId) {
		String url = DELETE_PERMANENT_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpsUtil.getJsonObject(url, "POST", "{\"media_id\":"+"\""+mediaId+"\"}");
		return jsonObject.getInt("errcode")==0;
	}
	
	public static boolean updateNews(String accessToken,String jsonUpdateNews) {
		String url = UPDATE_NEWS_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpsUtil.getJsonObject(url, "POST", jsonUpdateNews);
		return jsonObject.getInt("errcode")==0;
	}
	
	public static boolean updateNews(String accessToken,NewsForUpdate newsForUpdate) {
		return updateNews(accessToken,JSONObject.fromObject(newsForUpdate).toString());
	}
	
	public static String getMaterialCountJsonStr(String accessToken) {
		String url = GET_MATERIAL_COUNT_URL.replace("ACCESS_TOKEN", accessToken);
		return HttpsUtil.getString(url, "GET", "");
	}
	
	public static MaterialCount getMaterialCount(String accessToken) {
		String json = getMaterialCountJsonStr(accessToken);
		JSONObject jsonObject = JSONObject.fromObject(json);
		if(jsonObject.optInt("errcode")!=0){
			return null;
		}
		MaterialCount materialCount = (MaterialCount) JSONObject.toBean(jsonObject, MaterialCount.class);
		return materialCount;
	}
	
	/**
	 * 
	 * @param accessToken
	 * @param type:素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
	 * @param offset:从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @param count:返回素材的数量，取值在1到20之间
	 * @return
	 */
	public static String getBatchMaterial(String accessToken,String type,int offset,int count) {
		String url = GET_BATCH_MATERIAL_URL.replace("ACCESS_TOKEN", accessToken);
		String outStr = "\"type\":\""+type+"\",\"offset\":\""+offset+"\",\"count\":\""+count+"\"";
		return HttpsUtil.getString(url, "POST", outStr);
	}
	
	public static void main(String[] args) {
		String str = "http://www.xxx.com/1噶为欧冠奇偶位i二姐http://www.xxx.com/1/2乌黑欧冠我几乎http://www.xxx.com/1/2/3的稿费欧文和岗位i偶尔给http://www.xxx.com/1.jpg";
        str = str.replaceAll("(?is)(http://[/\\.\\w]+\\.jpg)","<img src='$1'/>");
        str = str.replaceAll("(?is)(?<!')(http://[/\\.\\w]+)","<a href='$1'>$1</a>");
        System.out.println(str);
	}
}
