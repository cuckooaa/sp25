## Project 3: Design Document
### Data Structures
arrayList<room> roomList; the room is a class that contains constants x and y which refer to a position inside the room. After all the rooms have been generated, the "room" in the list 
links each other through the hallway in order.

Data structures you are planning to use and why. Include any potential helper classes here. If you think something will require code repetition, state how you will mitigate it.
For example, if this were for Project 1B:
I will use a 1D backing array to contain the items in my deque.

### Algorithms
Pseudocode and general logic behind your implementation, and why. Include any potential helper methods here. If you think something will require code repetition, state how you will mitigate it.
World Generation (Task 3)
Firstly, i will write a method that randomly generate a room(actually it's just a rectangle). And then repeat run the method several times. For each time, i will return a random position 
inside the room to a arrayList which will be used to link rooms later.
Main Menu (Task 4)
creat a world instance to generate a random world.
Interactivity (Task 5)
…
HUD (Task 6)
…
Saving & Loading (Task 7)
…

