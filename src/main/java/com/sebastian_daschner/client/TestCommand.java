package com.sebastian_daschner.client;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import picocli.CommandLine.Command;

@TopCommand
@Command(name = "test")
public class TestCommand implements Runnable {

    private volatile boolean stopped = false;

    @Override
    public void run() {
        System.out.println("starting");
        try (Terminal terminal = TerminalBuilder.builder().nativeSignals(true).build()) {
            terminal.enterRawMode();

            terminal.handle(Terminal.Signal.INT, signal -> {
                System.out.println("INT");
                stopped = true;
            });
            terminal.handle(Terminal.Signal.QUIT, signal -> System.out.println("QUIT"));
            terminal.handle(Terminal.Signal.TSTP, signal -> System.out.println("TSTP"));

            while (!stopped) {
                int read = terminal.reader().read(200); // 200ms
                if (read == -1) break; // EOF
                if (read == -2) continue; // timeout

                // swallow input
//                System.out.write(read);
//                System.out.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
