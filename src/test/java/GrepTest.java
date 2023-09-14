import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.CmdLineException;
import programming.task2.Grep;
import programming.task2.Parser;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GrepTest {

    private static String toString(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (var i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i + 1 < list.size()) {
                sb.append("\n");
            }
        }
        return (sb.toString());
    }


    @Test
    public void basicGrep() throws IOException {
        assertEquals(toString(Grep.word("src/input/test1", "cat", false, false, false)),
                "Later, I caught her gnawing on the corner of my cat book.\n" +
                        "Even though she can be mischievous, I wouldn't trade my cute little cat for anything in the world.");
    }

    @Test
    public void allTrue() throws IOException {
        assertEquals(toString(Grep.word("src/input/test1", "cat", true, true, true)),
                "I love my tabby CAT, Whiskers.\n" +
                        "She has the softest fur and loves to be petted.\n" +
                        "Yesterday, I found her Catlike tummy napping in a cardboard box in the corner of the living room.\n" +
                        "When I opened the window, she jumped out and chased a butterfly in the garden.\n" +
                        "She looked up at me with those big green eyes, as if to say \"I'm sorry!\"");
    }

    @Test
    public void ignoreCase() throws IOException {
        assertEquals(toString(Grep.word("src/input/test1", "cat", false, false, true)),
                "I love my tabby CAT, Whiskers.\n" +
                        "Yesterday, I found her Catlike tummy napping in a cardboard box in the corner of the living room.\n" +
                        "Later, I caught her gnawing on the corner of my cat book.\n" +
                        "Even though she can be mischievous, I wouldn't trade my cute little cat for anything in the world.");
    }

    @Test
    public void invert() throws IOException {
        assertEquals(toString(Grep.word("src/input/test1", "cat", false, true, false)),
                "I love my tabby CAT, Whiskers.\n" +
                        "She has the softest fur and loves to be petted.\n" +
                        "Yesterday, I found her Catlike tummy napping in a cardboard box in the corner of the living room.\n" +
                        "When I opened the window, she jumped out and chased a butterfly in the garden.\n" +
                        "She looked up at me with those big green eyes, as if to say \"I'm sorry!\"");
    }

    @Test
    public void regex() throws IOException {
        assertEquals(toString(Grep.word("src/input/test1", "my*", true, false, false)),
                "I love my tabby CAT, Whiskers.\n" +
                        "Yesterday, I found her Catlike tummy napping in a cardboard box in the corner of the living room.\n" +
                        "When I opened the window, she jumped out and chased a butterfly in the garden.\n" +
                        "Later, I caught her gnawing on the corner of my cat book.\n" +
                        "She looked up at me with those big green eyes, as if to say \"I'm sorry!\"\n" +
                        "Even though she can be mischievous, I wouldn't trade my cute little cat for anything in the world.");
    }

    @Test
    public void noLines() throws IOException {
        assertEquals(toString(Grep.word("src/input/test1", "fool", false, false, false)), "");
        assertEquals(toString(Grep.word("src/input/test1", "fool", true, false, false)), "");
        assertEquals(toString(Grep.word("src/input/test1", "fool", false, false, true)), "");
    }

    @Test
    public void parserTest() {
        assertThrows(CmdLineException.class, () -> Parser.main(new String[]{"[-i]", "[-r]", "[-v]", "word", "output.txt"}));
        assertThrows(CmdLineException.class, () -> Parser.main(new String[]{"[-v]", "[-r]", "word", "output.txt"}));
        assertThrows(CmdLineException.class, () -> Parser.main(new String[]{"[-i]", "[-v]", "src/input/test1"}));
        assertDoesNotThrow(() -> Parser.main(new String[]{"-v", "-i", "-r", "I", "src/input/test1"}));
    }

    @Test
    public void fileIsNotFoundTest() {
        assertThrows(FileNotFoundException.class, () -> Grep.word("src/input/test", "cat", false, true, false));
        assertThrows(FileNotFoundException.class, () -> Grep.word("output.txt", "cat", false, true, false));
    }

}