# Smart Restaurant Management System
Die Repository fokussiert sich auf das Lernen von Java, Spring Boot und Testing. 
Sowohl die Spring Boot Anwendung als auch die Datenbank werden in separaten Docker Containern bereitgestellt. 
Die Anwendung ist darauf ausgerichtet, eine reibungslose Verwaltung eines Restaurants zu ermöglichen, 
indem Kernfunktionen wie Tischreservierungen, das Abrufen der Menukarte sowie das einfache Aufgeben von Bestellungen integriert sind.

# Technologie
- Java / Spring Boot
- MySQL
- Docker

# Makefile
- make fresh, Stoppt die Container und baut diese neu. Der db-data Order wird hierbei nicht gelöscht
- make up, Startet die Container
- make down, Stoppt die Container

# How to Start
    cd into project
    make fresh
    make up

# Verfügbarkeit
## Backend
    127.0.0.1:8081
## Datenbank
    127.0.0.1:3306 

# Todo
## Reservierung
- Abfrage der Verfügbarkeit (passt die Anzahl von Personen zu einem Tisch)
- Reservieren 
- Bearbeiten von Reservierungen
- Löschen von Reservierungen

## Menu
- Abfragen der Menukarte
- Suchen
- Filtern? (z.B. Kategorie)
- Gesicherter Endpunkt für die Verwaltung von Artikel
    - Hinzufügen
    - Bearbeiten
    - Löschen

## Bestellung
- Abfragen von Bestellungen
- Suchen nach Bestellungen
- Anlegen von Bestellungen
- Gesicherter Endpunkt für die Verwaltung von Bestellungen
    - Löschen
    - Bearbeiten

## Authentication
- Registrierung
- Login

