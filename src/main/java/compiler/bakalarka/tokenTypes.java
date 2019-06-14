package compiler.bakalarka;

public enum tokenTypes {


    IF("if"),ELSE("else"),WHILE("while"),DO("do"),THEN("then"),
    PLUS("+"),MINUS("-"),MUL("*"),LE("<="),ASSIGN("="),
    NUM("num"),VAR("var"),TRUE("true"),FALSE("false"),
    NEG("!"),CON("AND"),SKIP("skip"),LPAR("("),RPAR(")"),SETVAL(":="),
    SEMCOL(";"),SEQWHILE(""),SEQDO(""),SEQENDWHILE(""), SEQIF(""),
    SEQTHEN(""),SEQELSE(""),SEQENDTHEN(""),SEQENDELSE("");

    public final String token;

    tokenTypes(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
