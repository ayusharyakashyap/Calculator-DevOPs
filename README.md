# Scientific Calculator with DevOps Pipeline

A comprehensive scientific calculator application demonstrating complete DevOps implementation including CI/CD, containerization, and automated deployment.

<!-- Email Test: September 29, 2025 - Testing email notifications after SMTP configuration -->

## Features

The calculator supports the following mathematical operations:
- **Square Root (√x)**: Calculate square root of positive numbers
- **Factorial (!x)**: Calculate factorial of non-negative integers
- **Natural Logarithm (ln(x))**: Calculate natural logarithm of positive numbers  
- **Power (x^b)**: Calculate power of any number

## Technology Stack

- **Language**: Java 11
- **Build Tool**: Maven 3.9.x
- **Testing**: JUnit 5
- **Logging**: Log4j2
- **Containerization**: Docker
- **CI/CD**: Jenkins
- **Configuration Management**: Ansible
- **Version Control**: Git/GitHub

## Project Structure

```
scientific-calculator/
├── src/
│   ├── main/
│   │   ├── java/com/calculator/
│   │   │   ├── ScientificCalculator.java
│   │   │   └── ScientificCalculatorApp.java
│   │   └── resources/
│   │       └── log4j2.xml
│   └── test/
│       └── java/com/calculator/
│           └── ScientificCalculatorTest.java
├── pom.xml
├── Dockerfile
├── Jenkinsfile
├── ansible/
│   └── playbook.yml
└── README.md
```

## Building and Running

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Docker (for containerization)

### Build
```bash
mvn clean compile
```

### Test
```bash
mvn test
```

### Package
```bash
mvn package
```

### Run
```bash
java -jar target/scientific-calculator-1.0.0.jar
```

## DevOps Pipeline

### 1. Source Control Management
- **Tool**: GitHub
- **Repository**: [Your GitHub Repo URL]
- **Branching Strategy**: Feature branches with main branch protection

### 2. Testing
- **Framework**: JUnit 5
- **Coverage**: 16 comprehensive test cases covering all functions
- **Automation**: Tests run automatically in CI/CD pipeline

### 3. Build
- **Tool**: Maven
- **Output**: Executable JAR with all dependencies
- **Automation**: Automated builds on every commit

### 4. Continuous Integration
- **Tool**: Jenkins
- **Pipeline**: Automated build, test, and Docker image creation
- **Triggers**: Git webhooks for automatic builds

### 5. Containerization
- **Tool**: Docker
- **Base Image**: OpenJDK 11
- **Registry**: Docker Hub

### 6. Configuration Management & Deployment
- **Tool**: Ansible
- **Target**: Local machine deployment
- **Process**: Pull Docker image and run container

## Usage Examples

```
=================================
  SCIENTIFIC CALCULATOR
=================================
Welcome to the Scientific Calculator!
This calculator supports the following operations:

Choose an operation:
1. Square Root (√x)
2. Factorial (!x)
3. Natural Logarithm (ln(x))
4. Power (x^b)
5. Exit

Enter your choice (1-5): 1
Enter a number for square root: 25
√25.0 = 5.0
```

## Author

**Your Name**  
**Roll Number**: IMT2022xxx  
**Course**: Software Production Engineering  
**Institution**: IIIT Bangalore

## License

This project is created for educational purposes as part of the Software Production Engineering course.