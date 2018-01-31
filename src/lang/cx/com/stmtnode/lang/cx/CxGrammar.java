package com.stmtnode.lang.cx;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Grammar;
import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.head.ArgumentDeclareNode;
import com.stmtnode.lang.cx.head.FunctionNode;
import com.stmtnode.lang.cx.head.HeadNode;
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
import com.stmtnode.lang.cx.stmt.StmtNode;
import com.stmtnode.lang.cx.stmt.WhileNode;
import com.stmtnode.lang.cx.type.CharTypeNode;
import com.stmtnode.lang.cx.type.IdTypeNode;
import com.stmtnode.lang.cx.type.IntTypeNode;
import com.stmtnode.lang.cx.type.PointerTypeNode;
import com.stmtnode.lang.cx.type.StructTypeNode;
import com.stmtnode.lang.cx.type.TypeNode;
import com.stmtnode.lang.cx.value.SizeofNode;
import com.stmtnode.lang.cx.value.TernaryNode;
import com.stmtnode.lang.cx.value.ValueNode;
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
		List<HeadNode> includes = new ArrayList<>();
		while (!eof() && can('#')) {
			includes.add(parsePrecompiler());
		}
		List<HeadNode> nodes = new ArrayList<>();
		while (!eof()) {
			nodes.add(parseUnitItem());
		}
		return new UnitNode(includes, nodes);
	}

	protected HeadNode parsePrecompiler() throws SyntaxException {
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

	protected HeadNode parsePreprocessorInclude() throws SyntaxException {
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

	protected HeadNode parsePreprocessorDefine() throws SyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

	protected HeadNode parsePreprocessorTypedef() throws SyntaxException {
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

	protected HeadNode parseUnitItem() throws SyntaxException {
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

	protected HeadNode parseDeclareStatic() {
		// TODO Auto-generated method stub
		return null;
	}

	protected HeadNode parseStruct() throws SyntaxException {
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
		TypeNode type = parseType();
		Token name = readIdentifier("expected name of field");
		return new StructFieldNode(name, type);
	}

	protected StmtNode parseDeclare() throws SyntaxException {
		Token token = read("let", "expected let keyword");
		TypeNode type = parseType();
		Token name = readIdentifier("expected name of variable");
		if (can('[')) {
			ValueNode count = parseValue();
			read(']', "expected close array count");
			return new DeclareArrayNode(token, type, name, count);
		}
		ValueNode value = null;
		if (can('=')) {
			value = parseValue();
		}
		return new DeclareValueNode(token, type, name, value);
	}

	protected StmtNode parseReturn() throws SyntaxException {
		Token token = read("return", "expected return keyword");
		ValueNode value = parseValue();
		return new ReturnNode(token, value);
	}

	protected FunctionNode parseFunction() throws SyntaxException {
		Token token = read("func", "expected func keyword");
		TypeNode type = parseType();
		Token name = readIdentifier("expected name of function");
		List<ArgumentDeclareNode> arguments = readNodes('(', "expected open parameter", ')', "expected close parameter", ',', this::parseFunctionArgument);
		BlockNode block = parseBlock();
		return new FunctionNode(token, type, name, arguments, block);
	}

	protected ArgumentDeclareNode parseFunctionArgument() throws SyntaxException {
		TypeNode type = parseType();
		Token name = readIdentifier("expected name of argument");
		return new ArgumentDeclareNode(name, type);
	}

	protected TypeNode parseType() throws SyntaxException {
		TypeNode type = parseTypeLiteral();
		while (can('*')) {
			type = new PointerTypeNode(type);
		}
		return type;
	}

	protected TypeNode parseTypeLiteral() throws SyntaxException {
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

	protected StmtNode parseCommand() throws SyntaxException {
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

	protected StmtNode parseIf() throws SyntaxException {
		Token token = read("if", "expected if keyword");
		if (is("let")) {
			token = token.join(next());
			TypeNode type = parseType();
			Token name = readIdentifier("expected name of variable");
			read('=', "expected = symbol");
			ValueNode value = parseValue();
			ValueNode cond = null;
			if (can(',')) {
				cond = parseValue();
			}
			StmtNode command = parseBlock();
			return new IfLetNode(token, type, name, value, cond, command);
		} else {
			ValueNode value = parseValue();
			StmtNode command = parseBlock();
			return new IfNode(token, value, command);
		}
	}

	protected StmtNode parseGuard() throws SyntaxException {
		Token token = read("guard", "expected guard keyword");
		if (is("let")) {
			token = token.join(next());
			TypeNode type = parseType();
			Token name = readIdentifier("expected name of variable");
			read('=', "expected = symbol");
			ValueNode value = parseValue();
			ValueNode cond = null;
			if (can(',')) {
				cond = parseValue();
			}
			StmtNode command = parseBlock();
			return new GuardLetNode(token, type, name, value, cond, command);
		} else {
			ValueNode value = parseValue();
			StmtNode command = parseBlock();
			return new GuardNode(token, value, command);
		}
	}

	protected StmtNode parseDefer() throws SyntaxException {
		Token token = read("defer", "expected defer keyword");
		StmtNode command = parseCommand();
		return new DeferNode(token, command);
	}

	protected StmtNode parseWhile() throws SyntaxException {
		Token token = read("while", "expected while keyword");
		ValueNode value = parseValue();
		StmtNode command = parseBlock();
		return new WhileNode(token, value, command);
	}

	protected StmtNode parseExpression() throws SyntaxException {
		ExpNode node = new ExpNode(parseValue());
		return node;
	}

	protected ValueNode parseValue() throws SyntaxException {
		return parseTernary();
	}

	protected ValueNode parseTernary() throws SyntaxException {
		ValueNode left = parseOr();
		if (is('?')) {
			Token token = next();
			ValueNode trueValue = parseOr();
			read(':', "expected else ternary");
			ValueNode falseValue = parseOr();
			left = new TernaryNode(token, left, trueValue, falseValue);
		}
		return left;
	}

	protected ValueNode parseOr() throws SyntaxException {
		ValueNode left = parseAnd();
		while (is("or") || is("orbit")) {
			if (is("or")) {
				Token token = next();
				ValueNode right = parseAnd();
				left = new OrNode(token, left, right);
			} else if (is("orbit")) {
				Token token = next();
				ValueNode right = parseBit();
				left = new OrBitNode(token, left, right);
			} else {
				throw new IllegalStateException();
			}
		}
		return left;
	}

	protected ValueNode parseAnd() throws SyntaxException {
		ValueNode left = parseCompare();
		while (is("and") || is("andbit")) {
			if (is("and")) {
				Token token = next();
				ValueNode right = parseCompare();
				left = new AndNode(token, left, right);
			} else if (is("andbit")) {
				Token token = next();
				ValueNode right = parseBit();
				left = new AndBitNode(token, left, right);
			} else {
				throw new IllegalStateException();
			}
		}
		return left;
	}

	protected ValueNode parseCompare() throws SyntaxException {
		ValueNode left = parseSum();
		while (is('=', '=') || is('!', '=') || is('>') || is('<')) {
			if (is('=', '=')) {
				Token token = next(2);
				ValueNode right = parseSum();
				left = new EqualNode(token, left, right);
			} else if (is('!', '=')) {
				Token token = next(2);
				ValueNode right = parseSum();
				left = new NotEqualNode(token, left, right);
			} else if (is('>', '=')) {
				Token token = next(2);
				ValueNode right = parseSum();
				left = new GreaterEqualNode(token, left, right);
			} else if (is('<', '=')) {
				Token token = next(2);
				ValueNode right = parseSum();
				left = new LowerEqualNode(token, left, right);
			} else if (is('>')) {
				Token token = next();
				ValueNode right = parseSum();
				left = new GreaterNode(token, left, right);
			} else if (is('<')) {
				Token token = next();
				ValueNode right = parseSum();
				left = new LowerNode(token, left, right);
			} else {
				throw new IllegalStateException();
			}
		}
		return left;
	}

	protected ValueNode parseSum() throws SyntaxException {
		ValueNode left = parseMul();
		while (is('+') || is('-')) {
			if (is('+')) {
				Token token = next();
				ValueNode right = parseMul();
				left = new SumNode(token, left, right);
			} else if (is('-')) {
				Token token = next();
				ValueNode right = parseMul();
				left = new SubNode(token, left, right);
			} else {
				throw new IllegalStateException();
			}
		}
		return left;
	}

	protected ValueNode parseMul() throws SyntaxException {
		ValueNode left = parseBit();
		while (is('*') || is('/') || is("mod")) {
			if (is('*')) {
				Token token = next();
				ValueNode right = parseBit();
				left = new MulNode(token, left, right);
			} else if (is('/')) {
				Token token = next();
				ValueNode right = parseBit();
				left = new DivNode(token, left, right);
			} else if (is("mod")) {
				Token token = next();
				ValueNode right = parseBit();
				left = new ModNode(token, left, right);
			} else {
				throw new IllegalStateException();
			}
		}
		return left;
	}

	protected ValueNode parseBit() throws SyntaxException {
		ValueNode left = parseUnary();
		while (is("lshift") || is("rshift") || is("andbit") || is("orbit")) {
			if (is("lshift")) {
				Token token = next();
				ValueNode right = parseBit();
				left = new LeftShiftNode(token, left, right);
			} else if (is("rshift")) {
				Token token = next();
				ValueNode right = parseBit();
				left = new RightShiftNode(token, left, right);
			} else if (is("andbit")) {
				Token token = next();
				ValueNode right = parseBit();
				left = new AndBitNode(token, left, right);
			} else {
				throw new IllegalStateException();
			}
		}
		return left;
	}

	protected ValueNode parseUnary() throws SyntaxException {
		if (is('!')) {
			return parseNot();
		} else if (is('&')) {
			return parseAddress();
		} else if (is('+', '+')) {
			Token token = next(2);
			ValueNode left = parseLiteral();
			return new PreIncNode(token, left);
		} else if (is('-', '-')) {
			Token token = next(2);
			ValueNode left = parseLiteral();
			return new PreDecNode(token, left);
		}
		ValueNode left = parseLiteral();
		if (is('+', '+')) {
			Token token = next(2);
			return new PosIncNode(token, left);
		} else if (is('-', '-')) {
			Token token = next(2);
			return new PosDecNode(token, left);
		}
		return left;
	}

	protected ValueNode parseNot() throws SyntaxException {
		Token token = read('!', "expected not keyword");
		ValueNode left = parseLiteral();
		return new NotNode(token, left);
	}

	protected ValueNode parseAddress() throws SyntaxException {
		Token token = read('&', "expected & symbol");
		ValueNode left = parseUnary();
		return new AddressNode(token, left);
	}

	protected ValueNode parseLiteral() throws SyntaxException {
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

	protected ValueNode parseSizeof() throws SyntaxException {
		Token token = read("sizeof", "expected sizeof keyword");
		read('(', "expected open sizeof");
		TypeNode type = parseType();
		read(')', "expected open sizeof");
		return new SizeofNode(token, type);
	}

	protected ValueNode parseString() throws SyntaxException {
		return new StringNode(readString("expected string token"));
	}

	protected NumberNode parseNumber() throws SyntaxException {
		return new NumberNode(readNumber("expected number token"));
	}

	protected ValueNode parseExp() throws SyntaxException {
		read('(', "expected open expression");
		ValueNode value = parseValue();
		read(')', "expected close expression");
		return parseGetCall(value);
	}

	protected ValueNode parseId() throws SyntaxException {
		ValueNode left = parseIdentifier();
		left = parseGetCall(left);
		while (is('=')) {
			Token token = next();
			ValueNode value = parseValue();
			left = new AssignNode(token, left, value);
		}
		return left;
	}

	protected ValueNode parseIdentifier() throws SyntaxException {
		Token token = readIdentifier("expected identifier");
		return new IdentifierNode(token);
	}

	protected ValueNode parseGetCall(ValueNode left) throws SyntaxException {
		while (is('(') || is('.')) {
			if (is('(')) {
				List<ValueNode> arguments = readNodes('(', "expected open parameter", ')', "expected close parameter", ',', this::parseValue);
				left = new CallNode(left.token, left, arguments);
			} else if (can('.')) {
				if (can("cast")) {
					read('(', "expected open cast");
					TypeNode type = parseType();
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
