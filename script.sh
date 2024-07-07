#!/bin/bash

# Set the path to the build.gradle.kts file
BUILD_GRADLE_KTS_FILE="app/build.gradle.kts"

# Check if the build.gradle.kts file exists
if [ ! -f "$BUILD_GRADLE_KTS_FILE" ]; then
    echo "build.gradle.kts not found in $BUILD_GRADLE_KTS_FILE"
    exit 1
fi

# Extract the package name using grep and awk
PACKAGE_NAME=$(grep -E "^\s*namespace\s*=\s*[\"']([^\"']+)[\"']" "$BUILD_GRADLE_KTS_FILE" | awk -F'[\"'\'']' '{print $2}')

# Check if the package name was found
if [ -z "$PACKAGE_NAME" ]; then
    echo "Package name not found in build.gradle.kts"
    exit 1
fi

# Display the package name
echo "Package Name: $PACKAGE_NAME"

