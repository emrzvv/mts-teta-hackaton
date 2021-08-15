package ru.mts.teta.hackaton.findmyphone.service;

import java.util.HashMap;
import java.util.Map;

public class SearchParser {
	public static Map<String, String> parse(String search) {
		HashMap<String, String> result = new HashMap<>();
		int indexWordStart=-1;
		boolean hasKey = false, needSpace = false;
		String key="", value="";
		for (int i=0; i<search.length(); ++i) {
			int let = search.codePointAt(i);
		    System.out.println("" + i + ". " + key + " " + let);
		    if ((let >= 97 && let <= 122) || (let >= 65 && let <= 90) || (let >= 48 && let <= 57)) {
		        if (!needSpace && indexWordStart == -1) {
		            indexWordStart = i;
		        }
		    } else if (let == 32 || let == 10) {
		        if (indexWordStart != -1 && hasKey == true) {
                    String v = search.substring(indexWordStart, i);
					result.put(key, v);
		        }
		        needSpace = false;
				key = "";
		        hasKey = false;
		        indexWordStart = -1;
		    } else if (let == 61) {
		        if (needSpace) {
		            continue;
		        }
		        
		        if (indexWordStart != -1 && hasKey == false) {
		            key = search.substring(indexWordStart, i);
		            hasKey = true;
		        } else {
		            if (hasKey) {
		                needSpace = true;
		            }
		            hasKey = false;
		        }
		        indexWordStart = -1;
		    }
		}
		if (!needSpace && hasKey && indexWordStart != -1) {
			String v = search.substring(indexWordStart, search.length());
			result.put(key.toLowerCase(), v);
		}
		return result;
	}
}