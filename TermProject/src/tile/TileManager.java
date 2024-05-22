package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Project2D.GamePanel;

public class TileManager {

	GamePanel gp; // GamePanel 객체를 저장할 변수
	Tile[] tile; // 타일 배열
	int mapTileNum[][]; // 맵 타일 번호 배열
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp; // GamePanel 객체를 초기화
		
		tile = new Tile[10]; // 10개의 타일 객체 배열을 초기화
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow]; // 맵 타일 번호 배열을 초기화
		
		getTileImage(); // 타일 이미지를 로드하는 메소드 호출
		loadMap("/maps/world01.txt"); // 맵 데이터를 로드하는 메소드 호출
	}
	
	public void getTileImage() {
		
		try {
		
			tile[0] = new Tile(); // 새로운 타일 객체 생성
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")); // 이미지 로드
			
			tile[1] = new Tile(); // 새로운 타일 객체 생성
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png")); // 이미지 로드
			
			tile[2] = new Tile(); // 새로운 타일 객체 생성
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png")); // 이미지 로드
			
			tile[3] = new Tile(); // 새로운 타일 객체 생성
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png")); // 이미지 로드
			
			tile[4] = new Tile(); // 새로운 타일 객체 생성
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png")); // 이미지 로드
			
			tile[5] = new Tile(); // 새로운 타일 객체 생성
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png")); // 이미지 로드
			
		} catch (IOException e) {
			e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
		}
		
	}
	
	public void loadMap(String filePath) {
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath); // 맵 파일의 InputStream을 얻음
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); // BufferedReader로 감쌈
			
			int col = 0; // 열을 나타내는 변수 초기화
			int row = 0; // 행을 나타내는 변수 초기화
			
			while (col < gp.maxScreenCol && row < gp.maxScreenRow) { // 모든 행과 열을 순회
				
				String line = br.readLine(); // 한 줄 읽음
				
				while(col < gp.maxScreenCol) { // 한 행의 모든 열을 순회
					
					String numbers[] = line.split(" "); // 공백으로 분리하여 숫자 배열로 변환
					
					int num = Integer.parseInt(numbers[col]); // 문자열을 정수로 변환
					
					mapTileNum[col][row] = num; // 맵 타일 번호 배열에 저장
					col++;
				}
				if(col == gp.maxScreenCol) { // 열이 최대 열 수에 도달하면
					col = 0; // 열을 0으로 초기화
					row++; // 행을 1 증가
				}
			}
			br.close(); // BufferedReader를 닫음
			
		} catch (Exception e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
//		int worldCol = 0; // 월드 열 초기화
//		int worldRow = 0; // 월드 행 초기화
		
		while(col < gp.maxScreenCol && row < gp.maxScreenRow) { // 모든 행과 열을 순회
			
			int tileNum = mapTileNum[col][row]; // 현재 위치의 타일 번호를 가져옴
			
//			int worldX = worldCol * gp.tileSize; // 월드 X 좌표 계산
//			int worldY = worldRow * gp.tileSize; // 월드 Y 좌표 계산
//			int screenX = worldX - gp.player.x + gp.player.screenX; // 스크린 X 좌표 계산
//			int screenY = worldY - gp.player.y + gp.player.screenY; // 스크린 Y 좌표 계산
			
			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null); // 타일 이미지를 그리기
			col++; // 열을 1 증가
			x += gp.tileSize;
			
			if(col == gp.maxScreenCol) { // 열이 최대 열 수에 도달하면
				col = 0; // 열을 0으로 초기화
				x = 0;
				row++;
				y += gp.tileSize;
			}
			
		}
		
	}
	
}
