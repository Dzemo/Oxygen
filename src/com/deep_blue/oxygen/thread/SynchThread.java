package com.deep_blue.oxygen.thread;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.deep_blue.oxygen.model.Utilisateur;
import com.deep_blue.oxygen.thread.handler.SynchThreadHandler;

/**
 * 
 * Ce Thread envoie une requete au serveur web Oxygen. Cette requete contient
 * éventuellement l'identifiant de l'utilisateur courant de l'application et les
 * fiches de sécurité validé.
 * 
 * 
 * @author Raphael
 * 
 */
public class SynchThread extends Thread {

	/**
	 * Tag pour les message de Log
	 */
	private static final String TAG = "SynchThread";

	/**
	 * Nom du parametre get dans la requete http contenant le login de
	 * l'utilisateur courant
	 */
	private static final String QUERY_USER_LOGIN = "utilisateur_login";

	/**
	 * Nom du parametre get dans la requete http contenant le mot de passe de
	 * l'utilisateur courant
	 */
	private static final String QUERY_USER_PASSWORD = "utilisateur_mot_de_passe";

	/**
	 * Nom du parametre get dans la requete http contenant les données à envoyer
	 */
	private static final String QUERY_DATA = "data";

	private static final String URL_SITE_WEB = "http://10.0.2.2/deep_blue/api.php";

	private Context pContext;
	private SynchThreadHandler handler;
	private Utilisateur utilisateur;

	public SynchThread(Context pContext) {
		super();

		this.pContext = pContext;
		handler = new SynchThreadHandler(pContext);
		this.utilisateur = null;
	}

	public SynchThread(Context pContext, Utilisateur utilisateur) {
		this(pContext);
		this.utilisateur = utilisateur;
	}

	@Override
	public void start() {
		
		// Instantiate the RequestQueue.
		RequestQueue queue = Volley.newRequestQueue(pContext);
		String url = URL_SITE_WEB ;//+ "?" + generateQuery(utilisateur);

		// Request a string response from the provided URL.
		SynchRequest stringRequest = new SynchRequest(url, generateParams(utilisateur),
		            new Response.Listener<String>() {
		    @Override
		    public void onResponse(String response) {
		    	onResponseHandler(response);
		    }
		}, new Response.ErrorListener() {
		    @Override
		    public void onErrorResponse(VolleyError error) {
		    	onErrorResponseHandler(error);
		    }
		});
		
		// Add the request to the RequestQueue.
		Log.w(TAG, "lancement de la requete: "+url);
		
		Message msg = handler.obtainMessage(SynchThreadHandler.CODE_START);
		handler.sendMessage(msg);
		queue.add(stringRequest);
	}

	private void onResponseHandler(String response) {
		Log.w(TAG, "réponse de la requete '" + response + "'");

		Message msg = handler.obtainMessage(SynchThreadHandler.CODE_SUCCESS);
		handler.sendMessage(msg);
	}

	private void onErrorResponseHandler(VolleyError error) {
		Log.e(TAG, "erreur de la requete: " + error.getMessage());
	}

	/**
	 * Génère les paramêtre de la requete envoyer au serveur
	 * 
	 * @return
	 */
	private Map<String, String> generateParams(Utilisateur utilisateur) {
		Map<String, String> params = new HashMap<String, String>();

		// Ajout de l'utilisateur
		if (utilisateur != null && utilisateur.getLogin() != null
				&& utilisateur.getMotDePasse() != null) {
			try {

				byte[] dataLogin = utilisateur.getLogin().getBytes("UTF-8");
				String loginBase64 = Base64.encodeToString(dataLogin,
						Base64.DEFAULT);
				params.put(QUERY_USER_LOGIN, loginBase64);

				byte[] dataPassword = utilisateur.getMotDePasse().getBytes(
						"UTF-8");
				String passwordBase64 = Base64.encodeToString(dataPassword,
						Base64.DEFAULT);
				params.put(QUERY_USER_PASSWORD, passwordBase64);

			} catch (UnsupportedEncodingException e) {
				// Ne devrait jamais arriver
				e.printStackTrace();
			}
		}

		// Ajout des data
		params.put(QUERY_DATA, "no_data");

		return params;
	}

	/*
	 * private void parseJsonResponse(String response){ JsonFactory jsonFactory
	 * = new JsonFactory();
	 * 
	 * try { JsonParser jsonParser = jsonFactory.createJsonParser(response);
	 * ObjectMapper objectMapper = new ObjectMapper();
	 * 
	 * //objectMapper.readValue(jsonParser, valueType) } catch
	 * (JsonParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } }
	 */

}
