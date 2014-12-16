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
    void debug(final String msg) {
        if (this.yydebug) {
            System.out.println(msg);
        }
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
    final void state_push(final int state) {
        try {
            this.stateptr++;
            this.statestk[this.stateptr] = state;
        } catch (final ArrayIndexOutOfBoundsException e) {
            final int oldsize = this.statestk.length;
            final int newsize = oldsize * 2;
            final int[] newstack = new int[newsize];
            System.arraycopy(this.statestk, 0, newstack, 0, oldsize);
            this.statestk = newstack;
            this.statestk[this.stateptr] = state;
        }
    }

    final int state_pop() {
        return this.statestk[this.stateptr--];
    }

    final void state_drop(final int cnt) {
        this.stateptr -= cnt;
    }

    final int state_peek(final int relative) {
        return this.statestk[this.stateptr - relative];
    }

    // ###############################################################
    // method: init_stacks : allocate and prepare stacks
    // ###############################################################
    final boolean init_stacks() {
        this.stateptr = -1;
        this.val_init();
        return true;
    }

    // ###############################################################
    // method: dump_stacks : show n levels of the stacks
    // ###############################################################
    void dump_stacks(final int count) {
        int i;
        System.out.println("=index==state====value=     s:" + this.stateptr
                + "  v:" + this.valptr);
        for (i = 0; i < count; i++) {
            System.out.println(" " + i + "    " + this.statestk[i] + "      "
                    + this.valstk[i]);
        }
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
        this.valstk = new ParserVal[YYSTACKSIZE];
        this.yyval = new ParserVal();
        this.yylval = new ParserVal();
        this.valptr = -1;
    }

    void val_push(final ParserVal val) {
        if (this.valptr >= YYSTACKSIZE) {
            return;
        }
        this.valstk[++this.valptr] = val;
    }

    ParserVal val_pop() {
        if (this.valptr < 0) {
            return new ParserVal();
        }
        return this.valstk[this.valptr--];
    }

    void val_drop(final int cnt) {
        int ptr;
        ptr = this.valptr - cnt;
        if (ptr < 0) {
            return;
        }
        this.valptr = ptr;
    }

    ParserVal val_peek(final int relative) {
        int ptr;
        ptr = this.valptr - relative;
        if (ptr < 0) {
            return new ParserVal();
        }
        return this.valstk[ptr];
    }

    final ParserVal dup_yyval(final ParserVal val) {
        final ParserVal dup = new ParserVal();
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
            4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            7, 8, 8, 8, 8, 12, 12, 12, 12, 12, 12, 12, 9, 9, 11, 11, 11, 11,
            11, 11, 11, 11, 11, 11, 11, 11, 14, 14, 14, 14, 14, 15, 15, 15, 15,
            15, 13, 13, 13, 13, 13, 10, 10, 10, 10,};
    final static short yylen[] = {2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 7,
            5, 5, 7, 7, 9, 9, 8, 5, 5, 3, 3, 3, 10, 10, 10, 10, 10, 10, 10, 10,
            10, 10, 10, 2, 2, 2, 2, 3, 6, 6, 6, 6, 6, 6, 2, 2, 3, 3, 3, 3, 3,
            3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 1, 3, 3, 3, 3, 1, 2, 4, 1, 1,
            3, 2, 3, 2,};
    final static short yydefred[] = {0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 2, 4, 5,
            6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 0, 0, 0, 76, 77, 0, 0, 40, 0, 38,
            41, 39, 0, 0, 0, 81, 79, 0, 3, 50, 49, 0, 68, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 26, 24, 74, 25, 0, 0, 0, 0, 0, 0, 0, 0, 80, 78, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 66, 0, 67, 0, 57, 0, 58, 0, 59, 0, 60, 0, 61, 0, 62,
            0, 71, 69, 72, 70, 0, 0, 22, 0, 0, 0, 0, 23, 75, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 44, 0, 0, 0, 0,
            0, 17, 18, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 0, 0, 0, 0,
            0, 0, 0, 0, 19, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 37, 36, 35, 34,
            33, 32, 31, 30, 29, 27, 28,};
    final static short yydgoto[] = {9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            45, 20, 46, 47, 48,};
    final static short yysindex[] = {-77, -247, -201, -137, -127, -125, -207,
            -100, 0, 0, -77, 0, 0, 0, 0, 0, 0, 0, 0, 0, -245, -95, -95, -95,
            -65, -648, -218, 0, 0, -121, -626, 0, -206, 0, 0, 0, -138, -95,
            -126, 0, 0, -86, 0, 0, 0, -618, 0, -356, -311, -203, -616, -113,
            -113, -205, -95, -167, -105, 0, 0, 0, 0, -103, -124, -604, -606,
            -307, -651, -238, -254, 0, 0, -77, -128, -122, -120, -118, -116,
            -114, -112, -110, -104, -102, -77, -77, -77, -95, -95, -123, -601,
            -77, -579, -566, -361, -159, -357, -155, -353, -223, -147, -185,
            53, 0, -311, 0, -311, 0, -307, 0, -307, 0, -307, 0, -307, 0, -307,
            0, -307, 0, 0, 0, 0, 63, 64, 0, -540, -536, -210, -113, 0, 0, -95,
            -528, -95, -509, -95, -95, -97, -508, -501, -209, -77, -77, -77,
            -113, -113, -223, -493, -307, 105, -307, 107, -307, -307, 0, -307,
            109, 111, 112, -56, 0, 0, 0, -484, -483, -77, 115, 116, 117, 118,
            119, 120, -49, -77, -77, 0, 121, 122, 123, 124, 125, 126, 127, -42,
            0, 0, -481, -480, -470, -469, -466, -465, -464, -457, -215, -456,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
    final static short yyrindex[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 252, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -252, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -243, 0, 0, 0, 0, -342,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -251, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 1, 0, -232, 0, -224, 0, -244, 0, -241, 0, -240, 0, -231,
            0, -229, 0, -228, 0, 0, 0, 0, 10, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -333, 0, -221, 0, -220, 0,
            -219, -217, 0, -216, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
    final static short yygindex[] = {0, 258, 243, 0, 0, 0, 0, 0, 0, 188, 0,
            232, -23, 31, 263, 88,};
    final static int YYTABLESIZE = 726;
    static short yytable[];
    static {
        yytable();
    }

    static void yytable() {
        yytable =
                new short[]{1, 15, 98, 2, 73, 42, 3, 4, 5, 21, 16, 43, 51, 63,
                        1, 52, 53, 2, 96, 13, 3, 4, 5, 1, 64, 54, 2, 55, 56, 3,
                        4, 5, 65, 135, 30, 48, 47, 46, 57, 45, 43, 203, 51, 52,
                        72, 73, 96, 157, 171, 36, 61, 61, 94, 82, 56, 23, 182,
                        95, 73, 73, 73, 73, 63, 25, 26, 193, 194, 75, 75, 75,
                        75, 138, 25, 32, 67, 32, 67, 126, 25, 59, 25, 60, 25,
                        71, 25, 84, 25, 32, 25, 89, 25, 80, 81, 72, 73, 90, 25,
                        63, 25, 92, 6, 15, 93, 25, 15, 25, 127, 15, 15, 15, 16,
                        118, 120, 16, 6, 139, 16, 16, 16, 13, 162, 163, 13, 6,
                        129, 13, 13, 13, 101, 31, 66, 34, 91, 91, 103, 53, 105,
                        94, 107, 130, 109, 131, 111, 132, 113, 133, 115, 134,
                        73, 73, 73, 73, 117, 137, 119, 140, 39, 63, 63, 153,
                        102, 104, 64, 27, 28, 141, 142, 143, 64, 64, 69, 144,
                        27, 28, 68, 148, 65, 65, 27, 28, 27, 28, 27, 28, 27,
                        28, 27, 28, 27, 28, 27, 28, 33, 35, 150, 155, 27, 28,
                        27, 28, 170, 15, 156, 27, 28, 27, 28, 181, 164, 165,
                        16, 166, 54, 167, 192, 168, 169, 172, 173, 13, 175,
                        176, 177, 178, 179, 180, 195, 196, 185, 186, 187, 188,
                        189, 190, 191, 27, 28, 197, 198, 85, 86, 199, 200, 201,
                        74, 75, 76, 77, 78, 79, 202, 205, 1, 42, 49, 50, 55,
                        15, 73, 73, 73, 73, 73, 73, 29, 41, 16, 75, 75, 75, 75,
                        75, 75, 29, 0, 13, 0, 136, 0, 29, 0, 29, 0, 29, 42, 29,
                        88, 29, 0, 29, 0, 29, 0, 37, 37, 37, 0, 29, 0, 29, 65,
                        0, 0, 0, 29, 0, 29, 0, 0, 0, 0, 0, 0, 0, 100, 146, 0,
                        124, 125, 0, 0, 0, 0, 0, 0, 121, 122, 123, 0, 0, 0, 0,
                        128, 0, 0, 0, 29, 106, 108, 110, 112, 114, 116, 0, 0,
                        0, 0, 0, 73, 73, 73, 73, 73, 73, 0, 0, 0, 63, 63, 63,
                        63, 63, 63, 0, 0, 0, 0, 0, 64, 64, 64, 64, 64, 64, 0,
                        0, 65, 65, 65, 65, 65, 65, 0, 159, 160, 161, 0, 0, 0,
                        0, 0, 0, 0, 147, 0, 149, 0, 151, 152, 154, 0, 0, 0, 0,
                        0, 0, 0, 174, 0, 0, 0, 0, 0, 0, 0, 183, 184, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 73, 42, 0, 0, 22, 99,
                        73, 42, 51, 63, 0, 52, 53, 44, 51, 63, 97, 52, 53, 0,
                        64, 54, 0, 55, 56, 0, 64, 54, 65, 55, 56, 48, 47, 46,
                        65, 45, 43, 48, 47, 46, 58, 45, 43, 204, 145, 158, 38,
                        62, 87, 83, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 7, 40, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 70, 0,
                        8, 0, 0, 0, 0, 0, 7, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 15, 0, 15, 0,
                        0, 0, 0, 0, 16, 16, 0, 16, 0, 0, 0, 0, 0, 13, 13, 0,
                        13,};
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
                        256, 3, 256, 256, 256, 256, 256, 256, 256, 107, 108,
                        400, 401, 256, 256, 104, 256, 256, 256, 703, 256, 702,
                        256, 105, 708, 400, 401, 402, 403, 200, 200, 201, 107,
                        108, 400, 401, 402, 403, 256, 200, 200, 200, 200, 200,
                        200, 200, 200, 200, 707, 200, 701, 200, 701, 200, 200,
                        200, 256, 200, 402, 403, 400, 401, 200, 200, 200, 200,
                        703, 200, 100, 708, 200, 103, 200, 707, 106, 107, 108,
                        100, 80, 81, 103, 200, 300, 106, 107, 108, 100, 143,
                        144, 103, 200, 703, 106, 107, 108, 256, 256, 256, 256,
                        256, 256, 256, 200, 256, 703, 256, 500, 256, 300, 256,
                        500, 256, 300, 256, 500, 400, 401, 402, 403, 256, 300,
                        256, 102, 256, 400, 401, 256, 72, 73, 300, 300, 301,
                        102, 102, 707, 400, 401, 256, 707, 300, 301, 300, 703,
                        400, 401, 300, 301, 300, 301, 300, 301, 300, 301, 300,
                        301, 300, 301, 300, 301, 4, 5, 703, 703, 300, 301, 300,
                        301, 256, 200, 703, 300, 301, 300, 301, 256, 701, 104,
                        200, 104, 24, 104, 256, 104, 104, 701, 701, 200, 105,
                        105, 105, 105, 105, 105, 707, 707, 107, 107, 107, 107,
                        107, 107, 107, 300, 301, 707, 707, 51, 52, 707, 707,
                        707, 600, 601, 602, 603, 604, 605, 707, 707, 0, 10, 22,
                        23, 24, 256, 600, 601, 602, 603, 604, 605, 401, 7, 256,
                        600, 601, 602, 603, 604, 605, 401, -1, 256, -1, 500,
                        -1, 401, -1, 401, -1, 401, 41, 401, 54, 401, -1, 401,
                        -1, 401, -1, 500, 500, 500, -1, 401, -1, 401, 37, -1,
                        -1, -1, 401, -1, 401, -1, -1, -1, -1, -1, -1, -1, 71,
                        127, -1, 85, 86, -1, -1, -1, -1, -1, -1, 82, 83, 84,
                        -1, -1, -1, -1, 89, -1, -1, -1, 401, 74, 75, 76, 77,
                        78, 79, -1, -1, -1, -1, -1, 600, 601, 602, 603, 604,
                        605, -1, -1, -1, 600, 601, 602, 603, 604, 605, -1, -1,
                        -1, -1, -1, 600, 601, 602, 603, 604, 605, -1, -1, 600,
                        601, 602, 603, 604, 605, -1, 140, 141, 142, -1, -1, -1,
                        -1, -1, -1, -1, 130, -1, 132, -1, 134, 135, 136, -1,
                        -1, -1, -1, -1, -1, -1, 164, -1, -1, -1, -1, -1, -1,
                        -1, 172, 173, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, 701, 701, -1, -1, 700,
                        708, 707, 707, 701, 701, -1, 701, 701, 707, 707, 707,
                        703, 707, 707, -1, 701, 701, -1, 701, 701, -1, 707,
                        707, 701, 707, 707, 701, 701, 701, 707, 701, 701, 707,
                        707, 707, 707, 707, 707, 707, 703, 703, 702, 702, 702,
                        701, 700, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
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
                    "IterationStatement : FOR LPAREN INTEGER AssignmentExpression ConditionalExpression SEMICOLON AssignmentVariable RPAREN Statement",
                    "IterationStatement : FOR LPAREN DOUBLE AssignmentExpression ConditionalExpression SEMICOLON AssignmentVariable RPAREN Statement",
                    "IterationStatement : FOR LPAREN AssignmentExpression ConditionalExpression SEMICOLON AssignmentExpression RPAREN Statement",
                    "IterationStatement : FOR error ConditionalExpression RPAREN Statement",
                    "IterationStatement : FOR LPAREN ConditionalExpression error Statement",
                    "PrintExpression : PRINT STRING SEMICOLON",
                    "PrintExpression : PRINT UnaryExpression SEMICOLON",
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
                    "AssignmentVariable : ID ASSIGN AdditiveExpression",
                    "AssignmentVariable : ID LBRACK ID RBRACK ASSIGN AdditiveExpression",
                    "AssignmentVariable : ID LBRACK ID RBRACK ASSIGN error",
                    "AssignmentVariable : ID LBRACK ID RBRACK error AdditiveExpression",
                    "AssignmentVariable : ID LBRACK ID error ASSIGN AdditiveExpression",
                    "AssignmentVariable : ID LBRACK error RBRACK ASSIGN AdditiveExpression",
                    "AssignmentVariable : ID error ID RBRACK ASSIGN AdditiveExpression",
                    "AssignmentExpression : AssignmentVariable SEMICOLON",
                    "AssignmentExpression : AssignmentVariable error",
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
    void yylexdebug(final int state, int ch) {
        String s = null;
        if (ch < 0) {
            ch = 0;
        }
        if (ch <= YYMAXTOKEN) {
            s = yyname[ch];    // now get it
        }
        if (s == null) {
            s = "illegal-symbol";
        }
        this.debug("state " + state + ", reading " + ch + " (" + s + ")");
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
        this.init_stacks();
        this.yynerrs = 0;
        this.yyerrflag = 0;
        this.yychar = -1;          // impossible char forces a read
        this.yystate = 0;            // initial state
        this.state_push(this.yystate);  // save it
        this.val_push(this.yylval);     // save empty value
        while (true) // until parsing is done, either correctly, or w/error
        {
            doaction = true;
            if (this.yydebug) {
                this.debug("loop");
            }
            // #### NEXT ACTION (from reduction table)
            for (this.yyn = yydefred[this.yystate]; this.yyn == 0; this.yyn =
                    yydefred[this.yystate]) {
                if (this.yydebug) {
                    this.debug("yyn:" + this.yyn + "  state:" + this.yystate
                            + "  yychar:" + this.yychar);
                }
                if (this.yychar < 0)      // we want a char?
                {
                    this.yychar = this.yylex();  // get next token
                    if (this.yydebug) {
                        this.debug(" next yychar:" + this.yychar);
                    }
                    // #### ERROR CHECK ####
                    if (this.yychar < 0)    // it it didn't work/error
                    {
                        this.yychar = 0;      // change it to default string (no -1!)
                        if (this.yydebug) {
                            this.yylexdebug(this.yystate, this.yychar);
                        }
                    }
                }// yychar<0
                this.yyn = yysindex[this.yystate];  // get amount to shift by
                                                   // (shift index)
                if (this.yyn != 0 && (this.yyn += this.yychar) >= 0
                        && this.yyn <= YYTABLESIZE
                        && yycheck[this.yyn] == this.yychar) {
                    if (this.yydebug) {
                        this.debug("state " + this.yystate
                                + ", shifting to state " + yytable[this.yyn]);
                    }
                    // #### NEXT STATE ####
                    this.yystate = yytable[this.yyn];// we are in a new state
                    this.state_push(this.yystate);   // save it
                    this.val_push(this.yylval);      // push our lval as the input
                    // for next rule
                    this.yychar = -1;           // since we have 'eaten' a token, say we
                    // need another
                    if (this.yyerrflag > 0) {
                        --this.yyerrflag;        // give ourselves credit
                    }
                    doaction = false;        // but don't process yet
                    break;   // quit the yyn=0 loop
                }

                this.yyn = yyrindex[this.yystate];  // reduce
                if (this.yyn != 0 && (this.yyn += this.yychar) >= 0
                        && this.yyn <= YYTABLESIZE
                        && yycheck[this.yyn] == this.yychar) {   // we reduced!
                    if (this.yydebug) {
                        this.debug("reduce");
                    }
                    this.yyn = yytable[this.yyn];
                    doaction = true; // get ready to execute
                    break;         // drop down to actions
                } else // ERROR RECOVERY
                {
                    if (this.yyerrflag == 0) {
                        this.yyerror("syntax error");
                        this.yynerrs++;
                    }
                    if (this.yyerrflag < 3) // low error count?
                    {
                        this.yyerrflag = 3;
                        while (true)   // do until break
                        {
                            if (this.stateptr < 0)   // check for under & overflow
                                                   // here
                            {
                                this.yyerror("stack underflow. aborting...");  // note
                                                                              // lower
                                                                              // case
                                                                              // 's'
                                return 1;
                            }
                            this.yyn = yysindex[this.state_peek(0)];
                            if (this.yyn != 0 && (this.yyn += YYERRCODE) >= 0
                                    && this.yyn <= YYTABLESIZE
                                    && yycheck[this.yyn] == YYERRCODE) {
                                if (this.yydebug) {
                                    this.debug("state "
                                            + this.state_peek(0)
                                            + ", error recovery shifting to state "
                                            + yytable[this.yyn] + " ");
                                }
                                this.yystate = yytable[this.yyn];
                                this.state_push(this.yystate);
                                this.val_push(this.yylval);
                                doaction = false;
                                break;
                            } else {
                                if (this.yydebug) {
                                    this.debug("error recovery discarding state "
                                            + this.state_peek(0) + " ");
                                }
                                if (this.stateptr < 0)   // check for under &
                                                       // overflow here
                                {
                                    this.yyerror("Stack underflow. aborting...");  // capital
                                                                                  // 'S'
                                    return 1;
                                }
                                this.state_pop();
                                this.val_pop();
                            }
                        }
                    } else            // discard this token
                    {
                        if (this.yychar == 0) {
                            return 1; // yyabort
                        }
                        if (this.yydebug) {
                            this.yys = null;
                            if (this.yychar <= YYMAXTOKEN) {
                                this.yys = yyname[this.yychar];
                            }
                            if (this.yys == null) {
                                this.yys = "illegal-symbol";
                            }
                            this.debug("state " + this.yystate
                                    + ", error recovery discards token "
                                    + this.yychar + " (" + this.yys + ")");
                        }
                        this.yychar = -1;  // read another
                    }
                }// end error recovery
            }// yyn=0 loop
            if (!doaction) {
                continue;      // skip action
            }
            this.yym = yylen[this.yyn];          // get count of terminals on rhs
            if (this.yydebug) {
                this.debug("state " + this.yystate + ", reducing " + this.yym
                        + " by rule " + this.yyn + " (" + yyrule[this.yyn]
                        + ")");
            }
            if (this.yym > 0) {
                this.yyval = this.val_peek(this.yym - 1); // get current
                                                          // semantic value
            }
            this.yyval = this.dup_yyval(this.yyval); // duplicate yyval if
                                                     // ParserVal is used as
                                                     // semantic value
            switch (this.yyn) {
            // ########## USER-SUPPLIED ACTIONS ##########
                case 12:
                // #line 84 "grammar.y"
                {
                    this.yyinfo("Sentencia que contiene únicamente el operador de fin de sentencia");
                }
                    break;
                case 13:
                // #line 88 "grammar.y"
                {
                    this.yyinfo("Sentencia \"si\"");
                    this.ifCondition();
                }
                    break;
                case 14:
                // #line 89 "grammar.y"
                {
                    this.yyinfo("Sentencia \"si\" con \"sino\"");
                    this.ifElseCondition();
                }
                    break;
                case 15:
                // #line 90 "grammar.y"
                {
                    this.yyerror("Falta paréntesis izquierdo.");
                }
                    break;
                case 16:
                // #line 91 "grammar.y"
                {
                    this.yyerror("Falta paréntesis derecho.");
                }
                    break;
                case 17:
                // #line 92 "grammar.y"
                {
                    this.yyerror("Falta paréntesis izquierdo");
                }
                    break;
                case 18:
                // #line 93 "grammar.y"
                {
                    this.yyerror("Falta paréntesis derecho");
                }
                    break;
                case 19:
                // #line 97 "grammar.y"
                {
                    this.yyinfo("Sentencia \"para\"");
                    this.forCondition();
                }
                    break;
                case 20:
                // #line 98 "grammar.y"
                {
                    this.yyinfo("Sentencia \"para\"");
                    this.forCondition();
                }
                    break;
                case 21:
                // #line 99 "grammar.y"
                {
                    this.yyinfo("Sentencia \"para\"");
                    this.forCondition();
                }
                    break;
                case 22:
                // #line 100 "grammar.y"
                {
                    this.yyerror("Falta paréntesis izquierdo");
                }
                    break;
                case 23:
                // #line 101 "grammar.y"
                {
                    this.yyerror("Falta paréntesis derecho");
                }
                    break;
                case 24:
                // #line 105 "grammar.y"
                {
                    this.yyinfo("Sentencia \"imprimir\"");
                    this.print();
                }
                    break;
                case 25:
                // #line 106 "grammar.y"
                {
                    this.yyinfo("Sentencia \"imprimir\"");
                    this.print();
                }
                    break;
                case 26:
                // #line 107 "grammar.y"
                {
                    this.yyerror("Falta operador de fin de sentencia");
                }
                    break;
                case 27:
                // #line 111 "grammar.y"
                {
                    this.yyinfo("Sentencia declarativa \"vector\" de tipo \"entero\"");
                    this.vector();
                }
                    break;
                case 28:
                // #line 112 "grammar.y"
                {
                    this.yyinfo("Sentencia declarativa \"vector\" de tipo \"doble\"");
                    this.vector();
                }
                    break;
                case 29:
                // #line 113 "grammar.y"
                {
                    this.yyerror("Falta operador de fin de sentencia");
                }
                    break;
                case 30:
                // #line 114 "grammar.y"
                {
                    this.yyerror("Falta constante entera o doble");
                }
                    break;
                case 31:
                // #line 115 "grammar.y"
                {
                    this.yyerror("Falta operador \"de\"");
                }
                    break;
                case 32:
                // #line 116 "grammar.y"
                {
                    this.yyerror("Falta operador \"vector\"");
                }
                    break;
                case 33:
                // #line 117 "grammar.y"
                {
                    this.yyerror("Falta paréntesis derecho");
                }
                    break;
                case 34:
                // #line 118 "grammar.y"
                {
                    this.yyerror("Falta constante entera de rango derecho");
                }
                    break;
                case 35:
                // #line 119 "grammar.y"
                {
                    this.yyerror("Falta operador rango \"..\"");
                }
                    break;
                case 36:
                // #line 120 "grammar.y"
                {
                    this.yyerror("Falta constante entera de rango izquierdo");
                }
                    break;
                case 37:
                // #line 121 "grammar.y"
                {
                    this.yyerror("Falta paréntesis izquierdo");
                }
                    break;
                case 38:
                // #line 125 "grammar.y"
                {
                    this.yyinfo("Sentencia declarativa de tipo \"entero\"");
                }
                    break;
                case 39:
                // #line 126 "grammar.y"
                {
                    this.yyinfo("Sentencia declarativa de tipo \"doble\"");
                }
                    break;
                case 40:
                // #line 127 "grammar.y"
                {
                    this.yyerror("Falta expresión de asignación");
                }
                    break;
                case 41:
                // #line 128 "grammar.y"
                {
                    this.yyerror("Falta expresión de asignación");
                }
                    break;
                case 42:
                // #line 132 "grammar.y"
                {
                    this.yyinfo("Expresión de asignación de variable");
                    this.assignToId();
                }
                    break;
                case 43:
                // #line 133 "grammar.y"
                {
                    this.yyinfo("Expresión de asignación de vector");
                    this.assignToVector();
                }
                    break;
                case 44:
                // #line 134 "grammar.y"
                {
                    this.yyerror("Falta expresión aditiva");
                }
                    break;
                case 45:
                // #line 135 "grammar.y"
                {
                    this.yyerror("Falta operador de asignación");
                }
                    break;
                case 46:
                // #line 136 "grammar.y"
                {
                    this.yyerror("Falta corchete izquierdo");
                }
                    break;
                case 47:
                // #line 137 "grammar.y"
                {
                    this.yyerror("Falta identificador de índice");
                }
                    break;
                case 48:
                // #line 138 "grammar.y"
                {
                    this.yyerror("Falta corchete derecho");
                }
                    break;
                case 49:
                // #line 142 "grammar.y"
                {
                    this.yyinfo("Expresión de asignación de variable");
                }
                    break;
                case 50:
                // #line 143 "grammar.y"
                {
                    this.yyerror("Falta operador de fin de sentencia");
                }
                    break;
                case 51:
                // #line 147 "grammar.y"
                {
                    this.yyinfo("Expresión condicional con comparador =");
                    this.eq();
                }
                    break;
                case 52:
                // #line 148 "grammar.y"
                {
                    this.yyinfo("Expresión condicional con comparador ^=");
                    this.ne();
                }
                    break;
                case 53:
                // #line 149 "grammar.y"
                {
                    this.yyinfo("Expresión condicional con comparador <");
                    this.lt();
                }
                    break;
                case 54:
                // #line 150 "grammar.y"
                {
                    this.yyinfo("Expresión condicional con comparador >");
                    this.gt();
                }
                    break;
                case 55:
                // #line 151 "grammar.y"
                {
                    this.yyinfo("Expresión condicional con comparador <=");
                    this.le();
                }
                    break;
                case 56:
                // #line 152 "grammar.y"
                {
                    this.yyinfo("Expresión condicional con comparador >=");
                    this.ge();
                }
                    break;
                case 57:
                // #line 153 "grammar.y"
                {
                    this.yyerror("Falta expresión aditiva");
                }
                    break;
                case 58:
                // #line 154 "grammar.y"
                {
                    this.yyerror("Falta expresión aditiva");
                }
                    break;
                case 59:
                // #line 155 "grammar.y"
                {
                    this.yyerror("Falta expresión aditiva");
                }
                    break;
                case 60:
                // #line 156 "grammar.y"
                {
                    this.yyerror("Falta expresión aditiva");
                }
                    break;
                case 61:
                // #line 157 "grammar.y"
                {
                    this.yyerror("Falta expresión aditiva");
                }
                    break;
                case 62:
                // #line 158 "grammar.y"
                {
                    this.yyerror("Falta expresión aditiva");
                }
                    break;
                case 64:
                // #line 163 "grammar.y"
                {
                    this.yyinfo("Expresión aditiva con operador +");
                    this.add();
                }
                    break;
                case 65:
                // #line 164 "grammar.y"
                {
                    this.yyinfo("Expresión aditiva con operador -");
                    this.sub();
                }
                    break;
                case 66:
                // #line 165 "grammar.y"
                {
                    this.yyerror("Falta expresión multiplicativa");
                }
                    break;
                case 67:
                // #line 166 "grammar.y"
                {
                    this.yyerror("Falta expresión multiplicativa");
                }
                    break;
                case 69:
                // #line 171 "grammar.y"
                {
                    this.yyinfo("Expresión multiplicativa con operador *");
                    this.mul();
                }
                    break;
                case 70:
                // #line 172 "grammar.y"
                {
                    this.yyinfo("Expresión multiplicativa con operador /");
                    this.div();
                }
                    break;
                case 71:
                // #line 173 "grammar.y"
                {
                    this.yyerror("Falta expresión unaria.");
                }
                    break;
                case 72:
                // #line 174 "grammar.y"
                {
                    this.yyerror("Falta expresión unaria.");
                }
                    break;
                case 75:
                // #line 180 "grammar.y"
                {
                    this.idAt();
                }
                    break;
                case 78:
                // #line 186 "grammar.y"
                {
                    this.yyinfo("Bloque con sentencia");
                }
                    break;
                case 79:
                // #line 187 "grammar.y"
                {
                    this.yyinfo("Bloque vacío");
                }
                    break;
                case 80:
                // #line 188 "grammar.y"
                {
                    this.yyerror("Falta llave de fin de bloque.");
                }
                    break;
                case 81:
                // #line 189 "grammar.y"
                {
                    this.yyerror("Falta llave de fin de bloque.");
                }
                    break;
            // #line 924 "Parser.java"
            // ########## END OF USER-SUPPLIED ACTIONS ##########
            }// switch
             // #### Now let's reduce... ####
            if (this.yydebug) {
                this.debug("reduce");
            }
            this.state_drop(this.yym);             // we just reduced yylen states
            this.yystate = this.state_peek(0);     // get new state
            this.val_drop(this.yym);               // corresponding value drop
            this.yym = yylhs[this.yyn];            // select next TERMINAL(on lhs)
            if (this.yystate == 0 && this.yym == 0)// done? 'rest' state and at
                                                   // first TERMINAL
            {
                if (this.yydebug) {
                    this.debug("After reduction, shifting from state 0 to state "
                            + YYFINAL + "");
                }
                this.yystate = YYFINAL;         // explicitly say we're done
                this.state_push(YYFINAL);       // and save it
                this.val_push(this.yyval);           // also save the semantic value of
                // parsing
                if (this.yychar < 0)            // we want another character?
                {
                    this.yychar = this.yylex();        // get next character
                    if (this.yychar < 0) {
                        this.yychar = 0;  // clean, if necessary
                    }
                    if (this.yydebug) {
                        this.yylexdebug(this.yystate, this.yychar);
                    }
                }
                if (this.yychar == 0) {
                    break;                 // quit the loop--all DONE
                }
            }// if yystate
            else                        // else not done yet
            {                         // get next state and push, for next yydefred[]
                this.yyn = yygindex[this.yym];      // find out where to go
                if (this.yyn != 0 && (this.yyn += this.yystate) >= 0
                        && this.yyn <= YYTABLESIZE
                        && yycheck[this.yyn] == this.yystate) {
                    this.yystate = yytable[this.yyn]; // get new state
                } else {
                    this.yystate = yydgoto[this.yym]; // else go to new defred
                }
                if (this.yydebug) {
                    this.debug("after reduction, shifting from state "
                            + this.state_peek(0) + " to state " + this.yystate
                            + "");
                }
                this.state_push(this.yystate);     // going again, so push state &
                // val...
                this.val_push(this.yyval);         // for next action
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
        this.yyparse();
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
        this.yydebug = debugMe;
    }
    // ###############################################################

}
// ################### END OF CLASS ##############################
