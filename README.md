# LP2A Project - Ludo Board
> UTBM Spring 2021

The LP2A project consists into developing an application, more precisely a game, by focusing mainly on the conception and the development of the application itself. The goal of this project is not to produce a game for the video-game market, but rather to focus on the Object-Oriented Programming concepts and their implementations. Therefore, the graphical interface should not be the priority of your project: it can be considered as a bonus.  

For the evaluation, you will be asked to provide the whole Java code, a written report and an oral presentation of your project.  

- You can provide with the Java code an executable (JAR archive will be fine). The Java code should be commented, and the organization and cleanliness of your code will also be evaluated.  
- The written report should present your project (and subject) and should present and discuss the architecture of your program and the conception choices you made, guided by the OOP principles and concepts.  
- You will present your project, its architecture, maybe implementations issues that you had to fix, etc. You also must make a demonstration of your game. This demo will be included in the 10 minutes of your presentation.  

You have the liberty to choose the API (Swing, JavaFX, AWT, etc.) for which you are more comfortable with for the graphical user interface.  

- **The project will be done individually or in a group of two students.**  
- **The presentation will be on Teams or you can make a video and attach it with the other required materials.**  
- **The deadline for the project submission is April 16 2021.**  

## 1. The Game Board
A Ludo **board** is square with a pattern on it in the shape of a cross, each arm is divided into three adjacent columns of six squares. The center squares form the home column for each color and cannot be landed upon by other colors. The middle of the cross forms a large square which is the “home” area, and which is divided into 4 home triangles, one of each color. The starting square, the starting block, the home triangle and all the home column squares are colored to match the corresponding player’s color.  

![](https://i.imgur.com/mcsdKMZ.png)

## 2. The Game Play

- [x] Each player chooses one of the 4 colors (green, yellow, red or blue) and places the 4 pieces of that color in the corresponding block.  
- [x] The players start the run of their pieces from the starting square.  
- [x] Players take turns in a clockwise order; highest throw of the die starts.  
- [x] Each throw, the player decides which piece to move. A piece simply moves in a clockwise direction around the track given by the number thrown. If no piece can legally move according to the number thrown, play passes to the next player.  
- [x] A player must throw a 6 to move a piece from the starting block onto the first square on the track. The piece moves 6 squares around the circuit beginning with the appropriately colored start square (and the player then has another turn).  
- [x] Colored squares and the star ones are defined as safe zones that protect the piece from being sent to the starting block.  
- [x] If a piece lands on a piece of a different color, the piece jumped upon is returned to its starting block except in case of safe zones.  
- [x] If a piece lands upon a piece of the same color, this forms a block. This block cannot be passed or landed on by any opposing piece.  

> More details about the rules can be found in this [video](https://www.youtube.com/watch?v=lns9TeKVebY&ab_channel=Mr.Animate).  
> Also you can install check the game “Ludo King” from AppStore and PlayStore.  

## 3. Your work
The aim of this project is to implement the Ludo game using JAVA respecting the original rules. The game will be played with two modes:
- [x] **4 Players**  
    This is easiest mode; it requires 4 players and the game play is managed according the players actions specified through the keyboard: throw the dice and select the piece to move.  
    
- [x] (not fully implemented) **1 Player vs 3 COMs (optional)**  
    This mode is played with one player and three computer ones “COM”. You need to make the three COMs capable of challenging the player. The COMs will try to follow the moves of the player and send its pieces back to the starting block. If they are ahead of the player, the COMs try to make their pieces reach the home first. You can develop your own strategy (artificial intelligence) to beat the player 😉.
