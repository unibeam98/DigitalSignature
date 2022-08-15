# DigitalSignature
实现权威域名数据的签名与验证，使用RSA2048 + SHA256算法，需要使用证书进行验证，支持大文件读写，测试1G文本文件签名与验证为3-4秒

## 用户界面
### 验证界面
![Xnip2022-08-15_17-07-23](https://user-images.githubusercontent.com/44021048/184608127-b4f4980f-e44c-42ac-b892-566e58cd5d81.jpg)


### 签名界面
![Xnip2022-08-15_17-07-31](https://user-images.githubusercontent.com/44021048/184608367-fed47ba5-9ade-48d6-ae72-2754b1583259.jpg)


## 依赖
需要安装JRE17
验证需要签名方的验证码，写入文本文件进行读取
签名需要私钥文件，为der格式

## 使用方法
双击DigitalSignature.jar

## 说明
签名文件建议使用文本文件，不支持二进制文件签名
签名之后会有二次签名，写在文本文件末尾

## 从证书中提取公钥私钥方法
使用openssl

提取私钥文件
``` 
openssl pkcs8 -topk8 -inform PEM -outform DER -in certificate.pem -out private_key.der -nocrypt
```
提取公钥文件
```
openssl rsa -in certificate.pem -outform DER -out public_key.der
```
