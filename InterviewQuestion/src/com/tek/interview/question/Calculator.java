package com.tek.interview.question;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class Calculator {
	
	private static String IMPORTED = "imported";

	public static double rounding(double value, RoundingMode roundingMode) {
		return new BigDecimal(value).setScale(2, roundingMode).doubleValue();
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
		// Iterate through the orders
		if(o != null && !o.isEmpty()){
			for (Map.Entry<String, Order> entry : o.entrySet()) {
				System.out.println("*******" + entry.getKey() + "*******");
				Order r = entry.getValue();
				double totalTax = 0;
				double total = 0;
				if(r != null){
					// Iterate through the items in the order
					for (int i = 0; i < r.size(); i++) {
		
						// Calculate the taxes
						double tax = 0;
						Item item = r.get(i).getItem();
						if (item.getDescription() != null && !item.getDescription().isEmpty() && item.getDescription().toLowerCase()
								.contains(IMPORTED.toLowerCase())) {
							tax = rounding(item.getPrice() * 0.15, RoundingMode.HALF_EVEN); // Extra 5% tax
																		// on imported
																		// items
						} else {
							tax = rounding(item.getPrice() * 0.10, RoundingMode.HALF_EVEN);
						}
						
						// Calculate the total price
						double totalPrice = rounding(item.getPrice() + tax, RoundingMode.HALF_EVEN);
						// Print out the item's total price
						System.out.println(r.get(i).getQuantity() + " "
								+ item.getDescription() + ": "
								+ totalPrice);
		
						// Keep a running total
						totalTax += tax;
						total += item.getPrice();
		
					}
				}
				// Print out the total taxes
				System.out.println("Sales Tax: " + rounding(totalTax, RoundingMode.HALF_EVEN));
	
				total = rounding(total, RoundingMode.UP);
				// Print out the total amount
				System.out.println("Total: " + total);
	
				grandTotal += total;
			}
		}
		System.out.println("Sum of orders: " + Math.floor(grandTotal * 100)
				/ 100);
	}

}
