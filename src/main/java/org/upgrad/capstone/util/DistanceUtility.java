package org.upgrad.capstone.util;

import org.upgrad.capstone.domain.ZipCodePosIdMap;
import org.upgrad.capstone.hbase.HBaseDAO;

/**
 * Utility class that reads file zipCodePosId.csv and using same if two zip
 * codes are provided, it returns distances.
 */
public class DistanceUtility {

	/**
	 * Calculates distance between two zip codes
	 * @param zipcode1 - zip code of previous transaction
	 * @param zipcode2 - zip code of current transaction
	 * @return distance between two zip codes
	 */
	public double getDistanceViaZipCode(String zipcode1, String zipcode2) throws Exception {
		HBaseDAO hBaseDAO = new HBaseDAO();
		ZipCodePosIdMap z1 = hBaseDAO.getZipCodePosIdMap(zipcode1);
		ZipCodePosIdMap z2 = hBaseDAO.getZipCodePosIdMap(zipcode2);
		return distance(z1.getLatitude(), z1.getLongitude(), z2.getLatitude(), z2.getLongitude());
	}

	/**
	 * Calculates distance between two zip codes
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
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
