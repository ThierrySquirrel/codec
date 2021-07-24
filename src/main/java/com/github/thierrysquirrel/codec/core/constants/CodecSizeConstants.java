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

package com.github.thierrysquirrel.codec.core.constants;

/**
 * ClassName: CodecSizeConstants
 * Description:
 * date: 2019/7/15 11:32
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public enum CodecSizeConstants {
    /**
     * RSA最大加密明文大小
     */
    MAX_ENCRYPT_BLOCK (117),
    /**
     * RSA最大解密密文大小
     */
    MAX_DECRYPT_BLOCK (256);

    private Integer value;

    CodecSizeConstants(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
