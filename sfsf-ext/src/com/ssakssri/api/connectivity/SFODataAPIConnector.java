package com.ssakssri.api.connectivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sap.hana.cloud.samples.benefits.odata.beans.BenefitsAmount;
import com.sap.hana.cloud.samples.benefits.odata.beans.UserInfo;
import com.ssakssri.api.connectivity.helper.CoreODataParser;
import com.ssakssri.api.connectivity.helper.SFUser;
import com.ssakssri.api.connectivity.http.HTTPConnector;
import com.ssakssri.api.connectivity.http.InvalidResponseException;

@SuppressWarnings("nls")
public class SFODataAPIConnector {

	private static final String SF_ODATA_API_DESTINATION_NAME = "sap_hcmcloud_core_odata_basic";
	private static SFODataAPIConnector INSTANCE = null;
	private static final String MANAGED_EMPLOYEES_QUERY = "User?$select=userId,username,firstName,lastName,email&$filter=hr/userId%20eq%20'#'";
	private static final String PROFILE_QUERY = "User('#')?$select=userId,username,firstName,lastName,email,hr/userId,hr/firstName,hr/lastName,hr/email&$expand=hr";
	private static final String INFO_QUERY = "User('#')?$select=userId,username,firstName,lastName,location,businessPhone,division,title,department,email,hr/firstName,hr/lastName,hr/businessPhone&$expand=hr";
	private static final String USER_PHOTO_QUERY = "Photo(photoType=#1,userId='#2')?$select=photo";
	private static final String USER_QUERY_BY_USERID = "User?$select=userId,username,displayName,email,title,location,division,hireDate,custom01,custom02,custom03,custom04,custom05,custom06,businessPhone,gender,dateOfBirth,cellPhone,department&$filter=userId%20eq%20'#'";
	private static final String CURRENT_STUDYING_QUERY = "cust_hunetdata?$filter=cust_process_cd%20eq%20'@'%20and%20cust_user_id%20eq%20'woomi#'";
	private static final String QUERY_EMPJOB = "EmpJob?$format=json&$select=seqNumber,startDate,endDate,event,eventReason,businessUnit,division,department,eventReasonNav/nameTranslationNav/value_ko_KR,departmentNav/name_ko_KR&$expand=eventNav,eventReasonNav/nameTranslationNav,departmentNav&$filter=userId eq '#'&$orderby=startDate desc,seqNumber desc&fromDate=1900-01-01&toDate=9999-12-31";

	private final CoreODataParser coreODataParser;
	private final HTTPConnector httpConnector;

	// ,hr/firstName,hr/lastName,hr/email,hr/businessPhone
	public static synchronized SFODataAPIConnector getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SFODataAPIConnector();
		}
		return INSTANCE;
	}

	private SFODataAPIConnector() {
		this.httpConnector = new HTTPConnector(SF_ODATA_API_DESTINATION_NAME);
		this.coreODataParser = CoreODataParser.getInstance();
	}

	private String getMangedEmployeesQuery(String hrSFUserName) {
		return MANAGED_EMPLOYEES_QUERY.replace("#", urlEncode(hrSFUserName));
	}

	private String getProfileQuery(String userName) {
		return PROFILE_QUERY.replace("#", urlEncode(userName));
	}

	private String getInfoQuery(String userName) {
		return INFO_QUERY.replace("#", urlEncode(userName));
	}

	private String getUserQueryByUserID(String userId) {
		return USER_QUERY_BY_USERID.replace("#", urlEncode(userId));
	}

	private String getCurrentStudyingYNQuery(String username, String processcd) {
		return CURRENT_STUDYING_QUERY.replace("#", urlEncode(username)).replace("@", processcd);
	}

	private String getEmpJobQuery(String userId) {
		return QUERY_EMPJOB.replace("#", urlEncode(userId));
	}

	private String urlEncode(String text) {
		try {
			return URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			String errMsg = String.format("Fail to encode text [%s]. Unsupported encoding [%s]", text,
					StandardCharsets.UTF_8.toString());
			throw new IllegalArgumentException(errMsg, e);
		}
	}

	public List<SFUser> getManagedEmployees(String hrSFUserName) throws IOException, InvalidResponseException {
		String userListJson = executeGET(getMangedEmployeesQuery(hrSFUserName));
		return coreODataParser.loadSFUserProfileListFromJsom(userListJson);
	}

	public SFUser getUserProfile(String userName) throws IOException, InvalidResponseException {
		String userJson = executeGET(getProfileQuery(userName));
		return coreODataParser.loadSFUserProfileFromJsom(userJson);
	}

	public SFUser getUserProfileByUserID(String userId) throws IOException, InvalidResponseException {
		String userJson = executeGET(getUserQueryByUserID(userId));

		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(userJson);

		JsonArray results = jsonElement.getAsJsonObject().get("d").getAsJsonObject().get("results").getAsJsonArray();
		;
		System.out.println("results size:" + results.size());
		
		SFUser user = new SFUser();
		if (results.size() == 1) {
			JsonObject userObj = results.get(0).getAsJsonObject();
			user.userId = userObj.get("userId").isJsonNull()?"":userObj.get("userId").getAsString();
			user.username = userObj.get("username").isJsonNull()?"":userObj.get("username").getAsString();
			user.displayName = userObj.get("displayName").isJsonNull()?"":userObj.get("displayName").getAsString();
			user.email = userObj.get("email").isJsonNull()?"":userObj.get("email").getAsString();
			user.title = userObj.get("title").isJsonNull()?"":userObj.get("title").getAsString();
			user.location = userObj.get("location").isJsonNull()?"":userObj.get("location").getAsString();
			user.division = userObj.get("division").isJsonNull()?"":userObj.get("division").getAsString();
			user.department = userObj.get("department").isJsonNull()?"":userObj.get("department").getAsString();

			user.custom01 = userObj.get("custom01").isJsonNull()?"":userObj.get("custom01").getAsString();
			user.custom02 = userObj.get("custom02").isJsonNull()?"":userObj.get("custom02").getAsString();
			user.custom03 = userObj.get("custom03").isJsonNull()?"":userObj.get("custom03").getAsString();
			user.custom04 = userObj.get("custom04").isJsonNull()?"":userObj.get("custom04").getAsString();
			user.custom05 = userObj.get("custom05").isJsonNull()?"":userObj.get("custom05").getAsString();
			user.custom06 = userObj.get("custom06").isJsonNull()?"":userObj.get("custom06").getAsString();

			user.businessPhone = userObj.get("businessPhone").isJsonNull()?"":userObj.get("businessPhone").getAsString();
			user.cellPhone = userObj.get("cellPhone").isJsonNull()?"":userObj.get("cellPhone").getAsString();
			user.gender = userObj.get("gender").isJsonNull()?"":userObj.get("gender").getAsString();
			user.dateOfBirth = userObj.get("dateOfBirth").isJsonNull()?"":userObj.get("dateOfBirth").getAsString();
			user.hireDate = userObj.get("hireDate").isJsonNull()?"":userObj.get("hireDate").getAsString();
			
			if (user.dateOfBirth != null) 
				user.dateOfBirth = convertUnixTimeToDateFormat(user.dateOfBirth);

			if (user.hireDate != null) 
				user.hireDate = convertUnixTimeToDateFormat(user.hireDate);

			
		} else
			return null;

		return user;
	}

	public SFUser getEmpJobHistory(String userId) throws IOException, InvalidResponseException {
		String userJson = executeGET(getEmpJobQuery(userId));

		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(userJson);

		JsonArray results = jsonElement.getAsJsonObject().get("d").getAsJsonObject().get("results").getAsJsonArray();
		;
		System.out.println("results size:" + results.size());

		SFUser user = new SFUser();
		if (results.size() == 1) {
			JsonObject userObj = results.get(0).getAsJsonObject();
			user.userId = userObj.get("userId").getAsString();
			user.username = userObj.get("username").getAsString();
			user.displayName = userObj.get("displayName").getAsString();
			user.email = userObj.get("email").getAsString();
			user.title = userObj.get("title").getAsString();
			user.location = userObj.get("location").getAsString();
			user.division = userObj.get("division").getAsString();
			user.department = userObj.get("department").getAsString();

			user.custom01 = userObj.get("custom01").getAsString();
			user.custom02 = userObj.get("custom02").getAsString();
			user.custom03 = userObj.get("custom03").getAsString();
			user.custom04 = userObj.get("custom04").getAsString();
			user.custom05 = userObj.get("custom05").getAsString();
			user.custom06 = userObj.get("custom06").getAsString();

			user.businessPhone = userObj.get("businessPhone").getAsString();
			user.cellPhone = userObj.get("cellPhone").getAsString();
			user.gender = userObj.get("gender").getAsString();
			user.dateOfBirth = userObj.get("dateOfBirth").getAsString();

			if (user.dateOfBirth != null)
				user.dateOfBirth = convertUnixTimeToDateFormat(userObj.get("dateOfBirth").getAsString());

		} else
			return null;

		return user;
	}

	public JsonObject getJsonProfileByUserID(String userId) throws IOException, InvalidResponseException {
		String userJson = executeGET(getUserQueryByUserID(userId));

		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(userJson);

		JsonArray results = jsonElement.getAsJsonObject().get("d").getAsJsonObject().get("results").getAsJsonArray();
		;
		System.out.println("results size:" + results.size());
		if (results.size() == 1) {
			JsonObject userObj = results.get(0).getAsJsonObject();

			return userObj;

		} else
			return null;

	}

	public boolean getCurrentStudyingYN(String username, String processcd)
			throws IOException, InvalidResponseException {
		String userJson = executeGET(getCurrentStudyingYNQuery(username, processcd));

		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(userJson);

		JsonArray results = jsonElement.getAsJsonObject().get("d").getAsJsonObject().get("results").getAsJsonArray();
		System.out.println("results size:" + results.size());

		if (results.size() > 0) {
			return true;
		}
		return false;
	}

	public UserInfo getUserInfoProfile(String userName) throws IOException, InvalidResponseException {
		String userJson = executeGET(getInfoQuery(userName));
		return coreODataParser.loadUserInfoFromJson(userJson);
	}

	public BenefitsAmount getUserBenefitsAmount(String userId) {
		return BenefitsAmount.defaultBenefitsAmount(userId);
	}

	public String getUserPhoto(String userId, Integer photoType) throws IOException, InvalidResponseException {
		String userPhotoJSON = executeGET(getUserPhotoQuery(userId, photoType));
		return coreODataParser.loadUserPhoto(userPhotoJSON);
	}

	private String executeGET(String query) throws InvalidResponseException, IOException {
		return this.httpConnector.executeGET(query).getContent();
	}

	private String getUserPhotoQuery(String userId, Integer photoType) {
		return USER_PHOTO_QUERY.replace("#1", String.valueOf(photoType)).replace("#2", urlEncode(userId));
	}

	public String getJsonResponse(String uriQuery) throws IOException, InvalidResponseException {
		String resJson = executeGET(uriQuery);
		return resJson;
	}

	public static String convertUnixTimeToDateFormat(String input) {
		Pattern pattern = Pattern.compile("/Date\\((\\d+)\\)/");
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			long unixTime = Long.parseLong(matcher.group(1));
			Date date = new Date(unixTime);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			return sdf.format(date);
		}

		throw new IllegalArgumentException("Invalid Unix time format");
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String userJson = "{\"d\": { \"results\": [ {\"__metadata\": {                    \"uri\": \"https://api50preview.sapsf.com/odata/v2/User('EYADMIN1')\",                    \"type\": SFOData.User\"                },                \"userId\": \"EYADMIN1\",                \"firstName\": Jeong-Seok\",                \"lastName\": \"Lee\",                \"email\": \"jeong-seok.lee@kr.ey.com\",                \"username\": \"eyadmin1\"   } ] }}";
		// String userJson = "{\"message\":\"Hi\",\"place\":{\"name\":\"World!\"}}";

		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(userJson);

		JsonElement d = jsonElement.getAsJsonObject().get("d");
		System.out.println("d:" + d.toString());

		JsonElement resultString = d.getAsJsonObject().get("results");
		System.out.println("r:" + resultString.toString());

		JsonArray results = resultString.getAsJsonArray();
		System.out.println("results size:" + results.size());

		if (results.size() == 1) {
			JsonObject user = results.get(0).getAsJsonObject();
			System.out.println("user:" + user.get("username"));

		}
	}
}
