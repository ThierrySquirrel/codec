/**
 * Copyright 2026/6/1 ThierrySquirrel
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
 **/

package io.github.thierrysquirrel.codec.core.builder;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * ClassName: StringCoderBuilder
 * Description:
 * date: 2026/6/1
 *
 * @author ThierrySquirrel
 * @since JDK 25
 */
public class StringCoderBuilder {
    private final StringBuilder stringBuilder;

    public StringCoderBuilder() {
        stringBuilder = new StringBuilder();
    }

    public void append(String data) {
        stringBuilder.append(data);
    }

    public void appendEncode(String data) {
        append(URLEncoder.encode(data, StandardCharsets.UTF_8));
    }

    public void appendDecode(String data) {
        append(URLDecoder.decode(data, StandardCharsets.UTF_8));
    }

    public String builder() {
        return stringBuilder.toString();
    }
}
