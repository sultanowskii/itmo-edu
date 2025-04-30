#pragma once

#include "gen/LangParserBaseVisitor.h"

class DebugVisitor : public LangParserBaseVisitor {
  private:
    int  indent = 0;
    void printIndent(void);

  public:
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
