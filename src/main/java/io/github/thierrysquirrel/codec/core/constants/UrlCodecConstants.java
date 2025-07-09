/**
 * Copyright 2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.thierrysquirrel.codec.core.constants;


/**
 * ClassName: UrlCodecConstants
 * Description:
 * date: 2019/7/15 14:35
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public enum UrlCodecConstants {
    /**
     * 拆分符
     */
    PREFIX ("?"),
    /**
     * 连接符
     */
    CONNECT ("="),
    /**
     * 拆分符
     */
    SUFFIX ("&"),
    /**
     * 分割符
     */
    SEPARATOR("/");
    private final String value;

    UrlCodecConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
