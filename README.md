# SortGame

<img src="https://user-images.githubusercontent.com/28150646/184842252-de289b17-2301-425d-ab79-34ee07dde39a.gif">

This game is not really finished. 
At the moment all players play the same game status, meaning all players see the same moves. 
Nothing happens when a game is won. 
Players can get a hint but it is not properly displayed. 
At the end of the technical part there is description of the planned next steps. 

## Usage guide
I recreated a popular game i like to play on mobile myself. 
It is called water color sort or something similar there. 
This version is of course without ads. 
The game is simple: click two containers, the first to move away from and the second as target. 
All blocks with the same color on top of the from container will be moved on top of the target container if the move is allowed. 
The move is allowed if the target container is empty or the top block of the target container has the correct color. 
The goal of the game is to have all colors sorted, meaning for each color there is one container only filled with this color. 

## Technical

![image](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![image](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![image](https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white)
![image](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white)
![image](https://img.shields.io/badge/Material%20UI-007FFF?style=for-the-badge&logo=mui&logoColor=white)
![image](https://img.shields.io/badge/Heroku-430098?style=for-the-badge&logo=heroku&logoColor=white)
![Netlify](https://img.shields.io/badge/netlify-%23000000.svg?style=for-the-badge&logo=netlify&logoColor=#00C7B7)
![image](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![image](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)

The game/level data is saved in a MongoDB. 
A game object consists of the id, some game properties, the initial playing field and the actual playing field, 
where a playing field holds the containers with the color information. 
The most challenging part was developing the data structures and a solving algorithm for the game. 
This algorithm is necessary because it is used for creating levels. 
New levels are randomly generated and then checked for solvability.
Also getting a hint is very important because the game can be really really hard. 
