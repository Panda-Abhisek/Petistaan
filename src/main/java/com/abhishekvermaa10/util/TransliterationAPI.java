package com.abhishekvermaa10.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransliterationAPI {
	private static final String API = "https://inputtools.google.com/request?itc=%s&text=%s";

	public static String transliterate(String input_text, String isoCode) {
		String itcCode = isoCode + "-t-i0-und";
		String input = URLEncoder.encode(input_text, StandardCharsets.UTF_8);
		String url = String.format(API, itcCode, input);
		System.out.println(url);
		RestTemplate restTemplate = new RestTemplate();
		String object = restTemplate.getForObject(url, String.class);
		System.out.println(object);
		return extractTransliteratedText(object);
	}

	public static String extractTransliteratedText(String jsonResponse) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(jsonResponse);

			if (root.isArray() && root.size() > 1) {
				JsonNode secondNode = root.get(1);
				if (secondNode.isArray() && secondNode.size() > 0) {
					JsonNode firstInnerArray = secondNode.get(0);
					if (firstInnerArray.isArray() && firstInnerArray.size() > 1) {
						// The transliterated text is in the second element as an array, get its first
						// element
						JsonNode translitArray = firstInnerArray.get(1);
						if (translitArray.isArray() && translitArray.size() > 0) {
							return translitArray.get(0).asText();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
