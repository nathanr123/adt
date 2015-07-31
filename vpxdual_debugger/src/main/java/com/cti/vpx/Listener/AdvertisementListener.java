/**
 * 
 */
package com.cti.vpx.Listener;


/**
 * @author RajuDhachu
 *
 */
public interface AdvertisementListener extends UDPListener {

	public void updateProcessorStatus(String ip, String msg);
	
	public void updatePeriodicity(String ip, int periodicity);
}
