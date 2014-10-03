%{
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
import java.util.*;

%}

/* Keywords. */
%token IF                100
%token THEN              101
%token ELSE              102
%token FOR               103
%token VECTOR            104
%token OF                105
%token PRINT             106
%token INTEGER           107
%token DOUBLE            108

/* Identifiers. */
%token ID                200
%token STRING            201

/* Constants. */
%token CONST_INTEGER     300
%token CONST_DOUBLE      301

/* Arithmetic operators. */
%token ADD              400
%token SUB              401
%token MUL              402
%token DIV              403

/* Assignment operators. */
%token ASSIGN           500

/* Comparators. */
%token EQ               600
%token NE               601
%token LT               602
%token GT               603
%token LE               604
%token GE               605

/* Expression operators. */
%token LPAREN           700
%token RPAREN           701
%token LBRACK           702
%token RBRACK           703
%token LBRACE           704
%token RBRACE           705
%token COMMA            706
%token SEMICOLON        707
%token RANGE            708

%nonassoc LOWER_THAN_ELSE
%nonassoc ELSE

%start Program

%%

Program
    : DeclarationList
    ;

DeclarationList
    : Statement
    | DeclarationList Statement
    ;

Statement
    : EmptyStatement
    | SelectionStatement
    | IterationStatement
    | PrintExpression
    | VectorStatement
    | VariableStatement
    | AssignmentExpression
    | Block
    ;

EmptyStatement
    : SEMICOLON
    ;

SelectionStatement
    : IF LPAREN ConditionalExpression RPAREN Statement %prec LOWER_THAN_ELSE           {yyinfo("Sentencia \"si\"");}
    | IF LPAREN ConditionalExpression RPAREN Statement ELSE Statement                  {yyinfo("Sentencia \"si\" con \"sino\"");}
    | IF error ConditionalExpression RPAREN Statement %prec LOWER_THAN_ELSE            {yyerror("Falta paréntesis izquierdo.");}
    | IF LPAREN ConditionalExpression error Statement %prec LOWER_THAN_ELSE            {yyerror("Falta paréntesis derecho.");}
    | IF error ConditionalExpression RPAREN Statement ELSE Statement                   {yyerror("Falta paréntesis izquierdo");}
    | IF LPAREN ConditionalExpression error Statement ELSE Statement                   {yyerror("Falta paréntesis derecho");}
    ;

IterationStatement
    : FOR LPAREN ConditionalExpression RPAREN Statement                                {yyinfo("Sentencia \"para\"");}
    | FOR error ConditionalExpression RPAREN Statement                                 {yyerror("Falta paréntesis izquierdo");}
    | FOR LPAREN ConditionalExpression error Statement                                 {yyerror("Falta paréntesis derecho");}
    ;

PrintExpression
    : PRINT STRING SEMICOLON                                                           {yyinfo("Sentencia \"imprimir\"");}
    | PRINT STRING error                                                               {yyerror("Falta operador de fin de sentencia");}
    ;

VectorStatement
    : ID LBRACK CONST_INTEGER RANGE CONST_INTEGER RBRACK VECTOR OF INTEGER SEMICOLON   {yyinfo("Sentencia declarativa \"vector\" de tipo \"entero\"");}
    | ID LBRACK CONST_INTEGER RANGE CONST_INTEGER RBRACK VECTOR OF DOUBLE SEMICOLON    {yyinfo("Sentencia declarativa \"vector\" de tipo \"doble\"");}
    | ID LBRACK CONST_INTEGER RANGE CONST_INTEGER RBRACK VECTOR OF INTEGER error       {yyerror("Falta operador de fin de sentencia");}
    | ID LBRACK CONST_INTEGER RANGE CONST_INTEGER RBRACK VECTOR OF error SEMICOLON     {yyerror("Falta constante entera o doble");}
    | ID LBRACK CONST_INTEGER RANGE CONST_INTEGER RBRACK VECTOR error INTEGER SEMICOLON{yyerror("Falta operador \"de\"");}
    | ID LBRACK CONST_INTEGER RANGE CONST_INTEGER RBRACK error OF INTEGER SEMICOLON    {yyerror("Falta operador \"vector\"");}
    | ID LBRACK CONST_INTEGER RANGE CONST_INTEGER error VECTOR OF INTEGER SEMICOLON    {yyerror("Falta paréntesis derecho");}
    | ID LBRACK CONST_INTEGER RANGE error RBRACK VECTOR OF INTEGER SEMICOLON           {yyerror("Falta constante entera de rango derecho");}
    | ID LBRACK CONST_INTEGER error CONST_INTEGER RBRACK VECTOR OF INTEGER SEMICOLON   {yyerror("Falta operador rango \"..\"");}
    | ID LBRACK error RANGE CONST_INTEGER RBRACK VECTOR OF INTEGER SEMICOLON           {yyerror("Falta constante entera de rango izquierdo");}
    | ID error CONST_INTEGER RANGE CONST_INTEGER RBRACK VECTOR OF INTEGER SEMICOLON    {yyerror("Falta paréntesis izquierdo");}
    ;

VariableStatement
    : INTEGER AssignmentExpression                                                     {yyinfo("Sentencia declarativa de tipo \"entero\"");}
    | DOUBLE AssignmentExpression                                                      {yyinfo("Sentencia declarativa de tipo \"doble\"");}
    | INTEGER error                                                                    {yyerror("Falta expresión de asignación");}
    | DOUBLE error                                                                     {yyerror("Falta expresión de asignación");}
    ;

AssignmentExpression
    : ID ASSIGN AdditiveExpression SEMICOLON                                           {yyinfo("Expresión de asignación de variable");}
    | ID LBRACK ID RBRACK ASSIGN AdditiveExpression SEMICOLON                          {yyinfo("Expresión de asignación de vector");}
    | ID ASSIGN AdditiveExpression error                                               {yyerror("Falta operador de fin de sentencia");}
    | ID LBRACK ID RBRACK ASSIGN AdditiveExpression error                              {yyerror("Falta operador de fin de sentencia");}
    | ID LBRACK ID RBRACK ASSIGN error SEMICOLON                                       {yyerror("Falta expresión aditiva");}
    | ID LBRACK ID RBRACK error AdditiveExpression SEMICOLON                           {yyerror("Falta operador de asignación");}
    | ID LBRACK ID error ASSIGN AdditiveExpression SEMICOLON                           {yyerror("Falta corchete izquierdo");}
    | ID LBRACK error RBRACK ASSIGN AdditiveExpression SEMICOLON                       {yyerror("Falta identificador de índice");}
    | ID error ID RBRACK ASSIGN AdditiveExpression SEMICOLON                           {yyerror("Falta corchete derecho");}
    ;

ConditionalExpression
    : AdditiveExpression EQ AdditiveExpression                                         {yyinfo("Expresión condicional con comparador =");}
    | AdditiveExpression NE AdditiveExpression                                         {yyinfo("Expresión condicional con comparador ^=");}
    | AdditiveExpression LT AdditiveExpression                                         {yyinfo("Expresión condicional con comparador <");}
    | AdditiveExpression GT AdditiveExpression                                         {yyinfo("Expresión condicional con comparador >");}
    | AdditiveExpression LE AdditiveExpression                                         {yyinfo("Expresión condicional con comparador <=");}
    | AdditiveExpression GE AdditiveExpression                                         {yyinfo("Expresión condicional con comparador >=");}
    | AdditiveExpression EQ error                                                      {yyerror("Falta expresión aditiva");}
    | AdditiveExpression NE error                                                      {yyerror("Falta expresión aditiva");}
    | AdditiveExpression LT error                                                      {yyerror("Falta expresión aditiva");}
    | AdditiveExpression GT error                                                      {yyerror("Falta expresión aditiva");}
    | AdditiveExpression LE error                                                      {yyerror("Falta expresión aditiva");}
    | AdditiveExpression GE error                                                      {yyerror("Falta expresión aditiva");}
    ;

AdditiveExpression
    : MultiplicativeExpression
    | AdditiveExpression ADD MultiplicativeExpression                                  {yyinfo("Expresión aditiva con operador +");}
    | AdditiveExpression SUB MultiplicativeExpression                                  {yyinfo("Expresión aditiva con operador -");}
    | AdditiveExpression ADD error                                                     {yyerror("Falta expresión multiplicativa");}
    | AdditiveExpression SUB error                                                     {yyerror("Falta expresión multiplicativa");}
    ;

MultiplicativeExpression
    : UnaryExpression
    | MultiplicativeExpression MUL UnaryExpression                                     {yyinfo("Expresión multiplicativa con operador *");}
    | MultiplicativeExpression DIV UnaryExpression                                     {yyinfo("Expresión multiplicativa con operador /");}
    | MultiplicativeExpression MUL error                                               {yyerror("Falta expresión unaria.");}
    | MultiplicativeExpression DIV error                                               {yyerror("Falta expresión unaria.");}
    ;

UnaryExpression
    : ID
    | SUB ID
    | ID RBRACK ID LBRACK
    | CONST_INTEGER
    | CONST_DOUBLE
    ;

Block
    : LBRACE DeclarationList RBRACE                                                   {yyinfo("Bloque con sentencia");}
    | LBRACE RBRACE                                                                   {yyinfo("Bloque vacío");}
    | LBRACE DeclarationList error                                                    {yyerror("Falta llave de fin de bloque.");}
    | LBRACE error                                                                    {yyerror("Falta llave de fin de bloque.");}
    ;
