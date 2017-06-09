/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.components;

import java.util.List;

/**
 *
 * @author javert
 */
public class GResponse {
	private String status;
	private List<GResult> results;
	private String error_message;
	
	public String getFormattedAddress() {
		return this.results.get(0).formatted_address;
	}
	
	public double getLongitude() {
		return Double.parseDouble(this.results.get(0).geometry.location.lng);
	}
	
	public double getLatitude() {
		return Double.parseDouble(this.results.get(0).geometry.location.lat);
	}
	
	public boolean hasAddress() {
		return this.status.equals("OK") && this.results.size() > 0;
	}
	
	public String toString() {
		if ( this.status.equals("OK")) {
			return this.status + "\n"
				+ this.getFormattedAddress() + "\n" 
				+ this.getLatitude() + "\n"
				+ this.getLongitude() + "\n";
		} else {
			return this.status + ": " + this.error_message;
		}
	}	
}
