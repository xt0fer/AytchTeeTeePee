package rocks.zipcode.jvi;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The Escape Codes used throught this project are the vt100/ansi/xterm ones
 * Keep it simple
 */
public class JVITerminal {
    Console console;
    Reader reader;
    int screenrows, screencols;

    // logging
    Logger logger = Logger.getLogger("MyLog");
    FileHandler fh;


    public JVITerminal() {
        console = System.console();
        reader = console.reader();

        // logging
        try {
            fh = new FileHandler("jvi-log.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.setUseParentHandlers(false);
    }

    public int readKey() {
        try {
            return reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void outChar(char c) {
        System.out.printf("%c", c);
        System.out.flush();
    }

    public void putString(String s) {
        char[] cha = s.toCharArray();
        for (char k : cha) {
            System.out.printf("%c", (char) k);
        }
        System.out.flush();
    }

    public int getRows() {
        return this.screenrows;
    }
    public int getCols() {
        return this.screencols;
    }
    // adds ability to clear terminal screen
    public void blankScreen() {
        putString("\033[2J");
    }
    public void cursorZero() {
        putString("\033[H");
    }

    public void cursorHide() {
        putString("\033[?25l");
    }
    public void cursorShow() {
        putString("\033[?25h");
    }
    public void clearToEOL() {
        putString("\033[K");
    }


    static void setRawMode() {
        String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void setCookedMode() {
        String[] cmd = {"/bin/sh", "-c", "stty cooked </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

     public void logString(String s) {
        StringBuilder sb = new StringBuilder();
        for (char ch : s.toCharArray()) {
            if (Character.isISOControl(ch)) {
                sb.append(String.format("0x%02x", (int)ch));
            } else {
                sb.append(String.format(".%c", (char) ch));
            }
        }
        logger.info(sb.toString());
    }
    public void logList(ArrayList<Integer> s) {
        StringBuilder sb = new StringBuilder();
        for (int ch : s) {
            if (Character.isISOControl(ch)) {
                sb.append(String.format(".0x%02x", (int)ch));
            } else {
                sb.append(String.format("-%c", (char) ch));
            }
        }
        logger.info(sb.toString());
    }

    public int isControlKey(int k) {
        return ((k) & 0x1f);
    }


    // you might ask, what magic incantation is this,
    // and I'd say "'tis ancient vt100" that works on
    // xterms and mac terms are xterms sorta.
    void getTerminalSize() throws IOException {
        System.out.printf( "\u001b[s" +             // save cursor position
                "\u001b[5000;5000H" +    // move to col 5000 row 5000
                "\u001b[6n" +           // request cursor position
                "\u001b[u" );            // restore cursor position
        System.out.flush();
        int read = -1;
        StringBuilder sb = new StringBuilder();
        byte[] buff = new byte[1];
        while ((read = System.in.read(buff, 0, 1)) != -1) {
            sb.append((char) buff[0]);
            //System.err.printf("Read %s chars, buf size=%s%n", read, sb.length());
            if ('R' == buff[0]) {
                break;
            }
        }
        String size = sb.toString();
        this.screenrows = Integer.parseInt(size.substring(size.indexOf("\u001b[") + 2, size.indexOf(';')));
        this.screencols = Integer.parseInt(size.substring(size.indexOf(';') + 1, size.indexOf('R')));
        logger.info(String.format("Terminal size (%d,%d)", this.screenrows, this.screencols));
    }

}
