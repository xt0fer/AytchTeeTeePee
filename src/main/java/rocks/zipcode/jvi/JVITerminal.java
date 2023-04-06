package rocks.zipcode.jvi;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;

public class JVITerminal {
    Console console;
    Reader reader;

    public JVITerminal() {
        console = System.console();
        reader = console.reader();
    }

    public int readKey() {
        try {
            return reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void outChar(int k) {
        System.out.printf("%c",(char)k);
    }
    public void outHex(int k) {
        System.out.printf("0x%02x", k);
    }
    public int isControlKey(int k) {
        return ((k) & 0x1f);
    }
}
