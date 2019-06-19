package edu.handong.csee;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import java.io.File;

public class Main {
	
	String path;
	String[] pathfile;
	boolean help=false;
	boolean reverse=false;
	
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
		
		if(parseOptions(options,args))
		{
			if(help)
			{
				printHelp(options);
				return;
			}
			if(reverse)
			{
				File[] fileList = file.listFiles();
				 
		        for(File tFile : fileList)
		        {
		            System.out.print(tFile.getName());             
		        }          
			}
			
			File[] fileList = file.listFiles();
			 
	        for(File tFile : fileList)
	        {
	            System.out.print(tFile.getName());             
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

		options.addOption(Option.builder("h").longOpt("help")
		          .desc("Show a Help page")
		          .argName("Help")
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
