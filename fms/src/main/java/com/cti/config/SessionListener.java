/**
 * 
 */
package com.cti.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author nathanr_kamal
 *
 */
public class SessionListener implements HttpSessionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http
	 * .HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("session Created");

		se.getSession().setMaxInactiveInterval(20*60);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet
	 * .http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("session Destroyed");

	}

}
