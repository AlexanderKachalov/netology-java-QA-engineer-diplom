## Дипломный проект к профессии "Тестировщик"
## "Автоматизация тестирования комплексного веб-сервиса "Путешествие дня", взаимодействующего с СУБД и API Банка"     
***
### Документация
#### [План автоматизации](https://github.com/AlexanderKachalov/diplom/blob/master/docs/Plan.md)   
#### [Отчет о проведенном тестировании](https://github.com/AlexanderKachalov/diplom/blob/master/docs/Report.md)   
#### [Отчёт о проведённой автоматизации](https://github.com/AlexanderKachalov/diplom/blob/master/docs/Summary.md)      
***  
### Issues   
* На найденные ошибки в работе веб-приложения по проведенным авто-тестам составлены [Issues](https://github.com/AlexanderKachalov/diplom/issues?q=is%3Aissue+is%3Aopen+sort%3Acreated-asc)   
* На Issues #1, #2 и #21 авто-тесты не производились
***   

### Процедура запуска авто-тестов   
---  
#### Программное окружение   
* macOS Mojave v.10.14.6   
* Браузер Google Chrome v.81.0.4044.129    
---   
#### Перед запуском авто-тестов     
* устанавливаем на ПК программную платформу **Node** и устанавливаем и запускаем инструмент виртуализации **Docker**
---
### Запуск автотестов
  1. Запускаем мультиконтейнерное приложение _Docker_ командой   
   ```
   docker-compose up -d
   ```        
   * ждем подъема баз данных    
#### Для работы с MySQL  
  2. Запускаем веб-сервис командой  
   ```
   java -jar aqa-shop.jar --spring.profiles.active=mysql
   ```   
   * страница веб-сервиса находится по адресу   
   ```   
   http://localhost:8080    
   ```   
  3. Запускаем симулятор банковских сервисов:      
   * переходим в каталог _gate-simulator_ командой   
   ```
   cd gate-simulator
   ```  
   * запускаем симулятор сервисов командой   
   ```
   npm start
   ```   
  4. Запускаем авто-тесты в каталоге _diplom_ командой    
  ```
  ./gradlew clean test allureReport   
  ```   
  5. Формируем отчет в _Gradle_ командой   
  ```
  ./gradlew allureServe   
  ```   
### Для работы с PostgreSQL
  2. Запускаем веб-сервис командой   
  ```  
  java -jar aqa-shop.jar --spring.profiles.active=postgresql
  ```    
   * страница веб-сервиса находится по адресу    
   ```
   http://localhost:8080
   ```   
  3. Запускаем симулятор банковских сервисов:
   * переходим в каталог _gate-simulator_ командой      
   ```
   cd gate-simulator
   ```   
   * запускаем симулятор сервисов командой      
   ```
   npm start
   ```   
  4. Запускаем авто-тесты в каталоге _diplom_ командой     
  ```
  ./gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app allureReport
  ```      
  5. Формируем отчет в _Gradle_ командой 
  ```
  ./gradlew allureServe
  ```   
#### После проведения авто-тестов останавливаем работу приложения _Docker_   
  ```
  docker-compose down
  ```  

  
