package com.ClientImpl.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ClientImpl.model.ClientSide;



@WebServlet("/solvePuzzle")
public class solvePuzzle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("In Client Side...");
		
		String r_string = (String)request.getAttribute("r_string");
		String difficulty = (String)request.getAttribute("difficulty");
		String req_ID = (String)request.getAttribute("req_ID");
		
		//Send the reqID + r_string + diffulty to the client
		ClientSide clientobj1 = new ClientSide();
		ClientSide clientobj2 = new ClientSide();
				
				clientobj1.setR_string(r_string);
				clientobj1.setReqID(req_ID);
				clientobj1.setDifficulty(Integer.parseInt(difficulty));
				
				System.out.println("Completed client instance");
				
				//Client module solving puzzle
				String hash_solution = null;
				
				try {
					System.out.println("Find the solution");
					clientobj2 = clientobj1.findPuzzleSol();
					System.out.println("Nonce:" + clientobj2.getNonce());
					System.out.println("Hash Solution" + clientobj2.getHash_solution());
				} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ServletContext sc = request.getServletContext().getContext("/ServerImpl");
				request.setAttribute("nonce", clientobj2.getNonce());
				request.setAttribute("hashSol", clientobj2.getHash_solution());
				request.setAttribute("req_ID", req_ID);
				request.setAttribute("r_string", r_string);
				RequestDispatcher rd = sc.getRequestDispatcher("/verifyPuzzle");
			    rd.forward(request, response);
				
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
