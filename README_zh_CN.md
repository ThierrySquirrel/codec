#   codec

加密解密工具包  

[English](README.md)  

支持功能：
- [x] RSA与RSA2加密解密
- [x] RSA与RSA2的签名与验签
- [x] URL编码解码

```xml
<!--在pom.xml中添加依赖-->
        <dependency>
            <artifactId>codec</artifactId>
            <groupId>com.github.thierrysquirrel</groupId>
            <version>1.2.0.3-RELEASE</version>
        </dependency>
```

##  RSA与RSA2加密解密

```java
public class RsaAndRsa2 {
	public static void main(String[] args) {
		try {
			String encrypt = RsaUtils.rsaEncrypt("需要加密的字符", "您的公匙 RSA 或 RSA2");
			String rsaDecrypt = RsaUtils.rsaDecrypt(encrypt, "您的私匙 RSA 或 RSA2");
		} catch (CodecException e) {
			e.printStackTrace();
			System.out.println("rsaEncrypt 或 rsaDecrypt 失败");
		}
	}
}
```

##  RSA与RSA2的签名与验签

```java
public class Sign {
	public static void main(String[] args) {
		try {
			String rsaSign = SignUtils.rsaSign("需要签名的字符串", "您的私匙 RSA 或");
			Boolean resultRsa = SignUtils.rsaCheck("签名之前的字符串", "您的公匙 RSA", rsaSign);
			String rsa2Sign = SignUtils.rsa2Sign("需要签名的字符串", "您的私匙 RSA2");
			Boolean resultRsa2 = SignUtils.rsa2Check("签名之前的字符串", "您的公匙 RSA2", rsa2Sign);
		} catch (CodecException e) {
			e.printStackTrace();
			System.out.println("Sign 或 Check 失败");
		}
	}
}
```

##  URL编码解码

```java
public class TextC {
	public static void main(String[] args) {
		try {
			String encode = UrlUtils.encode("https://xxx.xxx.xxx?hello=world&this=encode");
			String decode = UrlUtils.decode(encode);
		} catch (CodecException e) {
			e.printStackTrace();
			System.out.println("encode 或 decode 失败");
		}
	}
}
```
