Projekt na zaliczenie - Aplikację można odpalić za pomocą ProductManager.jar

# Uzasadnienie użycia wzorców projektowych 

W przygotowanym rozwiązaniu zastosowano dwa wzorce projektowe: **Dekorator** i **Obserwator**. Wybór tych wzorców pozwolił na uzyskanie elastycznej, modularnej i skalowalnej architektury aplikacji.

## Wzorzec Dekorator
Wzorzec Dekorator został użyty do dynamicznego rozszerzania funkcjonalności klasy **DataProcessor**. Dzięki temu możemy łatwo dodawać nowe operacje przetwarzania danych, takie jak filtrowanie czy sortowanie, bez modyfikowania istniejącego kodu. 

### Przykłady dekoratorów:
- **PriceFilterDecorator** umożliwia filtrowanie produktów według zakresu cenowego.
- **SortByNameDecorator** i **SortByPriceDecorator** dodają możliwość sortowania produktów odpowiednio według nazwy i ceny.

Każdy z dekoratorów jest niezależny i może być łączony z innymi, co zapewnia dużą elastyczność przy budowaniu łańcucha przetwarzania danych.

## Wzorzec Obserwator
Wzorzec Obserwator został zastosowany do synchronizacji danych między modelem a interfejsem użytkownika. Klasa **DataSubject** przechowuje listę obserwatorów (implementujących interfejs **ProductObserver**) i powiadamia ich o zmianach w danych.

### Korzyści z zastosowania wzorca Obserwator:
- **Separacja logiki biznesowej od interfejsu użytkownika:** Klasa **ProductManagerGUI** otrzymuje informacje o zmianach w danych bez potrzeby bezpośredniego odwoływania się do logiki zarządzania produktami.
- **Reaktywność:** Każda zmiana w modelu danych (dodanie, usunięcie lub edycja produktu) automatycznie aktualizuje widok tabeli w GUI.


Zastosowanie wzorców Dekorator i Obserwator pozwoliło na zbudowanie elastycznej i łatwej w utrzymaniu aplikacji. Dekorator zapewnił możliwość dynamicznego rozszerzania funkcjonalności przetwarzania danych, natomiast Obserwator umożliwia synchronizację danych i aktualizację interfejsu w reakcji na zmiany w modelu.

