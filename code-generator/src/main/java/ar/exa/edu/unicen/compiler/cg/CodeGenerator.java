package ar.exa.edu.unicen.compiler.cg;

import static ar.exa.edu.unicen.compiler.lexical.Lexical.GRAMMAR_FILE;
import static ar.exa.edu.unicen.compiler.lexical.utils.MessageUtils.info;
import static com.aveise.automaton.utils.ParamUtils.buildProgramArguments;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.exa.edu.unicen.compiler.lexical.Lexical;
import ar.exa.edu.unicen.compiler.lexical.utils.SymbolTable;
import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;
import ar.exa.edu.unicen.compiler.syntactic.Branch;
import ar.exa.edu.unicen.compiler.syntactic.Syntactic;

public class CodeGenerator {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CodeGenerator.class);

    private static final String SOURCE_CODE = "sourceCode";

    private static final String ASSEMBLER_CODE = "asmCode";

    private static final SymbolTable SYMBOL_TABLE = SymbolTable.getInstance();

    private final Map<String, String> variables = new HashMap<String, String>();

    private final Syntactic syntactic;

    private TripletstHandler tripletHandler;

    public CodeGenerator(final Syntactic syntactic) {
        this.syntactic = syntactic;
    }

    public String loadProgramHeader(final List<Triplet> triplets) {

        final StringBuilder sb = new StringBuilder();
        sb.append("SECTION .MODEL small\n");
        sb.append("SECTION .STACK 200h\n");
        sb.append("SECTION .DATA\n");

        int count = 0;
        for (final String lexeme : SYMBOL_TABLE.getLexemes()) {

            String var;
            if (SYMBOL_TABLE.isVariableInteger(lexeme)) {

                var = "@_dw" + lexeme;
                sb.append(String.format("\t%s dw 0 \n", var, lexeme));
                this.variables.put(lexeme, var);

            } else if (SYMBOL_TABLE.isVariableDouble(lexeme)) {

                var = "@_dd" + lexeme;
                sb.append(String.format("\t%s dd 0 \n", var, lexeme));
                this.variables.put(lexeme, var);

            } else if (SYMBOL_TABLE.isConstantInteger(lexeme)) {

                var = "@_cdw" + count++;
                sb.append(String.format("\t%s dw %s \n", var, lexeme));
                this.variables.put(lexeme, var);

            } else if (SYMBOL_TABLE.isConstantDouble(lexeme)) {

                var = "@_cdd" + count++;
                sb.append(String.format("\t%s dd %s \n", var, lexeme));
                this.variables.put(lexeme, var);

            } else if (SYMBOL_TABLE.isConstantString(lexeme)) {

                var = "@_sdd" + count++;
                sb.append(String.format("\t%s dd %s \n", var, lexeme));
                this.variables.put(lexeme, var);

            }
        }

        for (final Triplet triplet : triplets) {

            final String operator = triplet.getOperator().toString();
            if (!Branch.BF.toString().equals(operator)
                    && !Branch.BI.toString().equals(operator)) {

                if (triplet.getOperand1() instanceof Integer) {
                    final String var =
                            "@_aux" + triplet.getOperand1().toString();
                    sb.append(String.format("\t%s dd 0 \n", var));
                    this.variables.put(triplet.getOperand1().toString(), var);
                }

                if (triplet.getOperand2() instanceof Integer) {
                    final String var =
                            "@_aux" + triplet.getOperand2().toString();
                    sb.append(String.format("\t%s dd 0 \n", var));
                    this.variables.put(triplet.getOperand2().toString(), var);
                }
            }
        }

        sb.append("SECTION .CODE\n");
        sb.append("START:\n");

        this.tripletHandler = new TripletstHandler(this.variables);
        return sb.toString();
    }

    public String loadProgramBody(final int count, final Triplet triplet) {
        return this.tripletHandler.getCodeForTriplet(count, triplet);
    }

    public String loadProgramFooter(final int size) {

        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("LABEL%d:\n", size));
        sb.append("END START\n");
        return sb.toString();
    }

    public void run(final String output) throws IOException {

        final BufferedWriter bw = new BufferedWriter(new FileWriter(output));

        final List<Triplet> triplets = this.syntactic.run();

        final String asmHeader = this.loadProgramHeader(triplets);
        bw.write(asmHeader);

        int count = 0;
        for (final Triplet triplet : triplets) {

            info(count, triplet.getOperator().toString(),
                    triplet.getOperand1(), triplet.getOperand2());

            final String asmBody = this.loadProgramBody(count, triplet);
            bw.write(asmBody);

            count++;
        }

        final String asmFooter = this.loadProgramFooter(triplets.size());
        bw.write(asmFooter);

        bw.close();
    }

    /**
     * Runs batch process to analyze lexical, syntactically and code generator
     * the given source code.
     *
     * @param args
     *            to specify the source code file to analyzer write
     *            --sourceCode=filename from console. For instance, something
     *            like
     *            <code>java -jar program.jar --sourceCode=sourceFilename --asmCode=asmFilename.asm</code>
     *            --asmCode=filename from console. For instance, something like
     *            <code>java -jar program.jar --sourceCode=sourceFilename --asmCode=asmFilename.asm</code>
     * @throws IOException
     *             throws an exception in case of error reading the source code
     *             file.
     */
    public static void main(final String[] args) throws IOException {
        final Map<String, String> programArgs = buildProgramArguments(args);
        final InputStream sourceCode =
                new FileInputStream(programArgs.get(SOURCE_CODE));
        final String asmCode = programArgs.get(ASSEMBLER_CODE);
        final InputStream grammarFile =
                Lexical.class.getResourceAsStream(GRAMMAR_FILE);
        final Lexical lexical = new Lexical(grammarFile);
        final Syntactic syntactic = new Syntactic(lexical, sourceCode);
        final CodeGenerator codeGenerator = new CodeGenerator(syntactic);

        try {
            codeGenerator.run(asmCode);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}
