# Subspace-Shooter
2D shooter game in java using swing and AWT

# Level-Structure Design
<img src="https://user-images.githubusercontent.com/43778235/124568994-44119c00-de65-11eb-8ead-1c9f30efe60c.png">

The main layout of the game is roughly designed pixel-per-pixel using a 64x64 grid in a paint software (eg: MS-paint). The sketch is then exported to a png file and this png file is then imported to the game. 
The pixels of the png file is read and stored using a double for-loop. Depending the values of the pixels stored, the main view of the game is render. 


# Sprite-Sheet
![alt text](https://github.com/[anuragsubedi]/[Subspace-Shooter]/blob/main/res/sprite_sheet.png?raw=true)
The spritesheet used for the game is a 192x64 pixel png image with 3 static images:
1. Player image of size 32x48, 
2. 3 static images of size 32x32 of the enemy and 
3. 3 other 32x32 images for the block, crate and wall.


