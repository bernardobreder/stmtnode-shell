package com.stmtnode.lang.cx;

import static com.stmtnode.module.Nodes.joinTokens;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Grammar;
import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.head.ArgumentDeclareCxNode;
import com.stmtnode.lang.cx.head.FunctionCxNode;
import com.stmtnode.lang.cx.head.HeadCxNode;
import com.stmtnode.lang.cx.head.IncludeCxNode;
import com.stmtnode.lang.cx.head.StructCxNode;
import com.stmtnode.lang.cx.head.StructFieldCxNode;
import com.stmtnode.lang.cx.head.UnitCxNode;
import com.stmtnode.lang.cx.stmt.BlockCxNode;
import com.stmtnode.lang.cx.stmt.BreakCxNode;
import com.stmtnode.lang.cx.stmt.ContinueCxNode;
import com.stmtnode.lang.cx.stmt.DeclareArrayCxNode;
import com.stmtnode.lang.cx.stmt.DeclareValueCxNode;
import com.stmtnode.lang.cx.stmt.DeferClearCxNode;
import com.stmtnode.lang.cx.stmt.DeferCxNode;
import com.stmtnode.lang.cx.stmt.ExpCxNode;
import com.stmtnode.lang.cx.stmt.GuardCxNode;
import com.stmtnode.lang.cx.stmt.GuardLetCxNode;
import com.stmtnode.lang.cx.stmt.IfCxNode;
import com.stmtnode.lang.cx.stmt.IfLetCxNode;
import com.stmtnode.lang.cx.stmt.ReturnCxNode;
import com.stmtnode.lang.cx.stmt.StmtCxNode;
import com.stmtnode.lang.cx.stmt.WhileCxNode;
import com.stmtnode.lang.cx.type.CharTypeCxNode;
import com.stmtnode.lang.cx.type.IdTypeCxNode;
import com.stmtnode.lang.cx.type.IntTypeCxNode;
import com.stmtnode.lang.cx.type.PointerTypeCxNode;
import com.stmtnode.lang.cx.type.StructTypeCxNode;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.lang.cx.value.SizeofCxNode;
import com.stmtnode.lang.cx.value.TernaryCxNode;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.lang.cx.value.binary.AndBitCxNode;
import com.stmtnode.lang.cx.value.binary.AndCxNode;
import com.stmtnode.lang.cx.value.binary.AssignCxNode;
import com.stmtnode.lang.cx.value.binary.DivCxNode;
import com.stmtnode.lang.cx.value.binary.EqualCxNode;
import com.stmtnode.lang.cx.value.binary.GreaterCxNode;
import com.stmtnode.lang.cx.value.binary.GreaterEqualCxNode;
import com.stmtnode.lang.cx.value.binary.LeftShiftCxNode;
import com.stmtnode.lang.cx.value.binary.LowerCxNode;
import com.stmtnode.lang.cx.value.binary.LowerEqualCxNode;
import com.stmtnode.lang.cx.value.binary.ModCxNode;
import com.stmtnode.lang.cx.value.binary.MulCxNode;
import com.stmtnode.lang.cx.value.binary.NotEqualCxNode;
import com.stmtnode.lang.cx.value.binary.OrBitCxNode;
import com.stmtnode.lang.cx.value.binary.OrCxNode;
import com.stmtnode.lang.cx.value.binary.RightShiftCxNode;
import com.stmtnode.lang.cx.value.binary.SubCxNode;
import com.stmtnode.lang.cx.value.binary.SumCxNode;
import com.stmtnode.lang.cx.value.primitive.BooleanCxNode;
import com.stmtnode.lang.cx.value.primitive.IdentifierCxNode;
import com.stmtnode.lang.cx.value.primitive.NumberCxNode;
import com.stmtnode.lang.cx.value.primitive.StringCxNode;
import com.stmtnode.lang.cx.value.unary.AddressCxNode;
import com.stmtnode.lang.cx.value.unary.CallCxNode;
import com.stmtnode.lang.cx.value.unary.CastCxNode;
import com.stmtnode.lang.cx.value.unary.GetCxNode;
import com.stmtnode.lang.cx.value.unary.NotCxNode;
import com.stmtnode.lang.cx.value.unary.PosDecCxNode;
import com.stmtnode.lang.cx.value.unary.PosIncCxNode;
import com.stmtnode.lang.cx.value.unary.PreDecCxNode;
import com.stmtnode.lang.cx.value.unary.PreIncCxNode;

public class CxGrammar extends Grammar {

	public CxGrammar(Token[] tokens) {
		super(tokens);
	}

	public UnitCxNode parseUnit() throws SyntaxException {
		List<HeadCxNode> includes = new ArrayList<>();
		while (!eof() && can('#')) {
			includes.add(parsePrecompiler());
		}
		List<HeadCxNode> nodes = new ArrayList<>();
		while (!eof()) {
			nodes.add(parseUnitItem());
		}
		return new UnitCxNode(includes, nodes);
	}

	protected HeadCxNode parsePrecompiler() throws SyntaxException {
		if (is("include")) {
			return parsePreprocessorInclude();
		} else if (is("define")) {
			return parsePreprocessorDefine();
		} else if (is("typedef")) {
			return parsePreprocessorTypedef();
		} else {
			throw error("expected pre-processor");
		}
	}

	protected HeadCxNode parsePreprocessorInclude() throws SyntaxException {
		read("include", "expected include");
		if (can('<')) {
			Token path = parsePath("expected include path");
			read('>', "expected close include");
			return new IncludeCxNode(path, true);
		} else if (isString()) {
			Token path = readString("expected include path");
			return new IncludeCxNode(path, false);
		} else {
			throw error("expected include name");
		}
	}

	protected HeadCxNode parsePreprocessorDefine() throws SyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

	protected HeadCxNode parsePreprocessorTypedef() throws SyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Token parsePath(String message) throws SyntaxException {
		List<Token> tokens = new ArrayList<>();
		tokens.add(readIdentifier(message));
		while (is('/') || is('.') || isIdentifier()) {
			if (is('/')) {
				tokens.add(next());
			} else if (is('.')) {
				tokens.add(next());
			} else {
				tokens.add(readIdentifier(message));
			}
		}
		return joinTokens(tokens);
	}

	protected HeadCxNode parseUnitItem() throws SyntaxException {
		if (is("func")) {
			return parseFunction();
		} else if (is("struct")) {
			return parseStruct();
		} else if (is("let")) {
			return parseDeclareStatic();
		} else {
			throw error("expected unit item");
		}
	}

	protected HeadCxNode parseDeclareStatic() {
		// TODO Auto-generated method stub
		return null;
	}

	protected HeadCxNode parseStruct() throws SyntaxException {
		Token token = read("struct", "expected struct keyword");
		Token name = readIdentifier("expected name of struct");
		read('{', "expected open struct");
		List<StructFieldCxNode> nodes = new ArrayList<>();
		while (!can('}')) {
			nodes.add(parseStructField(nodes));
		}
		return new StructCxNode(token, name, nodes);
	}

	protected StructFieldCxNode parseStructField(List<StructFieldCxNode> nodes) throws SyntaxException {
		TypeCxNode type = parseType();
		Token name = readIdentifier("expected name of field");
		return new StructFieldCxNode(name, type);
	}

	protected StmtCxNode parseDeclare() throws SyntaxException {
		Token token = read("let", "expected let keyword");
		TypeCxNode type = parseType();
		Token name = readIdentifier("expected name of variable");
		if (can('[')) {
			ValueCxNode count = parseValue();
			read(']', "expected close array count");
			return new DeclareArrayCxNode(token, type, name, count);
		}
		ValueCxNode value = null;
		if (can('=')) {
			value = parseValue();
		}
		return new DeclareValueCxNode(token, type, name, value);
	}

	protected StmtCxNode parseReturn() throws SyntaxException {
		Token token = read("return", "expected return keyword");
		ValueCxNode value = parseValue();
		return new ReturnCxNode(token, value);
	}

	protected FunctionCxNode parseFunction() throws SyntaxException {
		Token token = read("func", "expected func keyword");
		TypeCxNode type = parseType();
		Token name = readIdentifier("expected name of function");
		List<ArgumentDeclareCxNode> arguments = readNodes('(', "expected open parameter", ')', "expected close parameter", ',', this::parseFunctionArgument);
		BlockCxNode block = parseBlock();
		return new FunctionCxNode(token, type, name, arguments, block);
	}

	protected ArgumentDeclareCxNode parseFunctionArgument() throws SyntaxException {
		TypeCxNode type = parseType();
		Token name = readIdentifier("expected name of argument");
		return new ArgumentDeclareCxNode(name, type);
	}

	protected TypeCxNode parseType() throws SyntaxException {
		TypeCxNode type = parseTypeLiteral();
		while (can('*')) {
			type = new PointerTypeCxNode(type);
		}
		return type;
	}

	protected TypeCxNode parseTypeLiteral() throws SyntaxException {
		if (can("int")) {
			return IntTypeCxNode.GET;
		} else if (can("char")) {
			return CharTypeCxNode.GET;
		} else if (can("struct")) {
			Token name = readIdentifier("expected name of type");
			return new StructTypeCxNode(name);
		} else if (isIdentifier()) {
			Token name = readIdentifier("expected name of type");
			return new IdTypeCxNode(name);
		} else {
			throw error("expected type");
		}
	}

	protected BlockCxNode parseBlock() throws SyntaxException {
		return new BlockCxNode(readNodes('{', "expected open block", '}', "expected close block", this::parseCommand));
	}

	protected StmtCxNode parseCommand() throws SyntaxException {
		if (is('{')) {
			return parseBlock();
		} else if (is("if")) {
			return parseIf();
		} else if (is("while")) {
			return parseWhile();
		} else if (is("let")) {
			return parseDeclare();
		} else if (is("return")) {
			return parseReturn();
		} else if (is("continue")) {
			return new ContinueCxNode(next());
		} else if (is("break")) {
			return new BreakCxNode(next());
		} else if (is("defer")) {
			return parseDefer();
		} else if (is("guard")) {
			return parseGuard();
		} else {
			return parseExpression();
		}
	}

	protected StmtCxNode parseIf() throws SyntaxException {
		Token token = read("if", "expected if keyword");
		if (is("let")) {
			token = token.join(next());
			TypeCxNode type = parseType();
			Token name = readIdentifier("expected name of variable");
			read('=', "expected = symbol");
			ValueCxNode value = parseValue();
			ValueCxNode cond = null;
			if (can(',')) {
				cond = parseValue();
			}
			StmtCxNode command = parseBlock();
			return new IfLetCxNode(token, type, name, value, cond, command);
		} else {
			ValueCxNode value = parseValue();
			StmtCxNode command = parseBlock();
			return new IfCxNode(token, value, command);
		}
	}

	protected StmtCxNode parseGuard() throws SyntaxException {
		Token token = read("guard", "expected guard keyword");
		if (is("let")) {
			token = token.join(next());
			TypeCxNode type = parseType();
			Token name = readIdentifier("expected name of variable");
			read('=', "expected = symbol");
			ValueCxNode value = parseValue();
			ValueCxNode cond = null;
			if (can(',')) {
				cond = parseValue();
			}
			StmtCxNode command = parseBlock();
			return new GuardLetCxNode(token, type, name, value, cond, command);
		} else {
			ValueCxNode value = parseValue();
			StmtCxNode command = parseBlock();
			return new GuardCxNode(token, value, command);
		}
	}

	protected StmtCxNode parseDefer() throws SyntaxException {
		Token token = read("defer", "expected defer keyword");
		if (can("clear")) {
			return new DeferClearCxNode(token);
		} else {
			StmtCxNode command = parseCommand();
			return new DeferCxNode(token, command);
		}
	}

	protected StmtCxNode parseWhile() throws SyntaxException {
		Token token = read("while", "expected while keyword");
		ValueCxNode value = parseValue();
		StmtCxNode command = parseBlock();
		return new WhileCxNode(token, value, command);
	}

	protected StmtCxNode parseExpression() throws SyntaxException {
		ExpCxNode node = new ExpCxNode(parseValue());
		return node;
	}

	protected ValueCxNode parseValue() throws SyntaxException {
		return parseTernary();
	}

	protected ValueCxNode parseTernary() throws SyntaxException {
		ValueCxNode left = parseOr();
		if (is('?')) {
			Token token = next();
			ValueCxNode trueValue = parseOr();
			read(':', "expected else ternary");
			ValueCxNode falseValue = parseOr();
			left = new TernaryCxNode(token, left, trueValue, falseValue);
		}
		return left;
	}

	protected ValueCxNode parseOr() throws SyntaxException {
		ValueCxNode left = parseAnd();
		while (is("or") || is("orbit")) {
			if (is("or")) {
				Token token = next();
				ValueCxNode right = parseAnd();
				left = new OrCxNode(token, left, right);
			} else if (is("orbit")) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new OrBitCxNode(token, left, right);
			} else {
				throw new IllegalStateException();
			}
		}
		return left;
	}

	protected ValueCxNode parseAnd() throws SyntaxException {
		ValueCxNode left = parseCompare();
		while (is("and") || is("andbit")) {
			if (is("and")) {
				Token token = next();
				ValueCxNode right = parseCompare();
				left = new AndCxNode(token, left, right);
			} else if (is("andbit")) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new AndBitCxNode(token, left, right);
			} else {
				throw new IllegalStateException();
			}
		}
		return left;
	}

	protected ValueCxNode parseCompare() throws SyntaxException {
		ValueCxNode left = parseSum();
		while (is('=', '=') || is('!', '=') || is('>') || is('<')) {
			if (is('=', '=')) {
				Token token = next(2);
				ValueCxNode right = parseSum();
				left = new EqualCxNode(token, left, right);
			} else if (is('!', '=')) {
				Token token = next(2);
				ValueCxNode right = parseSum();
				left = new NotEqualCxNode(token, left, right);
			} else if (is('>', '=')) {
				Token token = next(2);
				ValueCxNode right = parseSum();
				left = new GreaterEqualCxNode(token, left, right);
			} else if (is('<', '=')) {
				Token token = next(2);
				ValueCxNode right = parseSum();
				left = new LowerEqualCxNode(token, left, right);
			} else if (is('>')) {
				Token token = next();
				ValueCxNode right = parseSum();
				left = new GreaterCxNode(token, left, right);
			} else if (is('<')) {
				Token token = next();
				ValueCxNode right = parseSum();
				left = new LowerCxNode(token, left, right);
			} else {
				throw new IllegalStateException();
			}
		}
		return left;
	}

	protected ValueCxNode parseSum() throws SyntaxException {
		ValueCxNode left = parseMul();
		while (is('+') || is('-')) {
			if (is('+')) {
				Token token = next();
				ValueCxNode right = parseMul();
				left = new SumCxNode(token, left, right);
			} else if (is('-')) {
				Token token = next();
				ValueCxNode right = parseMul();
				left = new SubCxNode(token, left, right);
			} else {
				throw new IllegalStateException();
			}
		}
		return left;
	}

	protected ValueCxNode parseMul() throws SyntaxException {
		ValueCxNode left = parseBit();
		while (is('*') || is('/') || is("mod")) {
			if (is('*')) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new MulCxNode(token, left, right);
			} else if (is('/')) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new DivCxNode(token, left, right);
			} else if (is("mod")) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new ModCxNode(token, left, right);
			} else {
				throw new IllegalStateException();
			}
		}
		return left;
	}

	protected ValueCxNode parseBit() throws SyntaxException {
		ValueCxNode left = parseUnary();
		while (is("lshift") || is("rshift") || is("andbit") || is("orbit")) {
			if (is("lshift")) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new LeftShiftCxNode(token, left, right);
			} else if (is("rshift")) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new RightShiftCxNode(token, left, right);
			} else if (is("andbit")) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new AndBitCxNode(token, left, right);
			} else {
				throw new IllegalStateException();
			}
		}
		return left;
	}

	protected ValueCxNode parseUnary() throws SyntaxException {
		if (is('!')) {
			return parseNot();
		} else if (is('&')) {
			return parseAddress();
		} else if (is('+', '+')) {
			Token token = next(2);
			ValueCxNode left = parseLiteral();
			return new PreIncCxNode(token, left);
		} else if (is('-', '-')) {
			Token token = next(2);
			ValueCxNode left = parseLiteral();
			return new PreDecCxNode(token, left);
		}
		ValueCxNode left = parseLiteral();
		if (is('+', '+')) {
			Token token = next(2);
			return new PosIncCxNode(token, left);
		} else if (is('-', '-')) {
			Token token = next(2);
			return new PosDecCxNode(token, left);
		}
		return left;
	}

	protected ValueCxNode parseNot() throws SyntaxException {
		Token token = read('!', "expected not keyword");
		ValueCxNode left = parseLiteral();
		return new NotCxNode(token, left);
	}

	protected ValueCxNode parseAddress() throws SyntaxException {
		Token token = read('&', "expected & symbol");
		ValueCxNode left = parseUnary();
		return new AddressCxNode(token, left);
	}

	protected ValueCxNode parseLiteral() throws SyntaxException {
		if (is('(')) {
			return parseExp();
		} else if (is("sizeof")) {
			return parseSizeof();
		} else if (isString()) {
			return parseString();
		} else if (isNumber()) {
			return parseNumber();
		} else if (is("true")) {
			return new BooleanCxNode(next(), TRUE);
		} else if (is("false")) {
			return new BooleanCxNode(next(), FALSE);
		} else if (isIdentifier()) {
			return parseId();
		} else {
			throw error("expected expression");
		}
	}

	protected ValueCxNode parseSizeof() throws SyntaxException {
		Token token = read("sizeof", "expected sizeof keyword");
		read('(', "expected open sizeof");
		TypeCxNode type = parseType();
		read(')', "expected open sizeof");
		return new SizeofCxNode(token, type);
	}

	protected ValueCxNode parseString() throws SyntaxException {
		return new StringCxNode(readString("expected string token"));
	}

	protected NumberCxNode parseNumber() throws SyntaxException {
		return new NumberCxNode(readNumber("expected number token"));
	}

	protected ValueCxNode parseExp() throws SyntaxException {
		read('(', "expected open expression");
		ValueCxNode value = parseValue();
		read(')', "expected close expression");
		return parseGetCall(value);
	}

	protected ValueCxNode parseId() throws SyntaxException {
		ValueCxNode left = parseIdentifier();
		left = parseGetCall(left);
		while (is('=')) {
			Token token = next();
			ValueCxNode value = parseValue();
			left = new AssignCxNode(token, left, value);
		}
		return left;
	}

	protected ValueCxNode parseIdentifier() throws SyntaxException {
		Token token = readIdentifier("expected identifier");
		return new IdentifierCxNode(token);
	}

	protected ValueCxNode parseGetCall(ValueCxNode left) throws SyntaxException {
		while (is('(') || is('.')) {
			if (is('(')) {
				List<ValueCxNode> arguments = readNodes('(', "expected open parameter", ')', "expected close parameter", ',', this::parseValue);
				left = new CallCxNode(left.token, left, arguments);
			} else if (can('.')) {
				if (can("cast")) {
					read('(', "expected open cast");
					TypeCxNode type = parseType();
					read(')', "expected open cast");
					left = new CastCxNode(left.token, left, type);
				} else {
					Token name = readIdentifier("expected name of field or function");
					left = new GetCxNode(name, left, name);
				}
			}
		}
		return left;
	}

}
