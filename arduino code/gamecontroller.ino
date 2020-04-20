#define debug false
#define button1 2
int button1State = 0;
#define button2 3
int button2State = 0;
#define potmeter A0
int potmeterValue = 0;
int potmeterNewValue = 0;

void setup() {
  Serial.begin(9600);
  pinMode(button1, INPUT);
  pinMode(button2, INPUT);
  pinMode(potmeter, INPUT);
}

void loop() {
  button1State = digitalRead(button1); // button left
  button2State = digitalRead(button2); // button right

  if(button1State == HIGH) {
    Serial.println("Left:");
    delay(200);
  } else if (button2State == HIGH) {
    Serial.println("Right:");
    delay(200);
  }
  
  static int potmeterPrevValue = 0;
  
  potmeterValue = analogRead(potmeter); // potmeter
  potmeterNewValue = map(potmeterValue, 0, 1023, 1, 4); // 4 is het aantal mogelijke rotaties

  // checkt of potmeter roteert
  if(potmeterPrevValue > potmeterNewValue) {
    Serial.println("Rotate to left:");
    delay(200);
  } else if(potmeterPrevValue < potmeterNewValue) {
    Serial.println("Rotate to right:");
    delay(200);
  }
  
  potmeterPrevValue = potmeterNewValue;

  

  if(debug) { // debug d.m.v. serial prints
    // button 1
    Serial.print("Button 1: ");
    Serial.println(button1State);
    // button 2
    Serial.print("Button 2: ");
    Serial.println(button2State);
    // potmeter
    Serial.print("Potmeter: ");
    Serial.println(potmeterValue);
    // potmeter new value
    Serial.print("New potmeter value: ");
    Serial.println(potmeterNewValue);
  }
}
