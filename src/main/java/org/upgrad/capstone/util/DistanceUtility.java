package org.upgrad.capstone.util;

import org.upgrad.capstone.domain.ZipCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Utility class that reads file zipCodePosId.csv and using same if two zip
 * codes are provided, it returns distances.
 */
public class DistanceUtility {

	HashMap<String, ZipCode> zipCodesMap = new HashMap<String, ZipCode>();

	/**
	 * Initialize zip codes using given file
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public DistanceUtility() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new FileReader("zipCodePosId.csv"));
		String line = null;
		while ((line = br.readLine()) != null) {
			String str[] = line.split(",");

			String zipCode = str[0];

			double lat = Double.parseDouble(str[1]);
			double lon = Double.parseDouble(str[2]);

			String city = str[3];
			String state_name = str[4];
			String postId = str[5];

			ZipCode zipCodeData = new ZipCode(lat, lon, city, state_name, postId);
			zipCodesMap.put(zipCode, zipCodeData);
		}
	}

	/**
	 *
	 * @param zipcode1
	 *            - zip code of previous transaction
	 * @param zipcode2
	 *            - zip code of current transaction
	 * @return distance between two zip codes
	 */
	public double getDistanceViaZipCode(String zipcode1, String zipcode2) {
		ZipCode z1 = zipCodesMap.get(zipcode1);
		ZipCode z2 = zipCodesMap.get(zipcode2);
		return distance(z1.getLat(), z1.getLon(), z2.getLat(), z2.getLon());
	}

	private double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return dist;
	}

	private double rad2deg(double rad) {
		return rad * 180.0 / Math.PI;
	}

	private double deg2rad(double deg) {
		return deg * Math.PI / 180.0;
	}
}

/*//To get the distance between two zipcodes from the main class  
public class PostcodeCalculator
{
	public static void main(String args[]) throws NumberFormatException, IOException
	{
		DistanceUtility disUtil=new DistanceUtility();
		
		System.out.println(disUtil.getDistanceViaZipCode("10001", "10524"));
	}
}
*/