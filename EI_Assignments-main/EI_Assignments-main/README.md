# Educational-Initiatives_TCE26 
**Ananth N**  

- **Email:** anxnth@gmail.com  
- **GitHub:** [https://github.com/anxnth-byte](https://github.com/anxnth-byte)  
- **College/Institute:** Thiagarajar College of Engineering  

---

## About Me
I am a passionate software developer learning and implementing Java design patterns.  
This repository showcases my understanding of behavioral, creational, and structural design patterns with practical examples.

# Design Patterns in Java (Exercise 1)

## 🎯 Overview
This project demonstrates practical use cases of **Software Design Patterns** in Java. It covers:

- **Behavioral Patterns:** Observer, Strategy  
- **Creational Patterns:** Singleton, Factory  
- **Structural Patterns:** Adapter, Composite  

Each pattern is implemented with unique and real-world inspired examples to showcase understanding and practical application.

---

## Table of Contents
1. [Observer Pattern](#observer-pattern)  
2. [Strategy Pattern](#strategy-pattern)  
3. [Singleton Pattern](#singleton-pattern)  
4. [Factory Pattern](#factory-pattern)  
5. [Adapter Pattern](#adapter-pattern)  
6. [Composite Pattern](#composite-pattern)  

---

## Observer Pattern
**Scenario:** A smart fridge that notifies the user when items are running low.  
- `SmartFridge` → Subject  
- `User` → Observer  
- Demonstrates dynamic notification of state changes.

---

## Strategy Pattern
**Scenario:** A music player with multiple playback modes.  
- `PlaybackStrategy` → Interface  
- `NormalPlay`, `ShufflePlay`, `RepeatPlay` → Concrete strategies  
- `MusicPlayer` uses interchangeable strategies for different behaviors.

---

## Singleton Pattern
**Scenario:** Logging service for a cloud application.  
- Ensures a single global `Logger` instance is used across the application.

---

## Factory Pattern
**Scenario:** Drone creation for a smart delivery system.  
- `DroneFactory` creates different types of drones (`CargoDrone`, `SurveyDrone`) without exposing object creation logic.

---

## Adapter Pattern
**Scenario:** Connecting a new payment gateway with an old payment system.  
- `PaymentAdapter` allows the new system to communicate with the legacy system without modifying it.

---

## Composite Pattern
**Scenario:** Virtual file system with files and folders.  
- Treats individual `File` and `Folder` objects uniformly.  
- Demonstrates tree-like hierarchical structures.
---
# Smart Office Facility Manager (Exercise 2)

## 🎯 Overview
This project implements a **console-based application** for managing a smart office facility, including conference room bookings, occupancy detection, and automated controls.  

It directly addresses the requirements of the coding exercise by focusing strictly on **logic, code quality, design patterns, and SOLID principles**.  

The application is built in **Java (Maven-based)** and is designed for **high maintainability and extensibility**.

---

## ✨ Design Philosophy: The Six Design Patterns
This solution integrates six core design patterns to demonstrate **two behavioral, two creational, and two structural patterns**.

| Pattern Type | Pattern                   | Component / Use Case             | Rationale / Requirement Met |
|--------------|--------------------------|----------------------------------|------------------------------|
| Creational   | **Singleton**             | SmartOfficeHub & Logger          | Ensures only one instance manages global state and logging. |
|              | **Factory Method**        | RoomDeviceFactory                | Creates and wires LightController and ACController to rooms, adhering to OCP. |
| Behavioral   | **Command**               | ICommand & Concrete Commands     | Encapsulates user requests (Booking, Config, Stats) for flexible, traceable operation. |
|              | **Strategy**              | FiveMinuteTimeoutStrategy        | Encapsulates the 5-minute booking auto-release rule (Req. 4), promoting flexibility. |
| Structural   | **Observer**              | MeetingRoom (Subject) & Controllers | Automates lights/AC based on occupancy status (Req. 5). |
|              | **Proxy**                 | AuthenticatedCommandProxy        | Controls access to secured commands (Config, Block, Cancel), demonstrating authentication (Optional Req. 2). |

---

## ⚙️ Project Structure
The project follows **best practices with logical separation of concerns** enforced by Java packages:

| Package     | Content Focus |
|-------------|---------------|
| **core**    | Central state (SmartOfficeHub), main entities (MeetingRoom, Session). |
| **commands**| User actions (BookRoomCommand, ConfigRoomCountCommand). |
| **controllers** | Devices and observers (IController, ACController, NotificationService). |
| **strategies**  | Business rule policies (IBookingReleaseStrategy, FiveMinuteTimeoutStrategy). |
| **adapters**    | Translates console input string into executable ICommand objects. |
| **proxies**     | Implementation of authentication proxy (AuthenticatedCommandProxy). |
| **services**    | Background tasks and helpers (BookingMonitorService, UserAuthenticator). |
| **utilities**   | Logger (Singleton) and custom exceptions. |

---

## 🔑 Gold Standards & Key Implementations

### ✅ Logging & Exception Handling
- **Logger (Singleton):** Tracks successes and errors.  
- **Custom Exceptions:** (e.g., `BookingConflictException`) ensure **user-friendly error output** while logging technical details internally.

### ✅ Transient Error Handling / Performance
- **BookingMonitorService** runs asynchronously using `ScheduledExecutorService` to manage time-dependent **auto-release (5 minutes)**.  
- Ensures main console remains responsive.

### ✅ Security (Proxy Pattern)
- **AuthenticatedCommandProxy** restricts access to secured commands.  
- Only **admin users** can execute configuration and booking operations.

---
