package com.deep_blue.oxygen.thread;

import java.util.Map;
import java.util.Map.Entry;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class SynchRequest extends StringRequest {

	private Map<String, String> params;

	/**
	 * 
	 * @param url
	 * @param params
	 * @param listener
	 * @param errorListener
	 */
	public SynchRequest(String url, Map<String, String> params,
			Response.Listener<String> listener,
			Response.ErrorListener errorListener) {
		super(Request.Method.POST, url, listener, errorListener);

		this.params = params;
	}

	@Override
	public Map<String, String> getParams() {
		for(Entry<String, String> param : params.entrySet()){
			System.out.println(param.getKey()+"->"+param.getValue());
		}
		return params;
	}

}
