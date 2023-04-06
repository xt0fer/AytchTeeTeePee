package rocks.zipcode.jvi;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import static java.lang.System.exit;
import static rocks.zipcode.jvi.JVITerminal.setCookedMode;
import static rocks.zipcode.jvi.JVITerminal.setRawMode;

public class Jvi {
    private JVITerminal term;

    public Jvi(JVITerminal t) {
        this.term = t;
    }
    public static void main(String[] args) throws IOException {
        Jvi editor = new Jvi(new JVITerminal());
        int error = editor.run();
    }

    private int run() {
        setRawMode();
        while (true) {
            this.refreshScreen();
            this.processKeyPress();
         }
    }

    public void refreshScreen() {
        term.blankScreen();
        term.cursorZero();
        term.cursorHide();

        this.drawRows();

        term.cursorShow();
        term.cursorZero();
    }

    void drawRows() {
        int r = term.getRows();
        term.clearToEOL();
        for (int y = 0; y < r; y++) {
            term.putString("~\r\n");
        }
    }



    private void die(int end) { // exit editor
        term.blankScreen();
        setCookedMode(); // reset terminal
        exit(end); // Bye, Felicia
    }
    private void processKeyPress() {
        int key = term.readKey();

        if (key == term.isControlKey('q')) {
            die(0);
        }
        if (key == term.isControlKey('t')) {
            try {
                term.getTerminalSize();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    // else do something with key.

    }


    /* Test routines */
    // this shows raw mode, and times the
    // delta milliseconds between each key press.
    private void loopWithTiming() throws IOException {
        setRawMode();
        Console console = System.console();
        Reader reader = console.reader();
        ArrayList<Long> timeStamps = new ArrayList<Long>();
        StringBuilder password = new StringBuilder();
        timeStamps.add(System.currentTimeMillis());
        System.out.println("Enter your 8 character password");
        for(int i = 0;i<8;i++) {
            int k = reader.read();
            password.append(k);
            timeStamps.add(System.currentTimeMillis());
            System.out.printf("%c",(char)k);
        }
        setCookedMode();
        this.printDeltas(timeStamps);

    }
    private void printDeltas(ArrayList<Long> timeStamps) {
        long t = timeStamps.get(0);
        for (long ti : timeStamps) {
            System.out.println(ti-t);
            t = ti;
        }
    }
}
