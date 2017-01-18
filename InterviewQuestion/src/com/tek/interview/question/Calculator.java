package com.tek.interview.question;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class Calculator {

	public static double rounding(double value) {
		return ((int) (value * 100)) / 100;
	}

	/*
	 * receives a collection of orders. For each order, iterates on the order
	 * lines and calculate the total price which is the item's price * quantity
	 * * taxes.
	 * 
	 * For each order, print the total Sales Tax paid and Total price without
	 * taxes for this order
	 */
	public void calculate(Map<String, Order> o) {

		double grandTotal = 0;
		RoundingMode roundingMode = RoundingMode.HALF_EVEN;
		BigDecimal bd;
		// Iterate through the orders
		for (Map.Entry<String, Order> entry : o.entrySet()) {
			System.out.println("*******" + entry.getKey() + "*******");
			Order r = entry.getValue();
			double totalTax = 0;
			double total = 0;
			// Iterate through the items in the order
			for (int i = 0; i < r.size(); i++) {

				// Calculate the taxes
				double tax = 0;
				if (r.get(i).getItem().getDescription().toLowerCase()
						.contains("imported".toLowerCase())) {
					tax = r.get(i).getItem().getPrice() * 0.15; // Extra 5% tax
																// on imported
																// items
				} else {
					tax = r.get(i).getItem().getPrice() * 0.10;
				}

				// Calculate the total price
				bd = new BigDecimal(tax).setScale(2, roundingMode);
				tax = bd.doubleValue();
				double totalPrice = r.get(i).getItem().getPrice() + tax;
				bd = new BigDecimal(totalPrice).setScale(2, roundingMode);
				totalPrice = bd.doubleValue();
				// Print out the item's total price
				System.out.println(r.get(i).getQuantity() + " "
						+ r.get(i).getItem().getDescription() + ": "
						+ totalPrice);

				// Keep a running total
				totalTax += tax;
				total += r.get(i).getItem().getPrice();

			}
			bd = new BigDecimal(totalTax).setScale(2, roundingMode);
			// Print out the total taxes
			System.out.println("Sales Tax: " + bd.doubleValue());

			// total = total + totalTax;
			bd = new BigDecimal(total).setScale(2, RoundingMode.UP);
			total = bd.doubleValue();
			// Print out the total amount
			System.out.println("Total: " + total);

			grandTotal += total;
		}
		System.out.println("Sum of orders: " + Math.floor(grandTotal * 100)
				/ 100);
	}

}
