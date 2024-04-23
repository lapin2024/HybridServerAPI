package com.github.yyeerai.hybridserverapi.common.util.core.lang.generator;

import com.github.yyeerai.hybridserverapi.common.util.core.lang.ObjectId;

/**
 * ObjectId生成器
 *
 * @author looly
 * @since 5.4.3
 */
public class ObjectIdGenerator implements Generator<String> {
	@Override
	public String next() {
		return ObjectId.next();
	}
}