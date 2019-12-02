package com.leimingtech.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author  
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface LmcmsEntityTitle {
	  String name();
}
