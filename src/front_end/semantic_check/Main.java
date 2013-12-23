package semantic_check;

import antlr.WaccParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import antlr.BasicLexer;


public class Main {
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("No Input !");
			return;
		}
		WaccParser parser = new WaccParser(new CommonTokenStream(new BasicLexer(
				new ANTLRInputStream(readFile(args[0])))));
		ParseTree tree = parser.program();
		System.out.println(args[0]);
		if (parser.getNumberOfSyntaxErrors() >= 1) {
			System.out.println(parser.getNumberOfSyntaxErrors());
			System.exit(100);
		}
		//PrintStream outputStream = System.out; //new PrintStream(new FileOutputStream(args[0].substring(0, args[0].length()-5)+".s"));
		TreeWalker checker = new TreeWalker();
		checker.visit(tree);
	  int x = 0;
	  int a = 0;
	
		
		//checker.generateCode();
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