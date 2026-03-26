# MovieBrowser

Android-приложение для просмотра популярных фильмов, открытия подробной информации и управления избранным.

## Что реализовано

- Главный экран с лентой фильмов из TMDB (`popular`)
- Карточка фильма: название, постер, краткое описание, кнопка избранного
- Переход на экран деталей по нажатию на карточку
- Экран деталей: название, постер, полное описание, рейтинг, год выпуска, длительность, жанры
- Переключение статуса избранного с экрана списка и деталей
- Отдельный экран избранного с удалением и переходом в детали
- Персистентность избранного через `DataStore`

## Архитектура

Проект организован по feature-first с разделением на UI и сервисный слой:

- `features/*/presentation` - Compose-экраны и ViewModel
- `shared/services` - интеграция с API и локальное хранилище
- `shared/abstraction` - интерфейсы сервисов
- `shared/modules` - DI-модули Hilt
- `ui` - root layout и навигация

Технологии:

- Kotlin, Jetpack Compose, Material 3
- Navigation Compose
- Hilt (DI)
- Retrofit + OkHttp
- DataStore (Preferences)
- Coil

## Конфигурация

В корне проекта используются файлы:

- `applicaiton.dev.properties`
- `applicaiton.properties`

Ожидаемые ключи:

- `TMDB_API_KEY`
- `TMB_API_URL`
- `TMB_API_IMAGES`

## Запуск

```bat
gradlew.bat :app:assembleDebug
```

## Тесты

Локальные unit-тесты:

- `app/src/test/java/com/leopold95/moviebrowser/shared/ReleaseDateFormatterTest.kt`
- `app/src/test/java/com/leopold95/moviebrowser/features/home/presentation/viewmodels/HomeViewModelTest.kt`
- `app/src/test/java/com/leopold95/moviebrowser/features/details/presentation/viewmodels/DetailsViewModelTest.kt`

Запуск:

```bat
gradlew.bat :app:testDebugUnitTest
```
