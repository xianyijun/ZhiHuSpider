#[ZhiHuSpider](http://git.oschina.net/xianyijun/ZhiHuSpider)


- - -

## Introduction
- - -

一个知乎的Java爬虫，目前实现了以下功能:
-	模拟登录
-	抓取用户信息
-	问题详情
-	答案信息
-	答案点赞用户列表

## Requirements

- **Java 8**

- **Apache Maven**

## Installation

- - -

- Clone the project

>  git clone git@git.oschina.net:xianyijun/ZhiHuSpider.git

- build the project

> mvn install 

## Usage


- - -

- 模拟登录，序列化cookie

在default.properties中配置email和password账号信息进行配置设置。
然后执行

>	mvn exec:java -Dexec.mainClass="me.kuye.spider.helper.LoginCookiesHelper"

它会下载验证码图片,存在在image目录下，文件名为valie.gif,然后人工输入验证码
执行完成之后，就可以序列化cookie，执行其他抓取数据的时候，就不需要进行登录操作了。

- 用户信息抓取

>mvn exec:java -Dexec.mainClass="me.kuye.spider.UserInfoSpider" userInfoUrl

## TODO List

- - -

- 抓取专栏信息
- 用户所有回答
- 用户所有点赞问题及文章
- 数据持久化
- 增量抓取
- 爬虫数据分析

## Contact us 

- - -

- Email : xianyijun0@gmail.com

- Github : https://github.com/xianyijun

## LICENSE


- - -

This project is open-sourced under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)