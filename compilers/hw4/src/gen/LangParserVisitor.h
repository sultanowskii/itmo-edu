
// Generated from LangParser.g4 by ANTLR 4.13.2

#pragma once


#include "antlr4-runtime.h"
#include "LangParser.h"



/**
 * This class defines an abstract visitor for a parse tree
 * produced by LangParser.
 */
class  LangParserVisitor : public antlr4::tree::AbstractParseTreeVisitor {
public:

  /**
   * Visit parse trees produced by LangParser.
   */
    virtual std::any visitProg(LangParser::ProgContext *context) = 0;

    virtual std::any visitStmtList(LangParser::StmtListContext *context) = 0;

    virtual std::any visitStmt(LangParser::StmtContext *context) = 0;

    virtual std::any visitExprAddSub(LangParser::ExprAddSubContext *context) = 0;

    virtual std::any visitExprIdentifier(LangParser::ExprIdentifierContext *context) = 0;

    virtual std::any visitExprMulDiv(LangParser::ExprMulDivContext *context) = 0;

    virtual std::any visitExprCompare(LangParser::ExprCompareContext *context) = 0;

    virtual std::any visitExprParentheses(LangParser::ExprParenthesesContext *context) = 0;

    virtual std::any visitExprDecimalLiteral(LangParser::ExprDecimalLiteralContext *context) = 0;

    virtual std::any visitBlock(LangParser::BlockContext *context) = 0;

    virtual std::any visitIfStmtIf(LangParser::IfStmtIfContext *context) = 0;

    virtual std::any visitIfStmtIfElse(LangParser::IfStmtIfElseContext *context) = 0;

    virtual std::any visitAssignStmt(LangParser::AssignStmtContext *context) = 0;

    virtual std::any visitWhileStmt(LangParser::WhileStmtContext *context) = 0;

    virtual std::any visitPrintStmt(LangParser::PrintStmtContext *context) = 0;


};

