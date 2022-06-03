/********
Nick Luo & Ray Zeng
All group members were present and contributing during all work on this project.
We have neither received nor given any unauthorized aid in this assignment.
********/
ReadMe:
1, Students: Nick Luo(Haiyu) Ray Zeng(Tianrui)

2, Name of files: EightPlayer.java, Node.java

3, Possible bugs: 
For some reasons, for some cases we get the A* has more nodes than the BFS, maybe the A* search has some inefficient implementation.

4, Filled-in table
Case           Numbers of moves	 Numbers of nodes generated		
				BFS	A*(Manhattan) 	A* (Col Row Check) 
1. 138742065	12		2278	1863		1863
2. 123456078	2		13	9		9
3. 123460758	3		29	18		18
4. 412053786	5		66	86		86
5. 412763058	8		294	413		413
6. 412763580	10		1062	806		806
7. 134805726	unsolvable(no)	no	no		no
Average for all iterations	4766	644		1603

5, Our new A* heuristic was inspired by the two heuristic methods discussed in class:
For each tile in the board, check whether this tile is on the correct column where the number in the tile suppose to be.
If it is not, then we add 1 to our heuristic value.
Same for the row that if it is not on the row that it suppose to be, add 1 to our heuristic value.
Note that this heuristic may look very similar to Manhattan distance, but this is because the board for this problem (3*3), so it 
seems to be similar, but if we expand the scale of the problem such as 15 puzzles, then this will be quite different from Manhattan distance.
This heuristic is admissible because given Manhattan Distance is admissible, this heuristic will only generate smaller heuristic than Manhattan
Distance.

For example, in the example we discussed in class: 12453678.
Manhattan Distance will be MD(4)=3 MD(5)=1 MD(3)=2 MD(x)=0 Total will be 3+2+1=6
My Heuristic will be h(4)=2 h(5)=1 h(3)=2 h(x)=0 Total will be 2+1+2=5

 
