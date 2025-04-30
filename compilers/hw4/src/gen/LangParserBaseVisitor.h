
// Generated from LangParser.g4 by ANTLR 4.13.2

#pragma once


#include "antlr4-runtime.h"
#include "LangParserVisitor.h"


/**
 * This class provides an empty implementation of LangParserVisitor, which can be
 * extended to create a visitor which only needs to handle a subset of the available methods.
 */
class  LangParserBaseVisitor : public LangParserVisitor {
public:

  virtual std::any visitProg(LangParser::ProgContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitStmtList(LangParser::StmtListContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitStmt(LangParser::StmtContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitExprAddSub(LangParser::ExprAddSubContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitExprIdentifier(LangParser::ExprIdentifierContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitExprMulDiv(LangParser::ExprMulDivContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitExprCompare(LangParser::ExprCompareContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitExprParentheses(LangParser::ExprParenthesesContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitExprDecimalLiteral(LangParser::ExprDecimalLiteralContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitBlock(LangParser::BlockContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitIfStmtIf(LangParser::IfStmtIfContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitIfStmtIfElse(LangParser::IfStmtIfElseContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitAssignStmt(LangParser::AssignStmtContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitWhileStmt(LangParser::WhileStmtContext *ctx) override {
    return visitChildren(ctx);
  }

  virtual std::any visitPrintStmt(LangParser::PrintStmtContext *ctx) override {
    return visitChildren(ctx);
  }


};

