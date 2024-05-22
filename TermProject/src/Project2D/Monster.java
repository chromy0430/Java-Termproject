package Project2D; // 패키지 선언

import java.awt.Color; // Color 클래스를 사용하기 위한 import
import java.awt.Graphics2D; // Graphics2D 클래스를 사용하기 위한 import
import java.awt.image.BufferedImage; // BufferedImage 클래스를 사용하기 위한 import
import java.io.IOException; // IOException 클래스를 사용하기 위한 import
import javax.imageio.ImageIO; // ImageIO 클래스를 사용하기 위한 import

public class Monster extends Entity { // Monster 클래스 선언 및 Entity 클래스를 상속

	GamePanel gp; // 게임 패널 객체 선언
	Player pl; // 플레이어 객체 선언
	// 추적속도
	int Tspeed = 2; // 추적 속도

	// 스프라이트 관련 변수
	BufferedImage[] upFrames; // 위로 이동 이미지 배열
	BufferedImage[] downFrames; // 아래로 이동 이미지 배열
	BufferedImage[] leftFrames; // 왼쪽으로 이동 이미지 배열
	BufferedImage[] rightFrames; // 오른쪽으로 이동 이미지 배열
	int currentFrame = 0; // 현재 프레임
	int frameCount = 7; // 프레임 수
	int frameDelay = 10; // 프레임 지연 (애니메이션 속도 조절)
	int frameCounter = 0; // 프레임 카운터

	public Monster(GamePanel gp, Player pl, int x, int y) { // Monster 생성자

		this.gp = gp; // 게임 패널 초기화
		this.pl = pl; // 플레이어 초기화
		this.x = x; // x 위치 초기화 //05/22 1:45 수정
		this.y = y; // y 위치 초기화 //05/22 1:45 수정

		// 몬스터 이미지 로드
		loadImages(); // 이미지 로드 메소드 호출
	}

	private void loadImages() { // 이미지 로드 메소드
		try {
			upFrames = new BufferedImage[7]; // 위로 이동 이미지 배열 초기화
			downFrames = new BufferedImage[7]; // 아래로 이동 이미지 배열 초기화
			leftFrames = new BufferedImage[7]; // 왼쪽으로 이동 이미지 배열 초기화
			rightFrames = new BufferedImage[7]; // 오른쪽으로 이동 이미지 배열 초기화

			// 위로 걷기
			for (int i = 0; i < 7; i++) {
				upFrames[i] = ImageIO.read(getClass().getResourceAsStream("/player/w" + (i + 1) + ".png")); // 이미지 읽기
			}

			// 아래로 걷기
			for (int i = 0; i < 7; i++) {
				downFrames[i] = ImageIO.read(getClass().getResourceAsStream("/player/s" + (i + 1) + ".png")); // 이미지 읽기
			}

			// 왼쪽으로 걷기
			for (int i = 0; i < 7; i++) {
				leftFrames[i] = ImageIO.read(getClass().getResourceAsStream("/player/a" + (i + 1) + ".png")); // 이미지 읽기
			}

			// 오른쪽으로 걷기
			for (int i = 0; i < 7; i++) {
				rightFrames[i] = ImageIO.read(getClass().getResourceAsStream("/player/d" + (i + 1) + ".png")); // 이미지 읽기
			}
		} catch (IOException e) { // 예외 처리
			e.printStackTrace(); // 예외 스택 트레이스 출력
		}
	}

	public void update() { // 업데이트 메소드
		// 플레이어와 몬스터 간의 x, y 거리 계산
		// 플레이어와 몬스터 간의 거리 계산
        double distanceX = pl.x - this.x; //05/22 1:45 수정
        double distanceY = pl.y - this.y; //05/22 1:45 수정

        // 플레이어와 몬스터 간의 거리의 크기 계산
        double magnitude = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

     // 거리가 0이 아니면 단위 벡터 계산
	    if (magnitude != 0) {
	        distanceX /= magnitude;
	        distanceY /= magnitude;
	    }

        // 몬스터의 위치 업데이트
        x += distanceX * Tspeed;
        y += distanceY * Tspeed;

		// 애니메이션 업데이트
		frameCounter++; // 프레임 카운터 증가
		if (frameCounter >= frameDelay)

		{
			frameCounter = 0; // 프레임 카운터 초기화
			currentFrame = (currentFrame + 1) % frameCount; // 현재 프레임 업데이트
		}
	}

	//05/22 1:45 수정
	public void draw(Graphics2D g2) { // 그리기 메소드
		// 현재 프레임을 그림
		if (pl.y < y) { 
			g2.drawImage(upFrames[currentFrame], x, y, gp.tileSize, gp.tileSize, null); // 위로 이동 이미지 그리기
		} else if (pl.y > y) {
			g2.drawImage(downFrames[currentFrame], x, y, gp.tileSize, gp.tileSize, null); // 아래로 이동 이미지 그리기
		} else if (pl.x < x) {
			g2.drawImage(leftFrames[currentFrame], x, y, gp.tileSize, gp.tileSize, null); // 왼쪽으로 이동 이미지 그리기
		} else if (pl.x > x) {
			g2.drawImage(rightFrames[currentFrame], x, y, gp.tileSize, gp.tileSize, null); // 오른쪽으로 이동 이미지 그리기
		}
	}
}