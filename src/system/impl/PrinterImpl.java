package system.impl;

import java.util.Collection;
import java.util.function.Consumer;

import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;
import system.Calculator;
import system.Formatter;
import system.Printer;
import application.TablePrinter;
import application.TablePrinter.Builder;


class PrinterImpl implements Printer {
	//
	private final Calculator calculator;
	private final Formatter formatter;

	PrinterImpl(Calculator calculator, Formatter formatter) {
		this.calculator = calculator;
		this.formatter = formatter;
	}

	@Override
	public TablePrinter createTablePrinter(StringBuffer sb, Consumer<Builder> builder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer printCustomer(StringBuffer sb, Customer c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer printCustomers(StringBuffer sb, Collection<Customer> customers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer printArticle(StringBuffer sb, Article a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer printArticles(StringBuffer sb, Collection<Article> articles) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer printOrder(StringBuffer sb, Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer printOrders(StringBuffer sb, Collection<Order> orders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TablePrinter printOrder(TablePrinter orderTable, Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TablePrinter printOrders(TablePrinter orderTable, Collection<Order> orders) {
		// TODO Auto-generated method stub
		return null;
	}
}
