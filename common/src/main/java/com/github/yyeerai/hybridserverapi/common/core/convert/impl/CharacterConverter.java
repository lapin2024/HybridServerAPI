package com.github.yyeerai.hybridserverapi.common.core.convert.impl;

import com.github.yyeerai.hybridserverapi.common.core.convert.AbstractConverter;
import com.github.yyeerai.hybridserverapi.common.core.util.BooleanUtil;
import com.github.yyeerai.hybridserverapi.common.core.util.StrUtil;

/**
 * 字符转换器
 *
 * @author Looly
 */
public class CharacterConverter extends AbstractConverter<Character> {
    private static final long serialVersionUID = 1L;

    @Override
    protected Character convertInternal(Object value) {
        if (value instanceof Boolean) {
            return BooleanUtil.toCharacter((Boolean) value);
        } else {
            final String valueStr = convertToStr(value);
            if (StrUtil.isNotBlank(valueStr)) {
                return valueStr.charAt(0);
            }
        }
        return null;
    }

}