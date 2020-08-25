package com.ClientImpl.puzzle;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.ClientImpl.model.ClientSide;

import util.StringUtil;


public class Puzzle {

	public String hash;
	public String reqID;
	public String rnd;
	public int nonce;
	public int difficulty;
	
	public Puzzle(String reqID, String rnd, int difficulty) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		this.reqID = reqID;
		this.rnd = rnd;
		this.difficulty = difficulty;
		this.hash = calculateHash();
	}
	
	public String calculateHash() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		System.out.println("Nonce"+ nonce);
		Random rand = new Random();
		nonce = rand.nextInt(999999999);
		System.out.println("Trying with nonce value: "+nonce);
		String calculatedHash = StringUtil.SHA256(Integer.toString(nonce) + reqID + rnd);
		return calculatedHash;
	}
	
	
	public ClientSide POW(int difficulty) throws NoSuchAlgorithmException, UnsupportedEncodingException {		
		String target = StringUtil.getDificultyString(difficulty); 
		while(! hash.substring(0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("POW Successful : " + hash);
		ClientSide clientobj = new ClientSide();
		clientobj.setNonce(Integer.toString(nonce));
		clientobj.setHash_solution(hash);
		return clientobj;
	}
   
	
}