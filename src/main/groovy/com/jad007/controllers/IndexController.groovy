package com.jad007.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author jduncan
 * @since Mar 25, 2016
 * @version 1.0
 */
@Controller
class IndexController {
	@RequestMapping('/')
	String index() {
		return 'index'
	}
}
