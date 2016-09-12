package org.mobangjack.wechat.message.request;

import java.util.Map;

import org.mobangjack.wechat.message.BaseMsg;

/**
 * 请求方地理位置消息
 * @author 帮杰
 *
 */

public class LocationMsg extends BaseMsg {
	// 地理位置维度
	private String Location_X;
	// 地理位置经度
	private String Location_Y;
	// 地图缩放大小
	private String Scale;
	// 地理位置信息
	private String Label;

	public String getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(String location_X) {
		this.Location_X = location_X;
	}

	public String getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(String location_Y) {
		this.Location_Y = location_Y;
	}

	public String getScale() {
		return Scale;
	}

	public void setScale(String scale) {
		this.Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		this.Label = label;
	}

	@Override
	public void parseRequestXML(Map<String, String> xmlMap) {
		// TODO Auto-generated method stub
		this.setLocation_X(xmlMap.get("Location_X"));
		this.setLocation_Y(xmlMap.get("Location_Y"));
		this.setScale(xmlMap.get("Scale"));
		this.setLabel(xmlMap.get("Label"));
		super.parseRequestXML(xmlMap);
	}

}
