#pragma once

#include <string>
#include <unordered_map>
#include <vector>
#include <optional>
#include <expected>

#include "Token.h"

#include "gen/LangParserBaseVisitor.h"

class EvalVisitor : public LangParserBaseVisitor {
  private:
    typedef std::expected<int, std::string> Result;

    // Naive variable scope implementation
    // It works here but it doesn't address:
    //  - functions, lambdas
    //  - types (in this language, we only have ints)
    std::vector<std::unordered_map<std::string, int>> scopes;
    int                                               scopeDepth = 0;
    std::string filepath;

    std::string formatError(antlr4::Token *token, std::string message);
    std::optional<int> getVariableValue(std::string name);
    std::optional<int> findScopeIndexOfVariable(std::string varName);

    Result visitWithRes(antlr4::tree::ParseTree *tree);
  public:
    EvalVisitor(std::string filepath) {
        this->filepath = filepath;
        this->scopes = std::vector<std::unordered_map<std::string, int>>();
    }

    // Evaluates the program. Returns error message if it occurs.
    std::optional<std::string> runProgram(LangParser::ProgContext *ctx);
  
    virtual std::any visitProg(LangParser::ProgContext *ctx);
    virtual std::any visitStmtList(LangParser::StmtListContext *ctx);
    virtual std::any visitStmt(LangParser::StmtContext *ctx);
    virtual std::any visitExprAddSub(LangParser::ExprAddSubContext *ctx);
    virtual std::any visitExprIdentifier(LangParser::ExprIdentifierContext *ctx);
    virtual std::any visitExprMulDiv(LangParser::ExprMulDivContext *ctx);
    virtual std::any visitExprCompare(LangParser::ExprCompareContext *ctx);
    virtual std::any visitExprParentheses(LangParser::ExprParenthesesContext *ctx);
    virtual std::any visitExprDecimalLiteral(LangParser::ExprDecimalLiteralContext *ctx);
    virtual std::any visitBlock(LangParser::BlockContext *ctx);
    virtual std::any visitIfStmtIf(LangParser::IfStmtIfContext *ctx);
    virtual std::any visitIfStmtIfElse(LangParser::IfStmtIfElseContext *ctx);
    virtual std::any visitAssignStmt(LangParser::AssignStmtContext *ctx);
    virtual std::any visitWhileStmt(LangParser::WhileStmtContext *ctx);
    virtual std::any visitPrintStmt(LangParser::PrintStmtContext *ctx);
};
