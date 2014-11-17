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
            4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
            8, 8, 8, 8, 12, 12, 12, 12, 12, 12, 12, 9, 9, 11, 11, 11, 11, 11,
            11, 11, 11, 11, 11, 11, 11, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14,
            15, 15, 15, 15, 15, 10, 10, 10, 10,};
    final static short yylen[] = {2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 7,
            5, 5, 7, 7, 9, 9, 8, 5, 5, 3, 3, 10, 10, 10, 10, 10, 10, 10, 10,
            10, 10, 10, 2, 2, 2, 2, 3, 6, 6, 6, 6, 6, 6, 2, 2, 3, 3, 3, 3, 3,
            3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 1, 3, 3, 3, 3, 1, 2, 4, 1, 1,
            3, 2, 3, 2,};
    final static short yydefred[] = {0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 2, 4, 5,
            6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 0, 0, 39, 0, 37, 40, 38, 0, 0, 0,
            80, 78, 0, 3, 49, 48, 0, 75, 76, 0, 0, 0, 0, 67, 0, 0, 0, 0, 0, 0,
            0, 25, 24, 0, 0, 0, 0, 0, 0, 0, 0, 79, 77, 0, 73, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 65, 0, 66, 0, 56, 0, 57, 0, 58, 0, 59, 0, 60, 0, 61, 0, 70,
            68, 71, 69, 0, 0, 22, 0, 0, 0, 0, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 43, 0, 0, 0, 0, 0, 17,
            18, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 0, 0, 0, 0, 0, 0,
            0, 0, 19, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 35, 34, 33, 32, 31,
            30, 29, 28, 26, 27,};
    final static short yydgoto[] = {9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            44, 20, 45, 46, 47,};
    final static short yysindex[] = {-77, -247, -201, -167, -144, -135, -207,
            -100, 0, 0, -77, 0, 0, 0, 0, 0, 0, 0, 0, 0, -245, -98, -98, -98,
            -65, -218, 0, -206, 0, 0, 0, -134, -98, -126, 0, 0, -86, 0, 0, 0,
            -623, 0, 0, -119, -618, -356, -326, 0, -203, -616, -113, -113,
            -205, -98, -161, 0, 0, -111, -127, -606, -609, -309, -651, -238,
            -254, 0, 0, -97, 0, -77, -136, -128, -122, -120, -118, -116, -114,
            -112, -110, -104, -77, -77, -77, -98, -98, -125, -596, -77, -579,
            -363, -159, -361, -157, -355, -223, -153, -185, -550, 53, 0, -326,
            0, -326, 0, -309, 0, -309, 0, -309, 0, -309, 0, -309, 0, -309, 0,
            0, 0, 0, 57, 58, 0, -540, -536, -210, -113, 0, -98, -528, -98,
            -508, -98, -98, -102, -499, -497, -209, 0, -77, -77, -77, -113,
            -113, -223, -494, -309, 104, -309, 105, -309, -309, 0, -309, 108,
            110, 111, -56, 0, 0, 0, -485, -484, -77, 113, 115, 116, 119, 120,
            121, -51, -77, -77, 0, 122, 123, 124, 125, 126, 127, 130, -45, 0,
            0, -480, -479, -469, -466, -465, -464, -457, -456, -215, -455, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
    final static short yyrindex[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 253, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, -252, 0, 0, 0, 0, 0, -243, 0, 0, 0, 0, 0, -342, 0,
            0, 0, 0, 0, 0, 0, 0, -251, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 0, -232, 0, -224, 0, -244, 0, -241, 0, -240, 0, -231, 0,
            -229, 0, -228, 0, 0, 0, 0, 10, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -333, 0, -221, 0, -220, 0, -219,
            -217, 0, -216, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
    final static short yygindex[] = {0, 248, 244, 0, 0, 0, 0, 0, 0, 189, 0,
            139, -48, 266, 35, 54,};
    final static int YYTABLESIZE = 726;
    static short yytable[];
    static {
        yytable();
    }

    static void yytable() {
        yytable =
                new short[]{1, 15, 95, 2, 72, 41, 3, 4, 5, 21, 16, 38, 50, 62,
                        1, 51, 52, 2, 93, 13, 3, 4, 5, 1, 63, 53, 2, 54, 55, 3,
                        4, 5, 64, 132, 25, 47, 46, 45, 55, 44, 42, 201, 50, 51,
                        70, 71, 93, 155, 169, 31, 57, 57, 91, 80, 180, 23, 27,
                        92, 72, 72, 72, 72, 191, 192, 40, 27, 59, 74, 74, 74,
                        74, 135, 40, 63, 63, 124, 78, 79, 40, 67, 40, 68, 40,
                        69, 40, 82, 40, 27, 40, 59, 40, 70, 71, 160, 161, 87,
                        40, 89, 40, 90, 6, 15, 40, 97, 15, 100, 102, 15, 15,
                        15, 16, 125, 26, 16, 6, 136, 16, 16, 16, 13, 99, 29,
                        13, 6, 91, 13, 13, 13, 101, 88, 62, 88, 116, 118, 103,
                        52, 105, 127, 107, 129, 109, 128, 111, 130, 113, 131,
                        115, 134, 72, 72, 72, 72, 117, 137, 151, 138, 34, 62,
                        62, 139, 140, 48, 49, 54, 41, 42, 60, 141, 63, 63, 65,
                        142, 41, 42, 64, 146, 64, 64, 41, 42, 41, 42, 41, 42,
                        41, 42, 41, 42, 41, 42, 41, 42, 86, 28, 30, 148, 41,
                        42, 41, 42, 168, 15, 41, 42, 153, 179, 154, 162, 163,
                        164, 16, 190, 165, 53, 166, 167, 170, 171, 173, 13,
                        174, 175, 122, 123, 176, 177, 178, 193, 194, 183, 184,
                        185, 186, 187, 188, 41, 42, 189, 195, 83, 84, 196, 197,
                        198, 72, 73, 74, 75, 76, 77, 199, 200, 203, 1, 37, 36,
                        0, 15, 72, 72, 72, 72, 72, 72, 0, 43, 16, 74, 74, 74,
                        74, 74, 74, 43, 0, 13, 0, 133, 0, 43, 37, 43, 0, 43, 0,
                        43, 0, 43, 0, 43, 0, 43, 0, 32, 32, 32, 0, 43, 61, 43,
                        0, 0, 0, 43, 0, 0, 0, 0, 0, 0, 0, 0, 0, 98, 144, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 119, 120, 121, 0, 0, 0, 0, 126, 0,
                        0, 0, 0, 43, 0, 104, 106, 108, 110, 112, 114, 0, 0, 0,
                        0, 72, 72, 72, 72, 72, 72, 0, 0, 0, 62, 62, 62, 62, 62,
                        62, 0, 0, 0, 0, 0, 63, 63, 63, 63, 63, 63, 0, 0, 64,
                        64, 64, 64, 64, 64, 157, 158, 159, 0, 0, 0, 0, 0, 0, 0,
                        0, 145, 0, 147, 0, 149, 150, 152, 0, 0, 0, 0, 0, 0,
                        172, 0, 0, 0, 0, 0, 0, 0, 181, 182, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 72, 41, 0, 0, 22, 96, 72,
                        41, 50, 62, 0, 51, 52, 39, 50, 62, 94, 51, 52, 0, 63,
                        53, 0, 54, 55, 0, 63, 53, 64, 54, 55, 47, 46, 45, 64,
                        44, 42, 47, 46, 45, 56, 44, 42, 202, 143, 156, 33, 58,
                        85, 81, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        7, 35, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 66, 0, 8,
                        0, 0, 0, 0, 0, 7, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 15, 0, 15, 0, 0,
                        0, 0, 0, 16, 16, 0, 16, 0, 0, 0, 0, 0, 13, 13, 0, 13,};
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
                        256, 201, 256, 256, 256, 256, 256, 256, 256, 107, 108,
                        400, 401, 256, 256, 104, 256, 256, 256, 703, 256, 105,
                        256, 200, 708, 400, 401, 402, 403, 107, 108, 200, 200,
                        200, 400, 401, 402, 403, 256, 200, 200, 200, 200, 402,
                        403, 200, 702, 200, 200, 200, 701, 200, 701, 200, 200,
                        200, 200, 200, 400, 401, 141, 142, 256, 200, 703, 200,
                        708, 200, 100, 200, 200, 103, 70, 71, 106, 107, 108,
                        100, 707, 256, 103, 200, 300, 106, 107, 108, 100, 256,
                        256, 103, 200, 703, 106, 107, 108, 256, 256, 256, 256,
                        78, 79, 256, 200, 256, 500, 256, 500, 256, 300, 256,
                        300, 256, 500, 256, 300, 400, 401, 402, 403, 256, 703,
                        256, 102, 256, 400, 401, 102, 102, 22, 23, 24, 300,
                        301, 300, 707, 400, 401, 256, 707, 300, 301, 300, 703,
                        400, 401, 300, 301, 300, 301, 300, 301, 300, 301, 300,
                        301, 300, 301, 300, 301, 53, 4, 5, 703, 300, 301, 300,
                        301, 256, 200, 300, 301, 703, 256, 703, 701, 104, 104,
                        200, 256, 104, 24, 104, 104, 701, 701, 105, 200, 105,
                        105, 83, 84, 105, 105, 105, 707, 707, 107, 107, 107,
                        107, 107, 107, 300, 301, 107, 707, 50, 51, 707, 707,
                        707, 600, 601, 602, 603, 604, 605, 707, 707, 707, 0,
                        10, 7, -1, 256, 600, 601, 602, 603, 604, 605, -1, 401,
                        256, 600, 601, 602, 603, 604, 605, 401, -1, 256, -1,
                        500, -1, 401, 36, 401, -1, 401, -1, 401, -1, 401, -1,
                        401, -1, 401, -1, 500, 500, 500, -1, 401, 32, 401, -1,
                        -1, -1, 401, -1, -1, -1, -1, -1, -1, -1, -1, -1, 69,
                        125, -1, -1, -1, -1, -1, -1, -1, -1, -1, 80, 81, 82,
                        -1, -1, -1, -1, 87, -1, -1, -1, -1, 401, -1, 72, 73,
                        74, 75, 76, 77, -1, -1, -1, -1, 600, 601, 602, 603,
                        604, 605, -1, -1, -1, 600, 601, 602, 603, 604, 605, -1,
                        -1, -1, -1, -1, 600, 601, 602, 603, 604, 605, -1, -1,
                        600, 601, 602, 603, 604, 605, 138, 139, 140, -1, -1,
                        -1, -1, -1, -1, -1, -1, 127, -1, 129, -1, 131, 132,
                        133, -1, -1, -1, -1, -1, -1, 162, -1, -1, -1, -1, -1,
                        -1, -1, 170, 171, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 701, 701, -1,
                        -1, 700, 708, 707, 707, 701, 701, -1, 701, 701, 707,
                        707, 707, 703, 707, 707, -1, 701, 701, -1, 701, 701,
                        -1, 707, 707, 701, 707, 707, 701, 701, 701, 707, 701,
                        701, 707, 707, 707, 707, 707, 707, 707, 703, 703, 702,
                        702, 702, 701, 700, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 704, 705,
                        -1, 707, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 704,
                        705, -1, 707, -1, -1, -1, -1, -1, 704, -1, -1, 707, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, 704, 705, -1, 707, -1, -1, -1, -1, -1, 704,
                        705, -1, 707, -1, -1, -1, -1, -1, 704, 705, -1, 707,};
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
                    this.yyerror("Falta operador de fin de sentencia");
                }
                    break;
                case 26:
                // #line 110 "grammar.y"
                {
                    this.yyinfo("Sentencia declarativa \"vector\" de tipo \"entero\"");
                    this.vector();
                }
                    break;
                case 27:
                // #line 111 "grammar.y"
                {
                    this.yyinfo("Sentencia declarativa \"vector\" de tipo \"doble\"");
                    this.vector();
                }
                    break;
                case 28:
                // #line 112 "grammar.y"
                {
                    this.yyerror("Falta operador de fin de sentencia");
                }
                    break;
                case 29:
                // #line 113 "grammar.y"
                {
                    this.yyerror("Falta constante entera o doble");
                }
                    break;
                case 30:
                // #line 114 "grammar.y"
                {
                    this.yyerror("Falta operador \"de\"");
                }
                    break;
                case 31:
                // #line 115 "grammar.y"
                {
                    this.yyerror("Falta operador \"vector\"");
                }
                    break;
                case 32:
                // #line 116 "grammar.y"
                {
                    this.yyerror("Falta paréntesis derecho");
                }
                    break;
                case 33:
                // #line 117 "grammar.y"
                {
                    this.yyerror("Falta constante entera de rango derecho");
                }
                    break;
                case 34:
                // #line 118 "grammar.y"
                {
                    this.yyerror("Falta operador rango \"..\"");
                }
                    break;
                case 35:
                // #line 119 "grammar.y"
                {
                    this.yyerror("Falta constante entera de rango izquierdo");
                }
                    break;
                case 36:
                // #line 120 "grammar.y"
                {
                    this.yyerror("Falta paréntesis izquierdo");
                }
                    break;
                case 37:
                // #line 124 "grammar.y"
                {
                    this.yyinfo("Sentencia declarativa de tipo \"entero\"");
                }
                    break;
                case 38:
                // #line 125 "grammar.y"
                {
                    this.yyinfo("Sentencia declarativa de tipo \"doble\"");
                }
                    break;
                case 39:
                // #line 126 "grammar.y"
                {
                    this.yyerror("Falta expresión de asignación");
                }
                    break;
                case 40:
                // #line 127 "grammar.y"
                {
                    this.yyerror("Falta expresión de asignación");
                }
                    break;
                case 41:
                // #line 131 "grammar.y"
                {
                    this.yyinfo("Expresión de asignación de variable");
                    this.assignToId();
                }
                    break;
                case 42:
                // #line 132 "grammar.y"
                {
                    this.yyinfo("Expresión de asignación de vector");
                    this.assignToVector();
                }
                    break;
                case 43:
                // #line 133 "grammar.y"
                {
                    this.yyerror("Falta expresión aditiva");
                }
                    break;
                case 44:
                // #line 134 "grammar.y"
                {
                    this.yyerror("Falta operador de asignación");
                }
                    break;
                case 45:
                // #line 135 "grammar.y"
                {
                    this.yyerror("Falta corchete izquierdo");
                }
                    break;
                case 46:
                // #line 136 "grammar.y"
                {
                    this.yyerror("Falta identificador de índice");
                }
                    break;
                case 47:
                // #line 137 "grammar.y"
                {
                    this.yyerror("Falta corchete derecho");
                }
                    break;
                case 48:
                // #line 141 "grammar.y"
                {
                    this.yyinfo("Expresión de asignación de variable");
                }
                    break;
                case 49:
                // #line 142 "grammar.y"
                {
                    this.yyerror("Falta operador de fin de sentencia");
                }
                    break;
                case 50:
                // #line 146 "grammar.y"
                {
                    this.yyinfo("Expresión condicional con comparador =");
                    this.eq();
                }
                    break;
                case 51:
                // #line 147 "grammar.y"
                {
                    this.yyinfo("Expresión condicional con comparador ^=");
                    this.ne();
                }
                    break;
                case 52:
                // #line 148 "grammar.y"
                {
                    this.yyinfo("Expresión condicional con comparador <");
                    this.lt();
                }
                    break;
                case 53:
                // #line 149 "grammar.y"
                {
                    this.yyinfo("Expresión condicional con comparador >");
                    this.gt();
                }
                    break;
                case 54:
                // #line 150 "grammar.y"
                {
                    this.yyinfo("Expresión condicional con comparador <=");
                    this.le();
                }
                    break;
                case 55:
                // #line 151 "grammar.y"
                {
                    this.yyinfo("Expresión condicional con comparador >=");
                    this.ge();
                }
                    break;
                case 56:
                // #line 152 "grammar.y"
                {
                    this.yyerror("Falta expresión aditiva");
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
                case 63:
                // #line 162 "grammar.y"
                {
                    this.yyinfo("Expresión aditiva con operador +");
                    this.add();
                }
                    break;
                case 64:
                // #line 163 "grammar.y"
                {
                    this.yyinfo("Expresión aditiva con operador -");
                    this.sub();
                }
                    break;
                case 65:
                // #line 164 "grammar.y"
                {
                    this.yyerror("Falta expresión multiplicativa");
                }
                    break;
                case 66:
                // #line 165 "grammar.y"
                {
                    this.yyerror("Falta expresión multiplicativa");
                }
                    break;
                case 68:
                // #line 170 "grammar.y"
                {
                    this.yyinfo("Expresión multiplicativa con operador *");
                    this.mul();
                }
                    break;
                case 69:
                // #line 171 "grammar.y"
                {
                    this.yyinfo("Expresión multiplicativa con operador /");
                    this.div();
                }
                    break;
                case 70:
                // #line 172 "grammar.y"
                {
                    this.yyerror("Falta expresión unaria.");
                }
                    break;
                case 71:
                // #line 173 "grammar.y"
                {
                    this.yyerror("Falta expresión unaria.");
                }
                    break;
                case 74:
                // #line 179 "grammar.y"
                {
                    this.idAt();
                }
                    break;
                case 77:
                // #line 185 "grammar.y"
                {
                    this.yyinfo("Bloque con sentencia");
                }
                    break;
                case 78:
                // #line 186 "grammar.y"
                {
                    this.yyinfo("Bloque vacío");
                }
                    break;
                case 79:
                // #line 187 "grammar.y"
                {
                    this.yyerror("Falta llave de fin de bloque.");
                }
                    break;
                case 80:
                // #line 188 "grammar.y"
                {
                    this.yyerror("Falta llave de fin de bloque.");
                }
                    break;
            // #line 917 "Parser.java"
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
