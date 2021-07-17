package com.myhome.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MyServlet {
	default void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	default void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
