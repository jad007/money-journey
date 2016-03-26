package com.jad007.commandline

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * @author jduncan
 * @since Mar 25, 2016
 * @version 1.0
 */
@Controller
class ExpenseController {
	
	@RequestMapping('/expenses')
	@ResponseBody
	def index() {
		return "hello world"
	}
}
