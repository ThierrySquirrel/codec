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

package io.github.thierrysquirrel.codec.core.recursion;

import io.github.thierrysquirrel.codec.core.builder.StringCoderBuilder;
import io.github.thierrysquirrel.codec.core.constants.UrlCodecConstants;
import io.github.thierrysquirrel.codec.core.error.CodecException;
import org.apache.commons.codec.EncoderException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * ClassName: UrlRecursion
 * Description:
 * date: 2019/7/15 14:22
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class UrlRecursion {
    private final StringCoderBuilder stringCoderBuilder;
    private final URI url;
    private Integer urlParamOffset;
    private Integer urlVariableOffset;

    public UrlRecursion(String url) throws URISyntaxException {
        stringCoderBuilder = new StringCoderBuilder ();
        this.url = new URI (url);
        this.urlParamOffset = 0;
        this.urlVariableOffset = 0;
    }

    public String encode() throws CodecException {
        try {
            appendPrefix ();
            String path = url.getPath ();
            if (isNotEmpty (path)) {
                urlVariableEncode (path.split (UrlCodecConstants.SEPARATOR.getValue ()));
            }
            String query = url.getQuery ();
            if (isNotEmpty (query)) {
                urlParamEncode (query.split (UrlCodecConstants.CONNECT.getValue ()));
            }
            return builder ();
        } catch (Exception e) {
            throw new CodecException ("encode失败", e);
        }
    }

    public String decode() throws CodecException {
        try {
            stringCoderBuilder.appendDecode (url.toString ());
            return builder ();
        } catch (Exception e) {
            throw new CodecException ("decode失败", e);
        }
    }

    public void urlVariableEncode(String[] urlVariableSplit) throws EncoderException {
        if (urlVariableSplit.length < 1) {
            return;
        }
        if (urlVariableOffset < urlVariableSplit.length) {
            String urlVariable = urlVariableSplit[urlVariableOffset++];
            if (isEmpty (urlVariable)) {
                urlVariableEncode (urlVariableSplit);
                return;
            }
            stringCoderBuilder.append (UrlCodecConstants.SEPARATOR.getValue ());
            stringCoderBuilder.appendEncode (urlVariable);
            urlVariableEncode (urlVariableSplit);
        }
    }

    public void urlParamEncode(String[] urlParamSplit) throws EncoderException {
        if (urlParamSplit.length < 1) {
            return;
        }
        String urlParam = urlParamSplit[urlParamOffset++];
        if (urlParamOffset == 1) {
            stringCoderBuilder.append (UrlCodecConstants.PREFIX.getValue ());
            stringCoderBuilder.appendEncode (urlParam);
            stringCoderBuilder.append (UrlCodecConstants.CONNECT.getValue ());
            urlParamEncode (urlParamSplit);
            return;
        }
        int suffixIndexOf = urlParam.indexOf (UrlCodecConstants.SUFFIX.getValue ());
        if (suffixIndexOf != -1) {
            String before = urlParam.substring (0, suffixIndexOf);
            stringCoderBuilder.appendEncode (before);
            String after = urlParam.substring (suffixIndexOf);
            stringCoderBuilder.append (after);
            stringCoderBuilder.append (UrlCodecConstants.CONNECT.getValue ());
            urlParamEncode (urlParamSplit);
            return;
        }
        if (urlParamOffset == urlParamSplit.length) {
            stringCoderBuilder.appendEncode (urlParam);
        }

    }
    private void appendPrefix() {
        String host = url.getHost ();
        String uriString = url.toString ();
        int hostIndex = uriString.indexOf (host);
        String uriPrefix = uriString.substring (0, hostIndex);
        stringCoderBuilder.append (uriPrefix);
        stringCoderBuilder.append (host);
    }

    private String builder() {
        return stringCoderBuilder.builder ();
    }

    private boolean isEmpty(String string) {
        return (null == string || "".equals (string));
    }

    private boolean isNotEmpty(String string) {
        return !isEmpty (string);
    }
}
