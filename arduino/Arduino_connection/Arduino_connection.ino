void checkBothButtons() {
#define LEFTBUTTON 2
#define RIGHTBUTTON 3
#define POTMETER A0

long previous_time = millis();
int interval = 200;

int previousValue = 1;

void setup() {
  Serial.begin(9600);

  pinMode(LEFTBUTTON, INPUT);
  pinMode(RIGHTBUTTON, INPUT);
  pinMode(POTMETER, INPUT);
}

void loop() {
  checkRightButton();
  checkLeftButton();
  checkPotmeter();
  checkLdr();
  checkBothButtons();
}

void checkRightButton(){
  int rightState = digitalRead(RIGHTBUTTON);
  
  if (rightState == 0 && millis() - previous_time > interval){
    Serial.println("Right");
    delay(interval);
  }
}

void checkLeftButton(){
  int leftState = digitalRead(LEFTBUTTON);

  if (leftState == 0 && millis() - previous_time > interval){
    Serial.println("Left");
    delay(interval);
  }
}

void checkPotmeter(){
  int potmeterValue = map(analogRead(POTMETER), 0, 1022, 1, 4);
  if (millis() - previous_time > interval){
    if (potmeterValue > previousValue){
      Serial.println("Rotate right");
      previousValue = potmeterValue;
    } else if (potmeterValue < previousValue){
      Serial.println("Rotate left");
      previousValue = potmeterValue;
    }
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
