package com.github.yyeerai.hybridserverapi.common.util.core.lang.generator;

import com.github.yyeerai.hybridserverapi.common.util.core.util.IdUtil;

/**
 * UUID生成器
 *
 * @author looly
 * @since 5.4.3
 */
public class UUIDGenerator implements Generator<String> {
	@Override
	public String next() {
		return IdUtil.fastUUID();
	}
}