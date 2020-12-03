JCC = javac
JV = java
JFLAGS = -cp

default: GUI.java
	$(JCC) $(JFLAGS) '.:./commons-io-2.8.0.jar' GUI.java

execute:
        $(JV) $(JFLAGS) '.:./commons-io-2.8.0.jar' GUI ./dist/ditaa.jar

delete:
	rm GUI.class
	rm 'GUI$1.class'
