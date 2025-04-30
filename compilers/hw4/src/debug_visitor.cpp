#include "debug_visitor.h"

#include <iostream>

#include "gen/LangParser.h"

void DebugVisitor::printIndent(void) {
    for (int i = 0; i < indent; i++) {
        std::cout << " ";
    }
}

std::any DebugVisitor::visitProg(LangParser::ProgContext *ctx) {
    printIndent();
    std::cout << "Prog" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitStmtList(LangParser::StmtListContext *ctx) {
    printIndent();
    std::cout << "StmtList" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitStmt(LangParser::StmtContext *ctx) {
    printIndent();
    std::cout << "Stmt" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitExprAddSub(LangParser::ExprAddSubContext *ctx) {
    printIndent();
    std::cout << "ExprAddSub(" << ctx->op->getText() << ")" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitExprIdentifier(LangParser::ExprIdentifierContext *ctx) {
    printIndent();
    std::cout << "ExprIdentifier(" << ctx->getText() << ")" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitExprMulDiv(LangParser::ExprMulDivContext *ctx) {
    printIndent();
    std::cout << "ExprMulDiv(" << ctx->op->getText() << ")" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitExprCompare(LangParser::ExprCompareContext *ctx) {
    printIndent();
    std::cout << "ExprCompare(" << ctx->op->getText() << ")" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitExprParentheses(LangParser::ExprParenthesesContext *ctx) {
    printIndent();
    std::cout << "ExprParentheses" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitExprDecimalLiteral(LangParser::ExprDecimalLiteralContext *ctx) {
    printIndent();
    std::cout << "ExprDecimalLiteral(" << ctx->DECIMAL_LITERAL()->getText() << ")" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitBlock(LangParser::BlockContext *ctx) {
    printIndent();
    std::cout << "Block" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitIfStmtIf(LangParser::IfStmtIfContext *ctx) {
    printIndent();
    std::cout << "IfStmtIf" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitIfStmtIfElse(LangParser::IfStmtIfElseContext *ctx) {
    printIndent();
    std::cout << "IfStmtIfElse" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitAssignStmt(LangParser::AssignStmtContext *ctx) {
    printIndent();
    std::cout << "AssignStmt(" << ctx->IDENTIFIER()->getText() << ")" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitWhileStmt(LangParser::WhileStmtContext *ctx) {
    printIndent();
    std::cout << "WhileStmt" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}

std::any DebugVisitor::visitPrintStmt(LangParser::PrintStmtContext *ctx) {
    printIndent();
    std::cout << "PrintStmt" << std::endl;
    indent++;
    auto result = visitChildren(ctx);
    indent--;
    return result;
}
