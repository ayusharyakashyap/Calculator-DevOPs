# Use OpenJDK 11 as base image
FROM openjdk:11-jre-slim

# Set maintainer information
LABEL maintainer="Your Name <your.email@example.com>"
LABEL description="Scientific Calculator with DevOps Pipeline"
LABEL version="1.0.0"

# Create app directory
WORKDIR /app

# Copy the executable JAR file
COPY target/scientific-calculator-1.0.0.jar /app/scientific-calculator.jar

# Create logs directory for application logs
RUN mkdir -p /app/logs

# Set environment variables
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Expose port (not needed for command-line app, but good practice for future web version)
EXPOSE 8080

# Create a non-root user for security
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
RUN chown -R appuser:appgroup /app
USER appuser

# Health check (for container orchestration)
HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD echo "Container is healthy" || exit 1

# Default command to run the application
CMD ["java", "-jar", "/app/scientific-calculator.jar"]