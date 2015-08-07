/**
 * 
 */
package com.cti.vpx.Listener;


/**
 * @author RajuDhachu
 *
 */
public interface VPXAdvertisementListener extends VPXUDPListener {

	public void updateProcessorStatus(String ip, String msg);
	
	public void updatePeriodicity(String ip, int periodicity);
	
	public void updatePeriodicity(int periodicity);
}
