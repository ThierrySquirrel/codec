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

package com.github.thierrysquirrel.core.recursion;

import com.github.thierrysquirrel.core.builder.StringCoderBuilder;
import com.github.thierrysquirrel.core.constants.URLCodecConstants;
import com.github.thierrysquirrel.core.error.CodecException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

/**
 * ClassName: URLRecursion
 * Description:
 * date: 2019/7/15 14:22
 *
 * @author ThierrySquirrel
 * @since JDK 1.8
 */
public class URLRecursion {
	private Integer offset;
	private StringCoderBuilder stringCoderBuilder;

	public URLRecursion() {
		this.offset = 0;
		stringCoderBuilder = new StringCoderBuilder();
	}

	public String encode(String[] splitUrl) throws CodecException {
		try {
			urlCodec(splitUrl, Boolean.TRUE);
			return stringCoderBuilder.builder();
		} catch (Exception e) {
			throw new CodecException("encode失败", e);
		}
	}

	public String decode(String[] splitUrl) throws CodecException {
		try {
			urlCodec(splitUrl, Boolean.FALSE);
			return stringCoderBuilder.builder();
		} catch (Exception e) {
			throw new CodecException("decode失败", e);
		}
	}

	private void urlCodec(String[] splitUrl, Boolean encode) throws DecoderException, EncoderException {
		String thisString = splitUrl[offset++];
		int prefixIndexOf = thisString.indexOf(URLCodecConstants.PREFIX.getValue());
		if (prefixIndexOf >= 0) {
			stringCoderBuilder.append(thisString);
			urlCodec(splitUrl, encode);
			return;
		}
		stringCoderBuilder.append(String.valueOf(URLCodecConstants.CONNECT.getValue()));
		int suffixIndexOf = thisString.indexOf(URLCodecConstants.SUFFIX.getValue());
		if (suffixIndexOf >= 0) {
			String suffixString = thisString.substring(0, suffixIndexOf);

			stringCoderBuilder.appendEncodeOrDecode(suffixString, encode);
			stringCoderBuilder.append(thisString.substring(suffixIndexOf));
			urlCodec(splitUrl, encode);
			return;
		}
		if (offset == splitUrl.length) {
			stringCoderBuilder.appendEncodeOrDecode(thisString, encode);
			offset = 0;
		}
	}

	public String builder() {
		return stringCoderBuilder.builder();
	}
}
