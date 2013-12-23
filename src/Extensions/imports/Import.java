package imports;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.*;

public class Import {

	public static void generateImport(String inputFile,String inputFolder) throws IOException {
		findImports(inputFile,inputFolder);
	}

	public static void findImports(String fileName,String inputFolder) throws IOException {
		String contents = getString(fileName);
		if (contents.contains("import ")) {
			String wacco = contents.substring(contents.indexOf("import") + 7, contents.indexOf(".wacco") + 6);
			inputFolder = (inputFolder == null) ? "" : inputFolder;
			String waccoContents = getString(inputFolder + wacco);
			String regex = "[a-z]*[(].*[)] is";
			String waccoresult = replaceFunctionNames(regex, waccoContents, wacco);
			String waccresult = placeFunctionsStart(contents, waccoresult);
			concat(waccresult, "temp.wacc");
		} else {
			String waccresult = placeFunctionsStart(contents, "");
			concat(waccresult, "temp.wacc");
		}
	}

	private static String placeFunctionsStart(String contents, String waccoresult) {
		if (!waccoresult.equals("")) {
			Pattern pattern = Pattern.compile("import .*;");
			waccoresult = "begin\n" + waccoresult;
			Matcher matcher = pattern.matcher(contents);
			String importInst = null;
			while (matcher.find()) {
				importInst = matcher.group();
			}
			contents = contents.replace(importInst, "");
			contents = contents.replace("begin", waccoresult);
			return contents;
		}
		return contents;
	}

	public static String replaceFunctionNames(String regex, String waccoContents, String waccoFile) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(waccoContents);
		String result = waccoContents;
		while (matcher.find()) {
			String funcName = waccoContents.substring(matcher.start(), matcher.start() + matcher.group().indexOf('('));
			result = result.replaceAll(funcName, waccoFile.substring(0, waccoFile.length() - 6) + "_" + funcName);
		}
		return result;
	}

	public static String getString(String name) throws IOException {
		File f = new File(name);
		if(f.exists()){
		String result;
		BufferedReader br = new BufferedReader(new FileReader(name));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			result = sb.toString();
		} finally {
			br.close();
		}
		return result;
		}
		System.out.println("Invalid Import !");
		return " ";
	}

	public static void concat(String inputString, String outputFile) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
			out.println(inputString);
			out.close();			
		} catch (IOException e) {
			System.out.println("IO EXCEPTION");
		}
	}
}
