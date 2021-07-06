# Subspace-Shooter
2D shooter game in java using swing and AWT

# Level-Structure Design
<img src="https://user-images.githubusercontent.com/43778235/124568994-44119c00-de65-11eb-8ead-1c9f30efe60c.png">

The main layout of the game is roughly designed pixel-per-pixel using a 64x64 grid in a paint software (eg: MS-paint). The sketch is then exported to a png file and this png file is then imported to the game. 
The pixels of the png file is read and stored using a double for-loop. Depending the values of the pixels stored, the main view of the game is render. 


# Sprite-Sheet
![sprite_sheet](https://user-images.githubusercontent.com/43778235/124569310-8d61eb80-de65-11eb-92be-4bb7cffe9029.png)
</br>
The spritesheet used for the game is a 192x64 pixel png image with 3 static images:
1. Player image of size 32x48, 
2. 3 static images of size 32x32 of the enemy and 
3. 3 other 32x32 images for the block, crate and wall.

# Modules of the game / Java Classes

The game is developed in java and its design is completely object oriented where there are separate classes and modules to handle components of the game, thus making the game program highly scalable and re-usable.

The module-structure of the game looks like this:

### Main Game module:
This is the main central class of the game, this is the class where the main method of the project is present. Thus, the initialization of all the game variable and objects, initialization of the game window, running of the main loop of the game and main rendering and tick methods of the game are defined in this module.

### Window module:
This is where the java swing JFrame window is initialiazed. Also, the main soundtrack playback of the game is handled here.

### GameObject module:
This is the abstract class that will be extended by all other game objects of the game (such as: Player, Enemies, Blocks, Bullets, etc…). This class contains all the common attributes and methods of all the game objects.



### Handler module:
This is where the game object initialization takes place. An arraylist of game objects is created here which stores all the game objects of the game. It also defines and calls the tick( ) and render( ) methods for all the game objects.

### Game objects (enemies, player, crates, blocks and bullets):
The definition for all of the game objects is done separately in their own modules and the user input is binded with the game objects to manipulate the objects based on user input.

### MouseInput and KeyInput module:
These modules are responsible to handle the game objects and their attributes based on the user’s input.

### Image loader and sprite sheet generation module:
The main layout of the game is based on a structure known as sprite sheet. This sprite sheet is generated from an uploaded png file and is loaded in the game’s render( ) method.

### Camera Module:
The view port of the game has to move along with the player. Thus, this activity is handled by the camera module which controls how much of the screen is visible at a time.

### Animation module:
The game objects: player and enemy are animated using this animated module. The animation is done using 3 static images from the sprite sheet.


# How to play

To start the game, you must compile and run the <strong>Game.java</strong> class in the src folder.

#### Once the game has started: 
1. You can aim the gun with your mouse and press the left-click button to shoot.
2. An enemy dies when it is hit twice by the bullet.
3. You lose 25 health when you collide with an enemy. (Your total health is 100, it is shown as a HUD in top left corner)
4. You have a limited amount of ammo, the remaining ammo is also shown as HUD in the top left corner.
5. There are lootboxes and health crates in random places of the room which can be picked up to refill ammo and regenerate health.



