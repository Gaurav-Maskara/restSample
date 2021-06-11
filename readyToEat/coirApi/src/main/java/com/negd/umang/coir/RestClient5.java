package com.negd.umang.coir;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class RestClient5 {

	public static void main(String[] args) throws UnirestException {
		HttpResponse<String> response = Unirest.get("http://coirservices.gov.in//androidService.asmx/getcoirServiceDataForUMANG?ApplicationNumber=100007&schemeCode=MCY&Beneficiary_ID=107516").asString();
		System.out.println("Response is ---->"+ response.getBody());

	}

}
