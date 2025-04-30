
// Generated from LangParser.g4 by ANTLR 4.13.2


#include "LangParserVisitor.h"

#include "LangParser.h"


using namespace antlrcpp;

using namespace antlr4;

namespace {

struct LangParserStaticData final {
  LangParserStaticData(std::vector<std::string> ruleNames,
                        std::vector<std::string> literalNames,
                        std::vector<std::string> symbolicNames)
      : ruleNames(std::move(ruleNames)), literalNames(std::move(literalNames)),
        symbolicNames(std::move(symbolicNames)),
        vocabulary(this->literalNames, this->symbolicNames) {}

  LangParserStaticData(const LangParserStaticData&) = delete;
  LangParserStaticData(LangParserStaticData&&) = delete;
  LangParserStaticData& operator=(const LangParserStaticData&) = delete;
  LangParserStaticData& operator=(LangParserStaticData&&) = delete;

  std::vector<antlr4::dfa::DFA> decisionToDFA;
  antlr4::atn::PredictionContextCache sharedContextCache;
  const std::vector<std::string> ruleNames;
  const std::vector<std::string> literalNames;
  const std::vector<std::string> symbolicNames;
  const antlr4::dfa::Vocabulary vocabulary;
  antlr4::atn::SerializedATNView serializedATN;
  std::unique_ptr<antlr4::atn::ATN> atn;
};

::antlr4::internal::OnceFlag langparserParserOnceFlag;
#if ANTLR4_USE_THREAD_LOCAL_CACHE
static thread_local
#endif
std::unique_ptr<LangParserStaticData> langparserParserStaticData = nullptr;

void langparserParserInitialize() {
#if ANTLR4_USE_THREAD_LOCAL_CACHE
  if (langparserParserStaticData != nullptr) {
    return;
  }
#else
  assert(langparserParserStaticData == nullptr);
#endif
  auto staticData = std::make_unique<LangParserStaticData>(
    std::vector<std::string>{
      "prog", "stmtList", "stmt", "expr", "block", "ifStmt", "assignStmt", 
      "whileStmt", "printStmt"
    },
    std::vector<std::string>{
      "", "'if'", "'else'", "'while'", "'print'", "'=='", "'!='", "'('", 
      "')'", "'{'", "'}'", "'='", "';'", "", "", "'+'", "'-'", "'*'", "'/'"
    },
    std::vector<std::string>{
      "", "IF", "ELSE", "WHILE", "PRINT", "EQUALS", "NOT_EQUALS", "L_PAREN", 
      "R_PAREN", "L_CURLY", "R_CURLY", "ASSIGN", "SEMICOLON", "DECIMAL_LITERAL", 
      "IDENTIFIER", "PLUS", "MINUS", "MUL", "DIV", "WS", "EOL"
    }
  );
  static const int32_t serializedATNSegment[] = {
  	4,1,20,131,2,0,7,0,2,1,7,1,2,2,7,2,2,3,7,3,2,4,7,4,2,5,7,5,2,6,7,6,2,
  	7,7,7,2,8,7,8,1,0,1,0,5,0,21,8,0,10,0,12,0,24,9,0,1,0,1,0,1,1,5,1,29,
  	8,1,10,1,12,1,32,9,1,1,2,1,2,4,2,36,8,2,11,2,12,2,37,1,2,3,2,41,8,2,1,
  	2,1,2,4,2,45,8,2,11,2,12,2,46,1,2,3,2,50,8,2,1,2,1,2,4,2,54,8,2,11,2,
  	12,2,55,1,2,3,2,59,8,2,1,2,1,2,4,2,63,8,2,11,2,12,2,64,1,2,3,2,68,8,2,
  	3,2,70,8,2,1,3,1,3,1,3,1,3,1,3,1,3,1,3,3,3,79,8,3,1,3,1,3,1,3,1,3,1,3,
  	1,3,1,3,1,3,1,3,5,3,90,8,3,10,3,12,3,93,9,3,1,4,1,4,1,4,1,4,1,4,1,5,1,
  	5,1,5,1,5,1,5,1,5,1,5,1,5,1,5,1,5,1,5,1,5,1,5,1,5,3,5,114,8,5,1,6,1,6,
  	1,6,1,6,1,7,1,7,1,7,1,7,1,7,1,7,1,8,1,8,1,8,1,8,1,8,1,8,0,1,6,9,0,2,4,
  	6,8,10,12,14,16,0,4,2,0,12,12,20,20,1,0,5,6,1,0,17,18,1,0,15,16,140,0,
  	18,1,0,0,0,2,30,1,0,0,0,4,69,1,0,0,0,6,78,1,0,0,0,8,94,1,0,0,0,10,113,
  	1,0,0,0,12,115,1,0,0,0,14,119,1,0,0,0,16,125,1,0,0,0,18,22,3,4,2,0,19,
  	21,3,4,2,0,20,19,1,0,0,0,21,24,1,0,0,0,22,20,1,0,0,0,22,23,1,0,0,0,23,
  	25,1,0,0,0,24,22,1,0,0,0,25,26,5,0,0,1,26,1,1,0,0,0,27,29,3,4,2,0,28,
  	27,1,0,0,0,29,32,1,0,0,0,30,28,1,0,0,0,30,31,1,0,0,0,31,3,1,0,0,0,32,
  	30,1,0,0,0,33,40,3,12,6,0,34,36,7,0,0,0,35,34,1,0,0,0,36,37,1,0,0,0,37,
  	35,1,0,0,0,37,38,1,0,0,0,38,41,1,0,0,0,39,41,5,0,0,1,40,35,1,0,0,0,40,
  	39,1,0,0,0,41,70,1,0,0,0,42,49,3,10,5,0,43,45,7,0,0,0,44,43,1,0,0,0,45,
  	46,1,0,0,0,46,44,1,0,0,0,46,47,1,0,0,0,47,50,1,0,0,0,48,50,5,0,0,1,49,
  	44,1,0,0,0,49,48,1,0,0,0,50,70,1,0,0,0,51,58,3,14,7,0,52,54,7,0,0,0,53,
  	52,1,0,0,0,54,55,1,0,0,0,55,53,1,0,0,0,55,56,1,0,0,0,56,59,1,0,0,0,57,
  	59,5,0,0,1,58,53,1,0,0,0,58,57,1,0,0,0,59,70,1,0,0,0,60,67,3,16,8,0,61,
  	63,7,0,0,0,62,61,1,0,0,0,63,64,1,0,0,0,64,62,1,0,0,0,64,65,1,0,0,0,65,
  	68,1,0,0,0,66,68,5,0,0,1,67,62,1,0,0,0,67,66,1,0,0,0,68,70,1,0,0,0,69,
  	33,1,0,0,0,69,42,1,0,0,0,69,51,1,0,0,0,69,60,1,0,0,0,70,5,1,0,0,0,71,
  	72,6,3,-1,0,72,79,5,14,0,0,73,79,5,13,0,0,74,75,5,7,0,0,75,76,3,6,3,0,
  	76,77,5,8,0,0,77,79,1,0,0,0,78,71,1,0,0,0,78,73,1,0,0,0,78,74,1,0,0,0,
  	79,91,1,0,0,0,80,81,10,4,0,0,81,82,7,1,0,0,82,90,3,6,3,5,83,84,10,3,0,
  	0,84,85,7,2,0,0,85,90,3,6,3,4,86,87,10,2,0,0,87,88,7,3,0,0,88,90,3,6,
  	3,3,89,80,1,0,0,0,89,83,1,0,0,0,89,86,1,0,0,0,90,93,1,0,0,0,91,89,1,0,
  	0,0,91,92,1,0,0,0,92,7,1,0,0,0,93,91,1,0,0,0,94,95,5,9,0,0,95,96,5,20,
  	0,0,96,97,3,2,1,0,97,98,5,10,0,0,98,9,1,0,0,0,99,100,5,1,0,0,100,101,
  	5,7,0,0,101,102,3,6,3,0,102,103,5,8,0,0,103,104,3,8,4,0,104,114,1,0,0,
  	0,105,106,5,1,0,0,106,107,5,7,0,0,107,108,3,6,3,0,108,109,5,8,0,0,109,
  	110,3,8,4,0,110,111,5,2,0,0,111,112,3,8,4,0,112,114,1,0,0,0,113,99,1,
  	0,0,0,113,105,1,0,0,0,114,11,1,0,0,0,115,116,5,14,0,0,116,117,5,11,0,
  	0,117,118,3,6,3,0,118,13,1,0,0,0,119,120,5,3,0,0,120,121,5,7,0,0,121,
  	122,3,6,3,0,122,123,5,8,0,0,123,124,3,8,4,0,124,15,1,0,0,0,125,126,5,
  	4,0,0,126,127,5,7,0,0,127,128,3,6,3,0,128,129,5,8,0,0,129,17,1,0,0,0,
  	15,22,30,37,40,46,49,55,58,64,67,69,78,89,91,113
  };
  staticData->serializedATN = antlr4::atn::SerializedATNView(serializedATNSegment, sizeof(serializedATNSegment) / sizeof(serializedATNSegment[0]));

  antlr4::atn::ATNDeserializer deserializer;
  staticData->atn = deserializer.deserialize(staticData->serializedATN);

  const size_t count = staticData->atn->getNumberOfDecisions();
  staticData->decisionToDFA.reserve(count);
  for (size_t i = 0; i < count; i++) { 
    staticData->decisionToDFA.emplace_back(staticData->atn->getDecisionState(i), i);
  }
  langparserParserStaticData = std::move(staticData);
}

}

LangParser::LangParser(TokenStream *input) : LangParser(input, antlr4::atn::ParserATNSimulatorOptions()) {}

LangParser::LangParser(TokenStream *input, const antlr4::atn::ParserATNSimulatorOptions &options) : Parser(input) {
  LangParser::initialize();
  _interpreter = new atn::ParserATNSimulator(this, *langparserParserStaticData->atn, langparserParserStaticData->decisionToDFA, langparserParserStaticData->sharedContextCache, options);
}

LangParser::~LangParser() {
  delete _interpreter;
}

const atn::ATN& LangParser::getATN() const {
  return *langparserParserStaticData->atn;
}

std::string LangParser::getGrammarFileName() const {
  return "LangParser.g4";
}

const std::vector<std::string>& LangParser::getRuleNames() const {
  return langparserParserStaticData->ruleNames;
}

const dfa::Vocabulary& LangParser::getVocabulary() const {
  return langparserParserStaticData->vocabulary;
}

antlr4::atn::SerializedATNView LangParser::getSerializedATN() const {
  return langparserParserStaticData->serializedATN;
}


//----------------- ProgContext ------------------------------------------------------------------

LangParser::ProgContext::ProgContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}

std::vector<LangParser::StmtContext *> LangParser::ProgContext::stmt() {
  return getRuleContexts<LangParser::StmtContext>();
}

LangParser::StmtContext* LangParser::ProgContext::stmt(size_t i) {
  return getRuleContext<LangParser::StmtContext>(i);
}

tree::TerminalNode* LangParser::ProgContext::EOF() {
  return getToken(LangParser::EOF, 0);
}


size_t LangParser::ProgContext::getRuleIndex() const {
  return LangParser::RuleProg;
}


std::any LangParser::ProgContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitProg(this);
  else
    return visitor->visitChildren(this);
}

LangParser::ProgContext* LangParser::prog() {
  ProgContext *_localctx = _tracker.createInstance<ProgContext>(_ctx, getState());
  enterRule(_localctx, 0, LangParser::RuleProg);
  size_t _la = 0;

#if __cplusplus > 201703L
  auto onExit = finally([=, this] {
#else
  auto onExit = finally([=] {
#endif
    exitRule();
  });
  try {
    enterOuterAlt(_localctx, 1);
    setState(18);
    stmt();
    setState(22);
    _errHandler->sync(this);
    _la = _input->LA(1);
    while ((((_la & ~ 0x3fULL) == 0) &&
      ((1ULL << _la) & 16410) != 0)) {
      setState(19);
      stmt();
      setState(24);
      _errHandler->sync(this);
      _la = _input->LA(1);
    }
    setState(25);
    match(LangParser::EOF);
   
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }

  return _localctx;
}

//----------------- StmtListContext ------------------------------------------------------------------

LangParser::StmtListContext::StmtListContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}

std::vector<LangParser::StmtContext *> LangParser::StmtListContext::stmt() {
  return getRuleContexts<LangParser::StmtContext>();
}

LangParser::StmtContext* LangParser::StmtListContext::stmt(size_t i) {
  return getRuleContext<LangParser::StmtContext>(i);
}


size_t LangParser::StmtListContext::getRuleIndex() const {
  return LangParser::RuleStmtList;
}


std::any LangParser::StmtListContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitStmtList(this);
  else
    return visitor->visitChildren(this);
}

LangParser::StmtListContext* LangParser::stmtList() {
  StmtListContext *_localctx = _tracker.createInstance<StmtListContext>(_ctx, getState());
  enterRule(_localctx, 2, LangParser::RuleStmtList);
  size_t _la = 0;

#if __cplusplus > 201703L
  auto onExit = finally([=, this] {
#else
  auto onExit = finally([=] {
#endif
    exitRule();
  });
  try {
    enterOuterAlt(_localctx, 1);
    setState(30);
    _errHandler->sync(this);
    _la = _input->LA(1);
    while ((((_la & ~ 0x3fULL) == 0) &&
      ((1ULL << _la) & 16410) != 0)) {
      setState(27);
      stmt();
      setState(32);
      _errHandler->sync(this);
      _la = _input->LA(1);
    }
   
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }

  return _localctx;
}

//----------------- StmtContext ------------------------------------------------------------------

LangParser::StmtContext::StmtContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}

LangParser::AssignStmtContext* LangParser::StmtContext::assignStmt() {
  return getRuleContext<LangParser::AssignStmtContext>(0);
}

tree::TerminalNode* LangParser::StmtContext::EOF() {
  return getToken(LangParser::EOF, 0);
}

std::vector<tree::TerminalNode *> LangParser::StmtContext::EOL() {
  return getTokens(LangParser::EOL);
}

tree::TerminalNode* LangParser::StmtContext::EOL(size_t i) {
  return getToken(LangParser::EOL, i);
}

std::vector<tree::TerminalNode *> LangParser::StmtContext::SEMICOLON() {
  return getTokens(LangParser::SEMICOLON);
}

tree::TerminalNode* LangParser::StmtContext::SEMICOLON(size_t i) {
  return getToken(LangParser::SEMICOLON, i);
}

LangParser::IfStmtContext* LangParser::StmtContext::ifStmt() {
  return getRuleContext<LangParser::IfStmtContext>(0);
}

LangParser::WhileStmtContext* LangParser::StmtContext::whileStmt() {
  return getRuleContext<LangParser::WhileStmtContext>(0);
}

LangParser::PrintStmtContext* LangParser::StmtContext::printStmt() {
  return getRuleContext<LangParser::PrintStmtContext>(0);
}


size_t LangParser::StmtContext::getRuleIndex() const {
  return LangParser::RuleStmt;
}


std::any LangParser::StmtContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitStmt(this);
  else
    return visitor->visitChildren(this);
}

LangParser::StmtContext* LangParser::stmt() {
  StmtContext *_localctx = _tracker.createInstance<StmtContext>(_ctx, getState());
  enterRule(_localctx, 4, LangParser::RuleStmt);
  size_t _la = 0;

#if __cplusplus > 201703L
  auto onExit = finally([=, this] {
#else
  auto onExit = finally([=] {
#endif
    exitRule();
  });
  try {
    setState(69);
    _errHandler->sync(this);
    switch (_input->LA(1)) {
      case LangParser::IDENTIFIER: {
        enterOuterAlt(_localctx, 1);
        setState(33);
        assignStmt();
        setState(40);
        _errHandler->sync(this);
        switch (_input->LA(1)) {
          case LangParser::SEMICOLON:
          case LangParser::EOL: {
            setState(35); 
            _errHandler->sync(this);
            _la = _input->LA(1);
            do {
              setState(34);
              _la = _input->LA(1);
              if (!(_la == LangParser::SEMICOLON

              || _la == LangParser::EOL)) {
              _errHandler->recoverInline(this);
              }
              else {
                _errHandler->reportMatch(this);
                consume();
              }
              setState(37); 
              _errHandler->sync(this);
              _la = _input->LA(1);
            } while (_la == LangParser::SEMICOLON

            || _la == LangParser::EOL);
            break;
          }

          case LangParser::EOF: {
            setState(39);
            match(LangParser::EOF);
            break;
          }

        default:
          throw NoViableAltException(this);
        }
        break;
      }

      case LangParser::IF: {
        enterOuterAlt(_localctx, 2);
        setState(42);
        ifStmt();
        setState(49);
        _errHandler->sync(this);
        switch (_input->LA(1)) {
          case LangParser::SEMICOLON:
          case LangParser::EOL: {
            setState(44); 
            _errHandler->sync(this);
            _la = _input->LA(1);
            do {
              setState(43);
              _la = _input->LA(1);
              if (!(_la == LangParser::SEMICOLON

              || _la == LangParser::EOL)) {
              _errHandler->recoverInline(this);
              }
              else {
                _errHandler->reportMatch(this);
                consume();
              }
              setState(46); 
              _errHandler->sync(this);
              _la = _input->LA(1);
            } while (_la == LangParser::SEMICOLON

            || _la == LangParser::EOL);
            break;
          }

          case LangParser::EOF: {
            setState(48);
            match(LangParser::EOF);
            break;
          }

        default:
          throw NoViableAltException(this);
        }
        break;
      }

      case LangParser::WHILE: {
        enterOuterAlt(_localctx, 3);
        setState(51);
        whileStmt();
        setState(58);
        _errHandler->sync(this);
        switch (_input->LA(1)) {
          case LangParser::SEMICOLON:
          case LangParser::EOL: {
            setState(53); 
            _errHandler->sync(this);
            _la = _input->LA(1);
            do {
              setState(52);
              _la = _input->LA(1);
              if (!(_la == LangParser::SEMICOLON

              || _la == LangParser::EOL)) {
              _errHandler->recoverInline(this);
              }
              else {
                _errHandler->reportMatch(this);
                consume();
              }
              setState(55); 
              _errHandler->sync(this);
              _la = _input->LA(1);
            } while (_la == LangParser::SEMICOLON

            || _la == LangParser::EOL);
            break;
          }

          case LangParser::EOF: {
            setState(57);
            match(LangParser::EOF);
            break;
          }

        default:
          throw NoViableAltException(this);
        }
        break;
      }

      case LangParser::PRINT: {
        enterOuterAlt(_localctx, 4);
        setState(60);
        printStmt();
        setState(67);
        _errHandler->sync(this);
        switch (_input->LA(1)) {
          case LangParser::SEMICOLON:
          case LangParser::EOL: {
            setState(62); 
            _errHandler->sync(this);
            _la = _input->LA(1);
            do {
              setState(61);
              _la = _input->LA(1);
              if (!(_la == LangParser::SEMICOLON

              || _la == LangParser::EOL)) {
              _errHandler->recoverInline(this);
              }
              else {
                _errHandler->reportMatch(this);
                consume();
              }
              setState(64); 
              _errHandler->sync(this);
              _la = _input->LA(1);
            } while (_la == LangParser::SEMICOLON

            || _la == LangParser::EOL);
            break;
          }

          case LangParser::EOF: {
            setState(66);
            match(LangParser::EOF);
            break;
          }

        default:
          throw NoViableAltException(this);
        }
        break;
      }

    default:
      throw NoViableAltException(this);
    }
   
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }

  return _localctx;
}

//----------------- ExprContext ------------------------------------------------------------------

LangParser::ExprContext::ExprContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}


size_t LangParser::ExprContext::getRuleIndex() const {
  return LangParser::RuleExpr;
}

void LangParser::ExprContext::copyFrom(ExprContext *ctx) {
  ParserRuleContext::copyFrom(ctx);
}

//----------------- ExprAddSubContext ------------------------------------------------------------------

std::vector<LangParser::ExprContext *> LangParser::ExprAddSubContext::expr() {
  return getRuleContexts<LangParser::ExprContext>();
}

LangParser::ExprContext* LangParser::ExprAddSubContext::expr(size_t i) {
  return getRuleContext<LangParser::ExprContext>(i);
}

tree::TerminalNode* LangParser::ExprAddSubContext::PLUS() {
  return getToken(LangParser::PLUS, 0);
}

tree::TerminalNode* LangParser::ExprAddSubContext::MINUS() {
  return getToken(LangParser::MINUS, 0);
}

LangParser::ExprAddSubContext::ExprAddSubContext(ExprContext *ctx) { copyFrom(ctx); }


std::any LangParser::ExprAddSubContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitExprAddSub(this);
  else
    return visitor->visitChildren(this);
}
//----------------- ExprIdentifierContext ------------------------------------------------------------------

tree::TerminalNode* LangParser::ExprIdentifierContext::IDENTIFIER() {
  return getToken(LangParser::IDENTIFIER, 0);
}

LangParser::ExprIdentifierContext::ExprIdentifierContext(ExprContext *ctx) { copyFrom(ctx); }


std::any LangParser::ExprIdentifierContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitExprIdentifier(this);
  else
    return visitor->visitChildren(this);
}
//----------------- ExprMulDivContext ------------------------------------------------------------------

std::vector<LangParser::ExprContext *> LangParser::ExprMulDivContext::expr() {
  return getRuleContexts<LangParser::ExprContext>();
}

LangParser::ExprContext* LangParser::ExprMulDivContext::expr(size_t i) {
  return getRuleContext<LangParser::ExprContext>(i);
}

tree::TerminalNode* LangParser::ExprMulDivContext::MUL() {
  return getToken(LangParser::MUL, 0);
}

tree::TerminalNode* LangParser::ExprMulDivContext::DIV() {
  return getToken(LangParser::DIV, 0);
}

LangParser::ExprMulDivContext::ExprMulDivContext(ExprContext *ctx) { copyFrom(ctx); }


std::any LangParser::ExprMulDivContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitExprMulDiv(this);
  else
    return visitor->visitChildren(this);
}
//----------------- ExprCompareContext ------------------------------------------------------------------

std::vector<LangParser::ExprContext *> LangParser::ExprCompareContext::expr() {
  return getRuleContexts<LangParser::ExprContext>();
}

LangParser::ExprContext* LangParser::ExprCompareContext::expr(size_t i) {
  return getRuleContext<LangParser::ExprContext>(i);
}

tree::TerminalNode* LangParser::ExprCompareContext::EQUALS() {
  return getToken(LangParser::EQUALS, 0);
}

tree::TerminalNode* LangParser::ExprCompareContext::NOT_EQUALS() {
  return getToken(LangParser::NOT_EQUALS, 0);
}

LangParser::ExprCompareContext::ExprCompareContext(ExprContext *ctx) { copyFrom(ctx); }


std::any LangParser::ExprCompareContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitExprCompare(this);
  else
    return visitor->visitChildren(this);
}
//----------------- ExprParenthesesContext ------------------------------------------------------------------

tree::TerminalNode* LangParser::ExprParenthesesContext::L_PAREN() {
  return getToken(LangParser::L_PAREN, 0);
}

LangParser::ExprContext* LangParser::ExprParenthesesContext::expr() {
  return getRuleContext<LangParser::ExprContext>(0);
}

tree::TerminalNode* LangParser::ExprParenthesesContext::R_PAREN() {
  return getToken(LangParser::R_PAREN, 0);
}

LangParser::ExprParenthesesContext::ExprParenthesesContext(ExprContext *ctx) { copyFrom(ctx); }


std::any LangParser::ExprParenthesesContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitExprParentheses(this);
  else
    return visitor->visitChildren(this);
}
//----------------- ExprDecimalLiteralContext ------------------------------------------------------------------

tree::TerminalNode* LangParser::ExprDecimalLiteralContext::DECIMAL_LITERAL() {
  return getToken(LangParser::DECIMAL_LITERAL, 0);
}

LangParser::ExprDecimalLiteralContext::ExprDecimalLiteralContext(ExprContext *ctx) { copyFrom(ctx); }


std::any LangParser::ExprDecimalLiteralContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitExprDecimalLiteral(this);
  else
    return visitor->visitChildren(this);
}

LangParser::ExprContext* LangParser::expr() {
   return expr(0);
}

LangParser::ExprContext* LangParser::expr(int precedence) {
  ParserRuleContext *parentContext = _ctx;
  size_t parentState = getState();
  LangParser::ExprContext *_localctx = _tracker.createInstance<ExprContext>(_ctx, parentState);
  LangParser::ExprContext *previousContext = _localctx;
  (void)previousContext; // Silence compiler, in case the context is not used by generated code.
  size_t startState = 6;
  enterRecursionRule(_localctx, 6, LangParser::RuleExpr, precedence);

    size_t _la = 0;

#if __cplusplus > 201703L
  auto onExit = finally([=, this] {
#else
  auto onExit = finally([=] {
#endif
    unrollRecursionContexts(parentContext);
  });
  try {
    size_t alt;
    enterOuterAlt(_localctx, 1);
    setState(78);
    _errHandler->sync(this);
    switch (_input->LA(1)) {
      case LangParser::IDENTIFIER: {
        _localctx = _tracker.createInstance<ExprIdentifierContext>(_localctx);
        _ctx = _localctx;
        previousContext = _localctx;

        setState(72);
        match(LangParser::IDENTIFIER);
        break;
      }

      case LangParser::DECIMAL_LITERAL: {
        _localctx = _tracker.createInstance<ExprDecimalLiteralContext>(_localctx);
        _ctx = _localctx;
        previousContext = _localctx;
        setState(73);
        match(LangParser::DECIMAL_LITERAL);
        break;
      }

      case LangParser::L_PAREN: {
        _localctx = _tracker.createInstance<ExprParenthesesContext>(_localctx);
        _ctx = _localctx;
        previousContext = _localctx;
        setState(74);
        match(LangParser::L_PAREN);
        setState(75);
        expr(0);
        setState(76);
        match(LangParser::R_PAREN);
        break;
      }

    default:
      throw NoViableAltException(this);
    }
    _ctx->stop = _input->LT(-1);
    setState(91);
    _errHandler->sync(this);
    alt = getInterpreter<atn::ParserATNSimulator>()->adaptivePredict(_input, 13, _ctx);
    while (alt != 2 && alt != atn::ATN::INVALID_ALT_NUMBER) {
      if (alt == 1) {
        if (!_parseListeners.empty())
          triggerExitRuleEvent();
        previousContext = _localctx;
        setState(89);
        _errHandler->sync(this);
        switch (getInterpreter<atn::ParserATNSimulator>()->adaptivePredict(_input, 12, _ctx)) {
        case 1: {
          auto newContext = _tracker.createInstance<ExprCompareContext>(_tracker.createInstance<ExprContext>(parentContext, parentState));
          _localctx = newContext;
          pushNewRecursionContext(newContext, startState, RuleExpr);
          setState(80);

          if (!(precpred(_ctx, 4))) throw FailedPredicateException(this, "precpred(_ctx, 4)");
          setState(81);
          antlrcpp::downCast<ExprCompareContext *>(_localctx)->op = _input->LT(1);
          _la = _input->LA(1);
          if (!(_la == LangParser::EQUALS

          || _la == LangParser::NOT_EQUALS)) {
            antlrcpp::downCast<ExprCompareContext *>(_localctx)->op = _errHandler->recoverInline(this);
          }
          else {
            _errHandler->reportMatch(this);
            consume();
          }
          setState(82);
          expr(5);
          break;
        }

        case 2: {
          auto newContext = _tracker.createInstance<ExprMulDivContext>(_tracker.createInstance<ExprContext>(parentContext, parentState));
          _localctx = newContext;
          pushNewRecursionContext(newContext, startState, RuleExpr);
          setState(83);

          if (!(precpred(_ctx, 3))) throw FailedPredicateException(this, "precpred(_ctx, 3)");
          setState(84);
          antlrcpp::downCast<ExprMulDivContext *>(_localctx)->op = _input->LT(1);
          _la = _input->LA(1);
          if (!(_la == LangParser::MUL

          || _la == LangParser::DIV)) {
            antlrcpp::downCast<ExprMulDivContext *>(_localctx)->op = _errHandler->recoverInline(this);
          }
          else {
            _errHandler->reportMatch(this);
            consume();
          }
          setState(85);
          expr(4);
          break;
        }

        case 3: {
          auto newContext = _tracker.createInstance<ExprAddSubContext>(_tracker.createInstance<ExprContext>(parentContext, parentState));
          _localctx = newContext;
          pushNewRecursionContext(newContext, startState, RuleExpr);
          setState(86);

          if (!(precpred(_ctx, 2))) throw FailedPredicateException(this, "precpred(_ctx, 2)");
          setState(87);
          antlrcpp::downCast<ExprAddSubContext *>(_localctx)->op = _input->LT(1);
          _la = _input->LA(1);
          if (!(_la == LangParser::PLUS

          || _la == LangParser::MINUS)) {
            antlrcpp::downCast<ExprAddSubContext *>(_localctx)->op = _errHandler->recoverInline(this);
          }
          else {
            _errHandler->reportMatch(this);
            consume();
          }
          setState(88);
          expr(3);
          break;
        }

        default:
          break;
        } 
      }
      setState(93);
      _errHandler->sync(this);
      alt = getInterpreter<atn::ParserATNSimulator>()->adaptivePredict(_input, 13, _ctx);
    }
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }
  return _localctx;
}

//----------------- BlockContext ------------------------------------------------------------------

LangParser::BlockContext::BlockContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}

tree::TerminalNode* LangParser::BlockContext::L_CURLY() {
  return getToken(LangParser::L_CURLY, 0);
}

tree::TerminalNode* LangParser::BlockContext::EOL() {
  return getToken(LangParser::EOL, 0);
}

LangParser::StmtListContext* LangParser::BlockContext::stmtList() {
  return getRuleContext<LangParser::StmtListContext>(0);
}

tree::TerminalNode* LangParser::BlockContext::R_CURLY() {
  return getToken(LangParser::R_CURLY, 0);
}


size_t LangParser::BlockContext::getRuleIndex() const {
  return LangParser::RuleBlock;
}


std::any LangParser::BlockContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitBlock(this);
  else
    return visitor->visitChildren(this);
}

LangParser::BlockContext* LangParser::block() {
  BlockContext *_localctx = _tracker.createInstance<BlockContext>(_ctx, getState());
  enterRule(_localctx, 8, LangParser::RuleBlock);

#if __cplusplus > 201703L
  auto onExit = finally([=, this] {
#else
  auto onExit = finally([=] {
#endif
    exitRule();
  });
  try {
    enterOuterAlt(_localctx, 1);
    setState(94);
    match(LangParser::L_CURLY);
    setState(95);
    match(LangParser::EOL);
    setState(96);
    stmtList();
    setState(97);
    match(LangParser::R_CURLY);
   
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }

  return _localctx;
}

//----------------- IfStmtContext ------------------------------------------------------------------

LangParser::IfStmtContext::IfStmtContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}


size_t LangParser::IfStmtContext::getRuleIndex() const {
  return LangParser::RuleIfStmt;
}

void LangParser::IfStmtContext::copyFrom(IfStmtContext *ctx) {
  ParserRuleContext::copyFrom(ctx);
}

//----------------- IfStmtIfElseContext ------------------------------------------------------------------

tree::TerminalNode* LangParser::IfStmtIfElseContext::IF() {
  return getToken(LangParser::IF, 0);
}

tree::TerminalNode* LangParser::IfStmtIfElseContext::L_PAREN() {
  return getToken(LangParser::L_PAREN, 0);
}

tree::TerminalNode* LangParser::IfStmtIfElseContext::R_PAREN() {
  return getToken(LangParser::R_PAREN, 0);
}

tree::TerminalNode* LangParser::IfStmtIfElseContext::ELSE() {
  return getToken(LangParser::ELSE, 0);
}

LangParser::ExprContext* LangParser::IfStmtIfElseContext::expr() {
  return getRuleContext<LangParser::ExprContext>(0);
}

std::vector<LangParser::BlockContext *> LangParser::IfStmtIfElseContext::block() {
  return getRuleContexts<LangParser::BlockContext>();
}

LangParser::BlockContext* LangParser::IfStmtIfElseContext::block(size_t i) {
  return getRuleContext<LangParser::BlockContext>(i);
}

LangParser::IfStmtIfElseContext::IfStmtIfElseContext(IfStmtContext *ctx) { copyFrom(ctx); }


std::any LangParser::IfStmtIfElseContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitIfStmtIfElse(this);
  else
    return visitor->visitChildren(this);
}
//----------------- IfStmtIfContext ------------------------------------------------------------------

tree::TerminalNode* LangParser::IfStmtIfContext::IF() {
  return getToken(LangParser::IF, 0);
}

tree::TerminalNode* LangParser::IfStmtIfContext::L_PAREN() {
  return getToken(LangParser::L_PAREN, 0);
}

tree::TerminalNode* LangParser::IfStmtIfContext::R_PAREN() {
  return getToken(LangParser::R_PAREN, 0);
}

LangParser::BlockContext* LangParser::IfStmtIfContext::block() {
  return getRuleContext<LangParser::BlockContext>(0);
}

LangParser::ExprContext* LangParser::IfStmtIfContext::expr() {
  return getRuleContext<LangParser::ExprContext>(0);
}

LangParser::IfStmtIfContext::IfStmtIfContext(IfStmtContext *ctx) { copyFrom(ctx); }


std::any LangParser::IfStmtIfContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitIfStmtIf(this);
  else
    return visitor->visitChildren(this);
}
LangParser::IfStmtContext* LangParser::ifStmt() {
  IfStmtContext *_localctx = _tracker.createInstance<IfStmtContext>(_ctx, getState());
  enterRule(_localctx, 10, LangParser::RuleIfStmt);

#if __cplusplus > 201703L
  auto onExit = finally([=, this] {
#else
  auto onExit = finally([=] {
#endif
    exitRule();
  });
  try {
    setState(113);
    _errHandler->sync(this);
    switch (getInterpreter<atn::ParserATNSimulator>()->adaptivePredict(_input, 14, _ctx)) {
    case 1: {
      _localctx = _tracker.createInstance<LangParser::IfStmtIfContext>(_localctx);
      enterOuterAlt(_localctx, 1);
      setState(99);
      match(LangParser::IF);
      setState(100);
      match(LangParser::L_PAREN);
      setState(101);
      antlrcpp::downCast<IfStmtIfContext *>(_localctx)->cond = expr(0);
      setState(102);
      match(LangParser::R_PAREN);
      setState(103);
      block();
      break;
    }

    case 2: {
      _localctx = _tracker.createInstance<LangParser::IfStmtIfElseContext>(_localctx);
      enterOuterAlt(_localctx, 2);
      setState(105);
      match(LangParser::IF);
      setState(106);
      match(LangParser::L_PAREN);
      setState(107);
      antlrcpp::downCast<IfStmtIfElseContext *>(_localctx)->cond = expr(0);
      setState(108);
      match(LangParser::R_PAREN);
      setState(109);
      antlrcpp::downCast<IfStmtIfElseContext *>(_localctx)->ifBlock = block();
      setState(110);
      match(LangParser::ELSE);
      setState(111);
      antlrcpp::downCast<IfStmtIfElseContext *>(_localctx)->elseBlock = block();
      break;
    }

    default:
      break;
    }
   
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }

  return _localctx;
}

//----------------- AssignStmtContext ------------------------------------------------------------------

LangParser::AssignStmtContext::AssignStmtContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}

tree::TerminalNode* LangParser::AssignStmtContext::IDENTIFIER() {
  return getToken(LangParser::IDENTIFIER, 0);
}

tree::TerminalNode* LangParser::AssignStmtContext::ASSIGN() {
  return getToken(LangParser::ASSIGN, 0);
}

LangParser::ExprContext* LangParser::AssignStmtContext::expr() {
  return getRuleContext<LangParser::ExprContext>(0);
}


size_t LangParser::AssignStmtContext::getRuleIndex() const {
  return LangParser::RuleAssignStmt;
}


std::any LangParser::AssignStmtContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitAssignStmt(this);
  else
    return visitor->visitChildren(this);
}

LangParser::AssignStmtContext* LangParser::assignStmt() {
  AssignStmtContext *_localctx = _tracker.createInstance<AssignStmtContext>(_ctx, getState());
  enterRule(_localctx, 12, LangParser::RuleAssignStmt);

#if __cplusplus > 201703L
  auto onExit = finally([=, this] {
#else
  auto onExit = finally([=] {
#endif
    exitRule();
  });
  try {
    enterOuterAlt(_localctx, 1);
    setState(115);
    match(LangParser::IDENTIFIER);
    setState(116);
    match(LangParser::ASSIGN);
    setState(117);
    expr(0);
   
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }

  return _localctx;
}

//----------------- WhileStmtContext ------------------------------------------------------------------

LangParser::WhileStmtContext::WhileStmtContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}

tree::TerminalNode* LangParser::WhileStmtContext::WHILE() {
  return getToken(LangParser::WHILE, 0);
}

tree::TerminalNode* LangParser::WhileStmtContext::L_PAREN() {
  return getToken(LangParser::L_PAREN, 0);
}

tree::TerminalNode* LangParser::WhileStmtContext::R_PAREN() {
  return getToken(LangParser::R_PAREN, 0);
}

LangParser::BlockContext* LangParser::WhileStmtContext::block() {
  return getRuleContext<LangParser::BlockContext>(0);
}

LangParser::ExprContext* LangParser::WhileStmtContext::expr() {
  return getRuleContext<LangParser::ExprContext>(0);
}


size_t LangParser::WhileStmtContext::getRuleIndex() const {
  return LangParser::RuleWhileStmt;
}


std::any LangParser::WhileStmtContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitWhileStmt(this);
  else
    return visitor->visitChildren(this);
}

LangParser::WhileStmtContext* LangParser::whileStmt() {
  WhileStmtContext *_localctx = _tracker.createInstance<WhileStmtContext>(_ctx, getState());
  enterRule(_localctx, 14, LangParser::RuleWhileStmt);

#if __cplusplus > 201703L
  auto onExit = finally([=, this] {
#else
  auto onExit = finally([=] {
#endif
    exitRule();
  });
  try {
    enterOuterAlt(_localctx, 1);
    setState(119);
    match(LangParser::WHILE);
    setState(120);
    match(LangParser::L_PAREN);
    setState(121);
    antlrcpp::downCast<WhileStmtContext *>(_localctx)->cond = expr(0);
    setState(122);
    match(LangParser::R_PAREN);
    setState(123);
    block();
   
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }

  return _localctx;
}

//----------------- PrintStmtContext ------------------------------------------------------------------

LangParser::PrintStmtContext::PrintStmtContext(ParserRuleContext *parent, size_t invokingState)
  : ParserRuleContext(parent, invokingState) {
}

tree::TerminalNode* LangParser::PrintStmtContext::PRINT() {
  return getToken(LangParser::PRINT, 0);
}

tree::TerminalNode* LangParser::PrintStmtContext::L_PAREN() {
  return getToken(LangParser::L_PAREN, 0);
}

LangParser::ExprContext* LangParser::PrintStmtContext::expr() {
  return getRuleContext<LangParser::ExprContext>(0);
}

tree::TerminalNode* LangParser::PrintStmtContext::R_PAREN() {
  return getToken(LangParser::R_PAREN, 0);
}


size_t LangParser::PrintStmtContext::getRuleIndex() const {
  return LangParser::RulePrintStmt;
}


std::any LangParser::PrintStmtContext::accept(tree::ParseTreeVisitor *visitor) {
  if (auto parserVisitor = dynamic_cast<LangParserVisitor*>(visitor))
    return parserVisitor->visitPrintStmt(this);
  else
    return visitor->visitChildren(this);
}

LangParser::PrintStmtContext* LangParser::printStmt() {
  PrintStmtContext *_localctx = _tracker.createInstance<PrintStmtContext>(_ctx, getState());
  enterRule(_localctx, 16, LangParser::RulePrintStmt);

#if __cplusplus > 201703L
  auto onExit = finally([=, this] {
#else
  auto onExit = finally([=] {
#endif
    exitRule();
  });
  try {
    enterOuterAlt(_localctx, 1);
    setState(125);
    match(LangParser::PRINT);
    setState(126);
    match(LangParser::L_PAREN);
    setState(127);
    expr(0);
    setState(128);
    match(LangParser::R_PAREN);
   
  }
  catch (RecognitionException &e) {
    _errHandler->reportError(this, e);
    _localctx->exception = std::current_exception();
    _errHandler->recover(this, _localctx->exception);
  }

  return _localctx;
}

bool LangParser::sempred(RuleContext *context, size_t ruleIndex, size_t predicateIndex) {
  switch (ruleIndex) {
    case 3: return exprSempred(antlrcpp::downCast<ExprContext *>(context), predicateIndex);

  default:
    break;
  }
  return true;
}

bool LangParser::exprSempred(ExprContext *_localctx, size_t predicateIndex) {
  switch (predicateIndex) {
    case 0: return precpred(_ctx, 4);
    case 1: return precpred(_ctx, 3);
    case 2: return precpred(_ctx, 2);

  default:
    break;
  }
  return true;
}

void LangParser::initialize() {
#if ANTLR4_USE_THREAD_LOCAL_CACHE
  langparserParserInitialize();
#else
  ::antlr4::internal::call_once(langparserParserOnceFlag, langparserParserInitialize);
#endif
}
