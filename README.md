ex3 – Readme
This Project is an assignment number three for an oop Class in Ariel University.

The project is a pacman game variation where you have multiple pacman and fruit. the pacman have to eat the fruits in the fastest time.

In order to Start To Project you need to start the Main class under the GUI package.

This Will Open Jframe window with Ariel map on it. you can either create your own game on the map or load an existing one from a csv file. in order to create new  game all you need to do is press right click on the map to create pacman and left click to create fruit.
on the menu bar u can load game from a csv file, save a game to a new csv file or save your played game to a kml file. you can also start and restart the game.

Ex4 - Readme

in this Exercise we Add an Addition game that you can access Through Ex4main class. in this game you have one pacman in the color magenta that is your player. beside him you have a number of "enemy" pacman on the board and a number of ghosts and offcourse fruits. you also have barrier on your way which stop you from moving.
your objective is to gain the highst score. you can gain points by either eat fruits, eat the enemy pacman and finishing the game early.
you can loss points if you get touch by a ghost ,enter inside a barrier or leave the game map bounderies.
in the beginning of the game you can select which game you want to play in our "load csv" option in our menu in the upper left side of the screen. you have 9 option of game to choose from with diffrent number of pacmans, ghosts and barriers.
in the beggining you can also choose where to put your player, the player have a default location but you can choose to move it if you want. you move it by clicking with your mouse on where you want to put it.
now you have two option you can play it on manual mode or Automate.
in Manual mode you can control your player using the mouse button. you can push on the map to which direction you want it to go.
in The Autmate game your player move on it own using The dijkstra algorithm to choose the path to the fruits. 

in order to run this game you need two jars That locate in Ariel university moddel. They are also locate in The JAR folder in the project:

1.Ex4_v0.2.jar


2.Graph_v0.11.jar

For this Assigment we create a number of new classes. most notably:

Ex4Algo:
this class that resign in the Algorithm package contain all the logic we used in this Assigment.
The Most importent Aspect of this class is to Create a graph decribing our game and compute the shortes path from our player to the fruits in the game. we create a grpah and we put our player ,all the fruits and all the edges of the barriers that are not inside a diffrent barrier as edegs in our graph.
than we calculate all of the Vertices in the graph. we check every potential Vertex and make sure that its not going through any barrier in the way.
than we used the dijkstra algorithm in order to look for the shortes path from our player to any fruit.

sql:
this class that resign in the database package used the connect to the database in the cloud and compare our last game resolt to our earlier games and to the games of the rest of the class that played on the same map. the main function in this class called "PrintFromDatabase" and what it does is it first connect to the database and pull the result of our last game and the ID of the map we play. than its pull all of the game we played on the same ID and compare it to the latest game. after that it does the same to all the game played on the same ID on the database. in the end we print on to the console what placed we came comapre to our previous game and compare to the rest of the databse.


example of game number 9:
![game number 9](https://user-images.githubusercontent.com/20860993/50909107-d28f3900-1433-11e9-8abe-d165322a55ea.PNG)


example of game number 5:
![game number 5](https://user-images.githubusercontent.com/20860993/51048196-fccb2d00-15d2-11e9-8db3-99e3f3b1705a.PNG)


example of game number 2:
![game number 2](https://user-images.githubusercontent.com/20860993/51048267-2d12cb80-15d3-11e9-9936-4936e770a47a.PNG)
