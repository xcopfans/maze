package cn.xc.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

public class Maze {

	String[][] maze = null;
	int width = 0; //网格横坐标
	int height = 0; //网格纵坐标
	
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
	
	/**
	 * 格式检测
	 *  command1：
	 *  	> 如果不包含空格，抛出Incorrect command format
	 *  	> 如果包含，按空格分割，若长度不为2，抛出Incorrect command format
	 *  		*若长度为2，且不能解析出两个大于0的整数，抛出 Number out of range
	 *  command2：
	 *  	> 如果不包含 ";" ，说明只有两个网格连接
	 *  	    * 再检测是否包含 " " ，没有就抛出Incorrect command format
	 *  		* 如果包含  " "，再检测是否包含"," ，没有就抛出 Incorrect command format
	 *  			- 如果包含"," ，再按按空格分割，检测长度是否为2，如不是抛出Incorrect command format
	 *  			- 如果长度为2，且不能能解析出两个整数，抛出Invalid number format
	 */		 
	private void checkFormat(String command1, String command2) {
		/**
		 * 检测command1，由于检测command2时需要用到width和height
		 *  所以检测正确后直接赋值
		 */
		if(command1.contains(" ")){ 
			String[] subCom1 = command1.split(" ");
			if(subCom1.length == 2){
				try{
					width = Integer.valueOf(subCom1[0]);
					height = Integer.valueOf(subCom1[1]);
					
					if(width < 2 || height < 2){
						System.out.println("Number out of range");
						throw new RuntimeException("Number out of range!");
					}
				}catch(Exception e){
					System.out.println("Incorrect command format");
					throw new RuntimeException("Incorrect command format!");
				}
			}else{
				System.out.println("Incorrect command format");
				throw new RuntimeException("Incorrect command format!");
			}
		}else{
			System.out.println("Incorrect command format");
			throw new RuntimeException("Incorrect command format!");
		}
		
		/**
		 * 检测command2，使用正则表达式
		 */
		String regex = "[0-width],[0-height] [0-width],[0-height]";
		if(command2.contains(";")){
			String[] cells = command2.split(";");
			for(String cell : cells){
				boolean flag = cell.matches(regex);
				if(flag == false){
					System.out.println("Incorrect command format");
					throw new RuntimeException("Incorrect command format!");
				}
			}
		}else{
			boolean flag = command2.matches(regex);
			if(flag == false){
				System.out.println("Incorrect command format");
				throw new RuntimeException("Incorrect command format!");
			}
		}		
	}
	
	
	
	/**
	 * > 检测两个网格是否能连接，如不能就抛出 Maze format error
	 * 		*当横坐标相等时，纵坐标之差必须为1或-1
	 *   	*当纵坐标相等时，横坐标之差必须为1或-1
	 */
	private void checkCell(int cell_1_x, int cell_1_y, int cell_2_x, int cell_2_y) {
		if(cell_1_x == cell_2_x){
			int result = cell_1_y - cell_2_y;
			if(result != 1 && result != -1){
				System.out.println("Maze fromat error");
				throw new RuntimeException("Maze format error!");
			}
		}else if(cell_1_y == cell_2_y){
			int result = cell_1_x - cell_2_x;
			if(result != 1 && result != -1){
				System.out.println("Maze fromat error");
				throw new RuntimeException("Maze format error!");
			}
		}else{
			System.out.println("Maze fromat error");
			throw new RuntimeException("Maze format error!");
		}
	}

	
	
	/**
	 * 建造元迷宫
	 * @param command
	 */
	private void createMaze(String command){
		if(width == 0 || height == 0) throw new RuntimeException("请先检查输入命令");
		
		maze = new String[width*2+1][height*2+1];
		for(int i = 0; i < width*2+1; i++){
			for(int j = 0; j < height*2+1; j++){
				if(i%2 != 0 && j%2 != 0 && i !=0 && j!=0){ //同时为奇数行与奇数列时输出[R]
					maze[i][j] = "[R]";
				}else{
					maze[i][j] = "[W]";
				}
			}
		}
	}

	
	/**
	 * 连通迷宫
	 */
	private void connectMaze(String command){
		if(maze == null) throw new RuntimeException("请先创建元迷宫");
		//将两两相连的网格放入List集合中
		String[] substr = command.split(";");
		List<String> conCells = new ArrayList<String>();
		for(String s : substr){
			conCells.add(s);
		}
		
		for(int i = 0; i < conCells.size(); i++){
			String[] conCell = conCells.get(i).split(" ");
			/**
			 * 取出两个相连的网格
			 */
			String cell_1 = conCell[0];
			String cell_2 = conCell[1];
			//取出它们的坐标
			String[] coords_1 = cell_1.split(",");
			String[] coords_2 = cell_2.split(",");
			int cell_1_x = Integer.valueOf(coords_1[0]);
			int cell_1_y = Integer.valueOf(coords_1[1]);
			int cell_2_x = Integer.valueOf(coords_2[0]);
			int cell_2_y = Integer.valueOf(coords_2[1]);
			/**
			 * 检测两个网格能否连通
			 */
			checkCell(cell_1_x, cell_1_y, cell_2_x, cell_2_y);
			
			//换算成迷宫的坐标
			int maze_cell_1_x = cell_1_x*2+1;
			int maze_cell_1_y = cell_1_y*2+1;
			int maze_cell_2_x = cell_2_x*2+1;
			int maze_cell_2_y = cell_2_y*2+1;
			
			/**
			 * 计算连通的网格的坐标
			 * 	如果它们的横坐标相同，则纵坐标为两项和除2，反之一样。
			 */
			if(maze_cell_1_x == maze_cell_2_x){ //横坐标相等
				int con_x = maze_cell_1_x;
				int con_y = (maze_cell_1_y + maze_cell_2_y)/2;
				
				maze[con_x][con_y] = "[R]";
				
			}else if(maze_cell_1_y == maze_cell_2_y){ //纵坐标相等
				int con_y = maze_cell_1_y;
				int con_x = (maze_cell_1_x + maze_cell_2_x)/2;
				
				maze[con_x][con_y] = "[R]";
			}
		}
	}
	

	/**
	 * 打印迷宫
	 * @param maze
	 */
	private void printMaze(){
		if(maze == null) throw new RuntimeException("请先创建元迷宫");
		
		for(String[] strs : maze){
			for(String str : strs){
				System.out.print(str);
			}
			System.out.println();
		}
	}
	
}
