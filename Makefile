# Define variables
GRADLE=./gradlew

# Default target
.PHONY: all
all: clean build

# Clean the project
.PHONY: clean
clean:
	$(GRADLE) clean

# Build the project
.PHONY: build
build:
	$(GRADLE) build

# Run the project
.PHONY: run
run:
	$(GRADLE) run

# Run tests
.PHONY: test
test:
	$(GRADLE) test

# Generate a JAR file
.PHONY: jar
jar:
	$(GRADLE) jar
