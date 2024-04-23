package com.github.yyeerai.hybridserverapi.common.core.io.resource;

import com.github.yyeerai.hybridserverapi.common.core.io.FileUtil;

import java.io.File;

/**
 * Web root资源访问对象
 *
 * @author looly
 * @since 4.1.11
 */
public class WebAppResource extends FileResource {
    private static final long serialVersionUID = 1L;

    /**
     * 构造
     *
     * @param path 相对于Web root的路径
     */
    public WebAppResource(String path) {
        super(new File(FileUtil.getWebRoot(), path));
    }

}