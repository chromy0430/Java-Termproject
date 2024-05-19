package Project2D;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Monster extends Entity {
	GamePanel gp; // 게임 패널
	Player pl; // 플레이어
	// 추적속도
	int Tspeed = 1; // 추적 속도

	// 스프라이트 관련 변수
	BufferedImage[] upFrames; // 위로 이동 이미지 배열
	BufferedImage[] downFrames; // 아래로 이동 이미지 배열
	BufferedImage[] leftFrames; // 왼쪽으로 이동 이미지 배열
	BufferedImage[] rightFrames; // 오른쪽으로 이동 이미지 배열
	int currentFrame = 0; // 현재 프레임
	int frameCount = 7; // 프레임 수
	int frameDelay = 10; // 프레임 지연 (애니메이션 속도 조절)s
	int frameCounter = 0; // 프레임 카운터

	public Monster(GamePanel gp, Player pl, int x, int y) {
		this.gp = gp; // 게임 패널 초기화
		this.pl = pl; // 플레이어 초기화
		this.x = x; // x 위치 초기화
		this.y = y; // y 위치 초기화

		// 몬스터 이미지 로드
		loadImages(); // 이미지 로드 메소드 호출
	}

	private void loadImages() {
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
		} catch (IOException e) {
			e.printStackTrace(); // 예외 출력
		}
	}

	public void update() {
		// 플레이어와 몬스터 간의 x, y 거리 계산
		double distanceX = pl.screenX - this.x; // x 거리 계산
		double distanceY = pl.screenY - this.y; // y 거리 계산

		// 거리의 크기 계산
		double magnitude = Math.sqrt(distanceX * distanceX + distanceY * distanceY); // 거리 크기 계산

		// 거리가 0이 아니면 단위 벡터 계산
		if (magnitude != 0) {
			distanceX /= magnitude; // x 거리 단위 벡터 계산
			distanceY /= magnitude; // y 거리 단위 벡터 계산
		}

		double adjustedSpeed = Tspeed; // 조정된 속도 초기화
		if (Math.abs(distanceX) > 0 && Math.abs(distanceY) > 0) {
			// 대각선 이동 시 속도 조정
			adjustedSpeed = Tspeed / Math.sqrt(2);
		}

		double moveX = distanceX / magnitude * adjustedSpeed;
		double moveY = distanceY / magnitude * adjustedSpeed;

		x += moveX;
		y += moveY;

		// 애니메이션 업데이트
		frameCounter++; // 프레임 카운터 증가
		if (frameCounter >= frameDelay) {
			frameCounter = 0; // 프레임 카운터 초기화
			currentFrame = (currentFrame + 1) % frameCount; // 현재 프레임 업데이트
		}
	}

	public void draw(Graphics2D g2) {
		// 현재 프레임을 그림
		if (pl.screenY < y) {
			g2.drawImage(upFrames[currentFrame], x, y, gp.tileSize, gp.tileSize, null); // 위로 이동 이미지 그리기
		} else if (pl.screenY > y) {
			g2.drawImage(downFrames[currentFrame], x, y, gp.tileSize, gp.tileSize, null); // 아래로 이동 이미지 그리기
		} else if (pl.screenX < x) {
			g2.drawImage(leftFrames[currentFrame], x, y, gp.tileSize, gp.tileSize, null); // 왼쪽으로 이동 이미지 그리기
		} else if (pl.screenX > x) {
			g2.drawImage(rightFrames[currentFrame], x, y, gp.tileSize, gp.tileSize, null); // 오른쪽으로 이동 이미지 그리기
		}
	}
}