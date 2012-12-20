package net.openwatch.reporter.constants;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * A class containing values that are not user-facing and so
 * are not intended for translation.
 * All user-facing values are contained in res/values/
 * @author davidbrodsky
 *
 */
public class Constants {
	// Date Formatter for OW server time
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

	// SharedPreferences titles
	public static final String PROFILE_PREFS = "Profile";
	
	// External storage 
	public static final String ROOT_OUTPUT_DIR = "OpenWatch";
	public static final String RECORDING_OUTPUT_DIR = "recordings";
	
	// User profile keys. Used for SharedPreferences and Intent Extras
	public static final String EMAIL = "email";
	public static final String AUTHENTICATED = "authenticated";
	public static final String DB_READY = "db_ready";
	public static final String PUB_TOKEN = "public_upload_token"; 		// used for profile and to parse server login response
	public static final String PRIV_TOKEN = "private_upload_token";		// used for profile and to parse server login response
	public static final String REGISTERED = "registered"; 
	public static final String VIEW_TAG_MODEL = "model";		// key set on listview item holding corresponding model pk
	public static final String INTERNAL_DB_ID = "id";
	
	// Email REGEX
	public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
            "@" +
            "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
            "(" +
            "." +
            "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
            ")+"
        );
	
	// API Request timeout (ms)
	public static final int TIMEOUT = 5000;
	
	// OpenWatch web service root url and endpoints
	//public static final String OW_URL = "http://www.openwatch.net/api/"; // TODO: HTTPS
	//public static final String OW_API_URL = "http://192.168.1.27:8000/api/";
	//public static final String OW_URL = "http://192.168.1.27:8000/";
	public static final String OW_API_URL = "http://alpha.openwatch.net/api/";
	public static final String OW_URL = "http://alpha.openwatch.net/";
	public static final String OW_VIEW = "v/";
	public static final String OW_LOGIN = "login_account";
	public static final String OW_SIGNUP = "create_account";
	public static final String OW_REGISTER = "register_app";
	public static final String OW_RECORDING = "recording";
	public static final String OW_TAGS = "tags";
	public static final String OW_UPDATE_META = "update_metadata";
	
	// OpenWatch web service POST keys
	public static final String OW_EMAIL = "email_address";
	public static final String OW_PW = "password";
	public static final String OW_SIGNUP_TYPE = "signup_type";
	
	// OpenWatch web service response keys
	public static final String OW_SUCCESS = "success";
	public static final String OW_ERROR = "code";
	public static final String OW_REASON = "reason";
	
	// OpenWatch media capture web service url and endpoints
	public static final String OW_MEDIA_URL = "http://capture.openwatch.net/";
	public static final String OW_MEDIA_START = "start";
	public static final String OW_MEDIA_END = "end";
	public static final String OW_MEDIA_UPLOAD = "upload";
	public static final String OW_MEDIA_HQ_UPLOAD = "upload_hq";
	
	// OpenWatch media capture web service POST keys
	public static final String OW_REC_START = "recording_start";
	public static final String OW_REC_END = "recording_end";
	public static final String OW_REC_UUID = "uuid";
	public static final String OW_ALL_FILES = "all_files";
	public static final String OW_UP_TOKEN = "upload_token";
	public static final String OW_FILE = "upload";
	public static final String OW_TITE = "title";
	public static final String OW_DESCRIPTION = "description";
	public static final String OW_EDIT_TIME = "last_edited";
	public static final String OW_START_LAT = "start_lat";
	public static final String OW_START_LON = "start_lon";
	public static final String OW_END_LAT = "end_lat";
	public static final String OW_END_LON = "end_lon";
	
		
	
	

}