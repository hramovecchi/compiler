package ar.exa.edu.unicen.compiler.lexical;

/**
 * Hello world!
 * 
 */
public class App {

    public static String truncateId(final String id) {
        if (id.length() > 3) {
            System.err.println("Truncando caracter.");
            return id.substring(0, 3);
        }
        return id;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
