ANTLR4_CPP_RUNTIME_DIR := antlr4/runtime/Cpp
ANTLR4_CPP_RUNTIME_STATIC_LIBRARY := $(ANTLR4_CPP_RUNTIME_DIR)/build/runtime/libantlr4-runtime.a

GEN_DIR := src/gen

TARGET   := lang.elf
CFLAGS   := -Wall -std=c++23 -g
INCLUDES := -Isrc -I$(ANTLR4_CPP_RUNTIME_DIR)/runtime/src

SRCS := $(shell find src/ -type f -name '*.cpp')
HDRS := $(shell find src/ -type f -name '*.h')
OBJS := $(SRCS:.cpp=.o)

FILES_TO_LINT := $(SRCS) $(HDRS)

.PHONY: build
build: $(TARGET)

$(TARGET): $(OBJS) 
	$(CXX) $(INCLUDES) $(CFLAGS) $^ $(ANTLR4_CPP_RUNTIME_STATIC_LIBRARY) -o lang.elf

%.o: %.cpp
	$(CXX) $(INCLUDES) $(CFLAGS) -c -o $@ $<

.PHONY: generate
generate: LangParser.g4 LangLexer.g4
	rm -rf $(GEN_DIR)
	java -jar antlr.jar -Dlanguage=Cpp -visitor -no-listener -o $(GEN_DIR) $^

.PHONY: clang-format
clang-format:
	clang-format -i $(FILES_TO_LINT)

.PHONY: clean
clean:
	rm $(OBJS) $(TARGET)
