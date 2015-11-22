package common;

public class Common {

	/*
	 * Network
	 * This is default setting for all network request in app.
	 */
	
	/**
	 * Time out = NETWORK_TIME_OUT  (ms)
	 */
	public static final int NETWORK_TIME_OUT = 120000;
	
	/**
	 * Time out = NETWORK_TRANSFER_TIME_OUT  (ms)
	 */
	public static final int NETWORK_TRANSFER_TIME_OUT = 120000;
	
	/**
	 * Short time out = NETWORK_TIME_OUT_SHORT (ms)
	 */
	public static final int NETWORK_TIME_OUT_SHORT = 20000;
	
	public static final int NETWORK_MAX_ATTEMP = 0;
	public static final int NETWORK_BACKOFF_MULT = 0;
	
	public static final String PROTOCOL_CHARSET = "utf-8";
	
	public static final String SEARCH_JOBS_URL = "http://private-anon-abb346f45-jobhackv1.apiary-mock.com/jobs/search";
	public static final String LIST_PINNED_JOB_URL = "http://private-anon-abb346f45-jobhackv1.apiary-mock.com/pin/id";
	public static final String API_KEY ="75evg0vyi3x4bs";
	public static final String SECRET ="R9C4EbEHpN3KPTj1";

	public static String LINKEDIN_CONSUMER_KEY = "75evg0vyi3x4bs";
	public static String LINKEDIN_CONSUMER_SECRET = "R9C4EbEHpN3KPTj1";
	public static String scopeParams = "rw_nus+r_basicprofile";
	
	public static String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
	public static String OAUTH_CALLBACK_HOST = "callback";
	public static String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
}
