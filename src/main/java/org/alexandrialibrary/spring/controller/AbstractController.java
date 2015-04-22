package org.alexandrialibrary.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador base.
 */
public abstract class AbstractController {

	/**
	 * Común a todos los controladores. Lo utilizamos para hacer System.out().
	 */
	protected static final Logger logger = LoggerFactory
			.getLogger(AbstractController.class);
}
