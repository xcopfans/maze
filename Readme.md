本类包含：
 > 三个成员变量：
 	* String[][] maze = null;
	* int width = 0; //网格横坐标
	* int height = 0; //网格纵坐标

 > 五个私有方法：
 	* private void checkFormat(String command1, String command2)；
 		检查输入的格式是否正确

	* private void checkCell(int cell_1_x, int cell_1_y, int cell_2_x, int cell_2_y)；
		根据坐标检查两个网格之间是否能连通

	* private void createMaze(String command)；
		创建指定大小的初始迷宫

	* private void connectMaze(String command)；
		根据输入命令连通相应的网格

	* private void printMaze()；
		输出迷宫

测试用例：
	@Test
	public void fun(){

		Scanner s = new Scanner(System.in);
		String command1 = s.nextLine();
		String command2 = s.nextLine();
		
		checkFormat(command1, command2);
		createMaze(command1);
		connectMaze(command2);
		
		printMaze();	
		
	}

输入：
3 3
0,1 0,2;0,0 1,0;0,1 1,1;0,2 1,2;1,0 1,1;1,1 1,2;1,1 2,1;1,2 2,2;2,0 2,1

输出：
[W][W][W][W][W][W][W]
[W][R][W][R][R][R][W]
[W][R][W][R][W][R][W]
[W][R][R][R][R][R][W]
[W][W][W][R][W][R][W]
[W][R][R][R][W][R][W]
[W][W][W][W][W][W][W]

输入：
4 4
0,1 0,2;0,0 1,0;0,1 1,1;0,2 1,2;1,0 1,1;1,1 1,2;1,1 2,1;1,2 2,2;2,0 2,1

输出：
[W][W][W][W][W][W][W][W][W]
[W][R][W][R][R][R][W][R][W]
[W][R][W][R][W][R][W][W][W]
[W][R][R][R][R][R][W][R][W]
[W][W][W][R][W][R][W][W][W]
[W][R][R][R][W][R][W][R][W]
[W][W][W][W][W][W][W][W][W]
[W][R][W][R][W][R][W][R][W]
[W][W][W][W][W][W][W][W][W]

输入：
3 3
1,1 1,2

输出：
[W][W][W][W][W][W][W]
[W][R][W][R][W][R][W]
[W][W][W][W][W][W][W]
[W][R][W][R][R][R][W]
[W][W][W][W][W][W][W]
[W][R][W][R][W][R][W]
[W][W][W][W][W][W][W]
	