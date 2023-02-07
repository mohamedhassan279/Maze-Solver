import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class MazeSolver implements IMazeSolver {
	public static char[][] grid;
	public static int rows;
	public static int cols;
	public static boolean Error = false;
	public static Point s;
	public static Point e;
	public static int lengthBFS;
	public static int lengthDFS;
	public static int lengthBestFS;
	
	public void gridGenerator(java.io.File maze) {
        int counter3 = 0;
        int counter2 = 0;
        Scanner scan, ErrorScan;
        try {
            scan = new Scanner(maze);
            ErrorScan = new Scanner(maze);

        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found..");
            return;
        }
        if(maze.length() == 0) {
            System.out.println("File is Empty..");
            scan.close();
            return;
        }
        String[] dimString = scan.nextLine().split(" ");
        try {
        	rows = Integer.parseInt(dimString[0]);
            cols = Integer.parseInt(dimString[1]);
        } catch (NumberFormatException nfe){
        	Error = true;
        	return;
        }
        grid = new char[rows][cols];
        while(ErrorScan.hasNext()) {
            ErrorScan.nextLine();
            counter2++;
        }
        if(counter2 != rows+1) {
            Error= true;

            return;
        }
        ErrorScan.close();

        int counter = 0;
        while(scan.hasNext()) {
            String currentLine = scan.nextLine();
            if(currentLine.length() != cols) {
                Error = true;
                break;

            }
            for(int i = 0 ; i < currentLine.length(); i++) {
                if (currentLine.charAt(i) == 'S') {
                    counter3 ++;
                    s = new Point(counter, i);
                }
                if (currentLine.charAt(i) == 'E') {
                    counter3++;
                    e = new Point(counter, i);
                }
                if(currentLine.charAt(i) != 'E' && currentLine.charAt(i) != 'S' && currentLine.charAt(i) != '#' && currentLine.charAt(i) != '.') {
                    Error = true;
                    break;
 
                }
                grid[counter][i] = currentLine.charAt(i);
            }
            counter++;
        }
        scan.close();

        if(counter3 != 2) {
            Error = true;
        }

    }
	
	public int[][] getNeighbors(Point node, boolean[][] visited){
        int[][] neighbours = new int[5][2];
        int count = 0;

        if (node.x != rows - 1) {
            if (!Objects.equals(grid[node.x + 1][node.y], '#') && visited[node.x + 1][node.y] == false) {
                neighbours[count][0] = node.x + 1;
                neighbours[count][1] = node.y;
                count++;
            }
        }
        if (node.y != 0) {
            if (!Objects.equals(grid[node.x][node.y - 1], '#') && visited[node.x][node.y - 1] == false) {
                neighbours[count][0] = node.x;
                neighbours[count][1] = node.y - 1;
                count++;
            }
        }
        if (node.x != 0) {
            if (!Objects.equals(grid[node.x - 1][node.y], '#') && visited[node.x - 1][node.y] == false) {
                neighbours[count][0] = node.x - 1;
                neighbours[count][1] = node.y;
                count++;
            }
        }

        if (node.y != cols - 1) {
            if (!Objects.equals(grid[node.x][node.y + 1], '#') && visited[node.x][node.y + 1] == false) {
                neighbours[count][0] = node.x;
                neighbours[count][1] = node.y + 1;
                count++;
            }
        }
        neighbours[4][0] = count;
        return neighbours;

    }
	
	public int[][] solveBFS (java.io.File maze){
		boolean flag = false;
		int counter = 0;
		Queue q = new Queue();
		boolean[][] visited = new boolean[rows][cols];
		Point[][] prev = new Point[rows][cols];
		q.enqueue(s);
		visited[s.x][s.y] = true;
		while (!q.isEmpty() && !flag){
			Point Node = (Point) q.dequeue();
			int[][] neighbours = getNeighbors(Node, visited);
			for(int i = 0; i < neighbours[4][0]; i++) {
				Point Next = new Point(neighbours[i][0], neighbours[i][1]);
				q.enqueue(Next);
				visited[Next.x][Next.y] = true;
				prev[Next.x][Next.y] = Node;
				counter++;
				if(Next == e) {
					flag = true;
					break;
				}
			}
		}

		int[][] path = new int[counter][2];
		Point i = e;
		int j = 0;
		while(i != null) {
			path[j][0] = i.x;
		    path[j++][1] = i.y;
		    grid[i.x][i.y] = 'T';
		    i = prev[i.x][i.y];
		    lengthBFS++;
		}
		if(path[lengthBFS - 1][0] == s.x && path[lengthBFS - 1][1] == s.y) {
			return path;
		}
		else {
			return null;
		}
	}

	public int[][] solveDFS(java.io.File maze){
		Stack stack = new Stack();
		boolean flag = false;
		int counter = 0;
		boolean[][] visited = new boolean[rows][cols];
		Point[][] prev = new Point[rows][cols];
		stack.push(s);
		visited[s.x][s.y] = true;
		
		while (!stack.isEmpty() && !flag){
			Point Node = (Point) stack.peek();
			int[][] neighbours = getNeighbors(Node, visited);
			int i = 0;
			while(neighbours[4][0] > i) {
				Point Next = new Point(neighbours[i][0], neighbours[i][1]);
				if(!visited[Next.x][Next.y]) {
					stack.push(Next);
					visited[Next.x][Next.y] = true;
					prev[Next.x][Next.y] = Node;
					counter++;
					if(Next == e) {
						flag = true;
						break;
					}
					break;
				}
				else {
					i++;
				}
			}
			if(neighbours[4][0] <= 0) {
				stack.pop();
			}
		}
		int[][] path = new int[counter + 1][2];
		Point i = e;
		int j = 0;
		while(i != null) {
			path[j][0] = i.x;
		    path[j++][1] = i.y;
		    grid[i.x][i.y] = 'T';
		    i = prev[i.x][i.y];
		    lengthDFS++;
		}
		if(path[lengthDFS - 1][0] == s.x && path[lengthDFS - 1][1] == s.y) {
			return path;
		}
		else {
			return null;
		}
	}
	
	public int heuristic(Point node) {
		return Math.abs(node.x - e.x) + Math.abs(node.y - e.y);
	}
	
	public int steps(Point node, Point[][] prev) {
		Point i = node;
		int counter = 0;
		while(i != null) {
		    i = prev[i.x][i.y];
		    counter++;
		}
		return counter;
	}
	
	public int[][] solveBestFS(java.io.File maze){
		PQueue q = new PQueue();
		q.insert(s, heuristic(s));
		boolean[][] visited = new boolean[rows][cols];
		Point[][] prev = new Point[rows][cols];
		boolean flag = false;
		int counter = 0;
		visited[s.x][s.y] = true;
		while (!q.isEmpty() && !flag){
			Point Node = (Point) q.removeMin();
			int[][] neighbours = getNeighbors(Node, visited);
			for(int i = 0; i < neighbours[4][0]; i++) {
				Point Next = new Point(neighbours[i][0], neighbours[i][1]);
				counter++;
				prev[Next.x][Next.y] = Node;
				q.insert(Next, heuristic(Next) + steps(Next, prev));
				visited[Next.x][Next.y] = true;
				if(Next == e) {
					flag = true;
					break;
				}
			}
		}

		int[][] path = new int[counter][2];
		Point i = e;
		int j = 0;
		while(i != null) {
			path[j][0] = i.x;
		    path[j++][1] = i.y;
		    grid[i.x][i.y] = 'T';
		    i = prev[i.x][i.y];
		    lengthBestFS++;
		}
		if(path[lengthBestFS - 1][0] == s.x && path[lengthBestFS - 1][1] == s.y) {
			return path;
		}
		else {
			return null;
		}
	}
	
	public static void main(String[] args) {
		MazeSolver solver = new MazeSolver();
		File maze = new File("map.txt");
		solver.gridGenerator(maze);

		if(Error) {
			System.out.println("The File Contains Wrong Data!!!");
			return;
		}
		
		if (grid == null) {
			return;
		}
		
		System.out.println("Enter the searching type you want( BFS/ DFS/ BestFS ): ");
		Scanner sc = new Scanner(System.in);
		String keyword = sc.nextLine();

		if (Objects.equals(keyword, "BFS")) {

			int[][] pathBFS = solver.solveBFS(maze);
			if (pathBFS == null) {
				System.out.println("There is no possible path!!!");
				return;
			} else {
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[i].length; j++) {
						System.out.print(grid[i][j]);
					}
					System.out.println();
				}
				System.out.println();
				System.out.print("BFS:");

				for (int i = lengthBFS - 1; i >= 0; i--) {
					System.out.print("{" + pathBFS[i][0] + ", " + pathBFS[i][1] + "}");
					if (i != 0) {
						System.out.print(", ");
					}
				}
			}
		} else if (Objects.equals(keyword, "DFS")) {
			int[][] pathDFS = solver.solveDFS(maze);
			if (pathDFS == null) {
				System.out.println("There is no possible path!!!");
				return;
			} else {

				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[i].length; j++) {
						System.out.print(grid[i][j]);
					}
					System.out.println();
				}
				System.out.print("DFS:");
				for (int i = lengthDFS - 1; i >= 0; i--) {
					System.out.print("{" + pathDFS[i][0] + ", " + pathDFS[i][1] + "}");
					if (i != 0) {
						System.out.print(", ");
					}
				}
			}
		} else if (Objects.equals(keyword, "BestFS")) {

			int[][] pathBestFS = solver.solveBestFS(maze);
			if (pathBestFS == null) {
				System.out.println("There is no possible path!!!");
				return;
			} else {
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[i].length; j++) {
						System.out.print(grid[i][j]);
					}
					System.out.println();
				}
				System.out.print("Best-FS:");
				for (int i = lengthBestFS - 1; i >= 0; i--) {
					System.out.print("{" + pathBestFS[i][0] + ", " + pathBestFS[i][1] + "}");
					if (i != 0) {
						System.out.print(", ");
					}
				}
			}
		} else {
			System.out.println("Error");
		}
	}
}