#define LEFTBUTTON 2
#define RIGHTBUTTON 3
#define BUZZER 4
#define POTMETER A0
#define LDR A1

long previous_time = millis();
int interval = 200;
static int potmeterPrevValue = map(analogRead(POTMETER), 0, 1023, 1, 4);

void setup() {
  Serial.begin(9600);

  pinMode(LEFTBUTTON, INPUT);
  pinMode(RIGHTBUTTON, INPUT);
  pinMode(POTMETER, INPUT);
  pinMode(BUZZER, OUTPUT);
}

void loop() {
  checkRightButton();
  checkLeftButton();
  checkPotmeter();
  checkLdr();
  checkBothButtons();
  checkStateGame();
}

void checkRightButton(){
  int rightState = digitalRead(RIGHTBUTTON);
  
  if (rightState == 1){
    Serial.println("Right");
    delay(interval);
  }
}

void checkLeftButton(){
  int leftState = digitalRead(LEFTBUTTON);

  if (leftState == 1){
    Serial.println("Left");
    delay(interval);
  }
}

void checkPotmeter(){
  int potmeterCurrValue = map(analogRead(POTMETER), 0, 1023, 1, 5);
  
  if (potmeterCurrValue > potmeterPrevValue){
    Serial.println("Rotate right");
    delay(interval);
  } else if (potmeterCurrValue < potmeterPrevValue){
    Serial.println("Rotate left");
    delay(interval);
  }

  potmeterPrevValue = potmeterCurrValue;
}

void checkLdr() {
  int ldrValue = analogRead(LDR);

  while (ldrValue < 150) {
    ldrValue = analogRead(LDR);
    Serial.println("Pause");
  }
}

void checkBothButtons() {
  int leftState = digitalRead(LEFTBUTTON);
  int rightState = digitalRead(RIGHTBUTTON);

  while (leftState == 1 && rightState == 1 && millis() - previous_time > interval) {
    leftState = digitalRead(LEFTBUTTON);
    rightState = digitalRead(RIGHTBUTTON);
    Serial.println("Both pressed");
    previous_time = millis();
  }
}

void checkStateGame() {
  if (Serial.available() > 0) {
    byte incomingByte = Serial.read();

    // byte 1 = Start game
    if (incomingByte == 1) {
      Serial.println("Start game");
      
      for (int i = 0; i < 2; i++) {
        tone(BUZZER, 200);
        delay(200);
      }
    } else if (incomingByte == 2) { // byte 2 = game over
      Serial.println("Game over");
      
      for (int i = 0; i < 2; i++) {
        tone(BUZZER, 800);
        delay(200);
      }
    }
  }
}
