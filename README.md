VoxelDungeon
============

A voxel-based dungeon exploring game. A 3d Minecraft-esque game that focuses on action, role-playing, combat, and dungeon crawling rather than building and mining. Started at Code Day Seattle, using LWJGL OpenGL.


Made by Shreyas and Masilan.

By the way one of the causes of a "A fatal error has been detected by the Java Runtime Environment: EXCEPTION\_ACCESS\_VIOLATION" is supplying too many vertices to be drawn using VBO's, causing the graphics card to look for more memory than what exists.


Branches:

master: Latest stable copy.

VBO-testing: A branch identical to master but with VBO's.

VBO-testing-testing: A branch to test drawing object(s) using VBO's.

VBO-testing-testing-dynamic: A branch to test rendering of changing objects.

tutorial-copy-pastes: A branch to copy paste tutorials and test opengl stuff.


Time of things in render (in order of time taken):

  Binding buffers.
  
  Creating position/color arrays.
  
  Creating position/color buffers.
  
  Adding arrays to buffer.
  
  Drawing buffers.
  
  Flipping buffers.

