package ar.exa.edu.unicen.compiler.syntactic.parser.yacc;

// ### This file created by BYACC 1.8(/Java extension 1.15)
// ### Java capabilities added 7 Jan 97, Bob Jamison
// ### Updated : 27 Nov 97 -- Bob Jamison, Joe Nieten
// ### 01 Jan 98 -- Bob Jamison -- fixed generic semantic constructor
// ### 01 Jun 99 -- Bob Jamison -- added Runnable support
// ### 06 Aug 00 -- Bob Jamison -- made state variables class-global
// ### 03 Jan 01 -- Bob Jamison -- improved flags, tracing
// ### 16 May 01 -- Bob Jamison -- added custom stack sizing
// ### 04 Mar 02 -- Yuval Oren -- improved java performance, added options
// ### 14 Mar 02 -- Tomas Hurka -- -d support, static initializer workaround
// ### Please send bug reports to tom@hukatronic.cz
// ### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";

// #line 2 "grammar.y"
import java.io.IOException;
import java.io.InputStream;

import ar.exa.edu.unicen.compiler.lexical.Lexical;
import ar.exa.edu.unicen.compiler.syntactic.parser.BaseParser;

// #line 23 "Parser.java"

public class Parser extends BaseParser {

    boolean yydebug;        // do I want debug output?
    int yynerrs;            // number of errors so far
    int yyerrflag;          // was there an error?
    int yychar;             // the current working character

    // ########## MESSAGES ##########
    // ###############################################################
    // method: debug
    // ###############################################################
    void debug(String msg) {
        if (yydebug) System.out.println(msg);
    }

    // ########## STATE STACK ##########
    final static int YYSTACKSIZE = 500;  // maximum stack size
    int statestk[] = new int[YYSTACKSIZE]; // state stack
    int stateptr;
    int stateptrmax;                     // highest index of stackptr
    int statemax;                        // state when highest index reached
    // ###############################################################
    // methods: state stack push,pop,drop,peek
    // ###############################################################

    final void state_push(int state) {
        try {
            stateptr++;
            statestk[stateptr] = state;
        } catch (ArrayIndexOutOfBoundsException e) {
            int oldsize = statestk.length;
            int newsize = oldsize * 2;
            int[] newstack = new int[newsize];
            System.arraycopy(statestk, 0, newstack, 0, oldsize);
            statestk = newstack;
            statestk[stateptr] = state;
        }
    }

    final int state_pop() {
        return statestk[stateptr--];
    }

    final void state_drop(int cnt) {
        stateptr -= cnt;
    }

    final int state_peek(int relative) {
        return statestk[stateptr - relative];
    }

    // ###############################################################
    // method: init_stacks : allocate and prepare stacks
    // ###############################################################
    final boolean init_stacks() {
        stateptr = -1;
        val_init();
        return true;
    }

    // ###############################################################
    // method: dump_stacks : show n levels of the stacks
    // ###############################################################
    void dump_stacks(int count) {
        int i;
        System.out.println("=index==state====value=     s:" + stateptr + "  v:"
                + valptr);
        for (i = 0; i < count; i++)
            System.out.println(" " + i + "    " + statestk[i] + "      "
                    + valstk[i]);
        System.out.println("======================");
    }

    // ########## SEMANTIC VALUES ##########
    // public class ParserVal is defined in ParserVal.java

    String yytext;// user variable to return contextual strings
    ParserVal yyval; // used to return semantic vals from action routines
    ParserVal yylval;// the 'lval' (result) I got from yylex()
    ParserVal valstk[];
    int valptr;

    // ###############################################################
    // methods: value stack push,pop,drop,peek.
    // ###############################################################
    void val_init() {
        valstk = new ParserVal[YYSTACKSIZE];
        yyval = new ParserVal();
        yylval = new ParserVal();
        valptr = -1;
    }

    void val_push(ParserVal val) {
        if (valptr >= YYSTACKSIZE) return;
        valstk[++valptr] = val;
    }

    ParserVal val_pop() {
        if (valptr < 0) return new ParserVal();
        return valstk[valptr--];
    }

    void val_drop(int cnt) {
        int ptr;
        ptr = valptr - cnt;
        if (ptr < 0) return;
        valptr = ptr;
    }

    ParserVal val_peek(int relative) {
        int ptr;
        ptr = valptr - relative;
        if (ptr < 0) return new ParserVal();
        return valstk[ptr];
    }

    final ParserVal dup_yyval(ParserVal val) {
        ParserVal dup = new ParserVal();
        dup.ival = val.ival;
        dup.dval = val.dval;
        dup.sval = val.sval;
        dup.obj = val.obj;
        return dup;
    }

    // #### end semantic value section ####
    public final static short IF = 100;
    public final static short THEN = 101;
    public final static short ELSE = 102;
    public final static short FOR = 103;
    public final static short VECTOR = 104;
    public final static short OF = 105;
    public final static short PRINT = 106;
    public final static short INTEGER = 107;
    public final static short DOUBLE = 108;
    public final static short ID = 200;
    public final static short STRING = 201;
    public final static short CONST_INTEGER = 300;
    public final static short CONST_DOUBLE = 301;
    public final static short ADD = 400;
    public final static short SUB = 401;
    public final static short MUL = 402;
    public final static short DIV = 403;
    public final static short ASSIGN = 500;
    public final static short EQ = 600;
    public final static short NE = 601;
    public final static short LT = 602;
    public final static short GT = 603;
    public final static short LE = 604;
    public final static short GE = 605;
    public final static short LPAREN = 700;
    public final static short RPAREN = 701;
    public final static short LBRACK = 702;
    public final static short RBRACK = 703;
    public final static short LBRACE = 704;
    public final static short RBRACE = 705;
    public final static short COMMA = 706;
    public final static short SEMICOLON = 707;
    public final static short RANGE = 708;
    public final static short LOWER_THAN_ELSE = 257;
    public final static short YYERRCODE = 256;
    final static short yylhs[] = {-1, 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 4, 4,
            4, 4, 4, 4, 5, 5, 5, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8,
            8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 11, 11, 11, 11, 11, 11, 11, 11,
            11, 11, 11, 11, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 14, 14, 14,
            14, 14, 10, 10, 10, 10,};
    final static short yylen[] = {2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 7,
            5, 5, 7, 7, 5, 5, 5, 3, 3, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
            10, 2, 2, 2, 2, 4, 7, 4, 7, 7, 7, 7, 7, 7, 3, 3, 3, 3, 3, 3, 3, 3,
            3, 3, 3, 3, 1, 3, 3, 3, 3, 1, 3, 3, 3, 3, 1, 2, 4, 1, 1, 3, 2, 3,
            2,};
    final static short yydefred[] = {0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 2, 4, 5,
            6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 0, 37, 0, 35, 38, 36, 0, 0, 0, 78,
            76, 0, 3, 0, 73, 74, 0, 0, 0, 0, 65, 0, 0, 0, 23, 22, 0, 0, 0, 0,
            0, 0, 0, 0, 77, 75, 0, 71, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 41, 39, 0, 0, 0, 0, 0, 0, 0, 0, 63, 0, 64, 0, 54,
            0, 55, 0, 56, 0, 57, 0, 58, 0, 59, 0, 68, 66, 69, 67, 0, 0, 20, 21,
            19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 72, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 17, 18, 14, 47, 0, 46, 0, 45, 44, 43, 42, 40, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 34,
            33, 32, 31, 30, 29, 28, 27, 26, 24, 25,};
    final static short yydgoto[] = {9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            41, 42, 43, 44,};
    final static short yysindex[] = {-77, -228, -213, -149, -135, -132, -231,
            -100, 0, 0, -77, 0, 0, 0, 0, 0, 0, 0, 0, 0, -138, -138, -138, -138,
            -251, 0, -229, 0, 0, 0, -139, -138, -126, 0, 0, -86, 0, -642, 0, 0,
            -137, -634, -356, -333, 0, -223, -622, -222, 0, 0, -117, -127,
            -616, -619, -247, -637, -244, -254, 0, 0, -107, 0, -77, -162, -158,
            -136, -128, -122, -120, -116, -112, -110, -104, -77, -77, -77, -77,
            -77, -608, -395, -194, 0, 0, -389, -188, -385, -238, -169, -203,
            -571, 33, 0, -333, 0, -333, 0, -319, 0, -319, 0, -319, 0, -319, 0,
            -319, 0, -319, 0, 0, 0, 0, 35, 43, 0, 0, 0, -138, -570, -138, -556,
            -138, -138, -101, -536, -532, -240, 0, -77, -77, -77, -353, 71,
            -351, 78, -344, -342, -524, -241, 82, 88, 90, -58, 0, 0, 0, 0, 104,
            0, 106, 0, 0, 0, 0, 0, 107, 108, 109, 110, -54, 111, 113, 120, 121,
            122, 123, 124, -53, -512, -491, -490, -475, -474, -473, -472, -471,
            -245, -470, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
    final static short yyrindex[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 238, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, -252, 0, 0, 0, 0, 0, -243, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -232, 0, -224, 0,
            -221, 0, -220, 0, -219, 0, -217, 0, -216, 0, -215, 0, 0, 0, 0, 10,
            19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
    final static short yygindex[] = {0, 233, 131, 0, 0, 0, 0, 0, 0, 81, 0, 54,
            156, 28, 31,};
    final static int YYTABLESIZE = 726;
    static short yytable[];
    static {
        yytable();
    }

    static void yytable() {
        yytable =
                new short[]{1, 15, 87, 2, 70, 48, 3, 4, 5, 81, 16, 185, 85, 60,
                        1, 152, 140, 2, 121, 13, 3, 4, 5, 1, 61, 30, 2, 50, 20,
                        3, 4, 5, 62, 73, 76, 48, 49, 50, 37, 51, 52, 53, 37,
                        22, 63, 64, 158, 63, 64, 63, 64, 166, 24, 124, 175,
                        176, 63, 64, 63, 64, 60, 52, 37, 61, 37, 26, 83, 62,
                        26, 71, 72, 84, 37, 56, 56, 45, 46, 47, 37, 75, 37, 63,
                        64, 52, 37, 27, 29, 79, 37, 80, 37, 92, 94, 89, 91, 83,
                        37, 125, 93, 37, 6, 15, 108, 110, 15, 116, 117, 15, 15,
                        15, 16, 118, 119, 16, 6, 120, 16, 16, 16, 13, 95, 25,
                        13, 6, 28, 13, 13, 13, 97, 78, 55, 123, 126, 131, 99,
                        127, 101, 128, 38, 39, 103, 36, 38, 39, 105, 129, 107,
                        133, 70, 70, 70, 70, 109, 63, 64, 136, 33, 60, 60, 63,
                        64, 53, 38, 39, 38, 39, 36, 138, 61, 61, 58, 139, 38,
                        39, 57, 146, 62, 62, 38, 39, 38, 39, 148, 151, 38, 39,
                        154, 54, 38, 39, 38, 39, 155, 90, 156, 177, 38, 39,
                        157, 38, 39, 15, 165, 174, 111, 112, 113, 114, 115,
                        159, 16, 160, 161, 162, 163, 164, 178, 179, 167, 13,
                        168, 96, 98, 100, 102, 104, 106, 169, 170, 171, 172,
                        173, 180, 181, 182, 183, 184, 187, 1, 40, 35, 0, 0, 40,
                        65, 66, 67, 68, 69, 70, 0, 0, 0, 0, 0, 0, 0, 15, 142,
                        143, 144, 0, 122, 40, 0, 40, 16, 0, 0, 31, 0, 31, 130,
                        40, 132, 13, 134, 135, 137, 40, 0, 40, 0, 0, 0, 40, 0,
                        0, 0, 40, 0, 40, 0, 0, 0, 0, 0, 40, 0, 0, 40, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 70, 70, 70, 70, 70, 70, 145, 0,
                        147, 60, 60, 60, 60, 60, 60, 149, 0, 150, 0, 0, 61, 61,
                        61, 61, 61, 61, 0, 0, 62, 62, 62, 62, 62, 62, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 70, 0, 0, 0, 0, 88, 70,
                        49, 0, 60, 86, 82, 0, 186, 141, 60, 0, 153, 0, 0, 61,
                        0, 32, 21, 51, 0, 61, 0, 62, 74, 77, 48, 49, 50, 62,
                        51, 52, 53, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 34, 0, 8, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 59, 0, 8, 0, 0, 0, 0, 0,
                        7, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 15, 15, 0, 15, 0, 0, 0, 0, 0, 16, 16,
                        0, 16, 0, 0, 0, 0, 0, 13, 13, 0, 13,};
    }

    static short yycheck[];
    static {
        yycheck();
    }

    static void yycheck() {
        yycheck =
                new short[]{100, 0, 256, 103, 256, 256, 106, 107, 108, 256, 0,
                        256, 256, 256, 100, 256, 256, 103, 256, 0, 106, 107,
                        108, 100, 256, 256, 103, 256, 256, 106, 107, 108, 256,
                        256, 256, 256, 256, 256, 200, 256, 256, 256, 200, 256,
                        400, 401, 104, 400, 401, 400, 401, 105, 201, 256, 107,
                        108, 400, 401, 400, 401, 702, 200, 200, 200, 200, 200,
                        703, 701, 200, 402, 403, 708, 200, 200, 200, 21, 22,
                        23, 200, 701, 200, 400, 401, 200, 200, 4, 5, 703, 200,
                        708, 200, 63, 64, 200, 256, 703, 200, 300, 256, 200,
                        200, 100, 71, 72, 103, 500, 300, 106, 107, 108, 100,
                        500, 300, 103, 200, 500, 106, 107, 108, 100, 256, 256,
                        103, 200, 256, 106, 107, 108, 256, 256, 256, 300, 703,
                        703, 256, 102, 256, 102, 300, 301, 256, 10, 300, 301,
                        256, 102, 256, 703, 400, 401, 402, 403, 256, 400, 401,
                        256, 256, 400, 401, 400, 401, 300, 300, 301, 300, 301,
                        35, 703, 400, 401, 256, 703, 300, 301, 300, 104, 400,
                        401, 300, 301, 300, 301, 104, 707, 300, 301, 104, 31,
                        300, 301, 300, 301, 104, 62, 104, 707, 300, 301, 256,
                        300, 301, 200, 256, 256, 73, 74, 75, 76, 77, 105, 200,
                        105, 105, 105, 105, 105, 707, 707, 107, 200, 107, 65,
                        66, 67, 68, 69, 70, 107, 107, 107, 107, 107, 707, 707,
                        707, 707, 707, 707, 0, 401, 7, -1, -1, 401, 600, 601,
                        602, 603, 604, 605, -1, -1, -1, -1, -1, -1, -1, 256,
                        127, 128, 129, -1, 500, 401, -1, 401, 256, -1, -1, 500,
                        -1, 500, 116, 401, 118, 256, 120, 121, 122, 401, -1,
                        401, -1, -1, -1, 401, -1, -1, -1, 401, -1, 401, -1, -1,
                        -1, -1, -1, 401, -1, -1, 401, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        600, 601, 602, 603, 604, 605, 707, -1, 707, 600, 601,
                        602, 603, 604, 605, 707, -1, 707, -1, -1, 600, 601,
                        602, 603, 604, 605, -1, -1, 600, 601, 602, 603, 604,
                        605, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        701, -1, -1, -1, -1, 708, 707, 707, -1, 701, 703, 707,
                        -1, 707, 703, 707, -1, 707, -1, -1, 701, -1, 702, 700,
                        702, -1, 707, -1, 701, 701, 701, 701, 701, 701, 707,
                        701, 701, 701, 700, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, 704, 705, -1, 707,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 704, 705, -1,
                        707, -1, -1, -1, -1, -1, 704, -1, -1, 707, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, 704, 705, -1, 707, -1, -1, -1, -1, -1, 704, 705,
                        -1, 707, -1, -1, -1, -1, -1, 704, 705, -1, 707,};
    }

    final static short YYFINAL = 9;
    final static short YYMAXTOKEN = 708;
    final static String yyname[] = {"end-of-file", null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, "IF", "THEN", "ELSE",
            "FOR", "VECTOR", "OF", "PRINT", "INTEGER", "DOUBLE", null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, "ID", "STRING", null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, "LOWER_THAN_ELSE", null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, "CONST_INTEGER", "CONST_DOUBLE",
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, "ADD",
            "SUB", "MUL", "DIV", null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, "ASSIGN", null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, "EQ", "NE", "LT", "GT", "LE", "GE", null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, "LPAREN", "RPAREN", "LBRACK", "RBRACK",
            "LBRACE", "RBRACE", "COMMA", "SEMICOLON", "RANGE",};
    final static String yyrule[] =
            {
                    "$accept : Program",
                    "Program : DeclarationList",
                    "DeclarationList : Statement",
                    "DeclarationList : DeclarationList Statement",
                    "Statement : EmptyStatement",
                    "Statement : SelectionStatement",
                    "Statement : IterationStatement",
                    "Statement : PrintExpression",
                    "Statement : VectorStatement",
                    "Statement : VariableStatement",
                    "Statement : AssignmentExpression",
                    "Statement : Block",
                    "EmptyStatement : SEMICOLON",
                    "SelectionStatement : IF LPAREN ConditionalExpression RPAREN Statement",
                    "SelectionStatement : IF LPAREN ConditionalExpression RPAREN Statement ELSE Statement",
                    "SelectionStatement : IF error ConditionalExpression RPAREN Statement",
                    "SelectionStatement : IF LPAREN ConditionalExpression error Statement",
                    "SelectionStatement : IF error ConditionalExpression RPAREN Statement ELSE Statement",
                    "SelectionStatement : IF LPAREN ConditionalExpression error Statement ELSE Statement",
                    "IterationStatement : FOR LPAREN ConditionalExpression RPAREN Statement",
                    "IterationStatement : FOR error ConditionalExpression RPAREN Statement",
                    "IterationStatement : FOR LPAREN ConditionalExpression error Statement",
                    "PrintExpression : PRINT STRING SEMICOLON",
                    "PrintExpression : PRINT STRING error",
                    "VectorStatement : ID LBRACK CONST_INTEGER RANGE CONST_INTEGER RBRACK VECTOR OF INTEGER SEMICOLON",
                    "VectorStatement : ID LBRACK CONST_INTEGER RANGE CONST_INTEGER RBRACK VECTOR OF DOUBLE SEMICOLON",
                    "VectorStatement : ID LBRACK CONST_INTEGER RANGE CONST_INTEGER RBRACK VECTOR OF INTEGER error",
                    "VectorStatement : ID LBRACK CONST_INTEGER RANGE CONST_INTEGER RBRACK VECTOR OF error SEMICOLON",
                    "VectorStatement : ID LBRACK CONST_INTEGER RANGE CONST_INTEGER RBRACK VECTOR error INTEGER SEMICOLON",
                    "VectorStatement : ID LBRACK CONST_INTEGER RANGE CONST_INTEGER RBRACK error OF INTEGER SEMICOLON",
                    "VectorStatement : ID LBRACK CONST_INTEGER RANGE CONST_INTEGER error VECTOR OF INTEGER SEMICOLON",
                    "VectorStatement : ID LBRACK CONST_INTEGER RANGE error RBRACK VECTOR OF INTEGER SEMICOLON",
                    "VectorStatement : ID LBRACK CONST_INTEGER error CONST_INTEGER RBRACK VECTOR OF INTEGER SEMICOLON",
                    "VectorStatement : ID LBRACK error RANGE CONST_INTEGER RBRACK VECTOR OF INTEGER SEMICOLON",
                    "VectorStatement : ID error CONST_INTEGER RANGE CONST_INTEGER RBRACK VECTOR OF INTEGER SEMICOLON",
                    "VariableStatement : INTEGER AssignmentExpression",
                    "VariableStatement : DOUBLE AssignmentExpression",
                    "VariableStatement : INTEGER error",
                    "VariableStatement : DOUBLE error",
                    "AssignmentExpression : ID ASSIGN AdditiveExpression SEMICOLON",
                    "AssignmentExpression : ID LBRACK ID RBRACK ASSIGN AdditiveExpression SEMICOLON",
                    "AssignmentExpression : ID ASSIGN AdditiveExpression error",
                    "AssignmentExpression : ID LBRACK ID RBRACK ASSIGN AdditiveExpression error",
                    "AssignmentExpression : ID LBRACK ID RBRACK ASSIGN error SEMICOLON",
                    "AssignmentExpression : ID LBRACK ID RBRACK error AdditiveExpression SEMICOLON",
                    "AssignmentExpression : ID LBRACK ID error ASSIGN AdditiveExpression SEMICOLON",
                    "AssignmentExpression : ID LBRACK error RBRACK ASSIGN AdditiveExpression SEMICOLON",
                    "AssignmentExpression : ID error ID RBRACK ASSIGN AdditiveExpression SEMICOLON",
                    "ConditionalExpression : AdditiveExpression EQ AdditiveExpression",
                    "ConditionalExpression : AdditiveExpression NE AdditiveExpression",
                    "ConditionalExpression : AdditiveExpression LT AdditiveExpression",
                    "ConditionalExpression : AdditiveExpression GT AdditiveExpression",
                    "ConditionalExpression : AdditiveExpression LE AdditiveExpression",
                    "ConditionalExpression : AdditiveExpression GE AdditiveExpression",
                    "ConditionalExpression : AdditiveExpression EQ error",
                    "ConditionalExpression : AdditiveExpression NE error",
                    "ConditionalExpression : AdditiveExpression LT error",
                    "ConditionalExpression : AdditiveExpression GT error",
                    "ConditionalExpression : AdditiveExpression LE error",
                    "ConditionalExpression : AdditiveExpression GE error",
                    "AdditiveExpression : MultiplicativeExpression",
                    "AdditiveExpression : AdditiveExpression ADD MultiplicativeExpression",
                    "AdditiveExpression : AdditiveExpression SUB MultiplicativeExpression",
                    "AdditiveExpression : AdditiveExpression ADD error",
                    "AdditiveExpression : AdditiveExpression SUB error",
                    "MultiplicativeExpression : UnaryExpression",
                    "MultiplicativeExpression : MultiplicativeExpression MUL UnaryExpression",
                    "MultiplicativeExpression : MultiplicativeExpression DIV UnaryExpression",
                    "MultiplicativeExpression : MultiplicativeExpression MUL error",
                    "MultiplicativeExpression : MultiplicativeExpression DIV error",
                    "UnaryExpression : ID", "UnaryExpression : SUB ID",
                    "UnaryExpression : ID LBRACK ID RBRACK",
                    "UnaryExpression : CONST_INTEGER",
                    "UnaryExpression : CONST_DOUBLE",
                    "Block : LBRACE DeclarationList RBRACE",
                    "Block : LBRACE RBRACE",
                    "Block : LBRACE DeclarationList error",
                    "Block : LBRACE error",};

    // ###############################################################
    // method: yylexdebug : check lexer state
    // ###############################################################
    void yylexdebug(int state, int ch) {
        String s = null;
        if (ch < 0) ch = 0;
        if (ch <= YYMAXTOKEN) // check index bounds
        s = yyname[ch];    // now get it
        if (s == null) s = "illegal-symbol";
        debug("state " + state + ", reading " + ch + " (" + s + ")");
    }

    // The following are now global, to aid in error reporting
    int yyn;       // next next thing to do
    int yym;       //
    int yystate;   // current parsing state from state table
    String yys;    // current token string

    // ###############################################################
    // method: yyparse : parse input and execute indicated items
    // ###############################################################
    int yyparse() {
        boolean doaction;
        init_stacks();
        yynerrs = 0;
        yyerrflag = 0;
        yychar = -1;          // impossible char forces a read
        yystate = 0;            // initial state
        state_push(yystate);  // save it
        val_push(yylval);     // save empty value
        while (true) // until parsing is done, either correctly, or w/error
        {
            doaction = true;
            if (yydebug) debug("loop");
            // #### NEXT ACTION (from reduction table)
            for (yyn = yydefred[yystate]; yyn == 0; yyn = yydefred[yystate]) {
                if (yydebug) debug("yyn:" + yyn + "  state:" + yystate
                        + "  yychar:" + yychar);
                if (yychar < 0)      // we want a char?
                {
                    yychar = yylex();  // get next token
                    if (yydebug) debug(" next yychar:" + yychar);
                    // #### ERROR CHECK ####
                    if (yychar < 0)    // it it didn't work/error
                    {
                        yychar = 0;      // change it to default string (no -1!)
                        if (yydebug) yylexdebug(yystate, yychar);
                    }
                }// yychar<0
                yyn = yysindex[yystate];  // get amount to shift by (shift index)
                if ((yyn != 0) && (yyn += yychar) >= 0 && yyn <= YYTABLESIZE
                        && yycheck[yyn] == yychar) {
                    if (yydebug) debug("state " + yystate
                            + ", shifting to state " + yytable[yyn]);
                    // #### NEXT STATE ####
                    yystate = yytable[yyn];// we are in a new state
                    state_push(yystate);   // save it
                    val_push(yylval);      // push our lval as the input for next
                                      // rule
                    yychar = -1;           // since we have 'eaten' a token, say we need
                                 // another
                    if (yyerrflag > 0)     // have we recovered an error?
                    --yyerrflag;        // give ourselves credit
                    doaction = false;        // but don't process yet
                    break;   // quit the yyn=0 loop
                }

                yyn = yyrindex[yystate];  // reduce
                if ((yyn != 0) && (yyn += yychar) >= 0 && yyn <= YYTABLESIZE
                        && yycheck[yyn] == yychar) {   // we reduced!
                    if (yydebug) debug("reduce");
                    yyn = yytable[yyn];
                    doaction = true; // get ready to execute
                    break;         // drop down to actions
                } else // ERROR RECOVERY
                {
                    if (yyerrflag == 0) {
                        yyerror("syntax error");
                        yynerrs++;
                    }
                    if (yyerrflag < 3) // low error count?
                    {
                        yyerrflag = 3;
                        while (true)   // do until break
                        {
                            if (stateptr < 0)   // check for under & overflow here
                            {
                                yyerror("stack underflow. aborting...");  // note
                                                                         // lower
                                                                         // case
                                                                         // 's'
                                return 1;
                            }
                            yyn = yysindex[state_peek(0)];
                            if ((yyn != 0) && (yyn += YYERRCODE) >= 0
                                    && yyn <= YYTABLESIZE
                                    && yycheck[yyn] == YYERRCODE) {
                                if (yydebug) debug("state " + state_peek(0)
                                        + ", error recovery shifting to state "
                                        + yytable[yyn] + " ");
                                yystate = yytable[yyn];
                                state_push(yystate);
                                val_push(yylval);
                                doaction = false;
                                break;
                            } else {
                                if (yydebug) debug("error recovery discarding state "
                                        + state_peek(0) + " ");
                                if (stateptr < 0)   // check for under & overflow
                                                  // here
                                {
                                    yyerror("Stack underflow. aborting...");  // capital
                                                                             // 'S'
                                    return 1;
                                }
                                state_pop();
                                val_pop();
                            }
                        }
                    } else            // discard this token
                    {
                        if (yychar == 0) return 1; // yyabort
                        if (yydebug) {
                            yys = null;
                            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                            if (yys == null) yys = "illegal-symbol";
                            debug("state " + yystate
                                    + ", error recovery discards token "
                                    + yychar + " (" + yys + ")");
                        }
                        yychar = -1;  // read another
                    }
                }// end error recovery
            }// yyn=0 loop
            if (!doaction)   // any reason not to proceed?
            continue;      // skip action
            yym = yylen[yyn];          // get count of terminals on rhs
            if (yydebug) debug("state " + yystate + ", reducing " + yym
                    + " by rule " + yyn + " (" + yyrule[yyn] + ")");
            if (yym > 0)                 // if count of rhs not 'nil'
            yyval = val_peek(yym - 1); // get current semantic value
            yyval = dup_yyval(yyval); // duplicate yyval if ParserVal is used as
                                      // semantic value
            switch (yyn) {
            // ########## USER-SUPPLIED ACTIONS ##########
                case 12:
                // #line 84 "grammar.y"
                {
                    yyinfo("Sentencia que contiene únicamente el operador de fin de sentencia");
                }
                    break;
                case 13:
                // #line 88 "grammar.y"
                {
                    yyinfo("Sentencia \"si\"");
                }
                    break;
                case 14:
                // #line 89 "grammar.y"
                {
                    yyinfo("Sentencia \"si\" con \"sino\"");
                }
                    break;
                case 15:
                // #line 90 "grammar.y"
                {
                    yyerror("Falta paréntesis izquierdo.");
                }
                    break;
                case 16:
                // #line 91 "grammar.y"
                {
                    yyerror("Falta paréntesis derecho.");
                }
                    break;
                case 17:
                // #line 92 "grammar.y"
                {
                    yyerror("Falta paréntesis izquierdo");
                }
                    break;
                case 18:
                // #line 93 "grammar.y"
                {
                    yyerror("Falta paréntesis derecho");
                }
                    break;
                case 19:
                // #line 97 "grammar.y"
                {
                    yyinfo("Sentencia \"para\"");
                }
                    break;
                case 20:
                // #line 98 "grammar.y"
                {
                    yyerror("Falta paréntesis izquierdo");
                }
                    break;
                case 21:
                // #line 99 "grammar.y"
                {
                    yyerror("Falta paréntesis derecho");
                }
                    break;
                case 22:
                // #line 103 "grammar.y"
                {
                    yyinfo("Sentencia \"imprimir\"");
                }
                    break;
                case 23:
                // #line 104 "grammar.y"
                {
                    yyerror("Falta operador de fin de sentencia");
                }
                    break;
                case 24:
                // #line 108 "grammar.y"
                {
                    yyinfo("Sentencia declarativa \"vector\" de tipo \"entero\"");
                }
                    break;
                case 25:
                // #line 109 "grammar.y"
                {
                    yyinfo("Sentencia declarativa \"vector\" de tipo \"doble\"");
                }
                    break;
                case 26:
                // #line 110 "grammar.y"
                {
                    yyerror("Falta operador de fin de sentencia");
                }
                    break;
                case 27:
                // #line 111 "grammar.y"
                {
                    yyerror("Falta constante entera o doble");
                }
                    break;
                case 28:
                // #line 112 "grammar.y"
                {
                    yyerror("Falta operador \"de\"");
                }
                    break;
                case 29:
                // #line 113 "grammar.y"
                {
                    yyerror("Falta operador \"vector\"");
                }
                    break;
                case 30:
                // #line 114 "grammar.y"
                {
                    yyerror("Falta paréntesis derecho");
                }
                    break;
                case 31:
                // #line 115 "grammar.y"
                {
                    yyerror("Falta constante entera de rango derecho");
                }
                    break;
                case 32:
                // #line 116 "grammar.y"
                {
                    yyerror("Falta operador rango \"..\"");
                }
                    break;
                case 33:
                // #line 117 "grammar.y"
                {
                    yyerror("Falta constante entera de rango izquierdo");
                }
                    break;
                case 34:
                // #line 118 "grammar.y"
                {
                    yyerror("Falta paréntesis izquierdo");
                }
                    break;
                case 35:
                // #line 122 "grammar.y"
                {
                    yyinfo("Sentencia declarativa de tipo \"entero\"");
                }
                    break;
                case 36:
                // #line 123 "grammar.y"
                {
                    yyinfo("Sentencia declarativa de tipo \"doble\"");
                }
                    break;
                case 37:
                // #line 124 "grammar.y"
                {
                    yyerror("Falta expresión de asignación");
                }
                    break;
                case 38:
                // #line 125 "grammar.y"
                {
                    yyerror("Falta expresión de asignación");
                }
                    break;
                case 39:
                // #line 129 "grammar.y"
                {
                    yyinfo("Expresión de asignación de variable");
                }
                    break;
                case 40:
                // #line 130 "grammar.y"
                {
                    yyinfo("Expresión de asignación de vector");
                }
                    break;
                case 41:
                // #line 131 "grammar.y"
                {
                    yyerror("Falta operador de fin de sentencia");
                }
                    break;
                case 42:
                // #line 132 "grammar.y"
                {
                    yyerror("Falta operador de fin de sentencia");
                }
                    break;
                case 43:
                // #line 133 "grammar.y"
                {
                    yyerror("Falta expresión aditiva");
                }
                    break;
                case 44:
                // #line 134 "grammar.y"
                {
                    yyerror("Falta operador de asignación");
                }
                    break;
                case 45:
                // #line 135 "grammar.y"
                {
                    yyerror("Falta corchete izquierdo");
                }
                    break;
                case 46:
                // #line 136 "grammar.y"
                {
                    yyerror("Falta identificador de índice");
                }
                    break;
                case 47:
                // #line 137 "grammar.y"
                {
                    yyerror("Falta corchete derecho");
                }
                    break;
                case 48:
                // #line 141 "grammar.y"
                {
                    yyinfo("Expresión condicional con comparador =");
                }
                    break;
                case 49:
                // #line 142 "grammar.y"
                {
                    yyinfo("Expresión condicional con comparador ^=");
                }
                    break;
                case 50:
                // #line 143 "grammar.y"
                {
                    yyinfo("Expresión condicional con comparador <");
                }
                    break;
                case 51:
                // #line 144 "grammar.y"
                {
                    yyinfo("Expresión condicional con comparador >");
                }
                    break;
                case 52:
                // #line 145 "grammar.y"
                {
                    yyinfo("Expresión condicional con comparador <=");
                }
                    break;
                case 53:
                // #line 146 "grammar.y"
                {
                    yyinfo("Expresión condicional con comparador >=");
                }
                    break;
                case 54:
                // #line 147 "grammar.y"
                {
                    yyerror("Falta expresión aditiva");
                }
                    break;
                case 55:
                // #line 148 "grammar.y"
                {
                    yyerror("Falta expresión aditiva");
                }
                    break;
                case 56:
                // #line 149 "grammar.y"
                {
                    yyerror("Falta expresión aditiva");
                }
                    break;
                case 57:
                // #line 150 "grammar.y"
                {
                    yyerror("Falta expresión aditiva");
                }
                    break;
                case 58:
                // #line 151 "grammar.y"
                {
                    yyerror("Falta expresión aditiva");
                }
                    break;
                case 59:
                // #line 152 "grammar.y"
                {
                    yyerror("Falta expresión aditiva");
                }
                    break;
                case 61:
                // #line 157 "grammar.y"
                {
                    yyinfo("Expresión aditiva con operador +");
                }
                    break;
                case 62:
                // #line 158 "grammar.y"
                {
                    yyinfo("Expresión aditiva con operador -");
                }
                    break;
                case 63:
                // #line 159 "grammar.y"
                {
                    yyerror("Falta expresión multiplicativa");
                }
                    break;
                case 64:
                // #line 160 "grammar.y"
                {
                    yyerror("Falta expresión multiplicativa");
                }
                    break;
                case 66:
                // #line 165 "grammar.y"
                {
                    yyinfo("Expresión multiplicativa con operador *");
                }
                    break;
                case 67:
                // #line 166 "grammar.y"
                {
                    yyinfo("Expresión multiplicativa con operador /");
                }
                    break;
                case 68:
                // #line 167 "grammar.y"
                {
                    yyerror("Falta expresión unaria.");
                }
                    break;
                case 69:
                // #line 168 "grammar.y"
                {
                    yyerror("Falta expresión unaria.");
                }
                    break;
                case 75:
                // #line 180 "grammar.y"
                {
                    yyinfo("Bloque con sentencia");
                }
                    break;
                case 76:
                // #line 181 "grammar.y"
                {
                    yyinfo("Bloque vacío");
                }
                    break;
                case 77:
                // #line 182 "grammar.y"
                {
                    yyerror("Falta llave de fin de bloque.");
                }
                    break;
                case 78:
                // #line 183 "grammar.y"
                {
                    yyerror("Falta llave de fin de bloque.");
                }
                    break;
            // #line 897 "Parser.java"
            // ########## END OF USER-SUPPLIED ACTIONS ##########
            }// switch
             // #### Now let's reduce... ####
            if (yydebug) debug("reduce");
            state_drop(yym);             // we just reduced yylen states
            yystate = state_peek(0);     // get new state
            val_drop(yym);               // corresponding value drop
            yym = yylhs[yyn];            // select next TERMINAL(on lhs)
            if (yystate == 0 && yym == 0)// done? 'rest' state and at first
                                         // TERMINAL
            {
                if (yydebug) debug("After reduction, shifting from state 0 to state "
                        + YYFINAL + "");
                yystate = YYFINAL;         // explicitly say we're done
                state_push(YYFINAL);       // and save it
                val_push(yyval);           // also save the semantic value of parsing
                if (yychar < 0)            // we want another character?
                {
                    yychar = yylex();        // get next character
                    if (yychar < 0) yychar = 0;  // clean, if necessary
                    if (yydebug) yylexdebug(yystate, yychar);
                }
                if (yychar == 0)          // Good exit (if lex returns 0 ;-)
                break;                 // quit the loop--all DONE
            }// if yystate
            else                        // else not done yet
            {                         // get next state and push, for next yydefred[]
                yyn = yygindex[yym];      // find out where to go
                if ((yyn != 0) && (yyn += yystate) >= 0 && yyn <= YYTABLESIZE
                        && yycheck[yyn] == yystate) yystate = yytable[yyn]; // get
                                                                            // new
                                                                            // state
                else
                    yystate = yydgoto[yym]; // else go to new defred
                if (yydebug) debug("after reduction, shifting from state "
                        + state_peek(0) + " to state " + yystate + "");
                state_push(yystate);     // going again, so push state & val...
                val_push(yyval);         // for next action
            }
        }// main loop
        return 0;// yyaccept!!
    }

    // ## end of method parse() ######################################

    // ## run() --- for Thread #######################################
    /**
     * A default run method, used for operating this parser object in the
     * background. It is intended for extending Thread or implementing Runnable.
     * Turn off with -Jnorun .
     */
    public void run() {
        yyparse();
    }

    // ## end of method run() ########################################

    // ## Constructors ###############################################
    /**
     * Default constructor. Turn off with -Jnoconstruct .
     */
    public Parser(final Lexical lexical, final InputStream sourceCode)
            throws IOException {
        super(lexical, sourceCode);
    }

    /**
     * Create a parser, setting the debug to true or false.
     * 
     * @param debugMe
     *            true for debugging, false for no debug.
     */
    public Parser(final Lexical lexical, final InputStream sourceCode,
            final boolean debugMe)
            throws IOException {
        super(lexical, sourceCode);
        yydebug = debugMe;
    }
    // ###############################################################

}
// ################### END OF CLASS ##############################
