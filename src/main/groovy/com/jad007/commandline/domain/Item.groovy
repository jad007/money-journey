package com.jad007.commandline.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * @author jduncan
 * @since Mar 25, 2016
 * @version 1.0
 */
@ToString(includeFields = true, includeNames = true)
@EqualsAndHashCode
class Item {
	String name
	String category
	BigDecimal price
	BigDecimal quantity
}
