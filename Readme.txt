This is my research folder for the open math problem for tiling the unit square with dimensions of 1/n by 1/(n+1). 
My work was inspired and done in collaboration with PhD Peter Hazard, as part of the University of Toronto's metorship program.

Mathematically, the sum of the areas, 1/1*1/2, 1/2*1/3, 1/3*1/4, ... can be shown to approach 1. We are investigating whether it is 
possible to tile a unit square (1x1) with tiles of dimensions 1/1*1/2, 1/2*1/3, 1/3*1/4, ...

We started by developing some hypotheses for tiling the tiles, and then worked on developing a systematic way to tile, tiling in decreasing
order of size. I then checked for the plausibility of tiling strategies by coding a computer simulation that includes a visual graphic of the
tiling process. In this my program, under Tiling-Square, the algorithm goes through all the orientations of tiles possible, given that the 
tiles are adjacent to previous tiles and do not go into the upper right region of other tiles. If it cannot keep tiling with the set rules,
the program will backtrack and try another method, using a depth first search. 