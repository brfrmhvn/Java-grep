package programming.task2;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Grep {
    public static ArrayList<String> word(String fileName, String word, boolean regex, boolean invert, boolean ignoreCase) throws IOException {
        File inputFile = new File(fileName);
        ArrayList<String> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            Pattern pattern = Pattern.compile(word);
            while ((line = reader.readLine()) != null) {
                if (regex) {
                    if (invert) {
                        if (!pattern.matcher(line).find()) {
                            result.add(line);
                        }
                    } else {
                        if (pattern.matcher(line).find()) {
                            result.add(line);
                        }
                    }
                } else {
                    if (ignoreCase) {
                        if (invert) {
                            if (!pattern.matcher("(?i).*" + line).find()) {
                                result.add(line);
                            }
                        } else {
                            pattern = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
                            if (pattern.matcher(line).find()) {
                                result.add(line);
                            }
                        }
                    } else {
                        if (invert) {
                            if (!pattern.matcher(line).find()) {
                                result.add(line);
                            }
                        } else {
                            if (pattern.matcher(line).find()) {
                                result.add(line);
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return result;
    }
}

