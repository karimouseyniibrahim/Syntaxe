package cup.example;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java.lang.*;
import java.io.InputStreamReader;

%%

%class Lexer
%implements sym
%public
%unicode
%line
%column
%cup
%char
%{
	

    public Lexer(ComplexSymbolFactory sf, java.io.InputStream is){
		this(is);
        symbolFactory = sf;
    }
	public Lexer(ComplexSymbolFactory sf, java.io.Reader reader){
		this(reader);
        symbolFactory = sf;
    }
    
    private StringBuffer sb;
    private ComplexSymbolFactory symbolFactory;
    private int csline,cscolumn;

    public Symbol symbol(String name, int code){
		return symbolFactory.newSymbol(name, code,
						new Location(yyline+1,yycolumn+1, yychar), // -yylength()
						new Location(yyline+1,yycolumn+yylength(), yychar+yylength())
				);
    }
    public Symbol symbol(String name, int code, String lexem){
	return symbolFactory.newSymbol(name, code, 
						new Location(yyline+1, yycolumn +1, yychar), 
						new Location(yyline+1,yycolumn+yylength(), yychar+yylength()), lexem);
    }
    
    protected void emit_warning(String message){
    	System.out.println("scanner warning: " + message + " at : 2 "+ 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
    
    protected void emit_error(String message){
    	System.out.println("scanner error: " + message + " at : 2" + 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
%}


atome = [a-z]+
Newline    = \r | \n | \r\n
Whitespace = [ \t\f] | {Newline}
Number     = [0-9]+


/* comments */
/*
Comment = {TraditionalComment} | {EndOfLineComment}
TraditionalComment = "/*" {CommentContent} \*+ "/"
EndOfLineComment = "//" [^\r\n]* {Newline}
CommentContent = ( [^*] | \*+[^*/] )*/

ident = ([:jletter:] | "_" ) ([:jletterdigit:] | [:jletter:] | "_" )*


%eofval{
    return symbolFactory.newSymbol("EOF",sym.EOF);
%eofval}

%state CODESEG

%%  

<YYINITIAL> {

  {Whitespace} {                              }
  ";"          { return symbolFactory.newSymbol("SEMI", SEMI); }
  "("          { return symbolFactory.newSymbol("LPAREN", LPAREN); }
  ")"          { return symbolFactory.newSymbol("RPAREN", RPAREN); }
  ","                                 { return symbolFactory.newSymbol("COMMA", COMMA);}
  ">>"                                 { return symbolFactory.newSymbol("SEQUENTIELLE",SEQUENTIELLE)    ; }
  "|["                                 { return symbolFactory.newSymbol("LSEYN",LSEYN)   ; }
  "]|"                                 { return symbolFactory.newSymbol("RSEYN",RSEYN)   ; }
  "[]"                                 { return symbolFactory.newSymbol("CHOICE",CHOICE)   ; }
  "["                                 {return symbolFactory.newSymbol("LBRACKET",LBRACKET)   ; }
  "{"                                 {return symbolFactory.newSymbol("LCROSS",LCROSS)   ; }
  "}"                                 {return symbolFactory.newSymbol("RCROSS",RCROSS)   ; }
  "]"                                 { return symbolFactory.newSymbol("RBRACKET",RBRACKET)   ; }
  "|||"                                 { return symbolFactory.newSymbol("PARARELLEP",PARARELLEP)   ; }
  "||"                                 { return symbolFactory.newSymbol("PARALLELE",PARALLELE) ;}
  ":="                                 {return symbolFactory.newSymbol("ASSIGN",ASSIGN)   ; }
  "[>"                                 { return symbolFactory.newSymbol("INTERUPT",INTERUPT)   ; }
  "IN"                                 { return symbolFactory.newSymbol("IN",IN)   ; }
  "STOP"                                 {return symbolFactory.newSymbol("STOP",STOP)   ; }
  "EXIT"                                 {return symbolFactory.newSymbol("EXIT",EXIT)   ; }
  "HIDE"                                 {return symbolFactory.newSymbol("HIDE",HIDE)   ; }
  "DELTA"                                 {return symbolFactory.newSymbol("DELTA",DELTA)   ; }
  "@"                                 {return symbolFactory.newSymbol("AROBASE",AROBASE)   ; }
  "!"                                 {return symbolFactory.newSymbol("EMISSION",EMISSION)   ; }
  "?"                                 {return symbolFactory.newSymbol("RECEPTION",RECEPTION)   ; }
  {atome}                        {return symbolFactory.newSymbol("ATOME",ATOME, yytext()); } 
  {Number}                        {return symbolFactory.newSymbol("TIME",TIME, Integer.parseInt(yytext()) ); }  

  
}



// error fallback
.|\n          { emit_warning("Unrecognized character '" +yytext()+"' -- ignored"); }
