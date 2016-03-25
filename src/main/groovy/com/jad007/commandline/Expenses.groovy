package com.jad007.commandline

import com.jad007.commandline.domain.Expense
import com.jad007.commandline.domain.Item
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

/**
 * @author jduncan
 * @since Mar 25, 2016
 * @version 1.0
 */
class Expenses {
	List<Expenses> expenses

	public static void main(String[] args) {
		assert args.size() == 1

		def fileName = args[0]
		def file = new File(fileName)
		if (!file.exists()) {
			file.createNewFile()
		}

		def text = file.text
		Expenses program = new Expenses(expenses: text ? parseExpenses(file.text) : [])
		def exit = false
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in))
		try {
			while (!exit) {
				println 'Enter Command (q/quit, a/add):'
				def command = console.readLine()
				switch (command) {
					case 'q':
					case 'quit':
						exit = true
						break
					case 'a':
					case 'add':
						program.expenses << readExpense(console)
						break
				}
			}
		}
		finally {
			console.close()
		}

		if (file.canWrite()) {
			file.text = JsonOutput.toJson(program.expenses)
		}
	}

	private static Expense readExpense(BufferedReader console) {
		Expense newExpense = new Expense()
		println 'Date:'
		newExpense.date = console.readLine()
		println 'Store:'
		newExpense.store = console.readLine()
		println 'Subtotal:'
		newExpense.subtotal = new BigDecimal(console.readLine())
		println 'Tax:'
		newExpense.tax = new BigDecimal(console.readLine())
		println 'Total:'
		newExpense.total = new BigDecimal(console.readLine())
		
		def exit = false
		while (!exit) {
			println 'Add Item? (y/n):'
			def answer = console.readLine()
			if('n' == answer) {
				exit = true
				continue
			}
			Item newItem = new Item()
			println 'Name:'
			newItem.name = console.readLine()
			println 'Price:'
			newItem.price = new BigDecimal(console.readLine())
			println 'Quantity:'
			newItem.quantity = new BigDecimal(console.readLine())
			println 'Category:'
			newItem.category = console.readLine()
			newExpense.items << newItem
		}
		return newExpense
	}

	public static Expense parseExpense(String input) {
		def parseResult = new JsonSlurper().parseText(input)
		parseExpenseMap(parseResult)
	}

	private static Expense parseExpenseMap(Map input) {
		def itemList = []
		input.items.each {
			itemList << new Item(it)
		}
		new Expense(input << [items: itemList])
	}

	public static List<Expenses> parseExpenses(String input) {
		def parseResult = new JsonSlurper().parseText(input)
		def expenseList = []

		parseResult.each { expenseList << parseExpenseMap(it) }

		return expenseList
	}
}
