# Автоматизированные API и UI тесты для web-приложения Book.Club

## Содержание

* <a href="#description">Описание</a>
* <a href="#tools">Технологии и инструменты</a>
* <a href="#jenkins">Сборка в Jenkins</a>
* <a href="#console">Запуск из терминала</a>
* <a href="#allure">Allure отчет</a>
* <a href="#allure-testops">Интеграция с Allure TestOps</a>
* <a href="#video">Примеры видео выполнения тестов на Selenoid</a>
  <a id="description"></a>

## Описание:

Автоматизированные UI-тесты для сайта [Book.Club](https://book-club.qa.guru/). Автоматизированы как UI, так и API тесты.


<a id="tools"></a>
## <a name="Технологии и инструменты">**Технологии и инструменты:**</a>

<p align="center">  
<a href="https://www.jetbrains.com/idea/"><img src="media/icons/Intelij_IDEA.svg" width="50" height="50"  alt="IDEA"/></a>  
<a href="https://www.java.com/"><img src="media/icons/Java.svg" width="50" height="50"  alt="Java"/></a>   
<a href="https://junit.org/junit5/"><img src="media/icons/JUnit5.svg" width="50" height="50"  alt="JUnit 5"/></a>  
<a href="https://gradle.org/"><img src="media/icons/Gradle.svg" width="50" height="50"  alt="Gradle"/></a>  
<a href="https://selenide.org/"><img src="media/icons/Selenide.svg" width="50" height="50"  alt="Selenide"/></a>  
<a href="https://github.com/allure-framework/allure2"><img src="media/icons/Allure_Report.svg" width="50" height="50"  alt="Allure"/></a> 
<a href="https://qameta.io/"><img src="media/icons/AllureTestops.svg" width="50" height="50"  alt="Allure TestOps"/></a>   
<a href="https://www.jenkins.io/"><img src="media/icons/Jenkins.svg" width="50" height="50"  alt="Jenkins"/></a>
<a href="https://selenide.org/"><img src="media/icons/Selenoid.svg" width="50" height="50"  alt="Selenoid"/>
</a>  
</p>


____
<a id="jenkins"></a>
## <img alt="Jenkins" height="25" src="media/icons/Jenkins.svg" width="25"/></a><a name="Сборка"></a>Сборка в [Jenkins](https://jenkins.qa.guru/view/java-students/job/41_SemenovVS_bookClubTests/)</a>
____
<p align="center">  
<a href="https://jenkins.qa.guru/view/java-students/job/41_SemenovVS_bookClubTests/"><img src="media/screen/JenkinsRun.png" alt="Jenkins" width="950"/></a>  
</p>


### **Параметры сборки в Jenkins:**

- *browserName (браузер, по умолчанию chrome)*
- *browserVersion (версия браузера, по умолчанию 127.0)*
- *browserSize (размер окна браузера, по умолчанию 1280x720)*
- *remoteUrl (логин, пароль и адрес удаленного сервера Selenoid)*

<a id="console"></a>
## Команды для запуска из терминала
___
***Локальный запуск:***
```bash  
gradle clean test
```

***Удалённый запуск через Jenkins:***
```bash  
clean test
-DbrowserName="$BROWSER_NAME"
-DbrowserVersion="$BROWSER_VERSION"
-DbrowserSize="BROWSER_SIZE"
-DremoteUrl=https://user1:1234@selenoid.autotests.cloud/wd/hub
```
___
<a id="allure"></a>
## <img alt="Allure" height="25" src="media/icons/Allure_Report.svg" width="25"/></a> <a name="Allure"></a> [Allure-отчет](https://jenkins.qa.guru/view/java-students/job/41_SemenovVS_bookClubTests/allure/)</a>
___

### *Тест-кейсы*

<p align="center">  
<img title="Allure Tests" src="media/screen/testOpsCase.png" width="850">  
</p>

___
<a id="allure-testops"></a>
## <img alt="Allure" height="25" src="media/icons/AllureTestops.svg" width="25"/></a> Интеграция с <a target="_blank" href="https://allure.qa.guru/project/5358/dashboards"> Allure TestOps</a>
____
### *Allure TestOps Dashboard*

<p align="center">  
<img title="Allure TestOps Dashboard" src="media/screen/dashboards.png" width="850">  
</p>  

### *Авто тест-кейсы*

<p align="center">  
<img title="Allure TestOps Tests" src="media/screen/testOpsCase.png" width="850">  
</p>

____
<a id="video"></a>
## <img alt="Selenoid" height="25" src="media/icons/Selenoid.svg" width="25"/></a> Примеры видео выполнения тестов на Selenoid
____
<p align="center">
<img title="Selenoid Video" src="media/video/video.gif" width="500" height="400"  alt="video">   
</p>
