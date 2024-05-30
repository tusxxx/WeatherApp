# Project: WeatherApp

## Description

WeatherApp is an Android application for retrieving and displaying current weather and forecasts based on data from the OpenWeather API. The project demonstrates the use of the MVVM (Model-View-ViewModel) architectural pattern, Clean Architecture principles, and a multi-module architecture for scalability and flexibility.

## Features

- Display current weather for the selected cities.
- Weather forecast for several days ahead.
- Search and add cities (from Firebase remote config) to track weather.
- Automatic data update from the OpenWeather API server.
- Notifications

## Tech Stack

- **Programming Language**: Kotlin
- **Architecture**: MVVM, Clean Architecture
- **Modularity**: Division into modules for better code readability and testability
- **API**: OpenWeather API, integration via Retrofit
- **Asynchronous Programming**: Coroutines, Flow
- **Libraries**: Hilt for dependency injection, LiveData, ViewModel

## Project Structure

The project is divided into several modules:

- `app`: the main application module containing the main UI and navigation.
- `data`: a module for data handling.
- `feature`: a module for UI functionalites
- `common`: a module for common utilities and helper classes.

## Demonstration
![Screenshot_20240531_001046](https://github.com/tusxxx/WeatherApp/assets/91781511/daefcdcd-3d77-4601-9cb3-f399387c637b)
![Screenshot_20240531_001059](https://github.com/tusxxx/WeatherApp/assets/91781511/96243985-dd65-430c-8b35-2b1bec0d1fb9)
![Screenshot_20240531_001106](https://github.com/tusxxx/WeatherApp/assets/91781511/74597921-2bc0-4f60-ab58-0cf0904ed0ec)

## Authors

- [Ignat Mustafaev](https://github.com/tusxxx) — project development and maintenance.

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---

# Проект: WeatherApp

## Описание

WeatherApp — это Android-приложение для получения и отображения текущей погоды и прогноза на основе данных от OpenWeather API. Проект демонстрирует использование архитектурного паттерна MVVM (Model-View-ViewModel), принципов Чистой Архитектуры и многомодульной архитектуры для масштабируемости и гибкости.

## Функциональность

- Отображение текущей погоды для выбранного города.
- Уведомления
- Поиск и добавление городов для отслеживания погоды (из Firebase Remote Config).
- Автоматическое обновление данных с сервера OpenWeather API.

## Стек технологий

- **Язык программирования**: Kotlin
- **Архитектура**: MVVM, Чистая Архитектура
- **Многомодульность**: разделение на модули для улучшения читаемости и тестируемости кода
- **API**: OpenWeather API, интеграция через Retrofit
- **Асинхронное программирование**: Coroutines, Flow
- **Библиотеки**: Hilt для внедрения зависимостей, LiveData, ViewModel, Room

## Структура проекта

Проект разделён на несколько модулей:

- `app`: главный модуль приложения, содержащий основной UI и навигацию.
- `feature`: модуль, содержащий UI специфичные функциональные требования.
- `data`: модуль для работы с данными, включая репозитории и источники данных (API, базы данных и т.д.).
- `common`: модуль для общих утилит и вспомогательных классов.

## Демонстрация
![Screenshot_20240531_001106](https://github.com/tusxxx/WeatherApp/assets/91781511/0ee3238e-56ac-4f98-b90b-4e6ea9fd8ef5)
![Screenshot_20240531_001059](https://github.com/tusxxx/WeatherApp/assets/91781511/b6bcd15c-70b4-4cf0-8ce8-32d9c60994a8)
![Screenshot_20240531_001046](https://github.com/tusxxx/WeatherApp/assets/91781511/e5999037-9364-47ad-87b2-44a8c83f57b6)

## Авторы

- [Игнат Мустафев](https://github.com/tusxxx) — разработка и поддержка проекта.

## Лицензия

Этот проект лицензируется на условиях MIT License — подробности в файле [LICENSE](LICENSE).
