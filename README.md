#**OOM Project: Interactive Polygon Area Calculator**

An interactive Java Swing desktop application that allows users to draw custom polygons, visualize them in real time, and compute their area using optimized mathematical algorithms.
Built with an intuitive GUI, the app supports user authentication, screen transitions, and responsive event-driven interaction.

**Table of Contents**

1.Overview

2.Features

3.Architecture & Project Layout

4.Component Responsibilities

5.Data Flow & Interactions

6.Installation & Run Instructions

7.Future Work / Enhancements





**Overview**

 ∙Purpose: An interactive polygon area calculator built with Java Swing.
 
 ∙Boots to a Login screen → Start/Welcome panel → Polygon drawing panel where users create polygons and see real-time area computation.
 
 ∙Architecture style: Small desktop MVC-ish Swing application (UI panels + drawing/model logic).
 
 ∙Current code mixes UI and control logic in panels but can be extended with a controller for clean separation.
 




**Features**

 ∙User authentication: signup and login screens with validation.
 
 ∙Interactive polygon drawing and area calculation.
 
 ∙Splash/start screen with project info and team members.
 
 ∙Responsive UI with color theme (black/golden-yellow) and styled fonts.
 
 ∙Real-time feedback and event-driven interaction.
 
 ∙Architecture & Project Layout

 
 

 
**Typical folder structure:**


    src/
     ├─ gui/
     │   ├─ Start.java         // Welcome panel, timer, navigation
     │   ├─ Login.java         // Login panel
     │   ├─ Plot.java          // Drawing surface, polygon creation, rendering
     │   └─ Controls.java      // Optional toolbar/buttons for Plot
     ├─ model/
     │   ├─ PolygonModel.java  // Polygon vertex list, validation, area computation
     │   ├─ Point2D.java       // Point class or use java.awt.Point
     │   └─ AreaCalculator.java// Shoelace formula and utility routines
     ├─ controller/
     │   └─ AppController.java // Manages panel transitions, timers, app state
     ├─ util/
     │   └─ IOUtils.java       // Optional: save/load/export polygons
     └─ Main.java              // Optional single entry point

 
 




**Component Responsibilities**

∙Start (UI panel)
 
  ∙Display project title, description, team members.
    
  ∙“NEXT” button and optional auto-advance timer to go to Plot panel.
    
∙Login (UI panel)
 
  ∙Collect username and password.
    
  ∙On successful login, transition to Start panel.
    
∙Plot (UI panel / canvas)
 
  ∙Handle mouse events to add/remove vertices.
    
  ∙Render polygons and display computed area in real time.
    
  ∙Expose methods for UI colors, polygon data, and control buttons.
    
∙PolygonModel (Domain)

   ∙Stores ordered list of vertices.
   
   ∙Computes area (shoelace formula), perimeter, and validity.
   
   ∙Supports serialization for saving/loading polygons (optional).
   
∙AppController (Optional but recommended)

   ∙Manages transitions between panels (Login → Start → Plot).
   
   ∙Holds application state, theme colors, and timers.
   





**Data Flow & Interactions**

    Startup:
       ∙Main → SwingUtilities.invokeLater → instantiate JFrame → show Login screen.
    Login → Start:
     ∙Successful login replaces frame content with Start panel.
    Start → Plot:
     ∙“NEXT” button or timer transitions to Plot panel.
    Plot Panel:
     ∙Mouse events update PolygonModel.
     ∙PolygonModel.area() called to compute area → Plot repaint → area label updated.


**Minimal sequence diagram (text):**

    Main -> JFrame -> show Login
    User -> Login (login success) -> Start panel shown
    User -> Start (NEXT or Timer) -> Plot panel shown
    User -> Plot (mouse clicks) -> PolygonModel updated -> AreaCalculator -> Plot repaint -> update area label

    


**Installation & Run Instructions**

Prerequisites:
 ∙Java JDK 8 or higher installed.
 ∙IDE (Eclipse, IntelliJ) or terminal/command line.

Compile:

  ```javac -d bin src/**/*.java```


Run:

```java -cp bin gui.Start```




**Future Work / Enhancements**

 ∙Save/load polygons using serialization or file export (JSON or CSV).
 
 ∙Undo/redo vertex actions.

 ∙Export polygons as images.
 
 ∙Support additional shapes or measurement units.
 
 ∙Improve separation of UI, model, and controller (pure MVC).
 






