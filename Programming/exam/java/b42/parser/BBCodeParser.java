package parser;


import nodes.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class BBCodeParser extends Parser {

    public BBCodeParser(InputStream is) {
        super(is);
    }

    public List<BBNode> parse() {
        List<BBNode> nodes = new ArrayList<>();
        while (this.hasNext()) {
            BBNode subnode = this.parseNode();
            nodes.add(subnode);
        }
        return nodes;
    }

    public List<BBNode> parseInner(String closeTag) {
        List<BBNode> nodes = new ArrayList<>();
        while (this.hasNext() && !this.parseCloseTag(closeTag)) {
            BBNode subnode = this.parseNode();
            nodes.add(subnode);
        }
        return nodes;
    }

    private BBNode parseNode() {
        if (this.getCurrentChar() != '[') {
            return this.parseText();
        }
        OpenTag tag = this.parseOpenTag();
        switch (tag.tagName) {
            case "b": return this.parseBold(tag);
            case "i": return this.parseItalic(tag);
            case "u": return this.parseUnderline(tag);
            case "s": return this.parseStrikeThrough(tag);
            case "url": return this.parseUrl(tag);
            case "img": return this.parseImg(tag);
            case "quote": return this.parseQuote(tag);
            case "code": return this.parseCode(tag);
            case "size": return this.parseSize(tag);
            case "color": return this.parseColor(tag);
            case "ul":
            case "ol":
            case "list": return this.parseList(tag);
            case "li" : return this.parseListItem(tag);
            default: {
                throw this.error(String.format("Неизвестный тег '%s'", tag.tagName));
            }
        }
    }

    private OpenTag parseOpenTag() {
        String tag = this.parseTagName();
        String attr = this.parseTagAttr();
        expect(']');
        return new OpenTag(tag, attr);
    }

    private String parseTagName() {
        StringBuilder sb = new StringBuilder();
        this.expect('[');
        while (this.hasNext() && (this.getCurrentChar() != ']' && this.getCurrentChar() != '=')) {
            sb.append(this.getCurrentChar());
            nextChar();
        }
        return sb.toString();
    }

    private String parseTagAttr() {
        if (this.getCurrentChar() != '=') {
            return null;
        }
        this.expect("=");
        StringBuilder sb = new StringBuilder();
        while (this.hasNext() && this.getCurrentChar() != ']') {
            sb.append(this.getCurrentChar());
            nextChar();
        }
        return sb.toString();
    }

    private boolean parseCloseTag(String closeTag) {
        String cls = "[/"+closeTag+"]";
        if (this.test(cls)) {
            this.expect(cls);
            return true;
        }
        return false;
    }

    public Text parseText() {
        StringBuilder sb = new StringBuilder();
        while (this.hasNext() && this.getCurrentChar() != '[') {
            sb.append(this.getCurrentChar());
            this.nextChar();
        }
        if (this.getCurrentChar() != '[') {
            sb.append(this.getCurrentChar());
        }
        return new Text(sb.toString());
    }

    private Bold parseBold(OpenTag tag) {
        return new Bold(this, tag);
    }

    private Italic parseItalic(OpenTag tag) {
        return new Italic(this, tag);
    }

    private Underline parseUnderline(OpenTag tag) {
        return new Underline(this, tag);
    }

    private StrikeThrough parseStrikeThrough(OpenTag tag) {
        return new StrikeThrough(this, tag);
    }

    private Url parseUrl(OpenTag tag) {
        return new Url(this, tag);
    }

    private Img parseImg(OpenTag tag) {
        return new Img(this, tag);
    }

    private Quote parseQuote(OpenTag tag) {
        return new Quote(this, tag);
    }

    private Code parseCode(OpenTag tag) {
        return new Code(this, tag);
    }

    private Size parseSize(OpenTag tag) {
        return new Size(this, tag);
    }

    private Color parseColor(OpenTag tag) {
        return new Color(this, tag);
    }

    private BBList parseList(OpenTag tag) {
        return new BBList(this, tag);
    }

    private BBList.ListItem parseListItem(OpenTag tag) {
        return new BBList.ListItem(this, tag);
    }

    private Table parseTable(OpenTag tag) {
        return new Table(this, tag);
    }

    private Table.TableRow parseTableRow(OpenTag tag) {
        return new Table.TableRow(this, tag);
    }

    private Table.TableCell parseTableCell(OpenTag tag) {
        return new Table.TableCell(this, tag);
    }

    public static class OpenTag {
        public final String tagName;
        public final String attr;

        OpenTag(String tag, String attr) {
            this.tagName = tag.trim().toLowerCase();
            this.attr = attr != null ? attr.trim() : null;
        }
    }
}
