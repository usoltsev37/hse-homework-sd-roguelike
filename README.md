# Архитектура игры GTAVI

## Разработчики

* Илья Смирнов
* Владимир Федоров
* Никита Усольцев

## Общие сведения о системе

### Назначение

Игра в жанре [Roguelike](https://ru.wikipedia.org/wiki/Roguelike).

### Границы системы

- Однопользовательская оффлайн игра
- Управление с помощью клавиатуры
- Отсутствует возможность сохранения прогресса
- Консольная графика
- Случайная генерация Grid-карты, есть возможность загрузки из файла
- При генерации карты в определенных местах создаются монстры и предметы с точностью до случайной окрестности
- Отображение здоровья, панели быстрого доступа к предметам
- Отдельное окно для характеристик персонажа и полного инвентаря по нажатию на заданную клавишу

### Контекст системы

- Приложение запускается непосредственно на компьютере пользователя в терминале, никакие сетевые взаимодействия не
  предусмотрены
- Приложение нацелено на поддержку наиболее популярных среди целевой аудитории операционных систем

## Ключевые требования

### Технические ограничения

- У разработчиков отсутствуют машины под операционной системой Windows
- Выбранный язык программирования должен иметь библиотеку для отрисовки консольной графики

### Бизнес-ограничения

- Срок выполнения проекта - 2 месяца
- Игра распространяется бесплатно, наши вложения минимальны - пользуемся open-source фреймворками

### Качественные характеристики системы

- Расширяемая система с возможностью добавлять новые игровые сущности: персонажи, монстры, предметы, карты
- Сопровождаемость игры является ключевым фактором для дальнейшего развития игры, этому будет уделено существенное
  внимание
- Система будет настолько производительна, насколько это возможно не в ущерб сопровождаемости
- Гарантируется, что все файлы игры безопасны и личные данные пользователя никак не используются

### Ключевые функциональные требования

- Консольная графика
- Персонаж игрока, способный перемещаться по карте, управляемый с клавиатуры
- У персонажа есть характеристики (здоровье/сила атаки)
- На поле расположены различные предметы, которые персонаж может подобрать и использовать(надеть/снять)

## Роли и случаи использования

### Роли:

- Игрок
- Разработчик игры
- Дизайнер карты

### Случаи использования

- Игрок хочет отдохнуть/отвлечься и сыграть в нашу игру. Он не хочет, чтобы игра отбирала у него много нервов. Поэтому
  уровни должны быть сбалансированы под характеристики персонажа.
- Дизайнер карты хочет иметь возможность создавать свои карты или сохранять наиболее удачные случайно сгенерированные
  карты. Он не хочет взаимодействовать с кодом. Поэтому необходимо предоставить интерфейс для сохранения и загрузки карт
  при помощи специальных файлов.
- Разработчик игры хочет поддерживать игру, внедрять новую функциональность. Он не хочет как-то модифицировать уже
  написанный код, он хочет добавлять свой. Поэтому игра должна быть спроектирована таким образом, чтобы у любого
  программиста была возможность легко добавить новую желаемую функциональность.

### Типичный пользователь

<p align="center">
  <img src="images/typical_user.jpg" width="1000" title="screen_authorization">
</p>

Джейсон - гений, миллиардер, плейбой, филантроп. Возраст: 54 года. Но мало кто знает, что он часто пользуется
терминалом. Он сильно устает от этого занятия и поэтому иногда ему необходимо отвлечься на 5-10 минут и повеселиться.
Правда работа не позволяет отвлекаться надолго, а компьютер не позволяет быстро запускать большие игры. Поэтому Джейсон
хочет в одну команду в терминале иметь возможность погружаться в совсем иной мир, где он будет сражаться с чудовищами и
носить тяжелую броню, а не писать bash скрипты.

## Композиция

<p align="center">
  <img src="images/composition.png" width="1000" title="screen_authorization">
</p>

Используем слоистую архитектуру, основанную на шаблоне Model-View-Controller.

### Input

Слушает нажатия клавиш с клавиатуры и отправляет соответствующие события в блок ```Controller```

### GameActivities

Отвечает за распределение по блокам  ```Model``` и ```View``` событий, связанных с перемещением игровых персонажей, а
также получением различных ресурсов

### InventoryActivities

Отвечает за распределение по блокам ```Model``` и ```View``` событий, связанных с просмотром инвентаря и использованием
предметов из инвентаря

### GameModel

Хранит состояние игрового сеанса

### MapGenerator

Генерирует случайную карту или загружает карту из файла

### MapView

Отображает игровую карту со всеми сущностями на ней

### InventoryView

Отображает инвентарь игрока, надетые предметы и характеристики персонажа

### InterfaceView

Отображает игровой интерфейс: характеристики персонажа, количество жизней, панель быстрого доступа

## Логическая структура

<p align="center">
  <img src="images/architecture_rouge.png" width="1000" title="screen_authorization">
</p>

### Input and Controller
* **Input** запускает основной цикл программы, следит за нажатием кнопок с клавиатуры, отправляет события в
  соответствующие **Activity*
* **GameActivity** обрабатывает события перемещения игровых персонажей, их взаимодействия
* **InventoryActivity** обрабатывает события, связанные с инвентарем

### Model
* **GameState** меняет состояние модели: положение героя, обновление карты и т.д. Предполагается реализация как
  синглтона.
* **Map** представляет собой список клеток, используется для сохранения конкретной карты
* **Cell** является частью карты. Хранит внутри свои координаты, а также имеет в себе информацию о содержании различных
  припасов и врагов
* **MapRepository**: класс для сохранения и загрузки карт из файлов
* **MapGenerator** случайно генерирует отдельные клетки или карту целиком
* **Movable**: базовый класс для сущностей, которые могут передвигаться по карте и атаковать друг друга
* **Hero**: класс главного героя, за которого будет играть пользователь. Наследуется от **Movable**
* **Enemy**: враг, с которым придется столкнуться главному герою на своем пути. Наследуется от **Movable**.
* **Item** предмет, который можно применить на героя
* **ConsumableItem** расходный предмет, восстанавливающий здоровье
* **EquipableItem** экипируемый предмет, может давать прирост к силе или здоровью. Можно одеть и снять предмет.
* **ItemType** тип предмета: 4 вида брони (могут быть экипированы одновременно) и 2 вида оружия (не могут быть
  экипированы одновременно)

### View
* **Drawable** интерфейс для класса, который может отобразиться на UI
* **MapView**, **InventoryView**, **InterfaceView** отображение карты, инвентаря и интерфейса на UI
* **ViewBuilder** интерфейс паттерна `Строитель` для отображений объектов игры
* **MapViewBuilder**, **InventoryViewBuilder**, **InterfaceViewBuilder** реализации интерфейса `ViewBuilder` для карты, инвентаря и интерфейса
* **GameWindow** игровое окно для отображения всего состояния
* **UIFramework** интерфейс для UI фреймворка
* **Terminal** реализация интерфейса `UIFramework` для терминала

## Взаимодействия и состояния
TODO
