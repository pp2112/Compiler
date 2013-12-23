package code_generator;

import antlr.WaccParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import semantic_check.TreeWalker;
import imports.Import;
import antlr.BasicLexer;

public class Code_Gen_Main {
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("No Input !");
			return;
		}
		File myFile = new File("temp.wacc");
		if (myFile.exists())
			myFile.delete();
		
		String path = null;
		Pattern pattern = Pattern.compile(".*/");
		Matcher matcher = pattern.matcher(args[0]);
		while(matcher.find()){
		 path = matcher.group();
		}		
		
		Import.generateImport(args[0],path);

		WaccParser parser = new WaccParser(new CommonTokenStream(new BasicLexer(new ANTLRInputStream(readFile("temp.wacc")))));
		ParseTree tree = parser.program();
		if (parser.getNumberOfSyntaxErrors() >= 1) {
			System.out.println(parser.getNumberOfSyntaxErrors());
			System.exit(100);
		}
		PrintStream outputStream = new PrintStream(new File(args[0].substring(0, args[0].length() - 4) + "s")); // System.out
		PreCodeGenWalker walker = new PreCodeGenWalker();
		walker.visit(tree);
		//TreeWalker semanticCheck = new TreeWalker();
	//	semanticCheck.visit(tree);
		CodeGenWalker generator = new CodeGenWalker(walker, outputStream);
		generator.visit(tree);
		generator.generateCode();
		if (myFile.exists())
			myFile.delete();

	}

	public static String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

}