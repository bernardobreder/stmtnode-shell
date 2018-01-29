package stmtnode.json;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Leitor de Json Stream
 *
 *
 * @author Tecgraf
 */
public class JsonInputStream {

  /** Stream */
  private final InputStream input;
  /** Proximo */
  private int next;

  /**
   * Construtor
   *
   * @param input
   * @throws IOException
   */
  public JsonInputStream(InputStream input) throws IOException {
    this.input = input;
    this.next = this.readInternalNoSpace(false);
  }

  /**
   * Ler o objeto
   *
   * @return obj
   * @throws IOException
   * @throws EOFException
   * @throws ParseException
   */
  public Object readObject() throws IOException, EOFException, ParseException {
    int c = this.lookahead();
    if (c == '[') {
      return readList();
    }
    else if (c == '{') {
      return readMap();
    }
    else if ((c >= '0' && c <= '9') || c == '-') {
      return readNumber();
    }
    else if (c == '\"' || c == '\'') {
      return readStringOrDate();
    }
    else if (c == 't') {
      return readTrue();
    }
    else if (c == 'f') {
      return readFalse();
    }
    else if (c == 'n') {
      return readNull();
    }
    else {
      throw new ParseException("", next);
    }
  }

  /**
   * @return null
   * @throws IOException
   * @throws ParseException
   */
  protected Object readNull() throws IOException, ParseException {
    if (this.readWithSpace() != 'n') {
      throw new ParseException("", next);
    }
    if (this.readWithSpace() != 'u') {
      throw new ParseException("", next);
    }
    if (this.readWithSpace() != 'l') {
      throw new ParseException("", next);
    }
    if (this.readWithSpace() != 'l') {
      throw new ParseException("", next);
    }
    return null;
  }

  /**
   * @return false
   * @throws IOException
   * @throws ParseException
   */
  protected Object readFalse() throws IOException, ParseException {
    if (this.readWithSpace() != 'f') {
      throw new ParseException("", next);
    }
    if (this.readWithSpace() != 'a') {
      throw new ParseException("", next);
    }
    if (this.readWithSpace() != 'l') {
      throw new ParseException("", next);
    }
    if (this.readWithSpace() != 's') {
      throw new ParseException("", next);
    }
    if (this.readWithSpace() != 'e') {
      throw new ParseException("", next);
    }
    return false;
  }

  /**
   * @return true
   * @throws IOException
   */
  protected Object readTrue() throws IOException {
    if (this.readWithSpace() != 't') {
      throw new RuntimeException();
    }
    if (this.readWithSpace() != 'r') {
      throw new RuntimeException();
    }
    if (this.readWithSpace() != 'u') {
      throw new RuntimeException();
    }
    if (this.readWithSpace() != 'e') {
      throw new RuntimeException();
    }
    return true;
  }

  /**
   * @return a string
   * @throws IOException
   * @throws ParseException
   */
  protected String readString() throws IOException, ParseException {
    if (this.readWithSpace() != '\"') {
      throw new RuntimeException();
    }
    StringBuilder sb = new StringBuilder();
    int c = this.readWithSpace();
    while (c != '\"') {
      if (c == '\\') {
        c = this.readWithSpace();
        if (c == '\"' || c == '/' || c == '\\') {
        }
        else if (c == 'b') {
          c = '\b';
        }
        else if (c == 'f') {
          c = '\f';
        }
        else if (c == 'n') {
          c = '\n';
        }
        else if (c == 'r') {
          c = '\r';
        }
        else if (c == 't') {
          c = '\t';
        }
        else if (c == 'u') {
          throw new ParseException("", next);
        }
        else {
          throw new ParseException("", next);
        }
      }
      sb.append((char) c);
      c = this.readWithSpace();
    }
    String text = sb.toString();
    text = text.replace("\\\\", "\\");
    text = text.replace("\\/", "/");
    text = text.replace("\\\"", "\"");
    text = text.replace("\\r", "\r");
    text = text.replace("\\n", "\n");
    text = text.replace("\\t", "\t");
    text = text.replace("\\b", "\b");
    text = text.replace("\\f", "\f");
    return text;
  }

  /**
   * @return a string or date
   * @throws IOException
   * @throws ParseException
   */
  protected Object readStringOrDate() throws IOException, ParseException {
    String text = this.readString();
    if (text.length() == 24) {
      String format = "dddd-dd-ddTdd:dd:dd.dddZ";
      for (int n = 0; n < 24; n++) {
        char c = text.charAt(n);
        char f = format.charAt(n);
        switch (f) {
          case 'd': {
            if (c < '0' || c > '9') {
              return text;
            }
            break;
          }
          default: {
            if (c != f) {
              return text;
            }
          }
        }
      }
      try {
        char c;
        int year = 0;
        c = text.charAt(0);
        if (c != '0') {
          year += (c - '0') * 1000;
        }
        c = text.charAt(1);
        if (c != '0') {
          year += (c - '0') * 100;
        }
        c = text.charAt(2);
        if (c != '0') {
          year += (c - '0') * 10;
        }
        c = text.charAt(3);
        if (c != '0') {
          year += (c - '0');
        }
        int month = 0;
        c = text.charAt(5);
        if (c != '0') {
          month += (c - '0') * 10;
        }
        c = text.charAt(6);
        if (c != '0') {
          month += (c - '0');
        }
        int day = 0;
        c = text.charAt(8);
        if (c != '0') {
          day += (c - '0') * 10;
        }
        c = text.charAt(9);
        if (c != '0') {
          day += (c - '0');
        }
        int hour = 0;
        c = text.charAt(11);
        if (c != '0') {
          hour += (c - '0') * 10;
        }
        c = text.charAt(12);
        if (c != '0') {
          hour += (c - '0');
        }
        int minute = 0;
        c = text.charAt(14);
        if (c != '0') {
          minute += (c - '0') * 10;
        }
        c = text.charAt(15);
        if (c != '0') {
          minute += (c - '0');
        }
        int second = 0;
        c = text.charAt(17);
        if (c != '0') {
          second += (c - '0') * 10;
        }
        c = text.charAt(18);
        if (c != '0') {
          second += (c - '0');
        }
        int milisecond = 0;
        c = text.charAt(20);
        if (c != '0') {
          milisecond += (c - '0') * 100;
        }
        c = text.charAt(21);
        if (c != '0') {
          milisecond += (c - '0') * 10;
        }
        c = text.charAt(22);
        if (c != '0') {
          milisecond += (c - '0');
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, milisecond);
        return calendar.getTime();
      }
      catch (NumberFormatException e) {
        return text;
      }
    }
    else {
      return text;
    }
  }

  /**
   * @return a number
   * @throws IOException
   * @throws ParseException
   */
  protected Object readNumber() throws IOException, ParseException {
    StringBuilder sb = new StringBuilder();
    int c = this.lookahead();
    if (c == '-') {
      sb.append((char) this.readWithSpace());
    }
    c = this.lookahead();
    if (c == '0') {
      sb.append((char) this.readWithSpace());
      c = this.lookahead();
    }
    else {
      while (c >= '0' && c <= '9') {
        sb.append((char) this.readWithSpace());
        c = this.lookahead();
      }
    }
    if (c == '.') {
      do {
        sb.append((char) this.readWithSpace());
        c = this.lookahead();
      } while (c >= '0' && c <= '9');
    }
    if (c == 'e' || c == 'E') {
      throw new ParseException("", next);
      // sb.append((char) this.readWithSpace());
      // c = this.lookahead();
      // if (c == '+' || c == '-') {
      // sb.append((char) this.readWithSpace());
      // c = this.lookahead();
      // }
      // while (c >= '0' && c <= '9') {
      // sb.append((char) this.readWithSpace());
      // c = this.lookahead();
      // }
    }
    try {
      return Integer.valueOf(sb.toString());
    }
    catch (NumberFormatException e) {
      try {
        return Long.valueOf(sb.toString());
      }
      catch (NumberFormatException ee) {
        return Double.valueOf(sb.toString());
      }
    }
  }

  /**
   * @return a map
   * @throws IOException
   * @throws ParseException
   */
  protected JsonObject readMap() throws IOException, ParseException {
    if (this.readIgnoringSpace() != '{') {
      throw new ParseException("", next);
    }
    if (this.lookahead() == '}') {
      return new JsonObject(new HashMap<String, Object>(0));
    }
    Map<String, Object> map = new HashMap<String, Object>();
    for (;;) {
      String key = this.readString();
      if (this.readIgnoringSpace() != ':') {
        throw new ParseException("", next);
      }
      Object value = this.readObject();
      map.put(key, value);
      int c = this.readIgnoringSpace();
      if (c == ',') {
        continue;
      }
      else if (c == '}') {
        break;
      }
      else {
        throw new ParseException("" + (char) c, next);
      }
    }
    return new JsonObject(map);
  }

  /**
   * @return a list
   * @throws IOException
   * @throws ParseException
   */
  protected Object readList() throws IOException, ParseException {
    if (this.readIgnoringSpace() != '[') {
      throw new ParseException("", next);
    }
    if (this.lookahead() == ']') {
      this.readWithSpace();
      return new ArrayList<Object>(0);
    }
    List<Object> list = new ArrayList<Object>();
    for (;;) {
      Object value = this.readObject();
      list.add(value);
      int c = this.readIgnoringSpace();
      if (c == ']') {
        break;
      }
      else if (c == ',') {
        continue;
      }
      else {
        throw new ParseException("", next);
      }
    }
    return list;
  }

  /**
   * Realiza a leitura de um caracter que não seja de espaçamento. Essa
   * leitura considera que não está no final de arquivo.
   *
   * @return leitura
   * @throws IOException
   */
  protected int readIgnoringSpace() throws IOException {
    int c = this.next;
    if (c >= 0) {
      while (c <= 32) {
        c = this.readInternalNoSpace(true);
      }
      this.next = this.readInternalNoSpace(true);
    }
    return c;
  }

  /**
   * Realiza a leitura de um caracter que não seja de espaçamento. Essa
   * leitura considera que não está no final de arquivo.
   *
   * @return leitura
   * @throws IOException
   */
  protected int readWithSpace() throws IOException {
    int c = this.next;
    if (c >= 0) {
      this.next = this.readInternalWithSpace();
    }
    else {
      throw new EOFException();
    }
    return c;
  }

  /**
   * Realiza o lookahead
   *
   * @return lookahead
   */
  protected int lookahead() {
    return this.next;
  }

  /**
   * Realiza a leitura de um caracter que não seja de espaçamento. Essa
   * leitura considera que não está no final de arquivo.
   *
   * @param safe
   * @return leitura
   * @throws IOException
   */
  private int readInternalNoSpace(boolean safe) throws IOException {
    int c = this.read();
    while (c <= 32) {
      c = this.read();
      if (c < 0) {
        if (safe) {
          return -1;
        }
        else {
          throw new EOFException();
        }
      }
    }
    return c;
  }

  /**
   * Realiza a leitura de um caracter que não seja de espaçamento. Essa
   * leitura considera que não está no final de arquivo.
   *
   * @return leitura
   * @throws IOException
   */
  private int readInternalWithSpace() throws IOException {
    int c = this.read();
    if (c < 0) {
      return -1;
    }
    return c;
  }

  /**
   * Realiza a leitura
   *
   * @return leitura
   * @throws IOException
   */
  private int read() throws IOException {
    int c = this.input.read();
    if (c <= 0x7F) {
      return c;
    }
    else if ((c >> 5) == 0x6) {
      int i2 = this.input.read();
      if (i2 < 0) {
        throw new EOFException();
      }
      return (((c & 0x1F) << 6) + (i2 & 0x3F));
    }
    else {
      int i2 = this.input.read();
      if (i2 < 0) {
        throw new EOFException();
      }
      int i3 = this.input.read();
      if (i3 < 0) {
        throw new EOFException();
      }
      return (((c & 0xF) << 12) + ((i2 & 0x3F) << 6) + (i3 & 0x3F));
    }
  }

}
