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

package com.github.thierrysquirrel.codec.core.builder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

/**
 * ClassName: StringCoderBuilder
 * Description:
 * date: 2019/7/15 14:14
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class StringCoderBuilder {
    private StringBuilder stringBuilder;
    private URLCodec codec;

    public StringCoderBuilder() {
        stringBuilder = new StringBuilder ();
    }

    public void append(String data) {
        stringBuilder.append (data);
        codec = new URLCodec ();
    }

    public void appendEncode(String data) throws EncoderException {
        append (codec.encode (data));
    }

    public void appendDecode(String data) throws DecoderException {
        append (codec.decode (data));
    }

    public void appendEncodeOrDecode(String data, Boolean encode) throws EncoderException, DecoderException {
        if (Boolean.TRUE.equals (encode)) {
            appendEncode (data);
        } else {
            appendDecode (data);
        }
    }

    public String builder() {
        return stringBuilder.toString ();
    }
}
