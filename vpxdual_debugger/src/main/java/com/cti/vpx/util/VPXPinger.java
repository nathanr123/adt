package com.cti.vpx.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class VPXPinger {

	public static boolean ping(String hostAddress) {
		String s;

		boolean isReachable = true;

		String Command = "ping -n 1 -i 1 " + hostAddress;

		try {
			Process p = Runtime.getRuntime().exec(Command);

			BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

			// reading output stream of the command
			while ((s = inputStream.readLine()) != null) {

				if (s.contains("host unreachable.") || s.contains("Request timed out") || s.contains("expired in transit")) {
					
					isReachable = false;
					
					break;
				}
			}

			s = null;
			
			inputStream.close();

		} catch (Exception e) {
			isReachable = false;
		}

		return isReachable;
	}
	
	public static void main(String[] args) {
		VPXPinger.ping("172.17.1.28");		
	}
}
