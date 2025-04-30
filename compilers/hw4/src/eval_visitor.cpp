#include "eval_visitor.h"

#include <string>
#include <unordered_map>
#include <vector>
#include <optional>
#include <expected>
#include <sstream>

#include "Token.h"
#include "tree/ParseTree.h"

#include "gen/LangParser.h"
#include "gen/LangParserVisitor.h"

std::string EvalVisitor::formatError(antlr4::Token *token, std::string message) {
    std::stringstream ss;
    ss << this->filepath << ":" << token->getLine() << ":" << token->getCharPositionInLine() + 1 << ": " << message;
    return ss.str();
}

EvalVisitor::Result EvalVisitor::visitWithRes(antlr4::tree::ParseTree *tree) {
    return std::any_cast<Result>(visit(tree));
}

std::optional<std::string> EvalVisitor::runProgram(LangParser::ProgContext *ctx) {
    auto res = std::any_cast<Result>(visitProg(ctx));
    if (res.has_value()) {
        return std::nullopt;
    }
    return res.error();
}

std::any EvalVisitor::visitProg(LangParser::ProgContext *ctx) {
    auto res = Result(0);
    this->scopeDepth = 0;
    this->scopes.push_back(std::unordered_map<std::string, int>());
    for (auto stmt : ctx->stmt()) {
        auto stmtRes = visitWithRes(stmt);
        if (!stmtRes) {
            res = stmtRes;
            break;
        }
    }
    this->scopes.pop_back();
    this->scopeDepth = 0;
    return res;
}

std::any EvalVisitor::visitStmtList(LangParser::StmtListContext *ctx) {
    for (auto stmt : ctx->stmt()) {
        auto res = visitWithRes(stmt);
        if (!res) {
            return res;
        }
    }
    return Result(0);
}

std::any EvalVisitor::visitStmt(LangParser::StmtContext *ctx) {
    if (auto stmt = ctx->assignStmt()) {
        return visit(stmt);
    }
    if (auto stmt = ctx->ifStmt()) {
        return visit(stmt);
    }
    if (auto stmt = ctx->whileStmt()) {
        return visit(stmt);
    }
    if (auto stmt = ctx->printStmt()) {
        return visit(stmt);
    }
    return std::unexpected(formatError(ctx->start, "unexpected statement type"));
}

std::any EvalVisitor::visitBlock(LangParser::BlockContext *ctx) {
    this->scopeDepth++;
    this->scopes.push_back(std::unordered_map<std::string, int>());
    auto res = visit(ctx->stmtList());
    this->scopes.pop_back();
    this->scopeDepth--;
    return res;
}

std::any EvalVisitor::visitExprAddSub(LangParser::ExprAddSubContext *ctx) {
    auto aRes = visitWithRes(ctx->expr(0));
    if (!aRes) {
        return aRes;
    }
    auto a = aRes.value();

    auto bRes = visitWithRes(ctx->expr(1));
    if (!bRes) {
        return bRes;
    }
    auto b = bRes.value();

    Result result;
    switch (ctx->op->getType()) {
        case LangParser::PLUS: {
            result = a + b;
            break;
        }
        case LangParser::MINUS: {
            result = a - b;
            break;
        }
        default: {
            result = std::unexpected(formatError(ctx->op, "unexpected operation (+, - expected)"));
            break;
        }
    }

    return result;
}

std::any EvalVisitor::visitExprIdentifier(LangParser::ExprIdentifierContext *ctx) {
    auto varName = ctx->IDENTIFIER()->getText();

    auto maybeValue = getVariableValue(varName);

    Result result;
    if (maybeValue.has_value()) {
        result = maybeValue.value();
    } else {
        result = std::unexpected(formatError(ctx->getStart(), "undefined variable: " + varName));
    }
    return result;
}

std::any EvalVisitor::visitExprMulDiv(LangParser::ExprMulDivContext *ctx) {
    auto aRes = visitWithRes(ctx->expr(0));
    if (!aRes) {
        return aRes;
    }
    auto a = aRes.value();

    auto bRes = visitWithRes(ctx->expr(1));
    if (!bRes) {
        return bRes;
    }
    auto b = bRes.value();

    Result result;
    switch (ctx->op->getType()) {
        case LangParser::MUL: {
            result = a * b;
            break;
        }
        case LangParser::DIV: {
            result = a / b;
            break;
        }
        default: {
            result = std::unexpected(formatError(ctx->op, "unexpected operation (*, / expected)"));
            break;
        }
    }

    return result;
}

std::any EvalVisitor::visitExprCompare(LangParser::ExprCompareContext *ctx) {
    auto aRes = visitWithRes(ctx->expr(0));
    if (!aRes) {
        return aRes;
    }
    auto a = aRes.value();

    auto bRes = visitWithRes(ctx->expr(1));
    if (!bRes) {
        return bRes;
    }
    auto b = bRes.value();

    Result result;
    switch (ctx->op->getType()) {
        case LangParser::EQUALS: {
            result = a == b;
            break;
        }
        case LangParser::NOT_EQUALS: {
            result = a != b;
            break;
        }
        default: {
            result = std::unexpected(formatError(ctx->op, "unexpected operation (==, != expected)"));
            break;
        }
    }
    return result;
}

std::any EvalVisitor::visitExprParentheses(LangParser::ExprParenthesesContext *ctx) {
    return visit(ctx->expr());
}

std::any EvalVisitor::visitExprDecimalLiteral(LangParser::ExprDecimalLiteralContext *ctx) {
    auto rawVal = ctx->DECIMAL_LITERAL()->getText();
    return Result(std::atoi(rawVal.c_str()));
}

std::any EvalVisitor::visitIfStmtIf(LangParser::IfStmtIfContext *ctx) {
    auto condResRes = visitWithRes(ctx->expr());
    if (!condResRes) {
        return condResRes;
    }
    auto condRes = condResRes.value();

    if (condRes) {
        return visit(ctx->block());
    }
    return Result(0);
}

std::any EvalVisitor::visitIfStmtIfElse(LangParser::IfStmtIfElseContext *ctx) {
    auto condResRes = visitWithRes(ctx->expr());
    if (!condResRes) {
        return condResRes;
    }
    auto condRes = condResRes.value();

    if (condRes) {
        return visit(ctx->ifBlock);
    }
    return visit(ctx->elseBlock);
}

std::any EvalVisitor::visitAssignStmt(LangParser::AssignStmtContext *ctx) {
    auto varName = ctx->IDENTIFIER()->getText();
    auto maybeScopeIndex = findScopeIndexOfVariable(varName);

    auto &scope = this->scopes[maybeScopeIndex.value_or(this->scopeDepth)];

    auto q = visit(ctx->expr());
    auto valueRes = std::any_cast<Result>(q);
    if (!valueRes) {
        return valueRes;
    }
    auto value = valueRes.value();

    scope[varName] = value;
    return Result(0);
}

std::any EvalVisitor::visitWhileStmt(LangParser::WhileStmtContext *ctx) {
    auto condition = ctx->cond;
    while (true) {
        auto condResRes = visitWithRes(condition);
        if (!condResRes) {
            return condResRes;
        }
        auto condRes = condResRes.value();
        if (!condRes) {
            break;
        }

        auto blockRes = visitWithRes(ctx->block());
        if (!blockRes) {
            return blockRes;
        }
    }

    return Result(0);
}

std::any EvalVisitor::visitPrintStmt(LangParser::PrintStmtContext *ctx) {
    auto valueRes = visitWithRes(ctx->expr());
    if (!valueRes) {
        return valueRes;
    }
    auto value = valueRes.value();
    std::cout << value << std::endl;

    return Result(0);
}

std::optional<int> EvalVisitor::getVariableValue(std::string name) {
    for (int i = this->scopeDepth; i >= 0; i--) {
        auto scope = this->scopes[i];
        if (scope.find(name) != scope.end()) {
            return std::optional<int>(scope[name]);
        }
    }
    
    return std::nullopt;
}

std::optional<int> EvalVisitor::findScopeIndexOfVariable(std::string name) {
    for (int i = this->scopeDepth; i >= 0; i--) {
        auto scope = this->scopes[i];
        if (scope.find(name) != scope.end()) {
            return std::optional<int>(i);
        }
    }

    return std::nullopt;
}
