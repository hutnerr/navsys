## Overview

NavSys is a Java-based GPS navigation application built entirely from scratch, providing real-time location tracking, route computation, and dynamic navigation updates. The system integrates directly with a USB GPS receiver to process live satellite data and deliver accurate positioning.

**Tech Stack:**
- Java (JavaFX & Swing, Object-Oriented Programming)  
- GPS Integration (GPGGA sentence parsing, real-time satellite data)  
- Data Structures: Graphs, Trees  
- Algorithms: Dijkstra's & Bellman-Ford  
- Design Patterns: MVC, Observer, Strategy  

## Showcase

NavSys delivers a fully functional navigation experience, combining real-time GPS data with efficient routing algorithms and responsive UI updates.

**Key Features:**
- Real-time GPS tracking via USB satellite receiver with custom GPGGA parser  
- Graph-based road network modeling with streets, intersections, and segments  
- Pathfinding using both Dijkstra's and Bellman-Ford algorithms with performance comparison  
- Automatic route relocation when deviating from the current path  
- Multithreaded architecture separating GPS data processing from UI rendering  
- Clean system design using MVC, Observer, and Strategy patterns  
- Interactive interface for destination input and route visualization  

**How It Works:**
1. GPS data is received and parsed into live location updates  
2. The road network is modeled as a graph structure  
3. A pathfinding algorithm computes the optimal route  
4. The system continuously updates location and adjusts the route if deviations occur

![](https://www.hunter-baker.com/assets/projects/navsys/navsys.gif)

![](https://www.hunter-baker.com/assets/projects/navsys/navsystemdesign.svg)
