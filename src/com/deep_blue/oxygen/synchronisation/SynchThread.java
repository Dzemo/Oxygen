package com.deep_blue.oxygen.synchronisation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.dao.AptitudeDao;
import com.deep_blue.oxygen.dao.EmbarcationDao;
import com.deep_blue.oxygen.dao.FicheSecuriteDao;
import com.deep_blue.oxygen.dao.HistoriqueDao;
import com.deep_blue.oxygen.dao.MoniteurDao;
import com.deep_blue.oxygen.dao.SiteDao;
import com.deep_blue.oxygen.dao.UtilisateurDao;
import com.deep_blue.oxygen.model.EnumEtat;
import com.deep_blue.oxygen.model.Historique;
import com.deep_blue.oxygen.model.ListeFichesSecurite;
import com.deep_blue.oxygen.model.Utilisateur;
import com.deep_blue.oxygen.synchronisation.json.JsonRequestContainer;
import com.deep_blue.oxygen.synchronisation.json.JsonResponseContainer;
import com.deep_blue.oxygen.util.IntentKey;
import com.deep_blue.oxygen.util.PreferenceKey;

/**
 * 
 * Ce Thread envoie une requete au serveur web Oxygen. Cette requete contient
 * éventuellement l'identifiant de l'utilisateurConnecte courant de l'application et les
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
	 * Nom du parametre get dans la requete http contenant les données à envoyer
	 */
	private static final String QUERY_DATA = "data";

	private Context pContext;
	private SynchThreadHandler handler;
	private Utilisateur utilisateurConnecte;
	private String url;
	private SharedPreferences preferences;

	/**
	 * Initialise le thread
	 */
	public SynchThread(Context pContext) {
		super();

		this.pContext = pContext;
		handler = new SynchThreadHandler(pContext);
		this.utilisateurConnecte = null;

		preferences = PreferenceManager.getDefaultSharedPreferences(pContext);

		url = preferences.getString(PreferenceKey.REMOTE_URL.toString(), "");

		if (!url.endsWith("/"))
			url += "/";
		url += "api.php";
	}

	public SynchThread(Context pContext, Utilisateur utilisateur) {
		this(pContext);
		this.utilisateurConnecte = utilisateur;
	}

	@Override
	public void start() {
		if (!isNetworkConnected()) {
			// Si les données ne sont pas activé on ne tente pas la
			// synchronisation
			Message msg = handler.obtainMessage(SynchThreadHandler.CODE_ERROR);
			msg.getData().putString(
					IntentKey.SYNCH_ERROR_TEXT.toString(),
					pContext.getResources().getString(
							R.string.synch_error_no_connection));
			handler.sendMessage(msg);

		} else {
			// Instantiate the RequestQueue.
			RequestQueue queue = Volley.newRequestQueue(pContext);

			// Request a string response from the provided URL.
			SynchRequest stringRequest;
			try {
				stringRequest = new SynchRequest(url, generateParams(),
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
				queue.add(stringRequest);

				Message msg = handler
						.obtainMessage(SynchThreadHandler.CODE_START);
				handler.sendMessage(msg);
			} catch (SynchronisationException e) {
				Message msg = handler
						.obtainMessage(SynchThreadHandler.CODE_ERROR);
				msg.getData().putString(
						IntentKey.SYNCH_ERROR_TEXT.toString(),
						pContext.getResources().getString(
								R.string.synch_error_generation));
				handler.sendMessage(msg);
			}
		}
	}

	private void onResponseHandler(String response) {
		Log.i(TAG, "réponse de la requete '" + response + "'");

		if("no-data".equals(response)){
			Message msg = handler.obtainMessage(SynchThreadHandler.CODE_ERROR);
			msg.getData().putString(IntentKey.SYNCH_ERROR_TEXT.toString(), pContext.getResources().getString(
							R.string.synch_error_no_data));
			handler.sendMessage(msg);
		} else{
			try {
				processResponse(response);
				Message msg = handler.obtainMessage(SynchThreadHandler.CODE_SUCCESS);
				handler.sendMessage(msg);
			} catch (SynchronisationException e) {
				Message msg = handler.obtainMessage(SynchThreadHandler.CODE_ERROR);
				msg.getData().putString(IntentKey.SYNCH_ERROR_TEXT.toString(), pContext.getResources().getString(
								R.string.synch_error_parse));
				handler.sendMessage(msg);
			}
		}
	}

	private void onErrorResponseHandler(VolleyError error) {
		String textMessageToaster = "";

		if (error.toString().startsWith("com.android.volley.TimeoutError")) {
			textMessageToaster = pContext.getResources().getString(
					R.string.synch_error_timeout);
		} else if (error.getMessage().startsWith(
				"java.net.UnknownHostException")) {
			textMessageToaster = pContext.getResources().getString(
					R.string.synch_error_host_unknown);
		} else {
			textMessageToaster = error.getMessage();
		}

		Message msg = handler.obtainMessage(SynchThreadHandler.CODE_ERROR);
		msg.getData().putString(IntentKey.SYNCH_ERROR_TEXT.toString(),
				textMessageToaster);
		handler.sendMessage(msg);
	}

	/**
	 * Génère les paramêtre de la requete envoyer au serveur
	 * 
	 * @return
	 * @throws SynchronisationException
	 */
	private Map<String, String> generateParams()
			throws SynchronisationException {
		Map<String, String> params = new HashMap<String, String>();

		JsonRequestContainer jsonRequestContainer = new JsonRequestContainer();
		UtilisateurDao utilisateurDao = new UtilisateurDao(pContext);

		// Initialisation de la requête
		jsonRequestContainer.setUtilisateurMaxVersion(utilisateurDao
				.getMaxVersion());

		// Si utilisateurConnecte présent
		if (utilisateurConnecte != null) {
			jsonRequestContainer.setUtilisateurLogin(utilisateurConnecte.getLogin());

			// Paramètre de synchronisation
			jsonRequestContainer.setSynchRetrieveLength(preferences.getInt(
					PreferenceKey.RETREIVE_LENGTH.toString(), 0));
			jsonRequestContainer.setSynchRetrieveTypeAll(preferences
					.getBoolean(
							PreferenceKey.RETREIVE_TYPE_ALL_FICHE.toString(),
							false));

			// MaxVersion de aptitude, enmbarcation, site, fiche et moniteur
			AptitudeDao aptitudeDao = new AptitudeDao(pContext);
			jsonRequestContainer.setAptitudeMaxVersion(aptitudeDao
					.getMaxVersion());
			EmbarcationDao embarcationDao = new EmbarcationDao(pContext);
			jsonRequestContainer.setEmbarcationMaxVersion(embarcationDao
					.getMaxVersion());
			SiteDao siteDao = new SiteDao(pContext);
			jsonRequestContainer.setSiteMaxVersion(siteDao.getMaxVersion());
			FicheSecuriteDao ficheSecuriteDao = new FicheSecuriteDao(pContext);
			jsonRequestContainer.setFicheSecuriteMaxVersion(ficheSecuriteDao
					.getMaxVersion());
			MoniteurDao moniteurDao = new MoniteurDao(pContext);
			jsonRequestContainer.setMoniteurMaxVersion(moniteurDao
					.getMaxVersion());

			// Fiche et historique
			ListeFichesSecurite listeFichesSecuriteValidees = ficheSecuriteDao
					.getByEtat(EnumEtat.VALIDE);
			jsonRequestContainer
					.setFichesSecuriteValidees(listeFichesSecuriteValidees);
			HistoriqueDao historiqueDao = new HistoriqueDao(pContext);
			List<Historique> listeHistoriques = historiqueDao
					.getForListFiche(listeFichesSecuriteValidees);
			jsonRequestContainer.setHistoriques(listeHistoriques);
		}

		// Transformation de JsonRequestContainer en String Json
		ObjectMapper objectMapper = new ObjectMapper();
		String json = "";

		try {
			json = objectMapper.writeValueAsString(jsonRequestContainer);
		} catch (JsonGenerationException e) {
			throw new SynchronisationException(e);
		} catch (JsonMappingException e) {
			throw new SynchronisationException(e);
		} catch (IOException e) {
			throw new SynchronisationException(e);
		}

		// Ajout des data
		params.put(QUERY_DATA, json);

		return params;
	}

	/**
	 * Enregistre les infos récupéré par le serveur
	 * @param response
	 * @throws SynchronisationException 
	 */
	private void processResponse(String response) throws SynchronisationException {
		JsonFactory jsonFactory = new JsonFactory();

		try {
			JsonParser jsonParser = jsonFactory.createJsonParser(response);

			ObjectMapper objectMapper = new ObjectMapper();

			JsonResponseContainer jsonResponseContainer = objectMapper.readValue(jsonParser, JsonResponseContainer.class);
			
			//Récupération des utilisateurs
			if(jsonResponseContainer.getUtilisateurs() != null){
				UtilisateurDao utilisateurDao = new UtilisateurDao(pContext);
				for(Utilisateur utilisateur : jsonResponseContainer.getUtilisateurs()){
					if(utilisateurDao.getByLogin(utilisateur.getLogin()) != null){
						utilisateurDao.update(utilisateur);
					}
					else{
						utilisateurDao.insert(utilisateur);
					}
				}
			}
			
			
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw new SynchronisationException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new SynchronisationException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SynchronisationException(e);
		}
	}

	/**
	 * Vérifie si le téléphone a accès aux données réseaux
	 * 
	 * @return
	 */
	private boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) pContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo() != null;
	}

}
