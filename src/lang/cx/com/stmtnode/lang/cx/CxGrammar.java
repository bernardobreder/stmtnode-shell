package com.stmtnode.lang.cx;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Grammar;
import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.head.ArgumentDeclareCxNode;
import com.stmtnode.lang.cx.head.FunctionNode;
import com.stmtnode.lang.cx.head.HeadCxNode;
import com.stmtnode.lang.cx.head.IncludeLibraryNode;
import com.stmtnode.lang.cx.head.IncludeSourceNode;
import com.stmtnode.lang.cx.head.PathNode;
import com.stmtnode.lang.cx.head.StructFieldNode;
import com.stmtnode.lang.cx.head.StructNode;
import com.stmtnode.lang.cx.head.UnitNode;
import com.stmtnode.lang.cx.stmt.BlockNode;
import com.stmtnode.lang.cx.stmt.BreakNode;
import com.stmtnode.lang.cx.stmt.ContinueNode;
import com.stmtnode.lang.cx.stmt.DeclareArrayNode;
import com.stmtnode.lang.cx.stmt.DeclareValueNode;
import com.stmtnode.lang.cx.stmt.DeferNode;
import com.stmtnode.lang.cx.stmt.ExpNode;
import com.stmtnode.lang.cx.stmt.GuardLetNode;
import com.stmtnode.lang.cx.stmt.GuardNode;
import com.stmtnode.lang.cx.stmt.IfLetNode;
import com.stmtnode.lang.cx.stmt.IfNode;
import com.stmtnode.lang.cx.stmt.ReturnNode;
import com.stmtnode.lang.cx.stmt.StmtCxNode;
import com.stmtnode.lang.cx.stmt.WhileNode;
import com.stmtnode.lang.cx.type.CharTypeNode;
import com.stmtnode.lang.cx.type.IdTypeNode;
import com.stmtnode.lang.cx.type.IntTypeNode;
import com.stmtnode.lang.cx.type.PointerTypeNode;
import com.stmtnode.lang.cx.type.StructTypeNode;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.lang.cx.value.SizeofNode;
import com.stmtnode.lang.cx.value.TernaryNode;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.lang.cx.value.binary.AndBitNode;
import com.stmtnode.lang.cx.value.binary.AndNode;
import com.stmtnode.lang.cx.value.binary.AssignNode;
import com.stmtnode.lang.cx.value.binary.DivNode;
import com.stmtnode.lang.cx.value.binary.EqualNode;
import com.stmtnode.lang.cx.value.binary.GreaterEqualNode;
import com.stmtnode.lang.cx.value.binary.GreaterNode;
import com.stmtnode.lang.cx.value.binary.LeftShiftNode;
import com.stmtnode.lang.cx.value.binary.LowerEqualNode;
import com.stmtnode.lang.cx.value.binary.LowerNode;
import com.stmtnode.lang.cx.value.binary.ModNode;
import com.stmtnode.lang.cx.value.binary.MulNode;
import com.stmtnode.lang.cx.value.binary.NotEqualNode;
import com.stmtnode.lang.cx.value.binary.OrBitNode;
import com.stmtnode.lang.cx.value.binary.OrNode;
import com.stmtnode.lang.cx.value.binary.RightShiftNode;
import com.stmtnode.lang.cx.value.binary.SubNode;
import com.stmtnode.lang.cx.value.binary.SumNode;
import com.stmtnode.lang.cx.value.primitive.BooleanNode;
import com.stmtnode.lang.cx.value.primitive.IdentifierNode;
import com.stmtnode.lang.cx.value.primitive.NumberNode;
import com.stmtnode.lang.cx.value.primitive.StringNode;
import com.stmtnode.lang.cx.value.unary.AddressNode;
import com.stmtnode.lang.cx.value.unary.CallNode;
import com.stmtnode.lang.cx.value.unary.CastNode;
import com.stmtnode.lang.cx.value.unary.GetNode;
import com.stmtnode.lang.cx.value.unary.NotNode;
import com.stmtnode.lang.cx.value.unary.PosDecNode;
import com.stmtnode.lang.cx.value.unary.PosIncNode;
import com.stmtnode.lang.cx.value.unary.PreDecNode;
import com.stmtnode.lang.cx.value.unary.PreIncNode;

public class CxGrammar extends Grammar {

	public CxGrammar(Token[] tokens) {
		super(tokens);
	}

	public UnitNode parseUnit() throws SyntaxException {
		List<HeadCxNode> includes = new ArrayList<>();
		while (!eof() && can('#')) {
			includes.add(parsePrecompiler());
		}
		List<HeadCxNode> nodes = new ArrayList<>();
		while (!eof()) {
			nodes.add(parseUnitItem());
		}
		return new UnitNode(includes, nodes);
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
			PathNode path = parsePath("expected include path");
			read('>', "expected close include");
			return new IncludeLibraryNode(path);
		} else if (isString()) {
			Token path = readString("expected include path");
			return new IncludeSourceNode(path);
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

	protected PathNode parsePath(String message) throws SyntaxException {
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
		return new PathNode(tokens);
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
		List<StructFieldNode> nodes = new ArrayList<>();
		while (!can('}')) {
			nodes.add(parseStructField(nodes));
		}
		return new StructNode(token, name, nodes);
	}

	protected StructFieldNode parseStructField(List<StructFieldNode> nodes) throws SyntaxException {
		TypeCxNode type = parseType();
		Token name = readIdentifier("expected name of field");
		return new StructFieldNode(name, type);
	}

	protected StmtCxNode parseDeclare() throws SyntaxException {
		Token token = read("let", "expected let keyword");
		TypeCxNode type = parseType();
		Token name = readIdentifier("expected name of variable");
		if (can('[')) {
			ValueCxNode count = parseValue();
			read(']', "expected close array count");
			return new DeclareArrayNode(token, type, name, count);
		}
		ValueCxNode value = null;
		if (can('=')) {
			value = parseValue();
		}
		return new DeclareValueNode(token, type, name, value);
	}

	protected StmtCxNode parseReturn() throws SyntaxException {
		Token token = read("return", "expected return keyword");
		ValueCxNode value = parseValue();
		return new ReturnNode(token, value);
	}

	protected FunctionNode parseFunction() throws SyntaxException {
		Token token = read("func", "expected func keyword");
		TypeCxNode type = parseType();
		Token name = readIdentifier("expected name of function");
		List<ArgumentDeclareCxNode> arguments = readNodes('(', "expected open parameter", ')', "expected close parameter", ',', this::parseFunctionArgument);
		BlockNode block = parseBlock();
		return new FunctionNode(token, type, name, arguments, block);
	}

	protected ArgumentDeclareCxNode parseFunctionArgument() throws SyntaxException {
		TypeCxNode type = parseType();
		Token name = readIdentifier("expected name of argument");
		return new ArgumentDeclareCxNode(name, type);
	}

	protected TypeCxNode parseType() throws SyntaxException {
		TypeCxNode type = parseTypeLiteral();
		while (can('*')) {
			type = new PointerTypeNode(type);
		}
		return type;
	}

	protected TypeCxNode parseTypeLiteral() throws SyntaxException {
		if (can("int")) {
			return new IntTypeNode();
		} else if (can("char")) {
			return new CharTypeNode();
		} else if (can("struct")) {
			Token name = readIdentifier("expected name of type");
			return new StructTypeNode(name);
		} else if (isIdentifier()) {
			Token name = readIdentifier("expected name of type");
			return new IdTypeNode(name);
		} else {
			throw error("expected type");
		}
	}

	protected BlockNode parseBlock() throws SyntaxException {
		return new BlockNode(readNodes('{', "expected open block", '}', "expected close block", this::parseCommand));
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
			return new ContinueNode(next());
		} else if (is("break")) {
			return new BreakNode(next());
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
			return new IfLetNode(token, type, name, value, cond, command);
		} else {
			ValueCxNode value = parseValue();
			StmtCxNode command = parseBlock();
			return new IfNode(token, value, command);
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
			return new GuardLetNode(token, type, name, value, cond, command);
		} else {
			ValueCxNode value = parseValue();
			StmtCxNode command = parseBlock();
			return new GuardNode(token, value, command);
		}
	}

	protected StmtCxNode parseDefer() throws SyntaxException {
		Token token = read("defer", "expected defer keyword");
		StmtCxNode command = parseCommand();
		return new DeferNode(token, command);
	}

	protected StmtCxNode parseWhile() throws SyntaxException {
		Token token = read("while", "expected while keyword");
		ValueCxNode value = parseValue();
		StmtCxNode command = parseBlock();
		return new WhileNode(token, value, command);
	}

	protected StmtCxNode parseExpression() throws SyntaxException {
		ExpNode node = new ExpNode(parseValue());
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
			left = new TernaryNode(token, left, trueValue, falseValue);
		}
		return left;
	}

	protected ValueCxNode parseOr() throws SyntaxException {
		ValueCxNode left = parseAnd();
		while (is("or") || is("orbit")) {
			if (is("or")) {
				Token token = next();
				ValueCxNode right = parseAnd();
				left = new OrNode(token, left, right);
			} else if (is("orbit")) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new OrBitNode(token, left, right);
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
				left = new AndNode(token, left, right);
			} else if (is("andbit")) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new AndBitNode(token, left, right);
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
				left = new EqualNode(token, left, right);
			} else if (is('!', '=')) {
				Token token = next(2);
				ValueCxNode right = parseSum();
				left = new NotEqualNode(token, left, right);
			} else if (is('>', '=')) {
				Token token = next(2);
				ValueCxNode right = parseSum();
				left = new GreaterEqualNode(token, left, right);
			} else if (is('<', '=')) {
				Token token = next(2);
				ValueCxNode right = parseSum();
				left = new LowerEqualNode(token, left, right);
			} else if (is('>')) {
				Token token = next();
				ValueCxNode right = parseSum();
				left = new GreaterNode(token, left, right);
			} else if (is('<')) {
				Token token = next();
				ValueCxNode right = parseSum();
				left = new LowerNode(token, left, right);
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
				left = new SumNode(token, left, right);
			} else if (is('-')) {
				Token token = next();
				ValueCxNode right = parseMul();
				left = new SubNode(token, left, right);
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
				left = new MulNode(token, left, right);
			} else if (is('/')) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new DivNode(token, left, right);
			} else if (is("mod")) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new ModNode(token, left, right);
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
				left = new LeftShiftNode(token, left, right);
			} else if (is("rshift")) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new RightShiftNode(token, left, right);
			} else if (is("andbit")) {
				Token token = next();
				ValueCxNode right = parseBit();
				left = new AndBitNode(token, left, right);
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
			return new PreIncNode(token, left);
		} else if (is('-', '-')) {
			Token token = next(2);
			ValueCxNode left = parseLiteral();
			return new PreDecNode(token, left);
		}
		ValueCxNode left = parseLiteral();
		if (is('+', '+')) {
			Token token = next(2);
			return new PosIncNode(token, left);
		} else if (is('-', '-')) {
			Token token = next(2);
			return new PosDecNode(token, left);
		}
		return left;
	}

	protected ValueCxNode parseNot() throws SyntaxException {
		Token token = read('!', "expected not keyword");
		ValueCxNode left = parseLiteral();
		return new NotNode(token, left);
	}

	protected ValueCxNode parseAddress() throws SyntaxException {
		Token token = read('&', "expected & symbol");
		ValueCxNode left = parseUnary();
		return new AddressNode(token, left);
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
			return new BooleanNode(next(), TRUE);
		} else if (is("false")) {
			return new BooleanNode(next(), FALSE);
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
		return new SizeofNode(token, type);
	}

	protected ValueCxNode parseString() throws SyntaxException {
		return new StringNode(readString("expected string token"));
	}

	protected NumberNode parseNumber() throws SyntaxException {
		return new NumberNode(readNumber("expected number token"));
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
			left = new AssignNode(token, left, value);
		}
		return left;
	}

	protected ValueCxNode parseIdentifier() throws SyntaxException {
		Token token = readIdentifier("expected identifier");
		return new IdentifierNode(token);
	}

	protected ValueCxNode parseGetCall(ValueCxNode left) throws SyntaxException {
		while (is('(') || is('.')) {
			if (is('(')) {
				List<ValueCxNode> arguments = readNodes('(', "expected open parameter", ')', "expected close parameter", ',', this::parseValue);
				left = new CallNode(left.token, left, arguments);
			} else if (can('.')) {
				if (can("cast")) {
					read('(', "expected open cast");
					TypeCxNode type = parseType();
					read(')', "expected open cast");
					left = new CastNode(left.token, left, type);
				} else {
					Token name = readIdentifier("expected name of field or function");
					left = new GetNode(name, left, name);
				}
			}
		}
		return left;
	}

}
