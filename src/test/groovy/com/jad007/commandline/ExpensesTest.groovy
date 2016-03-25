package com.jad007.commandline

import com.jad007.commandline.domain.Expense
import com.jad007.commandline.domain.Item
import groovy.json.JsonBuilder

/**
 * @author jduncan
 * @since Mar 25, 2016
 * @version 1.0
 */
class ExpensesTest extends GroovyTestCase {
	void "test parse single expense"() {
		def input = new JsonBuilder()
		input {
			date '2016/03/25'
			store 'Acme'
			subtotal 10.00
			tax 0.60
			total 10.60
			items({
				name 'bananas'
				price 0.50
				quantity 5
				category 'food'
			}, {
				name 'potatoes'
				price 1.00
				quantity 7.5
				category 'food'
			})
		}

		def expectedExpenses = new Expense(
			date: '2016/03/25', store: 'Acme', subtotal: 10.00, tax: 0.60, total: 10.60, items: [
			new Item(name: 'bananas', price: 0.50, quantity: 5, category: 'food'),
			new Item(name: 'potatoes', price: 1.00, quantity: 7.5, category: 'food')
		])

		assert Expenses.parseExpense(input.toPrettyString()) == expectedExpenses
	}

	void "test parse list of expenses"() {
		def input = new JsonBuilder()
		input({
			date '2016/03/25'
			store 'Acme'
			subtotal 10.00
			tax 0.60
			total 10.60
			items({
				name 'bananas'
				price 0.50
				quantity 5
				category 'food'
			}, {
				name 'potatoes'
				price 1.00
				quantity 7.5
				category 'food'
			})
		},
			{
				date '2016/03/26'
				store 'Acme'
				subtotal 10.00
				tax 0.60
				total 10.60
				items({
					name 'bananas'
					price 0.50
					quantity 5
					category 'food'
				}, {
					name 'potatoes'
					price 1.00
					quantity 7.5
					category 'food'
				})
			}
		)

		def expectedExpenses = [
			new Expense(
				date: '2016/03/25', store: 'Acme', subtotal: 10.00, tax: 0.60, total: 10.60, items: [
				new Item(name: 'bananas', price: 0.50, quantity: 5, category: 'food'),
				new Item(name: 'potatoes', price: 1.00, quantity: 7.5, category: 'food')
			]),
			new Expense(
				date: '2016/03/26', store: 'Acme', subtotal: 10.00, tax: 0.60, total: 10.60, items: [
				new Item(name: 'bananas', price: 0.50, quantity: 5, category: 'food'),
				new Item(name: 'potatoes', price: 1.00, quantity: 7.5, category: 'food')
			])
		]
		
		def actualExpenses = Expenses.parseExpenses(input.toPrettyString())

		assert actualExpenses == expectedExpenses
	}
}
