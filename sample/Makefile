compile:
	java -jar ../build/libs/lorikeet.jar -s src -b build

clean:
	rm -rf build

build:
	mkdir -p build/classes
	find ./build/java ./sdk -name '*.java' | xargs javac -d build/classes

.PHONY: build
