#define LEFTBUTTON 2
#define RIGHTBUTTON 3
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
}

void loop() {
  checkRightButton();
  checkLeftButton();
  checkPotmeter();
  checkLdr();
}

void checkRightButton(){
  int rightState = digitalRead(RIGHTBUTTON);
  
  if (rightState == 1){
    Serial.println("Right");
    previous_time = millis();
  }
}

void checkLeftButton(){
  int leftState = digitalRead(LEFTBUTTON);

  if (leftState == 1){
    Serial.println("Left");
    previous_time = millis();
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
