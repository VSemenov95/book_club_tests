# Автоматизированные тесты для тестовой платформы Яндекс.Самокат

## Содержание

* <a href="#description">Описание</a>
* <a href="#tools">Технологии и инструменты</a>
* <a href="#jenkins">Сборка в Jenkins</a>
* <a href="#console">Запуск из терминала</a>
* <a href="#allure">Allure отчет</a>
* <a href="#allure-testops">Интеграция с Allure TestOps</a>
* <a href="#jira">Интеграция с Jira</a>
* <a href="#telegram">Уведомление в Telegram при помощи бота</a>
* <a href="#video">Примеры видео выполнения тестов на Selenoid</a>
  <a id="description"></a>

## Описание:

Автоматизированные UI-тесты для сайта [Яндекс.Самокат](https://qa-scooter.praktikum-services.ru/). Покрытие сценарии:
1. Успешное заполнение формы *Для кого* после перехода через кнопку *Заказать* в шапке страницы.
2. Успешное заполнение формы *Для кого* после перехода через кнопку *Заказать* в центре страницы.
3. Валидация формы *Для кого* при незаполненном поле *Имя* после перехода через кнопку *Заказать* в шапке страницы.
4. Валидация формы *Для кого* при незаполненном поле *Имя* после перехода через кнопку *Заказать* в центре страницы.
5. Валидация формы *Для кого* при незаполненном поле *Фамилия* после перехода через кнопку *Заказать* в шапке страницы.
6. Валидация формы *Для кого* при незаполненном поле *Фамилия* после перехода через кнопку *Заказать* в центре страницы.

**Примечание:**
1. Для нажатия кнопки *Заказать* в шапке/по центру страницы использован подход параметризации тестов.
2. Для заполнения поля с типом dropdown использован тип данных enum.
3. В проекте написаны необходимые pageObject.
4. Шаги выделены аннотацией @Step("Название теста").


<a id="tools"></a>
## <a name="Технологии и инструменты">**Технологии и инструменты:**</a>

<p align="center">  
<a href="https://www.jetbrains.com/idea/"><img src="media/logo/Intelij_IDEA.svg" width="50" height="50"  alt="IDEA"/></a>  
<a href="https://www.java.com/"><img src="media/logo/Java.svg" width="50" height="50"  alt="Java"/></a>   
<a href="https://junit.org/junit5/"><img src="media/logo/JUnit5.svg" width="50" height="50"  alt="JUnit 5"/></a>  
<a href="https://gradle.org/"><img src="media/logo/Gradle.svg" width="50" height="50"  alt="Gradle"/></a>  
<a href="https://selenide.org/"><img src="media/logo/Selenide.svg" width="50" height="50"  alt="Selenide"/></a>  
<a href="https://aerokube.com/selenoid/"><img src="media/logo/Selenoid.svg" width="50" height="50"  alt="Selenoid"/></a>  
<a href="ht[images](images)tps://github.com/allure-framework/allure2"><img src="media/logo/Allure_Report.svg" width="50" height="50"  alt="Allure"/></a> 
<a href="https://qameta.io/"><img src="media/logo/AllureTestops.svg" width="50" height="50"  alt="Allure TestOps"/></a>   
<a href="https://www.jenkins.io/"><img src="media/logo/Jenkins.svg" width="50" height="50"  alt="Jenkins"/></a>  
<a href="https://www.atlassian.com/ru/software/jira/"><img src="media/logo/JiraIcon.svg" width="50" height="50"  alt="Jira"/></a>
<a href="https://telegram.org/"><img src="media/logo/Telegram.svg" width="50" height="50"  alt="Telegram"/></a>  
</p>


____
<a id="jenkins"></a>
## <img alt="Jenkins" height="25" src="media/logo/Jenkins.svg" width="25"/></a><a name="Сборка"></a>Сборка в [Jenkins](https://jenkins.autotests.cloud/view/java_students/job/VSemenov_qa-scooter_39/)</a>
____
<p align="center">  
<a href="https://jenkins.autotests.cloud/view/java_students/job/VSemenov_qa-scooter_39/"><img src="media/screen/jenkins.png" alt="Jenkins" width="950"/></a>  
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
## <img alt="Allure" height="25" src="media/logo/Allure_Report.svg" width="25"/></a> <a name="Allure"></a> [Allure-отчет](https://jenkins.autotests.cloud/view/java_students/job/VSemenov_qa-scooter_39/allure/)</a>
___

### *Тест-кейсы*

<p align="center">  
<img title="Allure Tests" src="media/screen/Allure_integration.png" width="850">  
</p>

___
<a id="allure-testops"></a>
## <img alt="Allure" height="25" src="media/logo/AllureTestops.svg" width="25"/></a> Интеграция с <a target="_blank" href="https://allure.autotests.cloud/project/5151/dashboards"> Allure TestOps</a>
____
### *Allure TestOps Dashboard*

<p align="center">  
<img title="Allure TestOps Dashboard" src="media/screen/Test_ops_dashboard.png" width="850">  
</p>  

### *Авто тест-кейсы*

<p align="center">  
<img title="Allure TestOps Tests" src="media/screen/Test_ops_test_case.png" width="850">  
</p>

___
<a id="jira"></a>
## <img alt="Jira" height="25" src="media/logo/JiraIcon.svg" width="25"/></a> Интеграция с <a target="_blank" href="https://jira.autotests.cloud/browse/HOMEWORK-1590">Jira</a>
____
<p align="center">  
<img title="Jira" src="media/screen/Jira_integration.png" width="850">  
</p>

____
<a id="telegram"></a>
## <img alt="Telegram" height="25" src="media/logo/Telegram.svg" width="25"/></a> Уведомление в Telegram при помощи бота
____
<p align="center">  
<img title="Allure Overview Dashboard" src="media/screen/telegram_bot.png" width="550">  
</p>

____
<a id="video"></a>
## <img alt="Selenoid" height="25" src="media/logo/Selenoid.svg" width="25"/></a> Примеры видео выполнения тестов на Selenoid
____
<p align="center">
<img title="Selenoid Video" src="media/video/example.gif" width="500" height="400"  alt="video">   
</p>
