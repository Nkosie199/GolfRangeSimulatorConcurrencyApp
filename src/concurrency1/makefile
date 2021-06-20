JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

java.class:
	$(JC) $(JFLAGS) *.java

CLASSES = DrivingRangeApp.java \
	Golfer.java \
	Bollie.java \
	Range.java \
	BallStash.java \
	golfBall.java
	
default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
