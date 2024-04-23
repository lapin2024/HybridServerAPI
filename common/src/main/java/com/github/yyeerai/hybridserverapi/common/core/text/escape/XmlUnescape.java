package com.github.yyeerai.hybridserverapi.common.core.text.escape;

import com.github.yyeerai.hybridserverapi.common.core.text.replacer.LookupReplacer;
import com.github.yyeerai.hybridserverapi.common.core.text.replacer.ReplacerChain;

/**
 * XML的UNESCAPE
 *
 * @author looly
 * @since 5.7.2
 */
public class XmlUnescape extends ReplacerChain {
    protected static final String[][] BASIC_UNESCAPE = InternalEscapeUtil.invert(XmlEscape.BASIC_ESCAPE);
    // issue#1118
    protected static final String[][] OTHER_UNESCAPE = new String[][]{new String[]{"&apos;", "'"}};
    private static final long serialVersionUID = 1L;

    /**
     * 构造
     */
    public XmlUnescape() {
        addChain(new LookupReplacer(BASIC_UNESCAPE));
        addChain(new NumericEntityUnescaper());
        addChain(new LookupReplacer(OTHER_UNESCAPE));
    }
}