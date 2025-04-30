#include <iostream>

#include "antlr4-runtime.h"

#include "gen/LangLexer.h"
#include "gen/LangParser.h"

#include "debug_visitor.h"
#include "eval_visitor.h"

int main(int argc, const char *argv[]) {
    if (argc != 2) {
        std::cout << "usage: " << argv[0] << " filepath" << std::endl;
        return 1;
    }
    auto filepath = argv[1];

    std::ifstream stream;
    stream.open(filepath);

    antlr4::ANTLRInputStream  input(stream);
    LangLexer                 lexer(&input);
    antlr4::CommonTokenStream tokens(&lexer);
    LangParser                parser(&tokens);

    LangParser::ProgContext *tree = parser.prog();

    if (lexer.getNumberOfSyntaxErrors() > 0) {
        return 1;
    }
    if (parser.getNumberOfSyntaxErrors() > 0) {
        return 1;
    }

    auto visitor = EvalVisitor(std::string(filepath));
    auto maybeErrorMessage = visitor.runProgram(tree);
    if (maybeErrorMessage.has_value()) {
        std::cerr << maybeErrorMessage.value() << std::endl;
        return 1;
    }

    return 0;
}
