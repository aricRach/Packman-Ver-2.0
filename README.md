# **EX4-Project**

Our project is a pack-man game that include fruits and pack-mans and some obstacles such as:
boxes and ghosts each of them has a unique data that influence the moving of the game.
The goal of the game is to calculate the time taken it takes for our player to eat the fruits
with the option to eat the other packmans. The score is determined by some rules:

- **Ghost:**
if ghost touch you your score decreasing by 20 points.
- **Box:**
if you enter to box your score decreasing by 1 point.
- **Packman:**
if you eat other packman your score increasing by 1 point.
- **Fruit:**
if you eat fruit your score increasing by 1 point.

## *Automatic program:*
###### *Main Algorithm idea:*

###### *Build Graph*
Check all the optional lines between Player, all fruits and all boxes vertexes, then check between any 2 Points if there is valid line then add this line to the graph.

###### *Dijakstra Algorithm*
gives us the shortest path between two points in the graph.

###### *Proceed to Fruit*
After we received the shortest path, we iterate the Path points and calculate azimuth between Player Location to  each point in the path, if we reach first point in the path we proceed to next path point, finally we eat the fruit and calculate again where is located the closer fruit

###### *Find closest fruit*
By using Dijakstra Algorithm we can calculate the distance between Player position to fruits positions and find the closest fruit.

## *More details about automatic program (include checks):*
Firstly, we set location to our Packman Player at the same place of the first fruit in our ArrayList.

Then we check all the optional lines between our player location to all boxes vertexes,
in addition we check all the fruits to all boxes edges vertexes.

## *Our Algorithm checks contain some checks:*

- We check if one of the positions (source and destination) is inside other boxes
then we don’t want to check those points.

- If the source and destination points are 2 opposite edges of the same box
(left down corner and right up corner or left up corner and right down corner)
then we don't want to check line between those points.

- If this vertical line then we call to function "ValidLine" for be sure there is no box between the y-Axis ranges.

###### *ValidLine function*
Check equation of line between source and destination and check if one of the boxes is cut the stretch line.

###### *BuildGraph function*
If all checks passed between 2 points then we use our Function addToGraph.

###### *AddToGraph function*
For every 2 points that passed all the checks we addEdge to our graph with the distance between those points.

## *Human program:*
There is another optional to play Packman-Game by your mouse.

###### *Player location*
In the Human program you choose where to locate the Player considering where is the best
place to eat all fruits in the shortest time.

###### *Start the Game*
Immediately when you choose the player location, the game start and
your player start move to your mouse location on the Screen.
