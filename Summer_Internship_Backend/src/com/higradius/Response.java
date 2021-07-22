package com.higradius;

public class Response {
private String customer_name;
private String customer_number;
private int invoice_id;
private float invoice_amount;
private String due_in_date;
private String Predicted_Date;
private String notes;
public String getCustomer_name() {
	return customer_name;
}
public void setCustomer_name(String customer_name) {
	this.customer_name = customer_name;
}
public String getCustomer_number() {
	return customer_number;
}
public void setCustomer_number(String customer_number) {
	this.customer_number = customer_number;
}
public int getInvoice_id() {
	return invoice_id;
}
public void setInvoice_id(int invoice_id) {
	this.invoice_id = invoice_id;
}
public float getInvoice_amount() {
	return invoice_amount;
}
public void setInvoice_amount(float invoice_amount) {
	this.invoice_amount = invoice_amount;
}
public String getDue_in_date() {
	return due_in_date;
}
public void setDue_in_date(String due_in_date) {
	this.due_in_date = due_in_date;
}
public String getPredicted_Date() {
	return Predicted_Date;
}
public void setPredicted_Date(String Predicted_date) {
	Predicted_Date = Predicted_date;
}
public String getNotes() {
	return notes;
}
public void setNotes(String notes) {
	this.notes = notes;
}}

