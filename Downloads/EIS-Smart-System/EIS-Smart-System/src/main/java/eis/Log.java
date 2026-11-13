package eis;

import java.util.function.Consumer;

// Service Layer
class Log {
    static Consumer<String> out = System.out::println;

    static void println(String s) {
        out.accept(s);
    }
}
