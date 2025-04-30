// Generated from /home/sultanowskii/Projects/itmo/itmo-edu/compilers/hw4/LangLexer.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class LangLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IF=1, ELSE=2, WHILE=3, PRINT=4, EQUALS=5, NOT_EQUALS=6, L_PAREN=7, R_PAREN=8, 
		L_CURLY=9, R_CURLY=10, ASSIGN=11, SEMICOLON=12, DECIMAL_LITERAL=13, IDENTIFIER=14, 
		PLUS=15, MINUS=16, MUL=17, DIV=18, WS=19, EOL=20;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"IF", "ELSE", "WHILE", "PRINT", "EQUALS", "NOT_EQUALS", "L_PAREN", "R_PAREN", 
			"L_CURLY", "R_CURLY", "ASSIGN", "SEMICOLON", "DECIMAL_LITERAL", "IDENTIFIER", 
			"PLUS", "MINUS", "MUL", "DIV", "WS", "EOL"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'else'", "'while'", "'print'", "'=='", "'!='", "'('", 
			"')'", "'{'", "'}'", "'='", "';'", null, null, "'+'", "'-'", "'*'", "'/'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "IF", "ELSE", "WHILE", "PRINT", "EQUALS", "NOT_EQUALS", "L_PAREN", 
			"R_PAREN", "L_CURLY", "R_CURLY", "ASSIGN", "SEMICOLON", "DECIMAL_LITERAL", 
			"IDENTIFIER", "PLUS", "MINUS", "MUL", "DIV", "WS", "EOL"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public LangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "LangLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0014w\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0003\fQ\b\f\u0001"+
		"\f\u0001\f\u0005\fU\b\f\n\f\f\fX\t\f\u0001\f\u0003\f[\b\f\u0001\r\u0001"+
		"\r\u0005\r_\b\r\n\r\f\rb\t\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0004"+
		"\u0012m\b\u0012\u000b\u0012\f\u0012n\u0001\u0012\u0001\u0012\u0001\u0013"+
		"\u0004\u0013t\b\u0013\u000b\u0013\f\u0013u\u0000\u0000\u0014\u0001\u0001"+
		"\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f"+
		"\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f"+
		"\u001f\u0010!\u0011#\u0012%\u0013\'\u0014\u0001\u0000\u0007\u0002\u0000"+
		"++--\u0001\u000019\u0001\u000009\u0003\u0000AZ__az\u0004\u000009AZ__a"+
		"z\u0002\u0000\t\t  \u0002\u0000\n\n\r\r|\u0000\u0001\u0001\u0000\u0000"+
		"\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000"+
		"\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000"+
		"\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000"+
		"\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000"+
		"\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000"+
		"\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000"+
		"\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000"+
		"\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001"+
		"\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000"+
		"\u0000\u0000\u0001)\u0001\u0000\u0000\u0000\u0003,\u0001\u0000\u0000\u0000"+
		"\u00051\u0001\u0000\u0000\u0000\u00077\u0001\u0000\u0000\u0000\t=\u0001"+
		"\u0000\u0000\u0000\u000b@\u0001\u0000\u0000\u0000\rC\u0001\u0000\u0000"+
		"\u0000\u000fE\u0001\u0000\u0000\u0000\u0011G\u0001\u0000\u0000\u0000\u0013"+
		"I\u0001\u0000\u0000\u0000\u0015K\u0001\u0000\u0000\u0000\u0017M\u0001"+
		"\u0000\u0000\u0000\u0019Z\u0001\u0000\u0000\u0000\u001b\\\u0001\u0000"+
		"\u0000\u0000\u001dc\u0001\u0000\u0000\u0000\u001fe\u0001\u0000\u0000\u0000"+
		"!g\u0001\u0000\u0000\u0000#i\u0001\u0000\u0000\u0000%l\u0001\u0000\u0000"+
		"\u0000\'s\u0001\u0000\u0000\u0000)*\u0005i\u0000\u0000*+\u0005f\u0000"+
		"\u0000+\u0002\u0001\u0000\u0000\u0000,-\u0005e\u0000\u0000-.\u0005l\u0000"+
		"\u0000./\u0005s\u0000\u0000/0\u0005e\u0000\u00000\u0004\u0001\u0000\u0000"+
		"\u000012\u0005w\u0000\u000023\u0005h\u0000\u000034\u0005i\u0000\u0000"+
		"45\u0005l\u0000\u000056\u0005e\u0000\u00006\u0006\u0001\u0000\u0000\u0000"+
		"78\u0005p\u0000\u000089\u0005r\u0000\u00009:\u0005i\u0000\u0000:;\u0005"+
		"n\u0000\u0000;<\u0005t\u0000\u0000<\b\u0001\u0000\u0000\u0000=>\u0005"+
		"=\u0000\u0000>?\u0005=\u0000\u0000?\n\u0001\u0000\u0000\u0000@A\u0005"+
		"!\u0000\u0000AB\u0005=\u0000\u0000B\f\u0001\u0000\u0000\u0000CD\u0005"+
		"(\u0000\u0000D\u000e\u0001\u0000\u0000\u0000EF\u0005)\u0000\u0000F\u0010"+
		"\u0001\u0000\u0000\u0000GH\u0005{\u0000\u0000H\u0012\u0001\u0000\u0000"+
		"\u0000IJ\u0005}\u0000\u0000J\u0014\u0001\u0000\u0000\u0000KL\u0005=\u0000"+
		"\u0000L\u0016\u0001\u0000\u0000\u0000MN\u0005;\u0000\u0000N\u0018\u0001"+
		"\u0000\u0000\u0000OQ\u0007\u0000\u0000\u0000PO\u0001\u0000\u0000\u0000"+
		"PQ\u0001\u0000\u0000\u0000QR\u0001\u0000\u0000\u0000RV\u0007\u0001\u0000"+
		"\u0000SU\u0007\u0002\u0000\u0000TS\u0001\u0000\u0000\u0000UX\u0001\u0000"+
		"\u0000\u0000VT\u0001\u0000\u0000\u0000VW\u0001\u0000\u0000\u0000W[\u0001"+
		"\u0000\u0000\u0000XV\u0001\u0000\u0000\u0000Y[\u00050\u0000\u0000ZP\u0001"+
		"\u0000\u0000\u0000ZY\u0001\u0000\u0000\u0000[\u001a\u0001\u0000\u0000"+
		"\u0000\\`\u0007\u0003\u0000\u0000]_\u0007\u0004\u0000\u0000^]\u0001\u0000"+
		"\u0000\u0000_b\u0001\u0000\u0000\u0000`^\u0001\u0000\u0000\u0000`a\u0001"+
		"\u0000\u0000\u0000a\u001c\u0001\u0000\u0000\u0000b`\u0001\u0000\u0000"+
		"\u0000cd\u0005+\u0000\u0000d\u001e\u0001\u0000\u0000\u0000ef\u0005-\u0000"+
		"\u0000f \u0001\u0000\u0000\u0000gh\u0005*\u0000\u0000h\"\u0001\u0000\u0000"+
		"\u0000ij\u0005/\u0000\u0000j$\u0001\u0000\u0000\u0000km\u0007\u0005\u0000"+
		"\u0000lk\u0001\u0000\u0000\u0000mn\u0001\u0000\u0000\u0000nl\u0001\u0000"+
		"\u0000\u0000no\u0001\u0000\u0000\u0000op\u0001\u0000\u0000\u0000pq\u0006"+
		"\u0012\u0000\u0000q&\u0001\u0000\u0000\u0000rt\u0007\u0006\u0000\u0000"+
		"sr\u0001\u0000\u0000\u0000tu\u0001\u0000\u0000\u0000us\u0001\u0000\u0000"+
		"\u0000uv\u0001\u0000\u0000\u0000v(\u0001\u0000\u0000\u0000\u0007\u0000"+
		"PVZ`nu\u0001\u0000\u0001\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}