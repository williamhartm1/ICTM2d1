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
  pinMode(LDR, INPUT);
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
  } else if (potmeterCurrValue < potmeterPrevValue){
    Serial.println("Rotate left");
  }
  
  delay(interval);
  potmeterPrevValue = potmeterCurrValue;
}

void checkLdr() {
  int ldrValue = analogRead(LDR);

  if (ldrValue < 150) {
    Serial.println("Pause");
  }
}

void checkBothButtons() {
  int leftState = digitalRead(LEFTBUTTON);
  int rightState = digitalRead(RIGHTBUTTON);

  if (leftState == 1 && rightState == 1 && millis() - previous_time > interval) {
    Serial.println("Both pressed");
    previous_time = millis();
  }
}

void checkStateGame() {
  if (Serial.available() > 0) {
    byte incomingByte = Serial.read();
    int NOTE1 = 2800;
    int delayOne = 120;
    int delayTwo = 70;

    // byte 1 = Start game
    if (incomingByte == 1) {
      Serial.println("Start game");
      
      for (int i = 0; i < 2; i++) {
        tone(BUZZER, NOTE1);
        delay(delayOne);
        noTone(BUZZER);
        delay(delayOne);
      }
      delay(150);
      for (int i = 3500; i < 3801; i+= 300) {
        tone(BUZZER, i);
        delay(delayTwo);
        noTone(BUZZER);
        delay(delayTwo);
      }
    } else if (incomingByte == 2) { // byte 2 = game over
      Serial.println("Game over");
      
      for (int i = 300; i > 99; i -= 100) {
        tone(BUZZER,i);
        delay(delayOne);
        noTone(BUZZER);
        delay(delayOne);
      }
    }
  }
}
