package com.ssakssri.api.connectivity.http;

public interface HTTPResponseValidator {
	
   void validateHTTPResponse(SimpleHttpResponse httpResponse) throws InvalidResponseException;

}
