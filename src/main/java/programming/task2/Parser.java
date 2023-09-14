package programming.task2;


import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;

public class Parser {

    @Option(name = "-r", metaVar = "Regex")
    private Boolean regex;

    @Option(name = "-v", metaVar = "Invert")
    private Boolean invert;

    @Option(name = "-i", metaVar = "IgnoreCase")
    private Boolean ignoreCase;

    @Argument(required = true, metaVar = "FilterWord")
    private String word;

    @Argument(required = true, index = 1, metaVar = "InputName", usage = "Input file name")
    private String inputName;

    public static void main(String[] args) throws CmdLineException, IOException {
        new Parser().launch(args);
    }

    private void launch(String[] args) throws CmdLineException, IOException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
            ArrayList<String> output = Grep.word(inputName, word, regex, invert, ignoreCase);
            for (String s : output) {
                System.out.print(s);
                System.out.println();
            }
        } catch (CmdLineException | IOException e) {
            System.err.println(e.getMessage());
            System.err.println("grep [-v] [-i] [-r] word inputname.txt");
            parser.printUsage(System.err);
            throw e;
        }
    }
}

