package edu.handong.csee;

import java.util.ArrayList;

public class Reverse {
	
	public ArrayList<String> run (ArrayList<String> unreverse)
	{
		ArrayList<String> reversed=new ArrayList<String>();
		
		for(int i=unreverse.size()-1;i>=0;i--)
		{
			reversed.add(unreverse.get(i));
		}
		
		return reversed;
	}
}
