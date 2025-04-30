
// Generated from LangLexer.g4 by ANTLR 4.13.2

#pragma once


#include "antlr4-runtime.h"




class  LangLexer : public antlr4::Lexer {
public:
  enum {
    IF = 1, ELSE = 2, WHILE = 3, PRINT = 4, EQUALS = 5, NOT_EQUALS = 6, 
    L_PAREN = 7, R_PAREN = 8, L_CURLY = 9, R_CURLY = 10, ASSIGN = 11, SEMICOLON = 12, 
    DECIMAL_LITERAL = 13, IDENTIFIER = 14, PLUS = 15, MINUS = 16, MUL = 17, 
    DIV = 18, WS = 19, EOL = 20
  };

  explicit LangLexer(antlr4::CharStream *input);

  ~LangLexer() override;


  std::string getGrammarFileName() const override;

  const std::vector<std::string>& getRuleNames() const override;

  const std::vector<std::string>& getChannelNames() const override;

  const std::vector<std::string>& getModeNames() const override;

  const antlr4::dfa::Vocabulary& getVocabulary() const override;

  antlr4::atn::SerializedATNView getSerializedATN() const override;

  const antlr4::atn::ATN& getATN() const override;

  // By default the static state used to implement the lexer is lazily initialized during the first
  // call to the constructor. You can call this function if you wish to initialize the static state
  // ahead of time.
  static void initialize();

private:

  // Individual action functions triggered by action() above.

  // Individual semantic predicate functions triggered by sempred() above.

};

