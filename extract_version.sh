#!/bin/bash

# Check if the JSON file path and name are provided as arguments
if [ $# -ne 2 ]; then
  echo "Usage: $0 <json_file_path> <name>"
  exit 1
fi

json_file="$1"      # JSON file path
name_to_find="$2"   # Name to search for

# Check if the JSON file exists
if [ ! -f "$json_file" ]; then
  echo "Error: JSON file '$json_file' not found."
  exit 1
fi

# Read JSON file content into a variable
json=$(<"$json_file")

# Function to extract version by name
extract_version() {
  local name="$1"
  local version=""

  # Remove whitespace characters (optional)
  json=$(echo "$json" | tr -d '[:space:]')

  # Extract "android" array using pattern matching
  android_array=$(echo "$json" | grep -o '"android":[^]]*]')

  # Check if "android" array exists
  if [ -n "$android_array" ]; then
    # Loop through each item in the "android" array
    while IFS= read -r line; do
      # Find lines containing "name" and "version"
      name_match=$(grep -o "\"name\":\"$name\"" <<< "$line")
      if [ -n "$name_match" ]; then
        version=$(grep -o "\"version\":\"[^\"]*\"" <<< "$line" | cut -d '"' -f 4)
        break
      fi
    done <<< "$(echo "$android_array" | grep -o '{[^}]*}')"
  fi

  echo "$version"
}

# Call the function to extract the version based on the provided name
extracted_version=$(extract_version "$name_to_find")

# Print the extracted version
if [ -n "$extracted_version" ]; then
  echo "Version of '$name_to_find': $extracted_version"
else
  echo "Error: Version for '$name_to_find' not found in JSON."
fi
