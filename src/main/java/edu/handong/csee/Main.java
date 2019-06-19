package edu.handong.csee;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

public class Main {
	
	String path;
	boolean help=false;
	boolean reverse=false;
	boolean format=false;
	boolean hidden=false;
	boolean size=false;
    ArrayList<String> pathfile=new ArrayList<String>();
    
	public static void main(String[] args) {
		
		Main main=new Main();
		main.run(args);
	}
	
	private void run(String[] args)
	{
		Options options =createOption(); 
		
		path=System.getProperty("user.dir");
		File file = new File(path);
		
		boolean isExist=file.exists();
		
		if(!isExist)
		{
			System.out.println("파일이 없습니다.");
		}
		
		File[] fileList = file.listFiles();
		for(File tFile:fileList)
		{
			pathfile.add(tFile.getName());
		}
			
		if(parseOptions(options,args))
		{
			if(help)
			{
				printHelp(options);
				return;
			}
			if(reverse)
			{
				Reverse reverse=new Reverse();
				pathfile=reverse.run(pathfile);
				Print pr=new Print();
				pr.run(pathfile);
				System.exit(0);
			}
			if(hidden)
			{
				ArrayList<String> temp=new ArrayList<String>();
				for(File tFile : fileList)
				{
					if((tFile.isHidden())==true||(tFile.isHidden())==false)
						temp.add(tFile.getName());
				}
				
				pathfile=temp;
			}
			else
			{
				ArrayList<String> temp=new ArrayList<String>();
				
				for(File tFile:fileList)
				{
					if(tFile.isHidden()==false)
					{
						temp.add(tFile.getName());
					}
				}
				
				pathfile=temp;
			}
			if(size)
			{
				HashMap<Long,String> temp=new HashMap<Long,String>();
				ArrayList<String> Temp=new ArrayList<String>();
				
				for(File tfile:fileList)
				{
					if(tfile.isHidden())
					{
						continue;
					}
					temp.put(tfile.length(),tfile.getName());
				}
				Map<Long,String> temp1=new TreeMap<Long,String>(temp);
				
				for(Long key:temp1.keySet())
				{
					String value=temp1.get(key)+" "+key.toString()+"bytes";
					Temp.add(value);
				}
				pathfile=Temp;
			}
			if(format)
			{
				ArrayList<String> temp = new ArrayList<String>();
		        for(File tFile : fileList)
		        {
		        	if(tFile.isDirectory())
		        	{
		        		temp.add(tFile.getName()+"/");	
		        	}
		        	else if(tFile.canExecute())
		        	{
		        		temp.add(tFile.getName()+"*");
		        	}
		        	else
		        	{
		        		temp.add(tFile.getName());
		        	}
		        } 
		        pathfile=temp;
			}
			Print pr=new Print();
			pr.run(pathfile);
			System.exit(0);
		}
		else
		{
			for(File tFile:fileList)
			{
				if(tFile.isHidden())
	        	{
	        		continue;
	        	}
	            System.out.println(tFile.getName());
				
			}
		}
	}
	
	private boolean parseOptions(Options options, String[] args)
	{
		
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			help = cmd.hasOption("h");
			reverse=cmd.hasOption("r");
			format=cmd.hasOption("F");
			hidden=cmd.hasOption("a");
			size=cmd.hasOption("ls");
		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}
	
	private Options createOption()
	{
		Options options =new Options();
		
		options.addOption(Option.builder("r").longOpt("reverse")
			   .desc("reverse print")
			   .build());
		 
		options.addOption(Option.builder("F").longOpt("format")
				.desc("append")
				.build());
		
		options.addOption(Option.builder("a").longOpt("hidden")
				.desc("print Hidden")
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
		          .desc("Show a Help page")
		          .argName("Help")
		          .build());
		options.addOption(Option.builder("ls").longOpt("size")
				.desc("print as size")
				.build());

		return options;
	}
	
	private void printHelp(Options options)
	{
		HelpFormatter formatter = new HelpFormatter();
		String header = "ls";
		String footer ="";
		formatter.printHelp("ls", header, options, footer, true);
	}
}